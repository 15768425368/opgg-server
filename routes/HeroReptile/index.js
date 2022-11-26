const { getHeroReptile } = require("../../controller/HeroReptile");

const router = require("koa-router")({
    prefix: "/api",
  });
  
  // 同步英雄列表
  router.post("/hero/reptile", async (ctx, next) => {
    let res = await new getHeroReptile().op(ctx);
    ctx.body = res
  });

  // 单个同步英雄详情
  router.post("/hero/detail/reptile", async (ctx, next) => {
    let res = await new getHeroReptile().singleSync(ctx);
    ctx.body = res
  })

  // 全部同步
  router.post("/hero/detail/many/reptile", async (ctx, next) => {
    let res = await new getHeroReptile().manySync(ctx);
    ctx.body = res
  })

  // 双排助手同步
  router.post("/hero/double/reptile", async(ctx, next) => {
    let res = await new getHeroReptile().doubleSync(ctx);
    ctx.body = res
  })

  
  module.exports = router;