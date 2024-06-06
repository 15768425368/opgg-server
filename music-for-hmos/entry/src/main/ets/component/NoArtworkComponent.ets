/**
 * 无图作品item
 */

@Component
export default struct NoArtworkComponent{
  /**
   * 作品数据内容
   */
  @Provide items: any = {}

  /**
   * 是否显示作品序号
   */
  @Provide showNumber?: boolean = false;
  /**
   * 当前作品序号
   */
  @Provide idx?: string = "1";

  build(){
    Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
      Row(){
        if (this.showNumber) {
          Text(this.idx).fontSize(14).fontWeight(FontWeight.Bold).margin({ right: 10 })
        }
        Column(){
          Text("西贝柳斯《卡累利亚》组曲，作品11").fontSize(14).fontColor(Color.Black).fontWeight(FontWeight.Bold).margin({ bottom: 6 }).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis })
          Row(){
            Text("SQ").fontSize(8).padding({ top: 1, right: 4, bottom: 1, left: 4 }).border({ width: 1, color: "#CF3230",radius: 4 }).fontColor("#CF3230").margin({ right: 4 })
            Text("作品名称：理查·斯特劳斯《查拉图斯特拉如是说》，作品30 演出时间：2011年6月18日 演出地点：星海音乐厅交响乐演奏大厅 指挥：大植英次").fontSize(12).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).fontColor("#b0b0b0")
          }
        }.alignItems(HorizontalAlign.Start).width("72%")
      }

      Row(){
        Image($r("app.media.favorite_icon")).width(20).height(20)
        Image($r("app.media.icon88")).width(22).height(22).margin({ left: 10 })
        // @ts-ignore
      }.alignItems(HorizontalAlign.Center).justifyContent(FlexAlign.End)
    }
  }
}