/**
 * 时间格式化方法
 * 调用方式 const format1 = formatDate(date, "{y}-{M}-{d} {h}:{m}:{s}");
 * @param {Object} date
 * @param {Object} format
 */
export const formatDate = (date, format) => {
	const o = {
		"M+": date.getMonth() + 1, // 月份
		"d+": date.getDate(), // 日
		"h+": date.getHours(), // 小时
		"m+": date.getMinutes(), // 分
		"s+": date.getSeconds(), // 秒
		"q+": Math.floor((date.getMonth() + 3) / 3), // 季度
		S: date.getMilliseconds(), // 毫秒
	};
	if (/(y+)/.test(format))
		format = format.replace(
			RegExp.$1,
			(date.getFullYear() + "").substr(4 - RegExp.$1.length)
		);
	for (let k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(
				RegExp.$1,
				RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)
			);
	return format;
};

export const defaultImage = "/static/icon/icon11.png";


/**
 * 跳转页面
 */
export const navigateToPage = (url, type = "navigateTo") => {
	uni[type]({
		url,
	});
};