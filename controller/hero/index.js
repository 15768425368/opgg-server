const { wxConfig } = require("../../config/config");
const { newError, newSuccess } = require("../../utils/message");
const { verification } = require("../../utils/verification");
const { wxHttp } = require("../../wxService/index");

/**
 * 获取英雄列表
 */
class getHeroListController extends verification {
  constructor() {
    super();
  }

  async op(ctx) {
    return new Promise(async (r) => {
      try {
        let { query } = ctx;
        query.pageNum = query.pageNum || 1;
        let pageNum = query.pageNum == 1 ? 0 : --query.pageNum;
        let pageSize = query.pageSize || 20;
        pageNum = pageNum * pageSize
        if(query.pageNum) delete query.pageNum;
        if(query.pageSize) delete query.pageSize;
        let queryStr = await this.formatParams(query);


        let datalist = await new wxHttp().check(
          {
            env: wxConfig.env,
            query: `db.collection("hero_list").where({${queryStr}}).limit(${pageSize}).skip(${pageNum}).get()`,
          },
          "post"
        );

        let json = datalist.data.map((item) => {
          return JSON.parse(item);
        });
        datalist.data = json;
        r(
          await new newSuccess({
            params: datalist,
          })
        );
      } catch (error) {
        r(new newError({ params: JSON.stringify(error) }));
      }
    });
  }

  getDetail(ctx) {
    return new Promise(async (R) => {
      try {
        let params = ctx.request.body;
        if (!params.id) {
          R(new newError({ msg: "请输入id" }));
          return;
        }

        let asyncWx = await new wxHttp().check({
          env: wxConfig.env,
          query: `db.collection("hero_list").where({ _id: '${params.id}' }).get()`
        }, 'post')

        if(asyncWx.errcode == 0){
          R(new newSuccess({ params: JSON.parse(asyncWx.data[0]) }))
        }else {
          throw new Error('获取失败')
        }
      } catch (error) {
        R(new newError({ msg: JSON.stringify(error) }));
      }
    });
  }
}

module.exports = {
  getHeroListController,
};
