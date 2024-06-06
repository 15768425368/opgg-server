<template>
	<view class="app-main-container">
		<!-- 内容插槽 -->
		<view class="app-main-container-body">
			<slot name="body"></slot>
		</view>
	</view>
</template>

<script setup>

</script>

<style lang="scss" scoped>
	.app-main-container-body {
		min-height: 100vh;
		background-color: #FAFAFA;
	}
</style>