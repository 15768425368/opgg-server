export default class MessageBox {
	success(title, duration = 2000, mask = true) {
		return new Promise((result) => {
			uni.showToast({
				title,
				icon: "success",
				duration,
				mask,
				success() {
					console.log(`MessageBox组件内容：${title}`)
					result(true);
				}
			});
		})
	}

	error(title, duration = 2000, mask = true) {
		return new Promise((result) => {
			uni.showToast({
				title,
				icon: "error",
				duration,
				mask,
				success() {
					console.log(`MessageBox组件内容：${title}`)
					result(true);
				}
			});
		})
	}

	tips(title, duration = 2000, mask = true) {
		return new Promise((result) => {
			uni.showToast({
				title,
				icon: "none",
				duration,
				mask,
				success() {
					console.log(`MessageBox组件内容：${title}`)
					result(true);
				}
			});
		})
	}

	loading(title, mask = true) {
		return new Promise((result) => {
			uni.showLoading({
				title,
				mask,
				success() {
					console.log(`MessageBox组件内容：${title}`)
					result(true);
				}
			})
		})
	}

	dialog(content, title = "提示", showCancel = false) {
		return new Promise((resolve, reject) => {
			uni.showModal({
				title,
				content,
				showCancel,
				success(res) {
					if (res.confirm) {
						resolve("confirm");
					} else if (res.cancel) {
						resolve("cancel");
					}
				},
				fail() {
					reject(new Error("showModal error"));
				},
			});
		});
	}
}