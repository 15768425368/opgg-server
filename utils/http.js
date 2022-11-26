const koa2Req = require("koa2-request");
class httpFn {
  constructor() {}

  get(url, params = {}) {
    return new Promise(async (r, e) => {
      try {
        let query = "";
        for (let key in params) {
          query
            ? (query += `&${key}=${params[key]}`)
            : (query += `?${key}=${params[key]}`);
        }
        let httpRes = await koa2Req({
          method: "get",
          url: url + query,
        });
        console.log('微信平台请求:' + url + query);
        r(JSON.parse(httpRes.body));
      } catch (error) {
        e(error);
      }
    });
  }

  post(url, params) {
    return new Promise(async (r, e) => {
      try {
        let wxRes = await koa2Req({
          method: "post",
          url: url,
          json: params,
        });
        console.log('微信平台请求:' + url + '***********' + '参数:' + JSON.stringify(params));
        r(wxRes);
      } catch (error) {
        e(error);
      }
    });
  }

  upload(url,params){
    return new Promise(async (r, e) => {
      try {
        let wxRes = await koa2Req({
          method: "post",
          url: url,
          json: params,
          headers: {
            'Content-Type': 'multipart/form-data;'
          }
        });
        console.log('微信平台上传请求:' + url + '***********');
        console.log('参数:' + JSON.stringify(params));
        r(wxRes);
      } catch (error) {
        e(error);
      }
    })
  }
}

module.exports = {
  httpFn,
};
