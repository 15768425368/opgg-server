@Component
export default struct PersonalCenter {
  build(){
    Column(){
      Scroll(){
        Column(){
          Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
            Text("我的").fontSize(22).fontWeight(FontWeight.Bold).fontColor(Color.Black).width(100)
            Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.End }){
              Image($r("app.media.set_icons")).width(22).height(22)
              Image($r("app.media.menu_icon")).width(22).height(22).margin({ left: 14 })
            }
          }.width("100%").margin({ bottom: 14})

          Column(){
            Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
              Flex({ alignItems: ItemAlign.Center }){
                Image("").backgroundColor("#cccccc").width(50).height(50).borderRadius(25).margin({ right: 10 })
                Column(){
                  Text("郭老师卖牛杂").fontSize(16).fontWeight(FontWeight.Bold).fontColor(Color.Black).margin({ bottom: 4 })
                  Text("Lv1").padding({ top: 1, left: 4, bottom: 1, right: 4 }).fontSize(10).border({ width: 1, color: "#CF3230" }).fontColor("#CF3230").borderRadius(8)
                }.alignItems(HorizontalAlign.Start)
              }

              Image($r("app.media.more_arrow_icons")).width(14).height(14)
            }.margin({ bottom: 26 }).padding({ left: 14, right: 14 })

            Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
              Column(){
                Text("224").fontSize(14).fontColor(Color.Black).margin({ bottom: 2 }).fontWeight(FontWeight.Bold)
                Text("消息").fontSize(12).fontColor("#b0b0b0")
              }.width("20%")

              Column(){
                Text("12").fontSize(14).fontColor(Color.Black).margin({ bottom: 2 }).fontWeight(FontWeight.Bold)
                Text("收藏").fontSize(12).fontColor("#b0b0b0")
              }.width("20%")

              Column(){
                Text("2").fontSize(14).fontColor(Color.Black).margin({ bottom: 2 }).fontWeight(FontWeight.Bold)
                Text("地址").fontSize(12).fontColor("#b0b0b0")
              }.width("20%")

              Column(){
                Text("26").fontSize(14).fontColor(Color.Black).margin({ bottom: 2 }).fontWeight(FontWeight.Bold)
                Text("最近").fontSize(12).fontColor("#b0b0b0")
              }.width("20%")
            }
          }.padding({ top: 14, bottom: 14 }).backgroundColor(Color.White).borderRadius(6).margin({ bottom: 14 })

          Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
            Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
              Flex({ alignItems: ItemAlign.Center }){
                Image($r("app.media.creator_icon")).width(22).height(22).margin({ right: 8 })
                Text("创作者中心").fontSize(14).fontColor(Color.Black).fontWeight(FontWeight.Bold)
              }
              Image($r("app.media.more_arrow_icons")).width(10).height(10)
            }.width("49%").backgroundColor(Color.White).padding(14).borderRadius(8)

            Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
              Flex({ alignItems: ItemAlign.Center }){
                Image($r("app.media.my_order_icon")).width(22).height(22).margin({ right: 8 })
                Text("我的订单").fontSize(14).fontColor(Color.Black).fontWeight(FontWeight.Bold)
              }
              Image($r("app.media.more_arrow_icons")).width(10).height(10)
            }.width("49%").backgroundColor(Color.White).padding(14).borderRadius(8)
          }.margin({ bottom: 14 })

          Column(){
            Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
              Text("自建歌单").fontWeight(FontWeight.Bold).fontSize(16)
              Row(){
                Text("查看全部4张").fontSize(12).fontColor("#b0b0b0")
                Image($r("app.media.more_arrow_icons")).width(10).height(10).margin({ left:6 })
                // @ts-ignore
              }.alignItems(HorizontalAlign.Center)
            }.width("100%").margin({ bottom: 10 })

            Column(){
              ForEach([1,2,3], (item, idx) => {
                Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }) {
                  Row(){
                    Image("").width(50).height(50).backgroundColor("#efefef").borderRadius(8).margin({ right: 8 })
                    Column(){
                      Text("默认收藏").fontColor(Color.Black).fontSize(14).margin({ bottom: 6 })
                      Text("320首").fontColor("#b0b0b0").fontSize(12)
                    }.alignItems(HorizontalAlign.Start)
                    // @ts-ignore
                  }.alignItems(HorizontalAlign.Center)
                  Image($r("app.media.more_menu_icons")).width(16).height(16)
                }.margin({ bottom: idx == 2 ? 0 : 12 })
              })
            }
          }.padding(14).backgroundColor(Color.White).borderRadius(8).margin({ bottom: 14 })

          Column(){
            Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
              Text("收藏歌单").fontWeight(FontWeight.Bold).fontSize(16)
              Row(){
                Text("查看全部4张").fontSize(12).fontColor("#b0b0b0")
                Image($r("app.media.more_arrow_icons")).width(10).height(10).margin({ left:6 })
                // @ts-ignore
              }.alignItems(HorizontalAlign.Center)
            }.width("100%").margin({ bottom: 10 })

            Column(){
              ForEach([1,2,3], (item, idx) => {
                Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }) {
                  Row(){
                    Image("").width(50).height(50).backgroundColor("#efefef").borderRadius(8).margin({ right: 8 })
                    Column(){
                      Text("默认收藏").fontColor(Color.Black).fontSize(14).margin({ bottom: 6 })
                      Text("320首").fontColor("#b0b0b0").fontSize(12)
                    }.alignItems(HorizontalAlign.Start)
                    // @ts-ignore
                  }.alignItems(HorizontalAlign.Center)
                  Image($r("app.media.more_menu_icons")).width(16).height(16)
                }.margin({ bottom: idx == 2 ? 0 : 12 })
              })
            }
          }.padding(14).backgroundColor(Color.White).borderRadius(8)
        }
      }.scrollBar(BarState.Off).edgeEffect(EdgeEffect.Spring)
    }.padding(15).width("100%").height("100%")
  }
}