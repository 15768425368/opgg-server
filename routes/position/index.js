const { getPosition } = require("../../controller/position");

const router = require("koa-router")({
  prefix: "/api",
});

router.get("/position/list", async (ctx, next) => {
  let res = await new getPosition().op(ctx);
  ctx.body = res;
});

module.exports = router;
