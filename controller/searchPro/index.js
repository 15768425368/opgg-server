const { wxConfig } = require("../../config/config");
const { httpFn } = require("../../utils/http");
const { newError, newSuccess } = require("../../utils/message");
const { getWebFun } = require("../../utils/puppeteer");
const { verification } = require("../../utils/verification");
const { wxHttp } = require("../../wxService");

class opSearchPro extends verification {
  constructor() {
    super();
  }

  /**
   * 获取用户可搜索次数
   */
  getNumber(ctx) {
    return new Promise(async (R) => {
      try {
        let { openId } = ctx.query;
        if (!openId) {
          R(new newError({ msg: "请传入openId" }));
          return;
        }

        let wxRes = await new wxHttp().check(
          {
            env: wxConfig.env,
            query: `db.collection("search_num").where({ openId: '${openId}' }).get()`,
          },
          "post"
        );

        if (wxRes.errcode == 0) {
            if(wxRes.data.length > 0){
                // 如果有用户信息，返回用户剩余搜索次数
                R(new newSuccess({ params: {
                    num: JSON.parse(wxRes.data[0]).num
                } }))
            }else {
                // 如果没有用户信息，返回默认1次搜索次数
                let addWx = await new wxHttp().add({
                    env: wxConfig.env,
                    query: `db.collection("search_num").add({ data: ${JSON.stringify({
                        openId: openId,
                        num: 1,
                        _createTime: new Date().getTime(),
                        _updateTime: new Date().getTime()
                    })} })`
                }, 'post')
                if(addWx.errcode == 0) {
                    R(new newSuccess({
                        params: {
                            num: 1
                        }
                    }))
                }else{
                    R(new newError({ params: addWx }));
                }
            }
        } else {
          R(new newError({ params: wxRes }));
        }
      } catch (error) {
        R(new newError({ params: error }));
      }
    });
  }

  /**
   * 绑定分享者获取搜索次数
   */
  binding(ctx){
    return new Promise(async R => {
      try {
        let { superior_id, openId } = ctx.request.body;
        if(!superior_id){
          R(new newError({ msg: '请传入上级openId' }))
          return
        }
        if(!openId){
          R(new newError({ msg: '请传入被分享人openId' }))
        }

        let wxRes = await new wxHttp().check({
          env: wxConfig.env,
          query: `db.collection("share_log").where({ openId: '${openId}', superior_id: '${superior_id}' }).get()`
        }, 'post')

        if(wxRes.errcode == 0){
          if(wxRes.data.length > 0){
            // 已经绑定过了
            R(new newSuccess({ msg: '已经绑定过分享者' }))
            return
          }else {
            // 没有绑定过
            let bindingRes = await new wxHttp().add({
              env: wxConfig.env,
              query: `db.collection("share_log").add({
                data: ${JSON.stringify({
                  openId,
                  superior_id,
                  _createTime: new Date().getTime(),
                  _updateTime: new Date().getTime()
                })}
              })`
            }, 'post')
            if(bindingRes.errcode == 0){
              await this.addSearchNum(superior_id, 'add', 1)
              R(new newSuccess({ msg: '绑定成功' }))
            }else {
              R(new newError({ params: bindingRes }))
            }
          }
        }else {
          throw new Error("pass")
        }
      } catch (error) {
        R(new newError({ params: error }))
      }
    })
  }

  /**
   * 操作用户次数
   */
  addSearchNum(openId, tag, num){
    return new Promise(async R => {
      let user = await new wxHttp().check({
        env: wxConfig.env,
        query: `db.collection("search_num").where({ openId: '${openId}' }).get()`
      }, 'post')
      if(user.errcode == 0){
        let userData = JSON.parse(user.data[0])
        if(tag == 'add'){
          userData.num = userData.num + num
        }else if(tag == 'delete') {
          userData.num = userData.num - num
        }else {
          userData.num = num
        }
        let addRes = await new wxHttp().update({
          env: wxConfig.env,
          query: `db.collection("search_num").where({ openId: '${openId}' }).update({
            data: ${JSON.stringify({
              num: userData.num
            })}
          })`
        })
        console.log(`*********${JSON.stringify(addRes)}*******`);
        R(new newSuccess({ params: addRes}))
      }else {
        R(new newError({ params: '' }))
      }
    })
  }

  /**
   * 搜索内容
   */
  getSearchList(ctx){
    return new Promise(async R => {
      try {
        let { keyWord, openId, pageNum } = ctx.request.body;
        if(!keyWord){
          R(new newError({ msg: '请输入搜索内容' }))
          return 
        }

        await new wxHttp().add({
          env: wxConfig.env,
          query: `db.collection("search_log").add({
            data: ${JSON.stringify({
              openId,
              keyWord,
              _createTime: new Date().getTime(),
              _updateTime: new Date().getTime()
            })}
          })`
        }, 'post')


        // let url = `http://www.zhuzhupan.com/search?query=${keyWord}&s=1&type=`
        let url = `https://www.pan131.com/yun/${keyWord}/?pn=${pageNum || 1}`
        // let url = `https://www.laisoyixia.com/s/search?q=${keyWord}&currentPage=1`
        let htmlJson = await new getWebFun().getzhuzhuList(url);
        if(htmlJson.length > 0 && openId){
          this.addSearchNum(openId, 'delete', 1)
        }
        R(new newSuccess({ params: htmlJson }))
      } catch (error) {
        R(new newError({ params: error }))
      }
    })
  }

  /**
   * 
   */
  ailiAjax(ctx){
    return new Promise(async R => {
      try {
        let httpRes = await new httpFn().post(
          'http://47.107.250.109:3000/api/search/list',
          {
            keyWord: ctx.query.keyWord || '',
            openId: ctx.query.openId,
            pageNum: ctx.query.pageNum || 1
          }
        )

        R(new newSuccess({ params: httpRes.body.data }))
      } catch (error) {
        R(new newError({ params: error }))
      }
    })
  }

  getBaiduPanUrl(ctx){
    return new Promise(async R => {
      try {
        let baiduUrl = await new getWebFun().getBaiduUrl(ctx.request.body.url)
        R(new newSuccess({ params: {
          url: baiduUrl
        } }))
      } catch (error) {
        R(new newError({ params: error }))
      }
    })
  }

  ailiAjaxBaiduPan(ctx) {
    return new Promise(async R => {
      try {
        let httpRes = await new httpFn().post(
          'http://47.107.250.109:3000/api/search/getbaidupan',
          {
            url: ctx.request.body.url
          }
        )

        R(new newSuccess({ params: httpRes.body.data }))
      } catch (error) {
        R(new newError({ params: error }))
      }
    })
  }
}

module.exports = {
    opSearchPro
}