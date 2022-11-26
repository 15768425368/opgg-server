class newError {
  constructor(data) {
    Object.assign(this, {
      code: 500,
      msg: data.msg || "系统错误",
      data: data.params || null,
    });
  }
}

class newSuccess {
  constructor(data) {
    return new Promise((r) => {
      r({
        code: 200,
        msg: data.msg || "请求成功",
        data: data.params || null,
      });
    });
  }
}

module.exports = {
  newError,
  newSuccess,
};
