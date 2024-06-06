@Component
export default struct ShoppingMall {
  private searchController: SearchController = new SearchController()

  @State searchContent: string = "";
  @State isRefreshing: boolean = false;

  build(){
    Column(){
      // @ts-ignore
      Refresh({ refreshing: $$this.isRefreshing, offset: 120, friction: 100 }){
        Column(){
          Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
            Search({ value: this.searchContent, placeholder: "专业钢琴", controller: this.searchController }).searchButton("搜索").height(32).placeholderColor("#666666").width("70%").textFont({ size: 14, weight: FontWeight.Bold }).placeholderFont({ size: 14 })
            Row(){
              Column(){
                Image($r("app.media.cart_icon")).width(24).height(24)
                Text("购物车").fontSize(12)
              }

              Column(){
                Image($r("app.media.order_icon")).width(24).height(24)
                Text("订单").fontSize(12)
              }.margin({ left: 20 })
              // @ts-ignore
            }.width("28%").alignItems(HorizontalAlign.Center).justifyContent(FlexAlign.End)
          }.margin({ bottom: 15 })

          WaterFlow(){
            ForEach([2,1,2,3,4,34,4,5,6,7], () => {
              FlowItem(){
                Column(){
                  Image("https://edata-jiaoxianghui-music.oss-cn-shenzhen.aliyuncs.com/images/1712818379539958").width("100%").height(Math.random() < 0.5 ? 145 : 175).borderRadius({ topLeft: 6, topRight: 6 })
                  Column(){
                    Text("Magesa Biseko艺术画饰V005《芯世界——昼》").fontSize(14).fontColor(Color.Black).maxLines(2).textOverflow({ overflow: TextOverflow.Ellipsis }).margin({ bottom: 10 }).width("100%").textAlign(TextAlign.Start)
                    Text("38.9"){
                      Span("￥").fontSize(14).fontColor(Color.Black).width("100%")
                      Span("38.9").fontSize(18).fontColor(Color.Black).width("100%")
                      // @ts-ignore
                    }.textAlign(TextAlign.Start).width("100%").align(ItemAlign.Center)
                  }.padding({ top: 13, left: 6, bottom: 13, right: 6 }).backgroundColor(Color.White).borderRadius({ bottomLeft: 6, bottomRight: 6 })
                }
              }
            })
          }.columnsTemplate("1fr 1fr").columnsGap(10).rowsGap(10).width("100%").padding({ bottom: 100 })
        }
      }.onRefreshing(() => {
        setTimeout(() => {
          this.isRefreshing = false;
        }, 3000)
      })

    }.padding(15).justifyContent(FlexAlign.Start).width("100%").height("100%").backgroundColor("#efefef")
  }
}