@Component
export default  struct Artist {

  @State departmentArray: string[] = ["指挥", "弦乐", "木管", "铜管", "打击乐器", "色彩乐器", "重奏组"]

  build(){
    Column(){
      Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
        Text("推荐艺术家").fontSize(14).fontColor("#b0b0b0")
        Text("关注艺术家 >").fontSize(14).fontColor("#b0b0b0")
      }.width("100%").height(46)

      List(){
        ListItem(){
          Text("全部").fontSize(14).padding({ top: 4, left: 6, bottom: 4, right: 6 }).backgroundColor("#CF3230").fontColor(Color.White).borderRadius(20)
        }.margin({ right: 6 })
        ForEach(this.departmentArray, (item) => {
          ListItem(){
            Text(item).fontSize(14).padding({ top: 4, left: 6, bottom: 4, right: 6 }).backgroundColor(Color.White).fontColor(Color.Black).borderRadius(20)
          }.margin({ right: 6 })
        })
      }.listDirection(Axis.Horizontal).width("100%").height(36).margin({ bottom: 10 })

      List(){
        ForEach([1,2,3,4,5,6,5,6,4,3,2,6,7], (item, idx) => {
          ListItem(){
            Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }) {
              Row(){
                Image("").width(50).height(50).borderRadius(25).backgroundColor("#efefef").margin({ right: 8 })
                Column(){
                  Text("余隆" + idx).fontSize(16).fontColor(Color.Black).margin({ bottom: 4 }).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis })
                  Text("单簧管演奏家，广州交响乐团单簧管首席，中国交响乐发展基金会单簧管研究会常务理事").fontSize(14).fontColor("#b0b0b0").maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis })
                }.alignItems(HorizontalAlign.Start)
              }.width("60%")
              Text("关注").padding({ top: 2, right: 12, bottom: 2, left: 12 }).border({ width: 1, color: "#b0b0b0", radius: 30 }).fontSize(14)
            }
          }.margin({ bottom: 16 })
        })

        ListItem(){
          Row(){


          }.width("100%").height(200)
        }
      }
    }.width("100%").height("100%").padding({ left: 15, right: 15 }).backgroundColor(Color.White)
  }
}