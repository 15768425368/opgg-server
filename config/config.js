const wxConfig = {
    accTokenUrl: 'https://api.weixin.qq.com/cgi-bin/token',
    appId: '******************',
    originalId: '******************',
    appSecret: '******************',
    env: 'cloud1-******************',
    uploadFunName: 'petAdoption'
}

const wxSql = {
    databaseAdd: 'https://api.weixin.qq.com/tcb/databaseadd', // 插入
    databaseUpdate: 'https://api.weixin.qq.com/tcb/databaseupdate', // 更新
    databaseQuery: 'https://api.weixin.qq.com/tcb/databasequery', // 查询
    uploadUrl: 'https://api.weixin.qq.com/tcb/uploadfile', // 获取上传链接
    uploadPublic_image: 'Image',
    getPhoneNumber: 'https://api.weixin.qq.com/wxa/business/getuserphonenumber', // 获取手机号
    code2Session: 'https://api.weixin.qq.com/sns/jscode2session', // 小程序登录
}

const redisConfig = {
    port: 6379,
    host: '******************'
    // host: '127.0.0.1'
}

module.exports = {
    wxConfig,
    redisConfig,
    wxSql
}