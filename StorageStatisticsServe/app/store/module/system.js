// 系统全局状态管理
export default {
	namespaced: true,
	// 变量池
	state: {
		Comfig: {
			statusBarHeight: 0, // 设备状态栏高度
			menuButtonHeight: 0, // 小程序胶囊高度
			deviceId: "", // 设备 id 。由 uni-app 框架生成并存储，清空 Storage 会导致改变
			deviceType: "", // 设备类型。如phone、pad、pc、unknow
			deviceModel: "", // 设备型号
			osName: "", // 系统名称
			osVersion: "", // 操作系统版本。如 ios 版本，android 版本
		},
		baseUrl: "", // 服务器请求地址
	},

	// 定义计算属性，对state进行处理并返回结果
	getters: {},

	// 定义修改状态的方法
	mutations: {
		COMFIG(state, data) {
			for (let key in state.Comfig) {
				state.Comfig[key] = data[key]
			}
		},
		BASEURL(state, data) {
			state.baseUrl = data;
		}
	},

	// 定义异步修改状态的方法
	actions: {
		updateComfig({
			commit
		}, data) {
			commit("COMFIG", data);
		},

		updateBaseUrl({ commit }, data) {
			commit("BASEURL", data);
		}
	},
};