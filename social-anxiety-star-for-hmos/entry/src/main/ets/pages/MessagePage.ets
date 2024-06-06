@Component
export default struct MessagePage {
  build(){
    Column(){
      Row(){
        Column(){
          Image($r("app.media.like_message_icon")).width(52).height(52).margin({ bottom: 6 })
          Text("获赞").fontSize(12).fontColor("#333333").fontWeight(FontWeight.Bold)
          // @ts-ignore
        }.alignItems(FlexAlign.Center)

        Column(){
          Image($r("app.media.follow_message_icon")).width(52).height(52).margin({ bottom: 6 })
          Text("粉丝").fontSize(12).fontColor("#333333").fontWeight(FontWeight.Bold)
          // @ts-ignore
        }.alignItems(FlexAlign.Center)

        Column(){
          Image($r("app.media.comment_message_icon")).width(52).height(52).margin({ bottom: 6 })
          Text("评论").fontSize(12).fontColor("#333333").fontWeight(FontWeight.Bold)
          // @ts-ignore
        }.alignItems(FlexAlign.Center)
      }.width("100%").padding({ right: 30, left: 30, top: 30, bottom: 30 }).justifyContent(FlexAlign.SpaceAround)

      List(){
        ListItem(){
          Flex({ alignItems: ItemAlign.Center, }){
            Row(){
              Image($r("app.media.system_jump_message_icon")).width(52).height(52)
            }.margin({ right: 15 }).width(52)
            Column(){
              Row(){
                Text("系统通知").fontSize(16).fontColor("#333333").fontWeight(FontWeight.Bold)
                Text("11:34").fontSize(12).fontColor("#b0b0b0")
              }.justifyContent(FlexAlign.SpaceBetween).width("100%").margin({ bottom: 6 })

              Text("内容内容内容内容能让你让你让男人内容内容内容...").textOverflow({ overflow: TextOverflow.Ellipsis }).width("100%").maxLines(1).fontSize(14).fontColor("#333333")
            }
          }
        }
      }.padding(15)
    }.width("100%").height("100%")
  }
}