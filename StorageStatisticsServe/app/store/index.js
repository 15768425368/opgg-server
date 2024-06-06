import {
	createStore
} from "vuex";
import system from "./module/system";
import user from "./module/user.js"
import createPersistedState from 'vuex-persistedstate' // 持久化插件

const store = createStore({
	plugins: [createPersistedState({ // 使用持久化插件
		storage: {
			getItem: (key) => uni.getStorageSync(key),
			setItem: (key, value) => uni.setStorageSync(key, value),
			removeItem: (key) => uni.removeStorageSync(key)
		}
	})],
	modules: {
		system,
		user
	},
});

export default store;