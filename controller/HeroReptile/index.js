const { wxConfig } = require("../../config/config");
const { newError, newSuccess } = require("../../utils/message");
const { getWebFun } = require("../../utils/puppeteer");
const { verification } = require("../../utils/verification");
const { wxHttp } = require("../../wxService");

class getHeroReptile extends verification {
  constructor() {
    super();
  }

  op(ctx) {
    return new Promise(async (R) => {
      try {
        let query = ctx.request.body;
        // console.log(JSON.stringify(query));
        if (!query.web_url) {
          R(new newError({ msg: "请输入爬取链接" }));
          return;
        }
        if (!query.position_rel) {
          R(new newError({ msg: "请选择位置" }));
          return;
        }

        let html = await new getWebFun().getList(query.web_url);
        html.forEach((item) => {
          item.position_rel = query.position_rel;
        });

        let asyncWx = await new wxHttp().add(
          {
            env: wxConfig.env,
            query: `db.collection("hero_list").add({ data: ${JSON.stringify(
              html
            )} })`,
          },
          "post"
        );

        if (asyncWx.errcode == 0) {
          R(
            new newSuccess({
              msg: "爬取成功；微信云开发数据库同步成功",
              params: html,
            })
          );
        } else {
          R(
            new newSuccess({
              msg: "爬取成功；微信云开发数据库同步失败",
              params: html,
            })
          );
        }
      } catch (error) {
        R(new newError({ params: JSON.stringify(error) }));
      }
    });
  }

  singleSync(ctx) {
    return new Promise(async (R) => {
      try {
        let query = ctx.request.body;
        if (!query.id) {
          R(new newError({ msg: "请输入英雄id" }));
          return;
        }

        // 查询英雄
        let heroItem = await new wxHttp().check(
          {
            env: wxConfig.env,
            query: `db.collection("hero_list").where({ _id: '${query.id}' }).get()`,
          },
          "post"
        );

        if (heroItem.errcode == 0) {
          let item = JSON.parse(heroItem.data[0]);
          let html = await new getWebFun().singleDetail(item.details_url);

          let asyncWx = await new wxHttp().update({
            env: wxConfig.env,
            query: `db.collection("hero_list").where({ _id: '${
              query.id
            }' }).update({ data: ${JSON.stringify(html)} })`,
          });
          if (asyncWx.errcode == 0) {
            R(new newSuccess({ params: html, msg: "英雄详情同步微信成功" }));
          } else {
            R(new newSuccess({ params: html, msg: "英雄详情同步微信失败" }));
          }
        } else {
          R(new newError({ msg: "查找英雄失败" }));
        }
      } catch (error) {
        R(new newError({ msg: JSON.stringify(error) }));
      }
    });
  }

  manySync(ctx) {
    return new Promise(async (R) => {
      try {
        // 获取英雄列表
        let heroList = await new wxHttp().check(
          {
            env: wxConfig.env,
            query: `db.collection("hero_list").limit(500).get()`,
          },
          "post"
        );
        let newArray = [];
        heroList.data.forEach((item) => {
          newArray.push(JSON.parse(item));
        });

        let splitArray = this.splitArray(newArray, 300); // 拆分数组，50一组多线程爬取

        let sucNum = 0;
        splitArray.forEach(async item => {
          await this.recursionSync(0, item, [], (options) => {
            // R(new newSuccess({ msg: "同步已执行完成", params: options }));
            sucNum = sucNum + 1
          });
        })


        let time = setInterval(() => {
          if(sucNum == splitArray.length){
            R(new newSuccess({ msg: "同步已执行完成", params: splitArray.length }));
            clearInterval(time)
          }
        })

       
        return false


        await this.recursionSync(0, newArray, [], (options) => {
          R(new newSuccess({ msg: "同步已执行完成", params: options }));
        });
      } catch (error) {
        R(new newError({ msg: JSON.stringify(error) }));
      }
    });
  }

  recursionSync(idx, list,resList, callback) {
    return new Promise(async (R, E) => {
      try {
        if (idx >= list.length) {
          callback(resList);
          R(callback);
          return;
        }

        let wxSync = await this.singleSync({
          request: {
            body: {
              id: list[idx]._id,
            },
          },
        });

        if (wxSync.code != 200) {
          E(new newError({ msg: "同步失败" }));
        } else {
          resList.push(wxSync);
          await this.recursionSync(idx + 1, list,resList, callback);
        }
      } catch (error) {
        E(new newError({ msg: error }));
      }
    });
  }

  /**
   * 拆分数组
   */
   splitArray(array, size){
    let data = [];
    for (let i = 0; i < array.length; i += size) {
      data.push(array.slice(i, i + size))
    }
    return data
  }

  /**
   * 同步双排助手
   */
  doubleSync(ctx){
    return new Promise(async R => {
      try {
        // 获取需要同步的分类
        let typeList = await new wxHttp().check({
          env: wxConfig.env,
          query: `db.collection("doubleClassify").get()`
        }, 'post')
        let newArray = typeList.data.map(item => {
          return JSON.parse(item)
        })

        let resList = []
        newArray.forEach(async item => {
          let htmlJson = await new getWebFun().getDouble(item.web_url, item.type);
          let asyncWx = await new wxHttp().add({
            env:wxConfig.env,
            query: `db.collection("doubleHero").add({ data: ${JSON.stringify(
              htmlJson
            )} })`,
          }, 'post')
          resList.push(asyncWx)
        })


        let time = setInterval(() => {
          if(newArray.length == resList.length){
            R(new newSuccess({ params: resList}))
            clearInterval(time)
          }
        })
      } catch (error) {
        R(new newError({ msg: error }))
      }
    })
  }

  async asyncAouble(list, num, callback){
      try {
        if(num == list.length){
          callback({ code: 100 })
          return 
        }else{
          
        }
      } catch (error) {
        
      }
  }
}

module.exports = {
  getHeroReptile,
};
