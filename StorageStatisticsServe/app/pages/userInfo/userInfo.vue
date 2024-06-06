<template>
	<uni-list>
		<uni-list-item title="头像" clickable>
			<template v-slot:footer>
				<view class="user-avatar-box">
					<button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar"></button>
					<image class="slot-image" :src="userInfo.avatar || defaultImage" mode="aspectFill"></image>
				</view>
			</template>
		</uni-list-item>
		<uni-list-item showArrow title="名字" :rightText="userInfo.nickName" clickable
			@click="openChangeTextPopup('nickName')" />
		<uni-list-item showArrow title="手机号" :rightText="userInfo.phoneNumber" clickable
			@click="openChangeTextPopup('phoneNumber')" />
		<uni-list-item showArrow title="性别" clickable>
			<template v-slot:footer>
				<picker @change="bindPickerChange" :value="userInfo.gender || 0" :range="sexArray">
					<view class="uni-input">{{sexArray[userInfo.gender] || '请选择'}}</view>
				</picker>
			</template>
		</uni-list-item>
		<!-- <uni-list-item showArrow title="生日" :rightText="userInfo.birthday" clickable
			@click="openChangeTextPopup('birthday')" /> -->
	</uni-list>

	<uni-popup ref="inputDialog" type="dialog" :is-mask-click="true">
		<uni-popup-dialog ref="inputClose" :before-close="true" @close="closeDialog" mode="input"
			:title="`设置${keyLabel[currentChangeKey]}`" :focus="false" :value="userInfo[currentChangeKey]"
			placeholder="请输入..." @confirm="changeTextDialogConfirm"></uni-popup-dialog>
	</uni-popup>
</template>

<script setup>
	import UniList from "@/uni_modules/uni-list/components/uni-list/uni-list.vue";
	import UniListItem from "@/uni_modules/uni-list/components/uni-list-item/uni-list-item.vue";
	import { onMounted, reactive, ref, computed } from "vue";
	import UniPopup from "@/uni_modules/uni-popup/components/uni-popup/uni-popup.vue";
	import UniPopupDialog from "@/uni_modules/uni-popup/components/uni-popup-dialog/uni-popup-dialog.vue";
	import { useStore } from "vuex";
	import MessageBox from "../../utils/Message";
	import request from "../../utils/request";

	const store = useStore();
	const inputDialog = ref(null); // 弹窗实例
	const userInfo = ref({}); // 用号信息
	const currentChangeKey = ref(""); // 当前正在修改弹窗的key
	const keyLabel = reactive({
		nickName: "名字",
		phoneNumber: "手机号",
		birthday: "生日"
	});
	const sexArray = ref(["保密", "男", '女']);

	onMounted(() => {
		userInfo.value = store.state.user.info
	})

	/**
	 * 打开修改文字信息弹窗
	 */
	const openChangeTextPopup = (key) => {
		currentChangeKey.value = key;
		inputDialog.value.open();
	}

	/**
	 * 修改文字信息弹窗确定按钮回调
	 */
	const changeTextDialogConfirm = (val) => {
		if (!val) {
			toast(`请输入${keyLabel[currentChangeKey.value]}`);
			return;
		} else {
			saveUserInfo(currentChangeKey.value, val);
			inputDialog.value.close();
		}
	}

	/**
	 * 关闭文字修改弹窗
	 */
	const closeDialog = () => {
		inputDialog.value.close();
		setTimeout(() => {
			currentChangeKey.value = "";
		}, 500)
	}

	/**
	 * 选择头像图片回掉
	 */
	const onChooseAvatar = (e) => {
		uni.showLoading({
			title: '上传中...'
		});
		// e.detail.avatarUrl
		uni.uploadFile({
			url: store.state.system.baseUrl + "/upload/uploadImage",
			filePath: e.detail.avatarUrl,
			name: 'file',
			header: {
				openid: uni.getStorageSync("openId")
			},
			success(response) {
				try {
					let params = JSON.parse(response.data);
					if (params.code == 200) {
						userInfo.value.avatar = params.data;
						saveUserInfo('avatar', params.data);
						return;
					}
					throw new Error("上传失败!")
				} catch (e) {
					//TODO handle the exception
					new MessageBox().error("上传失败!")
				}
			},
			fail(error) {
				new MessageBox().error("上传失败!")
			},
			complete() {
				uni.hideLoading();
			}
		})
	}

	/**
	 * 选择性别回调
	 */
	const bindPickerChange = (e) => {
		saveUserInfo("gender", e.detail.value)
	}

	/**
	 * 保存用户修改后的信息
	 */
	const saveUserInfo = async (key, val) => {
		try {
			let data = {
				id: userInfo.value.id
			}
			data[key] = val;

			let response = await new request().post("/user/update", data);
			if (response.code == 200) {
				new MessageBox().success("已保存!");
				let userInfoRes = await new request().get("/userInfo");
				if (userInfoRes.code == 200) {
					userInfo.value = userInfoRes.data;
					store.dispatch("user/updateInfo", userInfoRes.data)
				}
				return
			}
			throw new Error(response.message);
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message);
		}
	}
</script>

<style lang="scss" scoped>
	.user-avatar-box {
		width: 84upx;
		height: 84upx;
		position: relative;

		.slot-image {
			width: 100%;
			height: 100%;
		}

		.avatar-wrapper {
			position: absolute;
			top: 0;
			left: 0;
			z-index: 2;
			opacity: 0;
			width: 100%;
			height: 100%;
		}
	}

	.uni-input {
		color: #999;
		font-size: 12px;
	}
</style>