/**
 * 对象转url拼接参数
 */
export const toQueryParams = (paramObj) => {
  const sdata = [];
  for (let attr in paramObj) {
    sdata.push(`${attr}=${paramObj[attr]}`);
  }
  return "?" + sdata.join('&');
}