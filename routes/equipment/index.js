const { getEquipment } = require("../../controller/equipment");

const router = require("koa-router")({
  prefix: "/api",
});

router.get("/equipment/list", async (ctx, next) => {
  let res = await new getEquipment().op(ctx);
  ctx.body = res;
});

module.exports = router;
