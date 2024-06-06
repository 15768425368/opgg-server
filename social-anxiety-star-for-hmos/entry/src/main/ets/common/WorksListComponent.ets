/**
 * 作品列表组件
 * @author zhang
 * @date 2024年5月16日
 */
import { workDataTypeTs } from '../entity/homePage'
import EmptyDataComponent from './EmptyDataComponent';
import router from '@ohos.router'


@Component
export default struct WorksListComponent {
  @Link
  listOfWorksDataSource: any[];

  @Link
  total: number;

  aboutToAppear(){
  }

  build() {
    WaterFlow(){
      ForEach(this.listOfWorksDataSource, (item:workDataTypeTs, idx:number) => {
        FlowItem() {
          Column(){
            Row(){
              Image(item.cover).width("100%").height(Math.random() < 0.5 ? 180 : 220)
                .border({ radius: { topLeft: 6, topRight: 6 } })
            }
            Column(){
              Text(item.title).fontSize(16).fontColor("#333333").margin({ bottom: 8 }).textAlign(TextAlign.Start).width("100%")
              Row(){
                Row(){
                  Image(item.avatar).width(20).height(20).border({ radius: 10 }).margin({ right: 6 })
                  Text(item.nickName).fontSize(14).fontColor("#cccccc")
                }
                Row(){
                  Image(item.isLike ? $r("app.media.likeIconed") : $r("app.media.likeIcon")).width(16).height(16).margin({ right: 4 })
                  Text("62").fontSize(14).fontColor("#b0b0b0")
                }
              }.justifyContent(FlexAlign.SpaceBetween).alignItems(VerticalAlign.Center).width("100%")
            }.padding(14)
          }
        }.backgroundColor(Color.White)
        .border({ radius: 6 }).onClick(() => {
          router.pushUrl({ url: "pages/DetailsOfWorks/DetailsOfWorks", params: { id: item.workId } })
        })
      })
    }.columnsTemplate("1fr 1fr")
    .columnsGap(10)
    .rowsGap(10)
    .height('100%')
    .width("100%")
    .backgroundColor("#efefef").padding({ top: 10, left: 10, bottom: 70, right: 10 }).zIndex(1)
  }
}