
/**
 * 视频作品详情页面
 * @author zhang
 * @date 2024年6月4日 15点14分
 */

import router from '@ohos.router'

let storage = LocalStorage.GetShared()

@Entry(storage)
@Component
struct DetailsOfVideoWorks {
  private videoController: VideoController = new VideoController();

  @State whetherToExpandTheDetails: boolean = false;

  @LocalStorageLink("statusBarHeight")
  statusBarHeight: number = 0; // 状态栏高度

  build(){
    Column(){
      Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
        Image($r("app.media.arrowBackOutline")).width(16).height(16).onClick(() => {
          router.back()
        })
        Text("正在播放").fontSize(14).fontWeight(FontWeight.Bold).fontColor(Color.Black)
        Row().width(16).height(16)
      }.padding({ top: this.statusBarHeight + 30 + 'px', right: "15px", bottom: "30px", left: "20px" }).backgroundColor(Color.White).width("100%")

      Stack(){
        Video({ src: "https://edata-jiaoxianghui-music.oss-cn-shenzhen.aliyuncs.com/file/1714383958758040", controller: this.videoController }).width("100%").height("100%").autoPlay(true).loop(true)
      }.width("100%").height(200).backgroundColor("#efefef")

      Text("理查·斯特劳斯《查拉图斯特拉如是说》，作品30").fontSize(18).fontWeight(FontWeight.Bold).fontColor(Color.Black).padding(15).maxLines(2).textOverflow({ overflow: TextOverflow.Ellipsis }).width("100%").textAlign(TextAlign.Start)

      Scroll(){
        Column(){
          Column(){
            Text("详情介绍").moduleTitleStyle()
            Text("作品名称：耶安·西贝柳斯《卡累利亚》=").fontSize(14).fontColor("#b0b0b0").lineHeight(22).margin({ bottom: 10 }).maxLines(this.whetherToExpandTheDetails ? 999 : 3).textOverflow({ overflow: TextOverflow.Ellipsis })
            Row(){
              Image($r("app.media.open_more_icons")).width(16).height(16).margin({ right: 6 }).rotate({ angle: this.whetherToExpandTheDetails ? 180 : 0 }).animation({
                duration: 300
              })
              Text(this.whetherToExpandTheDetails ? "收起" : "展开").fontColor("#b0b0b0").fontSize(14)
              // @ts-ignore
            }.justifyContent(FlexAlign.Center).alignItems(HorizontalAlign.Center).width("100%").onClick(() => {
              this.whetherToExpandTheDetails = !this.whetherToExpandTheDetails;
            })
          }.alignItems(HorizontalAlign.Start).margin({ bottom: 20 })

          Column(){
            Text("相关视频").moduleTitleStyle()
            Column(){
              ForEach([1,2,3,4,5,6,7], () => {
                Flex({ alignItems: ItemAlign.Center }){
                  Image("").width(120).height(60).backgroundColor("#efefef").borderRadius(6).margin({ right: 10 })
                  Column(){
                    Text("《卡门 中提琴》").fontSize(14).fontColor(Color.Black).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).margin({ bottom: 6 })
                    Text("作品名称：耶安·西贝柳斯《卡累利亚》组曲，作品11 演出时间：2015年3首乐曲组成的").fontSize(12).fontColor("#b0b0b0").maxLines(2).textOverflow({ overflow: TextOverflow.Ellipsis })
                  }.alignItems(HorizontalAlign.Start)
                }.margin({ bottom: 14 })
              })
            }
          }.alignItems(HorizontalAlign.Start)

          Column().width("100%").height(320)
        }.padding(15)
      }
    }.width("100%").height("100%").backgroundColor(Color.White)
  }
}

@Extend(Text) function moduleTitleStyle() {
  .fontSize(14).fontColor("#333").margin({ bottom: 10 }).fontWeight(FontWeight.Bold)
}