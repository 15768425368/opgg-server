const { getMainSkills } = require("../../controller/mainSkills");

const router = require("koa-router")({
  prefix: "/api",
});

router.get("/mainskills/list", async (ctx, next) => {
  let res = await new getMainSkills().op(ctx);
  ctx.body = res
});

module.exports = router;
