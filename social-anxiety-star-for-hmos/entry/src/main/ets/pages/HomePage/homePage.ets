import EmptyDataComponent from '../../common/EmptyDataComponent';
import WorksListComponent from '../../common/WorksListComponent';
import { workDataTypeTs, queryWorkParametersTs } from '../../entity/homePage';
import { MessageBox } from '../../utils/Message';
import { httpPost } from '../../utils/request';

@Component
export default struct HomePage {
  private controller: TabsController = new TabsController();
  @State currentIndex: string = "0";
  @State fontColor: string = '#b0b0b0'
  @State selectedFontColor: string = '#FF6F61';
  @State listOfWorkCategories: any[] = [{ label: "热门", value: "0" }, { label: "发现", value: "1" }, { label: "附近", value: "2" }];

  @State
  listOfWorksDataSource: any[] = [];

  @State
  listOfWorksTatol: number = 0;

  // @ts-ignore
  @State queryWorkParameters: queryWorkParametersTs = {
    pageNum: 1,
    pageSize: 20,
    type: 0
  }

  aboutToAppear(){
    // this.getWorkArray()
  }

  build(){
    Column(){
      Flex({ alignItems: ItemAlign.Center }){
        Tabs({ barPosition: BarPosition.Start, controller: this.controller }) {
          ForEach(this.listOfWorkCategories, (item) => {
            TabContent() {
            }.tabBar(this.TabBuilder(item.value, item.label))
          })
        }.onChange((index: number) => {
          this.changeNavVal(index);
        })
        .width("100%").height("100%")

        Column(){
          Image($r("app.media.search")).size({ width: 28, height: 28 })
        }.width(80).height("100%").justifyContent(FlexAlign.Center);
      }.width("100%").height(46).zIndex(10)

      Column(){
        // @ts-ignore
        if(this.listOfWorksDataSource.length > 0) {
          WorksListComponent({ listOfWorksDataSource: $listOfWorksDataSource, total: $listOfWorksTatol })
        }else {
          Row(){
            EmptyDataComponent({ tips: "网络走丢了，请稍后重新试试吧~" })
          }.margin({ top: 100 })
        }
      }
    }.width("100%").height("100%")
  }

  /**
   * 作品分类导航栏
   * @param index
   * @param name
   */
  @Builder TabBuilder(index: string, name: string) {
    Column() {
      Text(name)
        .fontColor(this.currentIndex === index ? this.selectedFontColor : this.fontColor)
        .fontSize(18)
        .fontWeight(this.currentIndex === index ? FontWeight.Bold : FontWeight.Normal)
      .margin({ bottom: 10 })
      if(this.currentIndex == index) {
        Row(){}.width("50%").height(4).backgroundColor(this.selectedFontColor).border({ radius: 4, width: 1, color: this.selectedFontColor })
      }
    }.width('100%').justifyContent(FlexAlign.Center).alignItems(HorizontalAlign.Center);
  }

  /**
   * 获取作品列表
   */
  async getWorkArray(){
    try {
      let response = await httpPost("/work/queryWorkPage", this.queryWorkParameters, getContext(this));
      if(response.code == 200) {
        this.listOfWorksDataSource = response.data.list;
        this.listOfWorksTatol = response.data.total
        return
      }
      throw new Error(response.message);
    } catch (error) {
      MessageBox(error.message)
    }
  }

  changeNavVal(index: number) {
    let val = this.listOfWorkCategories[index].value;
    if(val == this.currentIndex) return;

    this.currentIndex = this.listOfWorkCategories[index].value;
    this.queryWorkParameters.type = Number(this.listOfWorkCategories[index].value);
    this.resetWorkData()
    this.getWorkArray();
  }

  resetWorkData(){
    this.listOfWorksDataSource = [];
    this.listOfWorksTatol = 0;
    this.queryWorkParameters.pageNum = 1;
    this.queryWorkParameters.keyword = "";
    this.queryWorkParameters.longitude = 0;
    this.queryWorkParameters.latitude = 0;
  }
}

