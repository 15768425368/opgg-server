<template>
	<AppMain>
		<template v-slot:body>
			<block v-if="roomInfo.id">
				<view class="room-info-box">
					<view class="room-name">
						{{ roomInfo.roomName }} 共({{ roomUserArray.length }})人
					</view>

					<uni-list>
						<uni-list-item v-for="item in roomUserArray" :key="item.id" showArrow :title="item.nickName"
							:rightText="item.isMaster == 1 ? '房主' : ''" clickable @click="changeRoomMember(item.id)" />
					</uni-list>
				</view>

				<view class="sheet-submit">
					<view class="button-close button" @click="deleteRoom">
						{{ roomInfo.hostUserOpenId == userInfo.openid ? '解散' : '退出' }}共享空间
					</view>
					<view style="width: 40upx;" class="">

					</view>
					<view class="button-next button">
						邀 请 伙 伴
						<button open-type="share" class="share-btn-box" @shareAppMessage="onShareAppMessage" />
					</view>
				</view>
			</block>
			<block v-else>
				<view class="no-data">
					<image class="icon" src="@/static/icon/icon17.png" mode=""></image>
					<view class="no-data-tips">
						暂无协同共享空间或暂未加入TA人的共享空间
					</view>

					<view class="no-data-btn" @click="createRoom">
						创建协同共享空间
					</view>
				</view>
			</block>
		</template>
	</AppMain>

	<uni-popup ref="editMember" type="center" border-radius="10px" background-color="#fff" @change="changePopup">
		<view class="member-user-form">
			<view class="popup-title">管理【{{ selectMemberInfo.nickName }}】成员权限</view>

			<view class="purview-box">
				<uni-section class="mb-10" title="编辑权限" sub-title="设置该成员对共享空间物品是否有编辑的权限"></uni-section>
				<view class="change-box">
					<uni-data-checkbox mode="button" v-model="selectMemberInfo.isEdit" :localdata="editArray"
						@change="changePer"></uni-data-checkbox>
				</view>
			</view>
			<view class="purview-box">
				<uni-section class="mb-10" title="删除权限" sub-title="设置该成员对共享空间物品是否有删除的权限"></uni-section>
				<view class="change-box">
					<uni-data-checkbox mode="button" v-model="selectMemberInfo.isDelete" :localdata="deleteArray"
						@change="changePer"></uni-data-checkbox>
				</view>
			</view>

			<view class="button-list">
				<button class="mini-btn" type="warn" size="mini" plain="true" @click="deleteMember">移出空间</button>
				<button class="mini-btn" type="default" size="mini" plain="true" @click="closePopup">关闭弹窗</button>
			</view>
		</view>
	</uni-popup>
</template>

<script setup>
	import { onMounted, ref, computed } from 'vue';
	import request from "@/utils/request.js";
	import MessageBox from "@/utils/Message.js"
	import UniList from "@/uni_modules/uni-list/components/uni-list/uni-list.vue";
	import UniListItem from "@/uni_modules/uni-list/components/uni-list-item/uni-list-item.vue";
	import { useStore } from "vuex";
	import { onShareAppMessage } from "@dcloudio/uni-app";
	import UniPopup from "@/uni_modules/uni-popup/components/uni-popup/uni-popup.vue";
	import UniSection from "@/uni_modules/uni-section/components/uni-section/uni-section.vue"

	const store = useStore();
	const roomInfo = ref({});
	const roomUserArray = ref([]);
	const editMember = ref(false);
	const selectMemberInfo = ref({});
	const editArray = ref([
		{ text: '可编辑', value: 1 },
		{ text: '不可编辑', value: 0 }
	])
	const deleteArray = ref([
		{ text: '可删除', value: 1 },
		{ text: '不可删除', value: 0 }
	])

	onMounted(() => {
		getRoomInfo()
	})

	onShareAppMessage(() => {
		return {
			title: userInfo.value.nickName + '邀请您加入收纳宝共享空间，一起管理收纳记录',
			path: `/pages/index/index?roomId=${roomInfo.value.id}&scene=joinRoom&roomName=${roomInfo.value.roomName}&pNickName=${userInfo.value.nickName}`,
			imageUrl: "https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/%E9%82%80%E8%AF%B7%E5%8A%A0%E5%85%A5%E5%85%B1%E4%BA%AB%E7%A9%BA%E9%97%B4.png"
		}
	})

	const userInfo = computed(() => {
		return store.state.user.info
	})

	/**
	 * 创建共享空间
	 */
	const createRoom = async () => {
		try {
			let response = await new request().post(
				"/room/createRoom", { roomName: `${userInfo.value.nickName}的共享空间` });
			if (response.code == 200) {
				new MessageBox().success("创建成功，赶紧去邀请小伙伴加入吧~");
				getRoomInfo()
				return
			}
			throw new Error(response.message);
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message);
		}
	}

	/**
	 * 获取共享空信息
	 */
	const getRoomInfo = async () => {
		try {
			let response = await new request().get("/room/isRoom");
			if (response.code == 200) {
				roomInfo.value = response.data;
				getRoomUserArray()
				return
			}
			// throw new Error(response.message);
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message);
		}
	}

	/**
	 * 获取共享空间用户列表
	 */
	const getRoomUserArray = async () => {
		try {
			let response = await new request().get("/room/getRoomUser");
			if (response.code == 200) {
				roomUserArray.value = response.data;
				return
			}
			throw new Error(response.message);
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message);
		}
	}

	/**
	 * 解散共享空间
	 */
	const deleteRoom = () => {
		if (roomInfo.value.hostUserOpenId == userInfo.value.openid) {
			new MessageBox().dialog("解散后将无法查看其他成员记录的物品，把房间内所有用户踢出并解散共享空间？", '警告', true).then(async (_o) => {
				if (_o == 'confirm') {
					try {
						let response = await new request().delete("/room/disbandRoom?roomId=" + roomInfo
							.value
							.id);
						if (response.code == 200) {
							new MessageBox().success("解散成功，空间内所有用户已全部解绑")
							setTimeout(() => {
								uni.navigateBack({
									delta: 1
								})
							}, 2000)
							return
						}
						throw new Error(response.message);
					} catch (e) {
						//TODO handle the exception
						new MessageBox().error(e.message);
					}
				}
			})
		} else {
			new MessageBox().dialog("退出共享空间后将无法查看其他成员的收纳记录，确定退出吗？", '警告', true).then(async (_o) => {
				if (_o == 'confirm') {
					try {
						let response = await new request().post("/room/quitRoom", {
							roomId: roomInfo
								.value.id
						});
						if (response.code == 200) {
							new MessageBox().success("退出成功")
							setTimeout(() => {
								uni.navigateBack({
									delta: 1
								})
							}, 2000)
							return
						}
						throw new Error(response.message);
					} catch (e) {
						//TODO handle the exception
						new MessageBox().error(e.message);
					}
				}
			})
		}

	}

	/**
	 * 编辑共享空间成员
	 * @param {Number} id
	 */
	const changeRoomMember = (id) => {
		if (roomInfo.value.hostUserOpenId != userInfo.value.openid) return;
		let item = roomUserArray.value.find(item => item.id == id);
		if (item.userOpenId == userInfo.value.openid) return;
		selectMemberInfo.value = item;
		editMember.value.open();
	}

	const closePopup = () => {
		editMember.value.close();
	}

	const changePopup = (event) => {
		if (event.show == false) {
			setTimeout(() => {
				resetPopupForm()
			}, 500)
		}
	}

	const resetPopupForm = () => {
		selectMemberInfo.value = {};
	}

	/**
	 * 移除共享空间成员
	 */
	const deleteMember = () => {
		new MessageBox().dialog(`确定将${selectMemberInfo.value.nickName}成员移除共享空间吗?`, '提示', true).then(async (_o) => {
			if (_o == "confirm") {
				try {
					let response = await new request().post("/room/removeUserRoom", {
						id: selectMemberInfo.value.id
					});
					if (response.code == 200) {
						new MessageBox().success("成员已移除");
						getRoomUserArray();
						closePopup();
						return
					}
					throw new Error(response.message);
				} catch (e) {
					//TODO handle the exception
					new MessageBox().error(e.message);
				}
			}
		})
	}

	const changePer = async () => {
		try {
			let response = await new request().post("/room/editMemberPer", {
				id: selectMemberInfo.value.id,
				isEdit: selectMemberInfo.value.isEdit,
				isDelete: selectMemberInfo.value.isEdit
			});
			if (response.code == 200) {
				new MessageBox().success("已保存生效");
				getRoomUserArray();
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
	.room-info-box {
		padding: 30upx;
		box-sizing: border-box;

		.room-name {
			font-size: 38upx;
			font-weight: bold;
			color: #333;
			margin-bottom: 60upx;
		}
	}

	.sheet-submit {
		position: fixed;
		padding: 0 50upx;
		box-sizing: border-box;
		bottom: 60upx;
		left: 0;
		z-index: 3;
		width: 100%;
		display: flex;

		.button {
			flex-grow: 1;
			margin: 0 30upx;
			height: 76upx;
			border-radius: 14upx;
			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 32upx;
			color: #fff;
		}

		.button-close {
			background-color: orangered;
		}

		.button-next {
			background: #5680FA;
			position: relative;

			.share-btn-box {
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;
				opacity: 0;
				z-index: 2;
			}
		}
	}

	.no-data {
		width: 100%;
		height: 90vh;
		display: flex;
		align-items: center;
		flex-direction: column;
		padding-top: 200upx;
		box-sizing: border-box;

		.icon {
			width: 566upx;
			height: 456upx;
		}

		.no-data-tips {
			width: 100%;
			text-align: center;
			font-size: 28upx;
			font-weight: normal;
			color: rgba(51, 51, 51, 1);
			margin-bottom: 40upx;
		}

		.no-data-btn {
			width: 60%;
			height: 78upx;
			background-color: #5680FA;
			border-radius: 40upx;

			text-align: center;
			line-height: 78upx;
			font-size: 32upx;
			font-weight: bold;
			color: rgba(255, 255, 255, 1);
		}
	}

	.member-user-form {
		width: 600upx;
		padding: 20upx;
		box-sizing: border-box;

		.popup-title {
			font-size: 32upx;
			font-weight: bold;
			color: #333;
			text-align: center;
			padding-bottom: 20upx;
		}

		.purview-box {
			margin-bottom: 20upx;

			.change-box {
				padding: 0 10upx;
				box-sizing: border-box;
			}
		}

		.button-list {
			margin-top: 50upx;
			width: 100%;

			.mini-btn {
				width: 100%;
				margin-bottom: 10upx;
			}
		}
	}
</style>