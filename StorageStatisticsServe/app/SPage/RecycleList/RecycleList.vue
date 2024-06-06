<template>
	<AppMain>
		<template v-slot:body>
			<scroll-view scroll-y="true" class="collect-list-container">
				<block v-if="collectArray.length > 0">
					<view class="collect-item" v-for="(item, idx) in collectArray" :key="item.id">
						<view class="collect-info">
							<view class="collect-cover">
								<image :src="item.cover || defaultImage" mode="aspectFill"></image>
							</view>
							<view class="collect-text">
								<text class="name">{{ item.name }}</text>
								<!-- <text class="intro" style="margin: 0;">{{ item.place || "暂未填写位置" }}</text> -->
								<text class="intro">创建人：{{ item.createUser }}</text>
								<text class="date">{{ item.place || "未填写具体位置" }}</text>
								<text class="date" v-if="item.pidName">属于{{ item.pidName }}收纳下的物品</text>
							</view>
						</view>
						<view class="button-list">
							<view class="delete-item" @click="confirmDelete(item.id)">
								彻底删除
							</view>
							<view class="recover-item" @click="recoverItem(item.id)">
								撤销删除
							</view>
						</view>
					</view>
				</block>

				<block v-else>
					<view class="no-data">
						<image class="icon" src="@/static/icon/icon17.png" mode=""></image>
						<view class="no-data-tips">
							回收站很干净~
						</view>

						<view class="no-data-btn" @click="goBlack">
							返回
						</view>
					</view>
				</block>
			</scroll-view>
		</template>
	</AppMain>
</template>

<script setup>
	import { onMounted, ref } from "vue";
	import AppMain from "@/components/AppMain/AppMain.vue";
	import { defaultImage } from "@/utils/utils.js"
	import MessageBox from "../../utils/Message";
	import request from "../../utils/request";

	const collectArray = ref([]);

	onMounted(() => {
		getItemsList()
	})

	/**
	 * 获取回收站列表
	 */
	const getItemsList = async () => {
		try {
			let response = await new request().get("/warehouseItems/getRecycleBinByOpenId", {})
			if (response.code == 200) {
				collectArray.value = response.data;
				return;
			}
			throw new Error(response.message);
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message);
		}
	}

	/**
	 * 彻底删除
	 */
	const confirmDelete = (id) => {
		new MessageBox().dialog("确定彻底删除该数据吗?", "提示", true).then(async (_o) => {
			if (_o == "confirm") {
				try {
					let response = await new request().delete("/warehouseItems/delete?id=" + id, {})
					if (response.code == 200) {
						new MessageBox().success("删除成功!")
						getItemsList();
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

	/**
	 * 恢复数据
	 */
	const recoverItem = (id) => {
		let msg = "撤销删除?";
		let idx = collectArray.value.findIndex(item => item.id == id);
		if (collectArray.value[idx].pid) {
			msg = "该数据是物品，如果该物品所属的收纳也被删除了，本次撤销删除将连同收纳一起，确定吗？"
		}
		new MessageBox().dialog(msg, "提示", true).then(async (_o) => {
			if (_o == "confirm") {
				try {
					let response = await new request().post(
						"/warehouseItems/recoverRecycleBinById", { id })
					if (response.code == 200) {
						new MessageBox().success("撤销成功!")
						getItemsList();
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

	const goBlack = () => {
		uni.navigateBack({
			delta: 1
		})
	}
</script>

<style lang="scss" scoped>
	.collect-list-container {
		.collect-item {
			padding: 30upx;
			box-sizing: border-box;
			background-color: #ffffff;
			border-radius: 8upx;
			border: 2upx solid #eaeaea;
			margin-bottom: 24upx;

			.collect-info {
				display: flex;
				align-items: center;
				margin-bottom: 20upx;

				.collect-cover {
					width: 160upx;
					height: 160upx;
					min-width: 160upx;
					margin-right: 16upx;

					image {
						width: 100%;
						height: 100%;
						background-color: #ccc;
					}
				}

				.collect-text {
					display: flex;
					flex-direction: column;

					.name {
						font-size: 32upx;
						font-family: PingFang SC;
						font-weight: bold;
						color: #333;
						line-height: 1.5;
					}

					.intro {
						font-size: 28upx;
						font-family: PingFang SC;
						font-weight: normal;
						color: #848484;
						line-height: 1.3;
						margin-bottom: 18upx;
					}

					.date {
						font-size: 28upx;
						font-family: PingFang SC;
						font-weight: normal;
						color: #848484;
						line-height: 1.3;
					}
				}


			}

			.button-list {
				display: flex;
				justify-content: flex-end;
				margin-top: 20upx;

				.delete-item {
					width: 140upx;
					height: 58upx;
					background: orangered;
					display: flex;
					align-items: center;
					justify-content: center;
					border-radius: 40upx;
					color: #ffffff;
					font-size: 24upx;
					font-weight: bold;
					margin-right: 14upx;
				}

				.recover-item {
					width: 140upx;
					height: 58upx;
					background: #5680FA;
					display: flex;
					align-items: center;
					justify-content: center;
					border-radius: 40upx;
					color: #ffffff;
					font-size: 24upx;
					font-weight: bold;
				}
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
</style>