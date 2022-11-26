const { newError } = require("./message");
const { wxConfig } = require("../config/config");
const koa2Req = require("koa2-request");
const { get, set } = require("./redis");
const { httpFn } = require("./http");
class verification {
  constructor() {}

  /**
   * 验证登录情况
   * @param {*} token
   * @param {*} ctx
   */
  verificationLogin(token, ctx) {
    try {
      throw new Error("暂未登录，请登录后重试");
    } catch (error) {
      ctx.body = new newError(500, "暂未登录，请登录后重试");
    }
  }

  /**
   * 获取微信acctoken
   * @returns
   */
  getWxAccessToken() {
    return new Promise(async (res, rej) => {
      try {
        // 获取redis是否有token值
        let redisToken = await get("accessToken");
        if (redisToken) {
          res(redisToken);
        } else {
          let token = await new httpFn().get(wxConfig.accTokenUrl, {
            appid: wxConfig.appId,
            secret: wxConfig.appSecret,
            grant_type: "client_credential",
          });
          console.log(token);
          // let token = await koa2Req({
          //   method: "get",
          //   url: `${wxConfig.accTokenUrl}?grant_type=client_credential&appid=${wxConfig.appId}&secret=${}`,
          // });
          set("accessToken", token.access_token, 7200);
          res(token.access_token);
        }
      } catch (error) {
        rej(JSON.stringify(error), "请求微信公众平台acctoken失败");
      }
    });
  }

  /**
   * 格式请求参数
   */
  formatParams(query = {}) {
    return new Promise((r) => {
      let queryStr = "";
      for (let key in query) {
        if (query[key]) {
          queryStr += queryStr
            ? `,${key}:'${query[key]}'`
            : `${key}:'${query[key]}'`;
        }
      }
      r(queryStr);
    });
  }
}

module.exports = {
  verification,
};
