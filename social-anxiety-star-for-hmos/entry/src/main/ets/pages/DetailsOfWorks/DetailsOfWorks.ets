import PageHeaderCom from '../../common/PageHeader';
import display from '@ohos.display';

@Entry
@Component
struct DetailsOfWorks {
  private swiperController: SwiperController = new SwiperController();
  private textInputController: TextInputController = new TextInputController();
  @State
  sizeValue:Area = null;
  @State
  pageHeight: number = 0;

  onPageShow(){
    this.getScreenHeight()
  }

  /**
   * 作品轮播图列表
   */
  @State
  listOfWorksCarouselImages:any = [
    { url: "https://img2.baidu.com/it/u=1369555074,1202293886&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1205" },
    { url: "http://img1.baidu.com/it/u=1503686764,3010748617&fm=253&app=138&f=JPEG?w=800&h=1198" },
    { url: "http://img1.baidu.com/it/u=3866235680,2006852644&fm=253&app=138&f=JPEG?w=800&h=1200" }
  ];

  build(){
    Column(){
      PageHeaderCom({ title: "作品详情", bgColor: "#ffffff", color: "#333333" })
      Stack(){
        Scroll(){
          // 轮播图
          Column(){
            Swiper(this.swiperController){
              ForEach(this.listOfWorksCarouselImages, (item, idx) => {
                Image(item.url).width("100%").height(380).backgroundColor("#efefef")
              }, idx => idx)
            }.autoPlay(true).interval(5000).indicatorStyle({
              color: "#efefef",
              selectedColor: "#FF6F61"
            })

            // 作品数据
            Column(){
              Text("我是作品标题我是作品表...").fontColor("#333333").fontSize(16).fontWeight(FontWeight.Bold).margin({ bottom: 6 }).width("100%").textAlign(TextAlign.Start)

              Text("我是作品内容内容人我是作品内容内容作品那日容...").fontSize("#333333").fontSize(16).fontWeight(FontWeight.Normal).width("100%").textAlign(TextAlign.Start)

              Row(){
                Row(){
                  Image("").width(36).height(36).border({ radius: 18 }).backgroundColor("#efefef").margin({ right: 6 })
                  Column(){
                    Text("作者名称").fontSize(14).fontColor("#b0b0b0").fontWeight(FontWeight.Normal).margin({ bottom: 2 })
                    Text("发布于3天前").textAlign(TextAlign.Start).margin({ top: 2 }).fontWeight(FontWeight.Normal).fontSize(12).fontColor("#b0b0b0")
                  }.alignItems(HorizontalAlign.Start)
                  // @ts-ignore
                }.alignItems(HorizontalAlign.Center)

                Row(){
                  Text("462人浏览").fontSize(14).fontColor("#b0b0b0").fontWeight(FontWeight.Normal)
                }
                // @ts-ignore
              }.justifyContent(FlexAlign.SpaceBetween).width("100%").margin({ top: 20 }).alignItems(HorizontalAlign.Center)

            }.padding(15)

            // 分割线
            Row().width("100%").height(6).backgroundColor("#efefef")

            // 评论列表
            Row(){
              Text("共216条评论").fontSize(14).fontColor("#b0b0b0")
            }.padding(15)
            Column(){
              ForEach([0,0,0,0,0,0,0], (item) => {
                Row(){
                  Row(){
                    Image("").width(42).height(42).border({ radius: 21 }).backgroundColor(Color.Grey)
                  }.width("14%")
                  Column(){
                    Text("评论用户名称").fontColor("#b0b0b0").fontSize(12).margin({ bottom: 8 }).textAlign(TextAlign.Start);
                    Text("评论内容内容评论评论内容评论内容评论让男人内容评论让男人普人口让男人派人内容").fontSize(14).fontColor("#333333").margin({ bottom: 4 }).textAlign(TextAlign.Start)
                    Row(){
                      Row(){
                        Text("6小时前 · 广东").fontColor("#b0b0b0").fontSize(12).margin({ right: 14 })
                        Text("回复").fontColor("#b0b0b0").fontSize(12)
                        // @ts-ignore
                      }.alignItems(HorizontalAlign.Center)
                      Row(){
                        Image("").width(20).height(20).backgroundColor(Color.Grey).margin({ right: 4 })
                        Text("666").fontSize(12).fontColor("#b0b0b0")
                        // @ts-ignore
                      }.alignItems(HorizontalAlign.Center)
                      // @ts-ignore
                    }.justifyContent(FlexAlign.SpaceBetween).alignItems(HorizontalAlign.Center).width("100%")
                  }.alignItems(HorizontalAlign.Start).width("86%").border({ width: { bottom: 2 }, color: "#efefef" }).padding({ bottom: 10 })
                  // @ts-ignore
                }.alignItems(HorizontalAlign.Start).width("100%").margin({ bottom: 15 })
              })
            }.padding({ bottom: 15, right: 15, top: 15, left: 15 })
          }

        }.padding({ bottom: 100 })

        // 底部操作栏
        Row(){
          // @ts-ignore
          Stack(Alignment.End){
            TextInput({ placeholder: "说点什么吧~", controller: this.textInputController }).width("100%").height("100%")
          }.width("54%").height("60%")

          Row(){
            Row(){
              Image("").width(28).height(28).backgroundColor(Color.Grey).margin({ right: 4 })
              Text("214").fontColor("#b0b0b0").fontSize(16)
            }
            Row(){
              Image("").width(28).height(28).backgroundColor(Color.Grey).margin({ right: 4 })
              Text("14").fontColor("#b0b0b0").fontSize(16)
            }.margin({ left: 16 })
          }.justifyContent(FlexAlign.End).width("46%")
          // @ts-ignore
        }.width("100%").height(64).alignItems(HorizontalAlign.Center).border({ width: { top: 2 }, color: "#efefef" }).padding({
          right: 15,
          left: 15
        }).justifyContent(FlexAlign.SpaceBetween).backgroundColor("#ffffff").position({ x: 0,  y: "100%" }).markAnchor({ x: 0, y: 116 })
      }

    }

  }

  /**
   * 获取屏幕高度
   */
  getScreenHeight(){
    let screenStatus = display.getDefaultDisplaySync();
    this.pageHeight = screenStatus.height - 62;
  }
}