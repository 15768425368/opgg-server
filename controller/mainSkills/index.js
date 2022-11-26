const { wxConfig } = require("../../config/config");
const { newSuccess, newError } = require("../../utils/message");
const { verification } = require("../../utils/verification");
const { wxHttp } = require("../../wxService");

/**
 * 召唤师技能
 */
class getMainSkills extends verification {
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
                query: `db.collection("main_skills").where({${queryStr}}).get()`,
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
  getMainSkills,
};
