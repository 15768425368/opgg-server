<script>
	import request from './utils/request';
	import MessageBox from '@/utils/Message';
	import { useStore } from "vuex";

	export default {
		onLaunch: function() {
			this.AppInit()
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},

		methods: {
			AppInit() {
				const store = useStore();

				store.dispatch("system/updateBaseUrl", "https://www.xinli-software.top/api");
				// store.dispatch("system/updateBaseUrl", "http://110.41.71.88/api");

				this.userLogin();
				this.getSystemConfig();
			},

			/**
			 * 获取设备系统信息
			 */
			getSystemConfig() {
				try {
					let { deviceId, deviceType, deviceModel, osName, osVersion, statusBarHeight } = uni
						.getSystemInfoSync();
					let menuButtonHeight = 0;

					// #ifdef MP-WEIXIN
					menuButtonHeight = uni.getMenuButtonBoundingClientRect().height + 8;
					// #endif
					// #ifdef APP
					menuButtonHeight = 40
					// #endif


					const store = useStore();
					store.dispatch("system/updateComfig", {
						deviceId,
						deviceType,
						deviceModel,
						osName,
						osVersion,
						statusBarHeight,
						menuButtonHeight
					})
				} catch (e) {
					//TODO handle the exception
					new MessageBox().error(e.message);
				}
			},

			userLogin() {
				const store = useStore();
				uni.login({
					async success(result) {
						try {
							let openIdRes = await new request().get("/getOpenId", { code: result.code });
							openIdRes.data = JSON.parse(openIdRes.data);
							if (!openIdRes.data.openid) throw new Error("获取用户身份信息失败，请稍后重试!");

							let loginResult = await new request().post("/login", {
								openid: openIdRes.data
									.openid
							});
							if (loginResult.code == 200) {
								uni.setStorageSync("openId", openIdRes.data
									.openid);

								store.dispatch("user/updateInfo", loginResult.data)
								return
							}

							throw new Error(loginResult.message);
						} catch (e) {
							new MessageBox().dialog(e.message || '程序错误，请稍后重试!', "提示", false).then(() => {
								uni.exitMiniProgram({
									success: function(res) {
										console.log(res)
									}
								})
							})
						}

					}
				})
			}
		}
	}
</script>

<style>
	/*每个页面公共css */
	.page-container {
		padding: 30upx;
		box-sizing: border-box;
	}

	/*每个页面公共css */
	.multiline-hiding {
		display: -webkit-box;
		text-overflow: ellipsis;
		overflow: hidden;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
	}


	::-webkit-scrollbar {
		display: none;
		width: 0;
		height: 0;
		color: transparent;
	}
</style>