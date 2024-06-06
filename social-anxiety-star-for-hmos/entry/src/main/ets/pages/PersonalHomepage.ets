/**
 * 个人主页
 * @author zhang
 * @date 2024年5月20日
 */
import EmptyDataComponent from '../common/EmptyDataComponent';
import WorksListComponent from '../common/WorksListComponent';
import { workDataTypeTs } from '../entity/homePage';

@Extend(Text) function  itemTextStyle () {
  .fontSize(12)
  .fontColor("rgba(255,255,255,0.8)")
  .fontWeight(FontWeight.Normal)
}
@Component
export default struct PersonalHomepage {
  private tabsController: TabsController = new TabsController();

  @State state: number = 0;
  @State navList: string[] = ["作品", "私密", "喜欢"]

  @State listOfWorksDataSource:any[] = [];
  @State listOfWorksTatol: number = 0;

  build(){
    Column(){
      Stack(){
        Image("http://gips2.baidu.com/it/u=195724436,3554684702&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960")
          .width("100%")
          .height("100%")
        Row().width("100%").height("100%").linearGradient({
          direction: GradientDirection.Top,
          colors: [["rgba(122,107,95,1)", 0.0], ["rgba(122,107,95,0)", 1.0]]
        })

        Column(){
          // @ts-ignore
          Row(){
            Image("https://b0.bdstatic.com/524f08d212091f632919da1f2aeee601.jpg@h_1280")
              .width(64)
              .height(64)
              .backgroundColor(Color.White)
              .margin({ right: 14 })
              .border({ radius: 32, width: 2, color: Color.White })
            Column(){
              Text("张大星")
                .fontSize(22)
                .fontColor("#ffffff")
                .fontWeight(FontWeight.Bold)
                .margin({ bottom: 6 })
              Text("星星号：662482941")
                .fontSize(12)
                .fontColor("rgba(255,255,255,0.8)")
            }.width("100%").alignItems(HorizontalAlign.Start)
            // @ts-ignore
          }.alignItems(HorizontalAlign.Center).margin({ bottom: 12 })

          Text("这里是简介简介简介简介")
            .fontColor(Color.White)
            .fontSize(12)
            .maxLines(3)
            .lineHeight(21)
            .textOverflow({ overflow: TextOverflow.Ellipsis })
            .width("100%")
            .textAlign(TextAlign.Start).margin({ bottom: 20 })

          Flex({ justifyContent: FlexAlign.SpaceBetween, alignItems: ItemAlign.Center }){
            Row(){
              Column(){
                Text("4")
                  .fontSize(16)
                  .fontWeight(FontWeight.Bold)
                  .fontColor("#ffffff")
                  .margin({ bottom: 2 })
                Text("关注")
                  .itemTextStyle()
              }.margin({ right: 22 })

              Column(){
                Text("98")
                  .fontSize(16)
                  .fontWeight(FontWeight.Bold)
                  .fontColor("#ffffff")
                  .margin({ bottom: 2 })
                Text("获奖")
                  .itemTextStyle()
              }.margin({ right: 22 })

              Column(){
                Text("26")
                  .fontSize(16)
                  .fontWeight(FontWeight.Bold)
                  .fontColor("#ffffff")
                  .margin({ bottom: 2 })
                Text("粉丝")
                  .itemTextStyle()
              }
            }

            Row(){
              Text("编辑资料")
                .padding({ top: 4, left: 12, bottom: 4, right: 12 })
                .backgroundColor("rgba(255,255,255,0.1)")
                .fontColor("#ffffff")
                .border({ radius: 16, width: 1, color: Color.White })
                .fontSize(14)
            }
          }
        }.position({ x: 0, y: 80 })
        .width("100%")
        .padding({ right: 15, left: 15 })
      }.width("100%").height(300)
      List(){
        ListItem(){
          Column(){
            Row(){
              Tabs({ index: this.state, controller: this.tabsController }){
                ForEach(this.navList, (item,idx) => {
                  TabContent(){}.tabBar(this.tabItemBuilder(item, idx))
                })
              }.onChange((idx: number) => {
                this.state = idx;
              })
            }.width("100%").height(46)
            if(this.listOfWorksDataSource.length == 0) {
              Row(){
                EmptyDataComponent({ tips: "暂无作品~" })
              }.margin({ top: 100 })
            } else {
              WorksListComponent({ listOfWorksDataSource: $listOfWorksDataSource, total: $listOfWorksTatol })
            }
          }.padding({ bottom: 300 })
        }
      }.zIndex(10).margin({ top: "-40" }) .backgroundColor(Color.White).border({ radius: { topLeft: 20, topRight: 20 } })
    }
  }

  @Builder tabItemBuilder(label: string, idx: number){
    Text(label).fontSize(16).fontColor(this.state == idx ? "#000000" : "#666666")
      .fontWeight(this.state == idx ? FontWeight.Bold : FontWeight.Normal)
  }
}
