import router from '@ohos.router';
import picker from '@ohos.file.picker';
import promptAction from '@ohos.promptAction';

let storage = LocalStorage.GetShared()
@Entry(storage)
@Component
struct UploadWorks {
  @LocalStorageLink("statusBarHeight")
  statusBarHeight: number = 0;

  @LocalStorageLink("bottomNavigationBarHeight")
  bottomNavigationBarHeight: number = 0;

  @State
  selectImageList: string[] = [];

  pageTransition(){
    PageTransitionEnter({ duration: 300, curve: Curve.Ease, type: RouteType.Push }).slide(SlideEffect.Bottom)
    PageTransitionExit({ duration: 300, curve: Curve.Ease,  type: RouteType.Push }).slide(SlideEffect.Bottom)
  }

  build(){
   Column(){
    Row(){
      Image($r("app.media.close")).width(22).height(22).onClick(() => {
        router.back();
      })
    }.headerDomStyle()

     Column(){
       List({ space: 15 }){
         if(this.selectImageList.length > 0) {
           ForEach(this.selectImageList, (item, idx) => {
             ListItem(){
               Column(){
                 Image(item).width("100%").height("100%")
               }.uploadItemStyle().gesture(LongPressGesture().onAction(() => {
                  this.editPicture(idx)
               }))
             }
           })
         }
         ListItem(){
           Column(){
             Text("+").fontSize(26).fontColor("#b0b0b0")
           }.uploadItemStyle().onClick(() => {
             this.selectAlbumImages()
           })
         }
       }.listDirection(Axis.Horizontal).cachedCount(5).width("100%").height(106)

       Column(){
         TextInput({ placeholder: "写个有吸引力的标题吧~" }).workTitleStyle()
         TextArea({ placeholder: "填写作品内容..." }).workContentStyle()
       }.width("100%").margin({ top: 10, bottom: 20 })

       Column(){
         Row(){
           Row(){
             Image($r("app.media.download_icon")).width(20).height(20).margin({ right: 6 })
             Text("保存相册").fontSize(16).fontColor("#232832").lineHeight(16)
             // @ts-ignore
           }.alignItems(HorizontalAlign.Center).onClick(() => {
             this.savePermissionPopup()
           })

           Image($r("app.media.right_arrow_icon")).width(16).height(16)
         }.styleOfWorkOptions().margin({ bottom: 18 })
         Row(){
           Row(){
             Image($r("app.media.edit_icon")).width(20).height(20).margin({ right: 6 })
             Text("发布类型").fontSize(16).fontColor("#232832").lineHeight(16)
             // @ts-ignore
           }.alignItems(HorizontalAlign.Center)

           Row(){
             Text("公开可见").fontSize(16).fontColor("#333333")
             Image($r("app.media.right_arrow_icon")).width(16).height(16)
             // @ts-ignore
           }.alignItems(HorizontalAlign.Center).justifyContent(FlexAlign.End)
         }.styleOfWorkOptions()
       }

       Row(){
         Button("发布作品", { type: ButtonType.Capsule, stateEffect: true }).width("72%").height(56)
           .backgroundColor("#FF6F61")
       }.width("100%").margin({ top: 100 }).justifyContent(FlexAlign.Center)
     }.padding(15).height("100%")


   }.width("100%")
    .height("100%")
    .backgroundColor("#ffffff")
    .padding({ bottom: this.bottomNavigationBarHeight + 'px', top: this.statusBarHeight + 'px' })
  }


  selectAlbumImages(){
    let PhotoSelectOptions = new picker.PhotoSelectOptions();
    PhotoSelectOptions.MIMEType = picker.PhotoViewMIMETypes.IMAGE_TYPE;
    PhotoSelectOptions.maxSelectNumber = 9;
    let photoPicker = new picker.PhotoViewPicker();
    photoPicker.select(PhotoSelectOptions).then((PhotoSelectResult) => {
      this.selectImageList = PhotoSelectResult.photoUris
    }).catch((err) => {
      console.error('PhotoViewPicker.select failed with err: ' + err);
    });
  }

  /**
   * 打开保存图片权限弹窗
   */
  savePermissionPopup(){

  }

  /**
   * 编辑图片
   */
  editPicture(idx: number){

  }
}


@Extend(Row) function headerDomStyle() {
  .width("100%")
  .height(56)
  .padding({ right: 15, left: 15 })
  .border({ width: { bottom: 1 }, color: "#efefef" })
}

@Extend(Column) function uploadItemStyle() {
  .width(106)
  .height(106)
  .backgroundColor("#efefef")
  .border({ radius: 8 })
  .alignItems(HorizontalAlign.Center)
  .justifyContent(FlexAlign.Center)
}

@Extend(TextInput) function workTitleStyle() {
  .width("100%")
  .height(46)
  .border({ width: { bottom: 1 }, color: "#efefef", radius: 0 })
  .backgroundColor(Color.White)
  .placeholderColor("#cccccc")
  .padding(0)
  .margin({ bottom: 10 })
  .placeholderFont({ size: 16, weight: FontWeight.Bold })
  .fontWeight(FontWeight.Bold)
}

@Extend(TextArea) function workContentStyle () {
  .width("100%")
  .height(100)
  .backgroundColor(Color.White)
  .borderRadius(0)
  .placeholderColor("#cccccc")
  .padding(0)
  .fontSize(14)
  .placeholderFont({ size: 16 })
}

@Extend(Row) function styleOfWorkOptions(){
  .width("100%")
  .justifyContent(FlexAlign.SpaceBetween)
  .border({ width: { bottom: 1 }, color: "#efefef" })
  .padding({ bottom: 12 })
}