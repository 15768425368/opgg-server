/**
 * 空数据组件
 * @author zhang
 * @date 2024年5月16日
 */

@Component
export default struct EmptyDataComponent {
  @Provide tips: string = "暂无数据~";

  build(){
    Column(){
      Image($r("app.media.empty_data_icon")).width("100%").margin({ bottom: 24 })
      Text(this.tips).fontColor("#b0b0b0").fontSize(16)
    }.padding(40).height(300)
  }
}