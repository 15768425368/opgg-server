/**
 * 全局通用页面图标组件
 * @author zhang
 * @date 2024年5月16日
 */
import router from '@ohos.router'

@Component
export default struct PageHeaderCom {
  @Prop
  title: string;

  @Prop
  bgColor: string;

  @Prop
  color: string;


  build() {
    Row() {
      Column(){
        Image($r("app.media.return_icon")).width(18).height(18)
      }.width(50).onClick(() => {
        router.back();
      })
      Text(this.title).fontColor(this.color ? this.color : "#333333").fontSize(18).fontWeight(FontWeight.Bold).flexGrow(1).textAlign(TextAlign.Center);
      Column(){}.width(50)
    }.width("100%")
    .height(52)
    .backgroundColor(this.bgColor ? this.bgColor : "#ffffff")
    .justifyContent(FlexAlign.SpaceBetween)
    .alignItems(VerticalAlign.Center)
    .border({ width: { bottom: 2 }, color: "#efefef", style: BorderStyle.Solid })
  }
}