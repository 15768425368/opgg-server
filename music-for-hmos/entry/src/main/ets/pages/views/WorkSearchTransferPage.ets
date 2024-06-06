/**
 * 作品搜索中转页
 * @author zhang
 * @date 2024年6月5日
 */
import router from '@ohos.router';

let storage = LocalStorage.GetShared();

@Entry(storage)
@Component
struct WorkSearchTransferPage {
  @State keyword: string = "";

  @LocalStorageLink("statusBarHeight")
  statusBarHeight: number = 0; // 状态栏高度

  pageTransition(){
    PageTransitionEnter({ type: RouteType.None, duration: 300, curve: Curve.Ease }).slide(SlideEffect.Bottom);
    PageTransitionExit({ type: RouteType.None, duration: 300, curve: Curve.Ease }).slide(SlideEffect.Bottom)
  }

  build(){
    Scroll(){
      Column(){
        Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
          Search({ value: this.keyword, placeholder: "广州交响乐团" }).height(32).placeholderColor("#666666").textFont({ size: 14, weight: FontWeight.Bold }).placeholderFont({ size: 14 })
          Text("取消").width(80).textAlign(TextAlign.Center).onClick(() => {
            router.back();
          })
        }.margin({ bottom: 15 })

        Column(){
          Text("历史搜索").fontSize(14).fontColor(Color.Black).margin({ bottom: 14 }).fontWeight(FontWeight.Bold)
          Flex({ alignItems: ItemAlign.Center, wrap: FlexWrap.Wrap }){
            Text("西贝柳斯《卡累利亚》组曲，作品11 Ⅲ").constraintSize({ maxWidth: 130 }).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).padding({ top: 6, left: 12, bottom: 6, right: 12 }).backgroundColor("#efefef").fontSize(14).borderRadius(14).margin({ right: 10, bottom: 10 })
            Text("西贝柳").constraintSize({ maxWidth: 130 }).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).padding({ top: 6, left: 12, bottom: 6, right: 12 }).backgroundColor("#efefef").fontSize(14).borderRadius(14).margin({ right: 10, bottom: 10 })
            Text("西贝柳").constraintSize({ maxWidth: 130 }).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).padding({ top: 6, left: 12, bottom: 6, right: 12 }).backgroundColor("#efefef").fontSize(14).borderRadius(14).margin({ right: 10, bottom: 10 })
            Text("西贝柳").constraintSize({ maxWidth: 130 }).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).padding({ top: 6, left: 12, bottom: 6, right: 12 }).backgroundColor("#efefef").fontSize(14).borderRadius(14).margin({ right: 10 })
          }.width("100%").onClick(() => {
            router.replaceUrl({ url: "pages/views/AggregateSearch" })
          })
        }.alignItems(HorizontalAlign.Start).width("100%")
      }.constraintSize({ minHeight: "100%" })
    }.width("100%").height("100%").padding({  top: this.statusBarHeight + 20 + 'px', right: 15, bottom: 15, left: 15  }).backgroundColor(Color.White)
  }
}