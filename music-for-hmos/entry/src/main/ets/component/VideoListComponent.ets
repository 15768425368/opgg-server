/**
 * 视频列表组件
 * @author zhang
 * @date 2024年6月5日
 */

@Component
export default struct VideoListComponent {
  build(){
    Flex({ alignItems: ItemAlign.Center }){
      Image("").width(140).height(80).backgroundColor("#efefef").margin({ right: 10 }).borderRadius(8)
      Column(){
        Text("西贝柳斯《卡累利亚》组曲，作品11 Ⅲ").fontSize(14).fontWeight(FontWeight.Bold).fontColor(Color.Black).margin({ bottom: 6 }).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis })
        Text("作品名称：耶安·西贝柳斯《卡累利亚》组曲，作品11 演出时间：2015年3月14日 演出地").fontSize(12).fontColor("#b0b0b0").maxLines(2).textOverflow({ overflow: TextOverflow.Ellipsis })
      }.alignItems(HorizontalAlign.Start)
    }
  }
}