const { getRune } = require("../../controller/rune");

const router = require("koa-router")({
  prefix: "/api",
});

router.get("/rune/list", async (ctx, next) => {
  let res = await new getRune().op(ctx);
  ctx.body = res;
});

module.exports = router;
