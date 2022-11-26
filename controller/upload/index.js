const { wxConfig, wxSql } = require("../../config/config");
const { httpFn } = require("../../utils/http");
const { newError, newSuccess } = require("../../utils/message");
const { verification } = require("../../utils/verification");
const { wxHttp } = require("../../wxService");

class uploadFn extends verification {
  constructor() {
    super();
  }

  op(ctx) {
    return new Promise(async (R) => {
      try {
        // ctx.request.files.file.filepath
        let wxUpload = await new wxHttp().wxUpload({
          env: wxConfig.env,
          path: wxSql.uploadPublic_image + `/${ctx.request.files.file.originalFilename}`,
        });

        console.log(wxUpload);
        if (wxUpload.errcode == 0) {
          // 获取上传链接成功，开始上传
          let u = await new httpFn().upload(
            wxUpload.url,
            {
              key: wxSql.uploadPublic_image + `/${ctx.request.files.file.originalFilename}`,
              Signature: wxUpload.authorization,
              "x-cos-security-token": wxUpload.token,
              "x-cos-meta-fileid": wxUpload.cos_file_id,
              file: ctx.request.files.file,
            }
          );
          R(
            new newSuccess({ params: u.body })
          )
        } else {
          R(new newError({ msg: "上传失败" }));
        }
      } catch (error) {
        R(new newError({ params: JSON.stringify(error) }));
      }
    });
  }
}

module.exports = {
  uploadFn,
};
