<template>
	<AppMain>
		<template v-slot:body>
			<view class="user-info-box">
				<view class="user-avatar">
					<image class="avata-url" :src="userInfo.avatar || defaultImage" mode="aspectFill"></image>
				</view>
				<view class="user-name">
					<text class="name">{{ userInfo.nickName }}</text>
					<text class="id">ID:{{ userInfo.id }}</text>
				</view>
				<view class="user-edit" @click="navigateToPage('/pages/userInfo/userInfo')">
					编辑资料
				</view>
			</view>

			<view class="user-serve">
				<view class="title">
					我的服务
				</view>

				<view class="list">
					<view class="item" v-for="(item, idx) in navList" :key="idx"
						:style="{ 'backgroundColor': item.color }" @click="goNavPage(item.name)">
						<image class="icon" :src="item.icon" mode=""></image>
						<text class="item-title">{{ item.name }}</text>
					</view>
				</view>
			</view>

			<view class="user-serve" style="margin-top: 24rpx;">
				<view class="title">
					猜你喜欢
				</view>

				<view class="plug-in-item" v-for="(item, idx) in proAppArray" :key="item.id" @click="goProApp(idx)">
					<view class="cover">
						<image class="cover-image" :src="item.cover" mode="aspectFill"></image>
					</view>

					<view class="info">
						<text class="title multiline-hiding">{{ item.title }} </text>
						<!-- <text class="desc">{{ item.popularity || 0 }}人看过</text> -->
					</view>
				</view>
			</view>
		</template>
	</AppMain>
</template>

<script setup>
	import { computed, onMounted, ref } from "vue";
	import AppMain from "@/components/AppMain/AppMain.vue";
	import { useStore } from "vuex";
	import { defaultImage, navigateToPage } from "@/utils/utils.js"
	import MessageBox from "../../utils/Message";
	import request from "../../utils/request";
	import { onReachBottom } from "@dcloudio/uni-app"

	const store = useStore();
	const navList = ref([
		{ name: "多人协同", color: "rgba(255, 248, 236, 1)", icon: "/static/icon/icon13.png" },
		{ name: "回收站", color: "#F1F6FF", icon: "/static/icon/icon18.png" },
		{ name: "意见反馈", color: "rgba(245, 242, 255, 1)", icon: "/static/icon/icon14.png" },
		{ name: "关于我们", color: "#FFF4F4", icon: "/static/icon/icon15.png" },
	])
	const proAppArray = ref([]);
	const total = ref(0);
	const proAppForm = ref({
		offset: 1,
		pagesize: 10
	})

	const userInfo = computed(() => {
		return store.state.user.info
	})

	onMounted(() => {
		getProApp()
	})

	onReachBottom(() => {
		if (total.value > proAppArray.value.length) {
			proAppForm.value.offset = proAppForm.value.offset + 1;
			getProApp()
		}
	})

	const goNavPage = (target) => {
		switch (target) {
			case "关于我们":
				navigateToPage("/pages/AboutUs/AboutUs")
				break;
			case "多人协同":
				navigateToPage("/SPage/SharedRoom/SharedRoom")
				break;
			case "意见反馈":
				navigateToPage("/pages/Feedback/Feedback")
				break;
			case "回收站":
				navigateToPage("/SPage/RecycleList/RecycleList")
				break;
		}
	}

	/**
	 * 获取推广小程序列表
	 */
	const getProApp = async () => {
		try {
			let response = await new request().post("/promoteApp/pageList", proAppForm.value);
			if (response.code == 200) {
				proAppArray.value = response.data.pageList || [];
				total.value = response.data.totalCount || 0
				return
			}
			throw new Error(response.message);
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message);
		}
	}

	/**
	 * 跳转推广应用
	 */
	const goProApp = (idx) => {
		let item = proAppArray.value[idx];
		uni.navigateToMiniProgram({
			appId: item.wxAppid,
			path: item.path,
			success(res) {
				// 打开成功
			}
		})
	}
</script>

<style lang="scss" scoped>
	.user-info-box {
		padding: 48upx;
		box-sizing: border-box;
		background-color: #fff;

		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 24upx;

		.user-avatar {
			width: 120upx;
			height: 120upx;
			margin-right: 32upx;

			.avata-url {
				width: 100%;
				height: 100%;
				border-radius: 50%;
			}
		}



		.user-name {
			display: flex;
			flex-direction: column;
			flex-grow: 1;

			.name {

				font-size: 36upx;
				font-weight: bold;
				color: #333;
				font-family: PingFang SC;
				margin-bottom: 20upx;
			}

			.id {
				font-size: 28upx;
				font-weight: normal;
				color: #535353;
				font-family: PingFang SC;
			}
		}

		.user-edit {
			width: 160upx;
			height: 64upx;
			border-radius: 32upx;
			display: flex;
			align-items: center;
			justify-content: center;

			font-size: 24upx;
			font-weight: normal;
			color: #fff;
			background: rgba(86, 128, 250, 1);
			box-shadow: 0px 20px 30px rgba(86, 128, 250, 0.25), 0px 10px 20px rgba(0, 0, 0, 0.1);

		}
	}

	.user-serve {
		padding: 38upx 48upx;
		box-sizing: border-box;
		background-color: #fff;

		.title {
			font-size: 32upx;
			font-weight: normal;
			color: #333;

			font-family: PingFang SC;
			margin-bottom: 42upx;
		}

		.list {
			display: flex;
			align-items: center;
			justify-content: space-between;
			flex-wrap: wrap;

			.item {
				min-width: 30%;
				width: 30%;
				height: 208upx;
				border-radius: 12upx;
				display: flex;
				align-items: center;
				justify-content: center;
				flex-direction: column;
				margin-bottom: 20upx;

				.icon {
					width: 72upx;
					height: 72upx;
					margin-bottom: 28upx;
				}

				.item-title {
					font-size: 24upx;
					font-weight: normal;
					color: #333;
					font-family: PingFang SC;
				}
			}
		}

		.plug-in-item {
			display: flex;
			flex-direction: column;
			margin-bottom: 20upx;

			.cover {
				width: 100%;
				height: 252upx;
				min-width: 100%;
				margin-bottom: 20upx;

				.cover-image {
					width: 100%;
					height: 100%;
					border-radius: 8upx;
					background-color: #ccc;
				}
			}

			.info {
				display: flex;
				justify-content: flex-start;

				.title {
					font-size: 32upx;
					font-weight: bold;
					color: rgba(54, 62, 82, 1);
					margin-bottom: 40upx;

					text-align: left;
				}

				.desc {
					font-size: 22upx;
					font-weight: bold;
					color: rgba(163, 169, 184, 1);
				}
			}
		}
	}
</style>