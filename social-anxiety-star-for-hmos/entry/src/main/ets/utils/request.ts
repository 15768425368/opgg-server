// 引入包名
import http from '@ohos.net.http';
import { baseUrl } from '../config/config';
import { getStorge } from './DataStorge';
import { MessageBox, MessageDialog } from './Message';
import { toQueryParams } from './utils';

// 每一个httpRequest对应一个HTTP请求任务，不可复用
let httpRequest = http.createHttp();

const request = (url, method, json, ctx) => {
  return new Promise(async (result, reject) => {
    let token:string = await getStorge("token", "", ctx);
    httpRequest.request(
      baseUrl + url,
      {
        method,
        // 开发者根据自身业务需要添加header字段
        header: {
          'Content-Type': 'application/json',
          "Authorization": token,
          "deviceNo": "1"
        },
        // 当使用POST请求时此字段用于传递内容
        extraData: json,
      },async (err, data) => {
      if (!err) {
        // data.result为HTTP响应内容，可根据业务需要进行解析
        if(data.responseCode != 200) {
          MessageDialog("提示", "网络错误，是否重新请求?").then(async () => {
            result(await request(url, method, json, ctx))
          }).catch(() => {
            reject({ code: 500, message: "网络错误", data: null });
          })
        }else {
          // @ts-ignore
          let res = JSON.parse(data.result)
          if(res.code == 200) {
            result(res)
            // @ts-ignore
          } if(res.code == 1001){
            MessageDialog("提示", "您暂未登录，去登录?").then(() => {
              // 跳转登录页面
            }).catch(() => {
              reject({ message: "暂无登录，正在返回首页~" })
            })
          }else {
            reject(res);
          }
        }

        httpRequest.destroy();
      } else {
        console.info(JSON.stringify(err))
        reject({ message: JSON.stringify(err) })
        // 当该请求使用完毕时，调用destroy方法主动销毁。
        httpRequest.destroy();
      }
    }
    )
  })
}

interface returnDataType {
  code?: number,
  message?: string,
  data?: any
}

/**
 * get请求
 */
export const httpGet = (url, params, ctx): Promise<returnDataType> => {
  return request(url + toQueryParams(params), http.RequestMethod.GET, {}, ctx);
}

/**
 * post请求
 */
export const httpPost = (url:string, data:any, ctx): Promise<returnDataType> => {
  return request(url, http.RequestMethod.POST, data, ctx);
}

/**
 * delete请求
 */
export const httpDelete = (url: string, data: any, ctx): Promise<returnDataType> => {
  return request(url + toQueryParams(data), http.RequestMethod.DELETE, {}, ctx);
}