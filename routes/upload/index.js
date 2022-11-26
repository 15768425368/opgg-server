const { uploadFn } = require("../../controller/upload");

const router = require("koa-router")({
  prefix: "/api",
});

router.post("/upload", async (ctx, next) => {
  let res = await new uploadFn().op(ctx);
  ctx.body = res;
});

module.exports = router;
