const { getUser } = require("../../controller/user");

const router = require("koa-router")({
  prefix: "/api",
});

// 提交意见反馈
router.post("/user/feedback", async (ctx, next) => {
  let res = await new getUser().feedback(ctx);
  ctx.body = res;
});

// 获取用户手机号码
router.post("/user/getPhone", async (ctx, next) => {
  let res = await new getUser().getPhoneNumber(ctx)
  ctx.body = res;
})

// 获取用户的openId
router.post("/user/getOpenId", async(ctx, next) => {
  let res = await new getUser().getOpenId(ctx)
  ctx.body = res
})

// 获取用户信息
router.post("/user/info", async(ctx, next) => {
  let res = await new getUser().getUserInfo(ctx)
  ctx.body = res
})

// 设置用户信息
router.post("/user/setinfo", async(ctx, next) => {
  let res = await new getUser().setUserInfo(ctx)
  ctx.body = res
})

module.exports = router;
