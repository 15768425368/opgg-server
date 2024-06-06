<!-- 
	新增收纳容器组件
 -->
<template>
	<view class="new-collect-component">
		<view class="component-head">
			<view class="title">
				<block v-if="props.target == 'collect'">
					<text v-if="props.id">编辑{{ formData.name }}收纳</text>
					<text v-else>添加收纳</text>
				</block>
				<block v-if="props.target == 'res'">
					<text v-if="props.id">编辑{{ props.pName }}收纳下的【{{ formData.name }}】物品</text>
					<text v-else>新增物品收纳至{{ props.pName }}</text>
				</block>
			</view>
			<image @click="closePopup" class="close-icon" src="@/static/icon/icon10.png" mode=""></image>
		</view>

		<view class="component-content">
			<view class="content-item">
				<view class="item-title">
					照片
				</view>
				<view class="upload-box" style="margin-bottom: 14rpx;" @click="changeImage">
					<image v-if="!formData.cover" src="@/static/icon/icon12.png" mode=""></image>
					<image v-else :src="formData.cover" mode="aspectFill"></image>
				</view>
			</view>

			<view class="content-item">
				<view class="item-title">
					名称
				</view>
				<view class="input-box">
					<input type="text" placeholder="请输入收纳名称" v-model="formData.name" />
				</view>
			</view>
			<view class="image-tag" v-if="imageTagArray.length > 0">
				<view class="box-title">
					图片可能是：
				</view>
				<view class="tag-list">
					<view :class="{'tag-item': true, 'active': formData.name == item.tag } "
						v-for="(item, idx) in imageTagArray" :key="idx" @click="selectImageTag(item.tag)">
						{{ item.tag }}
					</view>
				</view>
			</view>

			<view class="content-item">
				<view class="item-title">
					摆放位置
				</view>
				<view class="input-box">
					<input type="text" placeholder="请输入收纳具体摆放位置" v-model="formData.place" />
				</view>
			</view>

			<view class="content-item" v-if="userInfo.openid == formData.openId || !formData.id">
				<view class="item-title">
					共享空间
				</view>
				<view class="input-box">
					<uni-data-checkbox mode="button" selectedColor="#5680FA" selectedTextColor="#5680FA"
						v-model="formData.isShared" :localdata="range"></uni-data-checkbox>
				</view>
			</view>




			<block v-if="formData.createUser == userInfo.openid">
				<view class="submit-btn">
					<view class="btn" @click="submit">
						保存
					</view>
				</view>
				<view v-if="formData.id" class="delete-btn" @click="deleteItem">
					删除
				</view>
			</block>
			<block v-else>
				<view class="submit-btn" v-if="formData.isEdit == 1">
					<view class="btn" @click="submit">
						保存
					</view>
				</view>
				<view v-if="formData.id && formData.isDelete == 1" class="delete-btn" @click="deleteItem">
					删除
				</view>
				<view class="no-tips" v-if="formData.isDelete != 1 || formData.isEdit != 1">
					暂未权限:
					<text v-if="formData.isEdit!=1">无法保存编辑</text>
					<text v-if="formData.isDelete!=1">无法删除</text>
				</view>
			</block>



		</view>
	</view>
</template>

<script setup>
	import { onMounted, ref, computed } from "vue";
	import request from "../../utils/request";
	import MessageBox from "../../utils/Message";
	import { useStore } from "vuex"
	import UniDataCheckbox from "@/uni_modules/uni-data-checkbox/components/uni-data-checkbox/uni-data-checkbox.vue"

	const props = defineProps(['target', 'id', 'pid', 'pName'])
	const emits = defineEmits(['close'])
	const store = useStore();

	const userInfo = computed(() => {
		return store.state.user.info
	})

	const formData = ref({
		id: "",
		pid: "",
		place: "",
		cover: "",
		name: "",
		isShared: 0,
		isEdit: "1",
		isDelete: "1",
		openId: "",
		createUser: ""
	});

	const range = ref([{ "value": 0, "text": "启用" }, { "value": 1, "text": "停用" }])

	const pidForm = ref({});
	const imageTagArray = ref([]);

	onMounted(() => {
		formData.value.id = props.id;
		formData.value.pid = props.pid;
		if (props.id) {
			getDetails()
		}

	})

	const closePopup = () => {
		emits('close', false)
	}

	const getDetails = async () => {
		try {
			let response = await new request().get("/warehouseItems/load", { id: formData.value.id })
			if (response.code == 200) {
				for (let key in formData.value) {
					formData.value[key] = response.data[key]
				}
				formData.value.isShared = Number(formData.value.isShared)
				return
			}
			throw new Error(response.message);
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message)

			emits('close', false)
		}
	}


	const changeImage = () => {
		uni.chooseMedia({
			count: 1,
			mediaType: ["image"],
			sizeType: ["compressed"],
			success: async (result) => {
				new MessageBox().loading("上传中...");
				uni.uploadFile({
					url: store.state.system.baseUrl + "/upload/uploadImage",
					filePath: result.tempFiles[0].tempFilePath,
					name: 'file',
					header: {
						openid: uni.getStorageSync("openId")
					},
					success(response) {
						try {
							let params = JSON.parse(response.data);
							if (params.code == 200) {
								formData.value.cover = params.data;
								getImageTagArray(params.data)
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

					}
				})
			}
		})
	}

	const submit = async () => {
		try {
			if (!formData.value.name) throw new Error(`请输入${props.target == 'res' ? '物品' : '收纳'}名称`)
			let response = await new request().post("/warehouseItems/save", formData.value);
			if (response.code == 200) {
				new MessageBox().success(`${formData.value.id ? '编辑' : '新增'}成功!`)
				emits('close', false)
				return
			}
			throw new Error(response.message)
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message)
		}
	}

	const deleteItem = async () => {
		try {
			let response = await new request().delete("/warehouseItems/deleteItem?id=" + formData.value
				.id, { id: formData.value.id })
			if (response.code == 200) {
				new MessageBox().success("删除成功，已移至回收站，可以前往回收站恢复")
				emits('close', false)
				return
			}
			throw new Error(response.message)
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message);
		}
	}

	const getImageTagArray = async (url) => {
		try {
			let response = await new request().post("/imageTagging/identify", { imageUrl: url });
			if (response.code == 200) {
				imageTagArray.value = response.data.tags
				return
			}
			throw new Error(response.message)
		} catch (e) {
			//TODO handle the exception
			new MessageBox().error(e.message)
		}
	}

	const selectImageTag = (tag) => {
		formData.value.name = tag;
	}
</script>

<style lang="scss" scoped>
	.new-collect-component {
		padding: 30upx;
		box-sizing: border-box;

		.component-head {
			display: flex;
			align-items: center;
			justify-content: space-between;
			height: 80upx;
			margin-bottom: 20upx;

			.title {
				font-size: 40upx;
				font-family: PingFang SC;
				font-weight: bold;
				color: #333;
			}

			.close-icon {
				width: 34upx;
				height: 34upx;
			}
		}

		.component-content {
			.content-item {
				margin-bottom: 20upx;
				padding-top: 20upx;
				// padding-bottom: 20upx;
				border-bottom: 2upx solid #D6D6D6;

				.item-title {
					font-size: 28upx;
					font-family: PingFang SC;
					font-weight: bold;
					color: rgba(32, 49, 82, 1);
					// margin-bottom: 14upx;
				}

				.input-box {
					width: 100%;
					height: 88upx;

					input {
						width: 100%;
						height: 100%;
						font-size: 36upx;
						font-weight: normal;
						font-family: PingFang SC;
					}
				}

				.upload-box {
					width: 100%;
					margin-top: 14upx;

					image {
						width: 180upx;
						height: 180upx;
						background-color: #D6D6D6;
					}
				}
			}

			.submit-btn {
				width: 100%;
				display: flex;
				align-items: center;
				justify-content: center;
				margin-top: 100upx;

				.btn {
					width: 60%;
					height: 90upx;
					border-radius: 55upx;
					background: #5680FA;
					// box-shadow: 0rpx 40upx 0upx #5680FA, 0rpx 20upx 40upx #000000;
					box-shadow: 0rpx 10upx 20upx 10upx rgba(86, 128, 250, 0.3);

					display: flex;
					align-items: center;
					justify-content: center;

					font-size: 32upx;
					font-weight: bold;
					color: rgba(255, 255, 255, 1);
					font-family: PingFang SC;
				}
			}

			.delete-btn {
				width: 100%;
				text-align: center;
				margin-top: 40upx;
				color: orangered;
				font-size: 32upx;
				font-weight: bold;
				font-family: PingFang SC;
				text-decoration: underline;
			}
		}
	}

	.no-tips {
		display: flex;
		align-items: center;
		font-size: 24upx;
		color: #ccc;
		justify-content: center;
		margin-top: 40upx;

		text {
			text-decoration: underline;
			margin-right: 10upx;
		}
	}

	.image-tag {
		margin-bottom: 30upx;

		.box-title {
			font-size: 28upx;
			font-weight: bold;
			color: #333;
			margin-bottom: 12upx;
		}

		.tag-list {
			display: flex;
			flex-wrap: wrap;

			.tag-item {
				padding: 8upx 16upx;
				box-sizing: border-box;
				background-color: #e6e6e6;

				font-size: 24upx;
				color: #333;
				border-radius: 8upx;
				margin: 0 14upx 14upx 0;
			}

			.active {
				border: 2upx solid #5680FA;
				color: #5680FA;
			}
		}
	}
</style>