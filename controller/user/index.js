const { wxConfig, wxSql } = require("../../config/config");
const { httpFn } = require("../../utils/http");
const { newError, newSuccess } = require("../../utils/message");
const { verification } = require("../../utils/verification");
const { wxHttp } = require("../../wxService");

class getUser extends verification {
  constructor() {
    super();
  }

  // 提交意见反馈
  feedback(ctx) {
    return new Promise(async (R) => {
      try {
        let params = ctx.request.body;

        if (!params.type) {
          R(new newError({ msg: "请选择反馈问题类型" }));
          return;
        }
        if (!params.content) {
          R(new newError({ msg: "请输入反馈问题内容" }));
          return;
        }

        params._updateTime = new Date();
        params._createTime = new Date();

        let wxService = await new wxHttp().add(
          {
            env: wxConfig.env,
            query: `db.collection("feedback").add({ data: ${JSON.stringify(
              params
            )} })`,
          },
          "post"
        );

        if (wxService.errcode == 0) {
          R(new newSuccess({ msg: "反馈问题成功", params: wxService }));
        } else {
          R(new newError({ msg: "反馈问题失败", params: wxService }));
        }
      } catch (error) {
        R(new newError({ msg: error }));
      }
    });
  }

  // 获取手机号
  getPhoneNumber(ctx) {
    return new Promise(async (R) => {
      try {
        let { code } = ctx.request.body;
        if (!code) {
          R(new newError({ msg: "请传入登录code" }));
          return;
        }
        let access_token = await this.getWxAccessToken();

        let httpRes = await new httpFn().post(
          `${wxSql.getPhoneNumber}?access_token=${access_token}`,
          { code }
        );
        if (httpRes.body.errcode == 0) {
          R(new newSuccess({ msg: "获取成功", params: httpRes.body }));
        } else {
          R(new newError({ msg: "获取失败", params: httpRes.body }));
        }
      } catch (error) {
        R(new newError({ msg: error }));
      }
    });
  }

  // 获取用户openId
  getOpenId(ctx) {
    return new Promise(async (R) => {
      try {
        let { code } = ctx.request.body;
        if (!code) {
          R(new newError({ msg: "请传code参数" }));
          return;
        }

        let wxHttpRes = await new httpFn().get(`${wxSql.code2Session}`, {
          appid: wxConfig.appId,
          secret: wxConfig.appSecret,
          js_code: code,
          grant_type: "authorization_code",
        });

        if (wxHttpRes.openid) {
          R(new newSuccess({ params: wxHttpRes }));
        } else {
          R(new newError({ params: wxHttpRes.errmsg }));
        }
      } catch (error) {
        R(new newError({ msg: error.message }));
      }
    });
  }

  // 获取用户信息
  getUserInfo(ctx) {
    return new Promise(async (R) => {
      try {
        let { openId } = ctx.request.body;
        if (!openId) {
          R(new newError({ msg: "请传入用户openId" }));
          return;
        }

        let httpRes = await new wxHttp().check(
          {
            env: wxConfig.env,
            query: `db.collection("user_list").where({ openId: '${openId}' }).get()`,
          },
          "post"
        );

        let subscribeRes = await new wxHttp().check(
          {
            env: wxConfig.env,
            query: `db.collection("activitySubscribe").where({ openId: '${openId}' }).get()`,
          },
          "post"
        ); 

        let isActivity = false
        if(subscribeRes.data.length > 0){
          isActivity = true
        }

        if (httpRes.errcode == 0) {
          if (httpRes.data.length == 0) {
            R(new newError({ msg: "暂无该用户信息" }));
            return;
          }
          let user = JSON.parse(httpRes.data[0]);
          user.isActivity = isActivity
          R(new newSuccess({ params: user }));
        } else {
          R(new newError({ params: httpRes }));
        }
      } catch (error) {
        R(new newError({ params: error }));
      }
    });
  }

  // 更新用户信息
  setUserInfo(ctx) {
    return new Promise(async (R) => {
      try {
        let { avatarUrl, nickName, openId } = ctx.request.body;
        if (!avatarUrl) {
          R(new newError({ msg: "请上传用户头像" }));
          return;
        }
        if (!nickName) {
          R(new newError({ msg: "请输入用户昵称" }));
          return;
        }
        if (!openId) {
          R(new newError({ msg: "请传入用户openId" }));
          return;
        }

        // 获取用户信息
        let httpRes = await new wxHttp().check(
          {
            env: wxConfig.env,
            query: `db.collection("user_list").where({ openId: '${openId}' }).get()`,
          },
          "post"
        );

        if (httpRes.errcode == 0) {
          let wxRes = null;
          if (httpRes.data.length == 0) {
            //   无用户信息，注册
            wxRes = await new wxHttp().add({
              env: wxConfig.env,
              query: `db.collection("user_list").add({ data: ${JSON.stringify({
                avatarUrl,
                nickName,
                openId,
                _createTime: new Date(),
                _updateTime: new Date(),
              })} })`,
            }, 'post');
          } else {
            // 有用户信息，更新
            wxRes = await new wxHttp().update({
              env: wxConfig.env,
              query: `db.collection("user_list").where({ openId: '${openId}' }).update({ data: ${JSON.stringify(
                {
                  avatarUrl,
                  nickName,
                  openId,
                  _updateTime: new Date(),
                }
              )} })`,
            });
          }

          if(wxRes.errcode == 0){
            R(new newSuccess({ msg: httpRes.data.length == 0 ? '注册成功' : '更新成功', params: wxRes }))
          }else {
            R(new newError({ params: wxRes }))
          }
        } else {
          R(new newError({ params: httpRes }));
        }
      } catch (error) {
        R(new newError({ params: error.message }));
      }
    });
  }
}

module.exports = {
  getUser,
};
