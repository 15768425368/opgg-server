const Koa = require("koa");
const app = new Koa();
const views = require("koa-views");
const json = require("koa-json");
const onerror = require("koa-onerror");
const bodyparser = require("koa-bodyparser");
const logger = require("koa-logger");
const koaBody = require("koa-body");


app.use(koaBody({
  multipart: true,
  formidable: {
    maxFileSize: 100 * 1024 * 1024
  }
}))

// 使用redis做内存数据库，访问更快
const redisStore = require("koa-redis");

const hero = require("./routes/hero/index");
const mainSkills = require("./routes/mainSkills/index");
const position = require("./routes/position/index");
const equipment = require("./routes/equipment/index");
const rune = require("./routes/rune/index");
const upload = require("./routes/upload/index")
const HeroReptile = require("./routes/HeroReptile/index")
const user = require("./routes/user/index")
const system = require("./routes/system/index")
const searchPro = require("./routes/searchPro/index")

// error handler
onerror(app);

// middlewares
app.use(
  bodyparser({
    enableTypes: ["json", "form", "text"],
    store: redisStore({
      all: "127.0.0.1:6379",
    }),
  })
);
app.use(json());
app.use(logger());
app.use(require("koa-static")(__dirname + "/public"));

app.use(
  views(__dirname + "/views", {
    extension: "ejs",
  })
);

// logger
app.use(async (ctx, next) => {
  const start = new Date();
  await next();
  const ms = new Date() - start;
  console.log(`${ctx.method} ${ctx.url} - ${ms}ms`);
});

// routes
app.use(hero.routes(), hero.allowedMethods());
app.use(mainSkills.routes(), mainSkills.allowedMethods());
app.use(position.routes(), position.allowedMethods());
app.use(equipment.routes(), equipment.allowedMethods());
app.use(rune.routes(), rune.allowedMethods());
app.use(upload.routes(), upload.allowedMethods());
app.use(HeroReptile.routes(), HeroReptile.allowedMethods());
app.use(user.routes(), user.allowedMethods());
app.use(system.routes(), system.allowedMethods());
app.use(searchPro.routes(), searchPro.allowedMethods());

// error-handling
app.on("error", (err, ctx) => {
  console.error("server error", err, ctx);
});

// app.listen(8020, () => {
//   console.log("opgg服务已启动，http://127.0.0.1:8020");
// });

module.exports = app;
