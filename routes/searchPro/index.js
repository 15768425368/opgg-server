const { opSearchPro } = require("../../controller/searchPro");

const router = require("koa-router")({
  prefix: "/api",
});

/**
 * 获取用户搜索次数
 */
router.get("/search/getnum", async (ctx, next) => {
  let res = await new opSearchPro().getNumber(ctx)
  ctx.body = res
});

/**
 * 绑定分享者，获取搜索次数
 */
router.post('/search/share/binding', async (ctx, next) => {
  let res = await new opSearchPro().binding(ctx);
  ctx.body = res;
})

/**
 * 搜索资源
 */
router.post("/search/list", async (ctx, next) => {
  let res = await new opSearchPro().getSearchList(ctx);
  ctx.body = res;
})

/**
 * 微信转发
 */
router.get("/search/ailibaba", async (ctx, next) => {
  let res = await new opSearchPro().ailiAjax(ctx);
  ctx.body = res;
})

/**
 * 
 */
router.post("/search/getbaidupan", async (ctx, next) => {
  let res = await new opSearchPro().getBaiduPanUrl(ctx);
  ctx.body = res;
})


router.post("/search/getbaidupan/wx", async (ctx, next) => {
  let res = await new opSearchPro().ailiAjaxBaiduPan(ctx);
  ctx.body = res;
})
module.exports = router;
