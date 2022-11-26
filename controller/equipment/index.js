const { wxConfig } = require("../../config/config");
const { newError, newSuccess } = require("../../utils/message");
const { verification } = require("../../utils/verification");
const { wxHttp } = require("../../wxService");

/**
 * 装备
 */
class getEquipment extends verification {
  constructor() {
    super();
  }

  op(ctx) {
    return new Promise(async (R) => {
      try {
        let { query } = ctx;
        let queryStr = await this.formatParams(query);
        R(
          await new newSuccess({
            params: await new wxHttp().check(
              {
                env: wxConfig.env,
                query: `db.collection("equipment").where({${queryStr}}).get()`
              },
              "post"
            ),
          })
        );
      } catch (error) {
        R(new newError({ params: JSON.stringify(error) }));
      }
    });
  }
}

module.exports = {
  getEquipment,
};
