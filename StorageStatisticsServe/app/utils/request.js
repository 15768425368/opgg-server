import store from '../store';
import MessageBox from './Message';
export default class request {
	request(url, method, data) {
		return new Promise((resolve, reject) => {
			let header = {
				'Content-Type': 'application/json',
				"openid": uni.getStorageSync("openId")
			}
			let baseUrl = store.state.system.baseUrl;


			uni.request({
				url: baseUrl + url,
				method: method,
				data: data,
				header: header,
				success: res => {
					console.log("********************调用接口*********************")
					console.log(`接口：${url}`);
					console.log(`请求参数：${JSON.stringify(data)}`);
					console.log(`响应参数：${JSON.stringify(res.data)}`);
					console.log(`********************调用结束*********************`)

					if (res.data.code) {
						resolve(res.data)
					} else {
						new MessageBox().error("服务端错误：系统繁忙，请稍后刷新重试！");
						reject(res)
					}
				},
				fail: err => {
					console.log(`xxxxxxx接口调用失败：${url} 原因:${JSON.stringify(err)}`)
					new MessageBox().error("服务端错误：系统繁忙，请稍后刷新重试！");
					reject(err)
				},
				complete: () => {
					uni.hideLoading()
				},
			})
		})
	}

	get(url, data) {
		return this.request(url, 'GET', data)
	}

	post(url, data, type) {
		return this.request(url, 'POST', data)
	}

	delete(url, data, type) {
		return this.request(url, 'DELETE', data)
	}
}