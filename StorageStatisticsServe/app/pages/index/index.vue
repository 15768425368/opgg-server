<template>
	<AppMain>
		<template v-slot:body>
			<view class="page-head-box"
				:style="{ 'height': `${SystemConfig.menuButtonHeight + SystemConfig.statusBarHeight}px` }">
				<view class="head-padding" :style="{ 'width': '100%', 'height': `${SystemConfig.statusBarHeight}px` }">
				</view>
				<text class="page-title" :style="{ 'height': `${SystemConfig.menuButtonHeight}px` }">收 纳 列
					表({{ collectTotal >= 0 ? collectTotal : collectArray.length }})</text>
			</view>
			<view class="page-container">

				<view class="search-box">
					<input type="text" v-model="pageForm.name" @blur="changKeyword" placeholder="输入物品名称搜索..." />
					<view class="search-btn" @click="changKeyword">
						搜索
					</view>
				</view>

				<view class="list-type-box">
					<picker @change="bindPickerChange" :value="pageForm.type" :range="listTypeArray">
						<view class="uni-input">
							<text>{{ listTypeArray[pageForm.type] || '请选择' }}</text>
							<image src="@/static/icon/icon19.png" mode=""></image>
						</view>
					</picker>
				</view>
				<block v-if="collectArray.length > 0">
					<scroll-view scroll-y="true" class="collect-list-container">
						<view class="collect-item" v-for="(item, idx) in collectArray" :key="item.id">
							<view class="collect-info" @click="openNewCollect('collect', '', item.id)">
								<view class="collect-cover">
									<image :src="item.cover || defaultImage" mode="aspectFill"></image>
								</view>
								<view class="collect-text">
									<text class="name">{{ item.name }}</text>
									<!-- <text class="intro" style="margin: 0;">{{ item.place || "暂未填写位置" }}</text> -->
									<text class="intro">创建人：{{ item.createUser }}</text>
									<text class="date">{{ item.place }}
										共{{ item.child.length }}件物品</text>
								</view>
							</view>
							<view class="collect-res-list">
								<view class="res-list-title">
									包含物品：
								</view>
								<view class="res-ul" :style="{ 'flexWrap': item.wrap ? 'wrap' : '' }">
									<view class="res-item" v-for="child in item.child" :key="child.id"
										@click="openNewCollect('res', item.id, child.id, item.name)">
										<image class="res-cover" :src="child.cover || defaultImage" mode="aspectFill">
										</image>
										<text class="res-name">{{ child.name }}</text>
									</view>

									<view class="add-item" @click="openNewCollect('res', item.id, '', item.name)">
										<view class="res-cover">
											<image src="@/static/icon/icon16.png" mode=""></image>
											<text>添加</text>
										</view>
										<text class="res-name" style="opacity: 0;">添加物品</text>
									</view>
								</view>

							</view>

							<view class="more-btn" v-if="item.child.length > 5" @click="changeMore(idx)">
								{{ item.wrap ? '收起' : '查看全部' }}
							</view>
						</view>
					</scroll-view>
				</block>
				<block v-else>
					<view class="no-data">
						<image class="icon" src="@/static/icon/icon17.png" mode=""></image>
						<view class="no-data-tips">
							暂未收纳列表，赶紧去添加叭~
						</view>

						<view class="no-data-btn" @click="openNewCollect('collect')">
							添加收纳/物品
						</view>
					</view>
				</block>
			</view>

			<view v-if="collectArray.length > 0" class="new-container-btn" @click="openNewCollect('collect')">
				<image src="../../static/icon/icon9.png" mode=""></image>
			</view>

		</template>
	</AppMain>



	<uni-popup ref="newCollectRef" type="bottom" background-color="#fff" @change="changePopup">
		<NewCollect v-if="popupConfig.target" :target="popupConfig.target" :pid="popupConfig.pid" :id="popupConfig.id"
			@close="closeNewCollect" :pName="popupConfig.pName"></NewCollect>
	</uni-popup>
</template>

<script setup>
	import { computed, ref, onMounted } from "vue";
	import AppMain from "@/components/AppMain/AppMain.vue";
	import { useStore } from "vuex";
	import MessageBox from "../../utils/Message";
	import request from "../../utils/request";
	import { formatDate } from "../../utils/utils";
	import NewCollect from "../../components/NewCollect/NewCollect.vue";
	import UniPopup from "@/uni_modules/uni-popup/components/uni-popup/uni-popup.vue"
	import { defaultImage } from "../../utils/utils";
	import { onShow, onReachBottom, onLoad } from "@dcloudio/uni-app";

	const store = useStore();
	const collectArray = ref([]);
	const collectTotal = ref(0);
	const keyword = ref("");
	const newCollectRef = ref(null);
	const popupConfig = ref({
		target: "",
		pid: "",
		id: ""
	})
	const pageForm = ref({
		type: "0",
		name: "",
		pageSize: 20,
		pageNumber: 1
	})

	const listTypeArray = ref(["仅查询本人记录"]);

	onShow(() => {
		if (uni.getStorageSync("openId")) {
			PageInit()
		} else {
			new MessageBox().loading("加载中...")
			let timer = setInterval(() => {
				if (uni.getStorageSync("openId")) {
					PageInit()
					uni.hideLoading()
					clearInterval(timer)
				}
			}, 200)
		}
	})

	onLoad((options) => {
		if (options.scene == "joinRoom") {
			// 共享空间邀请加入场景
			joinRoom(options)
		}
	})

	onReachBottom(() => {
		if (!pageForm.value.name) {
			if (collectTotal.value > collectArray.value.length) {
				pageForm.value.pageNumber = pageForm.value.pageNumber + 1;
				getCollectArray()
			}
		}
	})

	/**
	 * 计算属性获取vuex存储的系统参数
	 */
	const SystemConfig = computed(() => {
		return store.state.system.Comfig;
	})

	const PageInit = () => {
		resetPageListForm()
		pageForm.value.type = 0;
		if (collectArray.value.length == 0) {
			getCollectArray();
		}

		getRoomInfo();
	}

	/**
	 * 获取收纳列表
	 */
	const getCollectArray = async () => {
		try {
			new MessageBox().loading("加载中...")
			let params = JSON.parse(JSON.stringify(pageForm.value));
			params.type = (Number(params.type) + 1).toString();
			let response = await new request().post("/warehouseItems/getListAndRoom", params)
			uni.hideLoading()
			if (response.code == 200) {
				response.data.list.forEach(item => {
					item.createTime = formatDate(new Date(item.createTime), "yyyy-MM-dd hh:mm")
				})
				collectArray.value = collectArray.value.concat(response.data.list);
				collectTotal.value = response.data.total;
				return;
			}
			throw new Error(response.message);
		} catch (e) {
			uni.hideLoading()
			new MessageBox().error(e.message);
		}
	}

	const openNewCollect = (target, pid, id, pName) => {
		popupConfig.value = {
			target: target || 'collect',
			pid: pid || "",
			id: id || "",

			pName: pName || ""
		}
		newCollectRef.value.open();
	}

	const closeNewCollect = () => {
		resetPageListForm();
		getCollectArray()
		newCollectRef.value.close()
	}

	const changePopup = (e) => {
		if (!e.show) {
			popupConfig.value = {
				target: "",
				collectId: "",
				resId: "",
				pName: ""
			}
		}
	}

	const changeMore = (idx) => {
		if (collectArray.value[idx].wrap) {
			collectArray.value[idx].wrap = false
		} else {
			collectArray.value[idx].wrap = true
		}
	}

	const bindPickerChange = (e) => {
		resetPageListForm();
		pageForm.value.type = e.detail.value;
		getCollectArray()
	}

	const resetPageListForm = () => {
		pageForm.value.pageSize = 20;
		pageForm.value.pageNumber = 1;
		collectArray.value = [];
		collectTotal.value = 0;
	}

	const changKeyword = () => {
		resetPageListForm()
		getCollectArray()
	}

	const getRoomInfo = async () => {
		try {
			let response = await new request().get("/room/isRoom");
			listTypeArray.value = ["仅查询本人记录"]
			if (response.code == 200) {

				if (response.data.id) {
					listTypeArray.value.push(response.data.roomName)
				}
				return
			}
			throw new Error(response.message);
		} catch (e) {
			//TODO handle the exception
			pageForm.value.type = "0"
			// new MessageBox().error(e.message);
		}
	}

	const joinRoom = (options) => {
		let { roomId, roomName, pNickName } = options;

		let timer = setInterval(() => {
			if (uni.getStorageSync("openId")) {
				clearInterval(timer)
				new MessageBox().dialog(`${pNickName}邀请您加入TA的共享空间，共同管理收纳记录`, '提示', true).then(async (_o) => {
					if (_o == 'confirm') {
						try {
							let response = await new request().post("/room/joinRoom", {
								roomId: Number(
									roomId)
							});
							if (response.code == 200) {
								new MessageBox().success("已加入")
								PageInit();
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
		}, 100)
	}
</script>

<style scoped lang="scss">
	.page-head-box {
		padding-left: 30upx;
		padding-right: 30upx;
		box-sizing: border-box;
		position: sticky;
		z-index: 3;
		top: 0%;
		background-color: #FAFAFA;

		.page-title {
			font-size: 40upx;
			font-family: PingFang SC;
			font-weight: bold;
			color: #333333;
			line-height: 1;
			display: block;
			width: 100%;
			display: flex;
			align-items: center;
			font-style: italic;
			padding-bottom: 10upx;
			border-bottom: 4upx solid #333333;
		}
	}

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

			.collect-res-list {
				.res-list-title {
					font-size: 28upx;
					font-family: PingFang SC;
					font-weight: bold;
					color: #4b4b4b;
					margin-bottom: 10upx;
				}

				.res-ul {
					display: flex;
					width: 100%;
					overflow: hidden;
					overflow-x: auto;
					position: relative;

					.res-item {
						width: 100upx;
						min-width: 100upx;
						margin-right: 14upx;
						display: flex;
						flex-direction: column;
						align-items: center;
						margin-bottom: 14upx;

						.res-cover {
							width: 100upx;
							height: 100upx;
							background-color: #ccc;
							margin-bottom: 6upx;
						}

						.res-name {
							font-size: 24upx;
							font-family: PingFang SC;
							font-weight: bold;
							color: #333;
							display: inline-block;
							width: 100%;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
							text-align: center;
						}
					}

					.add-item {
						display: flex;
						flex-direction: column;
						align-items: center;
						font-size: 28upx;
						color: #ccc;
						width: 100upx;
						min-width: 100upx;
						margin-right: 14upx;
						position: sticky;
						right: 0;
						z-index: 2;
						background-color: #ffffff;

						.res-cover {
							width: 100upx;
							height: 100upx;
							background-color: #5680FA !important;
							margin-bottom: 6upx;
							display: flex;
							align-items: center;
							justify-content: center;
							flex-direction: column;

							image {
								width: 30upx;
								height: 30upx;
								margin-bottom: 6upx;
							}

							text {
								font-size: 20upx;
								white-space: nowrap;
								color: #ffffff;

							}
						}

						.res-name {
							font-size: 24upx;
							font-family: PingFang SC;
							font-weight: bold;
							color: #333;
							display: inline-block;
							width: 100%;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
							text-align: center;

						}
					}
				}

			}

			.more-btn {
				width: 100%;
				text-align: center;
				margin-top: 14upx;
				font-size: 28upx;
				color: #5680FA;
			}
		}
	}

	.search-box {
		margin-bottom: 30upx;
		height: 74upx;
		position: relative;
		background-color: #ececec;
		display: flex;
		align-items: center;
		border-radius: 40upx;
		padding-right: 6upx;
		box-sizing: border-box;

		input {
			width: 100%;
			padding: 0 30upx;
			box-sizing: border-box;
			font-size: 28upx;
			font-family: PingFang SC;
			font-weight: normal;
			color: #333333;
		}

		.search-btn {
			height: 60upx;
			width: 150upx;
			background-color: red;
			z-index: 2;
			text-align: center;
			line-height: 60upx;

			font-size: 28upx;
			font-family: PingFang SC;
			font-weight: normal;
			color: #ffffff;
			border-radius: 40upx;
		}
	}

	.new-container-btn {
		position: fixed;
		bottom: 2%;
		right: 20upx;
		width: 140upx;
		height: 140upx;
		z-index: 9;

		image {
			width: 100%;
			height: 100%;
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

	.list-type-box {
		margin-bottom: 20upx;
	}

	.uni-input {
		display: flex;
		justify-content: center;
		align-items: center;

		text {
			font-size: 32upx;
			font-weight: bold;
			color: #000;
			margin-right: 8upx;
			line-height: 1;
		}

		image {
			width: 24upx;
			height: 24upx;
			min-width: 24upx;
		}
	}
</style>