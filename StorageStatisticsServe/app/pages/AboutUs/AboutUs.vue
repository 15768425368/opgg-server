<template>
	<AppMain>
		<template v-slot:body>
			<view class="about-us-page">
				<view class="html" v-html="infoData.aboutUs">

				</view>

				<view class="customer-service">
					联系作者：{{ infoData.customerService }}
				</view>
			</view>
		</template>
	</AppMain>
</template>

<script setup>
	import { onMounted, ref } from 'vue';
	import MessageBox from '../../utils/Message';
	import request from '../../utils/request';
	import AppMain from "@/components/AppMain/AppMain.vue";

	const infoData = ref({})

	onMounted(() => {
		getInfoData()
	})

	const getInfoData = async () => {
		try {
			let response = await new request().get("/sysConfig/getSystemParams");
			if (response.code == 200) {
				infoData.value = response.data;
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
	.about-us-page {
		padding: 30upx;
		box-sizing: border-box;
	}

	.customer-service {
		margin-top: 60upx;
		font-weight: bold;
		font-size: 32upx;
	}
</style>