/**
 * 作品数据类型
 */
interface workDataTypeTs {
  workId: number       // 作品id
  workNumber: string       // 作品编号
  title: string       // 作品标题
  content: string       // 作品内容
  userId: number       // 作品所属用户id
  releaseType: string       // 发布类型：0全网、1仅自己、2仅好友
  download: string       // 是否可下载：0是、1否
  longitude: string       // 发布时用户精度
  latitude: string       // 发布时用户纬度
  city: number       // 发布城市表id
  heat: string       // 热度值 (点赞/浏览*权重百分百) + (评论/浏览*权重百分百) / 发布时间距离当前时间天数
  status: string       // 作品状态：0发布、1草稿箱、2封禁
  cover: string       // 封面
  createUser: number       // 创建人
  createTime: number       // 创建时间
  updateTime: number       // 更新时间
  avatar: string // 用户头像
  nickName: string // 用户昵称
  isLike: boolean // 是否点赞
}

interface queryWorkParametersTs {
  pageNum: number
  pageSize: number
  keyword?: string
  type?: number
  longitude: number
  latitude: number
}

export {
  workDataTypeTs,
  queryWorkParametersTs
}