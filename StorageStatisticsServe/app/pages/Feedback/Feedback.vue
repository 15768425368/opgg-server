<template>
	<AppMain>
		<template v-slot:body>
			<view class="feedback-page">
				<view class="feedback-item">
					<textarea v-model="formData.content" class="textarea" cols="30" rows="10"
						placeholder="请输入你的意见或建议"></textarea>
				</view>
				<!-- <view class="feedback-item">
					<view class="feedback-item-title">
						联系方式(非必填)
					</view>

					<input v-model="formData.phone" type="number" placeholder="请输入..." />
				</view> -->
			</view>

			<view class="footer-button">
				<view class="content" @click="submit">
					提交
				</view>
			</view>
		</template>
	</AppMain>
</template>

<script setup>
	import { ref } from "vue";
	import AppMain from "@/components/AppMain/AppMain.vue";
	import MessageBox from "../../utils/Message";
	import request from "../../utils/request";

	const formData = ref({
		content: "",
		phone: ""
	})

	const submit = async () => {
		try {
			if (!formData.value.content) throw new Error("请输入您的意见或建议~")
			let response = await new request().post("/feedback/insert", formData.value);
			if (response.code == 200) {
				new MessageBox().success("提交成功!")
				setTimeout(() => {
					uni.navigateBack({
						delta: -1
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
</script>

<style lang="scss" scoped>
	.feedback-page {
		padding: 30upx;
		box-sizing: border-box;

		.feedback-item {
			padding: 26upx;
			box-sizing: border-box;
			background-color: #fff;
			border-radius: 16upx;

			.feedback-item-title {
				font-size: 28upx;
				font-weight: bold;
				color: rgba(51, 51, 51, 1);
				margin-bottom: 32upx;
			}

			.textarea {
				background-color: rgba(240, 240, 240, 1);
				width: 100%;
				border-radius: 16upx;
				padding: 26upx;
				box-sizing: border-box;

				font-size: 28upx;
				font-weight: normal;
				color: #333;
			}

			input {
				width: 100%;
				height: 68upx;

				background-color: rgba(240, 240, 240, 1);
				border-radius: 16upx;
				padding: 0 26upx;
				box-sizing: border-box;
				font-size: 28upx;
				font-weight: normal;
				color: #333;
			}
		}
	}

	.footer-button {
		position: fixed;
		bottom: 8%;
		left: 0;
		width: 100%;
		display: flex;
		justify-content: center;

		.content {
			width: 60%;
			height: 88upx;
			border-radius: 44upx;
			background: #5680FA;

			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 32upx;
			font-weight: bold;
			color: rgba(255, 255, 255, 1);
		}
	}
</style>