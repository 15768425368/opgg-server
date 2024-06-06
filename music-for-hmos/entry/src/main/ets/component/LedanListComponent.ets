/**
 * 乐单列表组件
 * @author zhang
 * @date 2024年6月5日
 */

@Component
export default struct LedanListComponent {
  /**
   * 作品数据内容
   */
  @Provide items: any = {}

  build(){
    Navigator({ target: "pages/views/SongListDetails" }){
      Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
        Row(){
          Image("").width(60).height(60).borderRadius(8).margin({ right: 10 }).backgroundColor("#efefef")
          Column(){
            Text("GSO广州新年音乐会").fontSize(14).fontColor(Color.Black).fontWeight(FontWeight.Bold).margin({ bottom: 6 }).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis })
            Text("作品名称：耶安·西贝柳斯《卡累利亚》组曲，作品11 演出时间：2015年3月14日 演出地").fontSize(12).fontColor("#b0b0b0").maxLines(2).textOverflow({ overflow: TextOverflow.Ellipsis })
          }.alignItems(HorizontalAlign.Start)
          // @ts-ignore
        }.alignItems(HorizontalAlign.Center).width("70%")
        Row(){
          Image($r("app.media.more_arrow_icons")).width(14).height(14)
        }.width(40).justifyContent(FlexAlign.End)
      }
    }
  }
}