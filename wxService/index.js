const { wxConfig, wxSql } = require("../config/config");
const { httpFn } = require("../utils/http");
const { newError } = require("../utils/message");
const { verification } = require("../utils/verification");
class wxHttp extends verification {
  constructor() {
    super();
  }

  add(query, method) {
    return new Promise(async (R) => {
      try {
        let accToken = await this.getWxAccessToken();
        if (accToken) {
          let wxHttp = null;
          if (method == "post") {
            wxHttp = await new httpFn().post(
              `${wxSql.databaseAdd}?access_token=${accToken}`,
              query
            );
          } else {
            wxHttp = await new httpFn().get(
              `${wxSql.databaseAdd}?access_token=${accToken}`,
              query
            );
          }
          R(wxHttp.body);
        } else {
          R(new newError({ msg: "缺少微信token" }));
        }
      } catch (error) {
        console.log(error);
        R(new newError({ msg: error }));
      }
    });
  }

  check(query, method) {
    return new Promise(async (R) => {
      try {
        let accToken = await this.getWxAccessToken();
        if (accToken) {
          let wxHttp = null;
          if (method == "post") {
            wxHttp = await new httpFn().post(
              `${wxSql.databaseQuery}?access_token=${accToken}`,
              query
            );
          } else {
            wxHttp = await new httpFn().get(
              `${wxSql.databaseQuery}?access_token=${accToken}`,
              query
            );
          }
          R(wxHttp.body);
        } else {
          R(new newError({ msg: "缺少微信token" }));
        }
      } catch (error) {
        console.log(error);
        R(new newError({ msg: error }));
      }
    });
  }

  wxUpload(query){
    return new Promise(async (R) => {
      try {
        let accToken = await this.getWxAccessToken();
        let wxHttp = await new httpFn().post(
          `${wxSql.uploadUrl}?access_token=${accToken}`,
          query
        )
        R(wxHttp.body)
      } catch (error) {
        console.log(error);
        R(new newError({ msg: error }));
      }
    })
  }

  update(query){
    return new Promise(async (R) => {
      try {
        let accToken = await this.getWxAccessToken();
        if (accToken){
          let wxHttp = await new httpFn().post(
            `${wxSql.databaseUpdate}?access_token=${accToken}`,
            query
          );
          R(wxHttp.body);
        }else {
          R(new newError({ msg: "缺少微信token" }));
        }
      } catch (error) {
        console.log(error);
        R(new newError({ msg: error }));
      }
    })
  }
}

module.exports = {
  wxHttp,
};
