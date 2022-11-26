const { getSystem } = require("../../controller/system");

const router = require("koa-router")({
  prefix: "/api",
});

router.get("/system/config", async (ctx, next) => {
  let res = await new getSystem().config(ctx)
  ctx.body = res
});

router.post("/system/activity/subscribe", async (ctx, next) => {
  let res = await new getSystem().activitySubscribe(ctx)
  ctx.body = res
})

router.get("/system/activity/state", async (ctx, next) => {
  let res = await new getSystem().activityUserState(ctx)
  ctx.body = res
})

module.exports = router;
