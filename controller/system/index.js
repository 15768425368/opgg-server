const { wxConfig } = require("../../config/config");
const { newError, newSuccess } = require("../../utils/message");
const { verification } = require("../../utils/verification");
const { wxHttp } = require("../../wxService");

class getSystem extends verification {
  constructor() {
    super();
  }

  config(ctx) {
    return new Promise(async (R) => {
      try {
        let wxRes = await new wxHttp().check(
          {
            env: wxConfig.env,
            query: 'db.collection("system_config").limit(100).get()',
          },
          "post"
        );

        if (wxRes.errcode == 0) {
          let newArray = wxRes.data.map((item) => {
            return JSON.parse(item);
          });
          R(new newSuccess({ params: newArray[0] }));
        } else {
          R(new newError({ params: wxRes }));
        }
      } catch (error) {
        R(new newError({ params: error }));
      }
    });
  }

  activitySubscribe(ctx) {
    return new Promise(async (R) => {
      try {
        let { openId } = ctx.request.body;
        if (!openId) {
          R(new newError({ msg: "请传入openId" }));
          return;
        }

        let wxhtRes = await new wxHttp().add({
          env: wxConfig.env,
          query: `db.collection("activitySubscribe").add({ data: ${JSON.stringify({
            openId: openId,
            _createTime: new Date(),
            _updateTime: new Date(),
          })} })`,

        }, 'post');
        if(wxhtRes.errcode == 0){
            R(new newSuccess({ msg: '预约成功', params: wxhtRes }))
        }else {
            R(new newError({ msg: '预约失败', params: wxhtRes }))
        }
      } catch (error) {
        R(new newError({ msg: error }));
      }
    });
  }

  activityUserState(ctx){
    return new Promise(async R => {
      try {
        let {openId} = ctx.query;
        if(!openId){
          R(new newError({ msg: '请传入openId' }))
          return
        }

        let wxRes = await new wxHttp().check({
          env: wxConfig.env,
          query: `db.collection("activitySubscribe").where({ openId: '${openId}' }).get()`
        }, 'post');
        if(wxRes.errcode == 0){
          R(new newSuccess({ params: {
            isClick: wxRes.data.length > 0 ? true : false
          } }))
        }else {
          R(new newError({ msg: '系统错误' }))
        }
      } catch (error) {
        R(new newError({ params: error }))
      }
    })
  }
}

module.exports = {
  getSystem,
};
