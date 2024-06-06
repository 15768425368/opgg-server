// 系统全局状态管理
export default {
	namespaced: true,
	// 变量池
	state: {
		info: {

		}
	},

	// 定义计算属性，对state进行处理并返回结果
	getters: {},

	// 定义修改状态的方法
	mutations: {
		INFO(state, data) {
			state.info = data;
		},
	},

	// 定义异步修改状态的方法
	actions: {
		updateInfo({ commit }, data) {
			commit("INFO", data);
		}
	},
};