const { getHeroListController } = require("../../controller/hero/index");

const router = require("koa-router")({
  prefix: "/api",
});

router.get("/hero/list", async (ctx, next) => {
  let res = await new getHeroListController().op(ctx);
  ctx.body = res
});

router.post("/hero/detail", async (ctx, next) => {
  let res = await new getHeroListController().getDetail(ctx);
  ctx.body = res
});

module.exports = router;
