
# GiftSurfaceView
[![Download](https://img.shields.io/badge/download-App-blue.svg)](https://raw.githubusercontent.com/jenly1314/GiftSurfaceView/master/app/app-release.apk)
[![](https://jitpack.io/v/jenly1314/GiftSurfaceView.svg)](https://jitpack.io/#jenly1314/GiftSurfaceView)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)
[![Blog](https://img.shields.io/badge/blog-Jenly-9933CC.svg)](http://blog.csdn.net/jenly121)

GiftSurfaceView 最初出自于2014年开发HalloStar项目时所写，主要用于HalloStar项目直播间的送礼物动画。现在想来，那夕阳下的奔跑，是我逝去的青春。因前几天，刚高仿全民TV，所以想起，稍加改善，以此记录。

## Gif展示

![gif](GIF.gif)

## 引入

### Maven：
```maven
<dependency>
  <groupId>com.king.view</groupId>
  <artifactId>giftsurfaceview</artifactId>
  <version>1.1.0</version>
  <type>pom</type>
</dependency>
```
### Gradle:
```gradle
compile 'com.king.view:giftsurfaceview:1.1.0'
```
### Lvy:
```lvy
<dependency org='com.king.view' name='giftsurfaceview' rev='1.1.0'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```
###### 如果Gradle出现compile失败的情况，可以在Project的build.gradle里面添加如下：（也可以使用上面的GitPack来complie）
```gradle
allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/jenly/maven' }
    }
}
```

### 示例

```Java
    public void updateGiftSurfaceView(int type){

        frame.removeAllViews();

        GiftSurfaceView giftSurfaceView = new GiftSurfaceView(context);
        if(type == RANDOM){
            giftSurfaceView.setImageResource(R.drawable.rose);
        }else{
            giftSurfaceView.setImageBitmap(bitmap,.5f);
        }

        giftSurfaceView.setPointScale(1,width/10,(int)(height/3.8f));
        giftSurfaceView.setRunTime(10000);

        try {

            switch (type){
                case RANDOM:
                    giftSurfaceView.setRandomPoint(9);
                    break;
                case V:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_V),true);
                    break;
                case HEART:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_HEART),true);
                    break;
                case LOVE:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_LOVE));
                    break;
                case SMILE:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_SMILE));
                    break;
                case X:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_X));
                    break;
                case V520:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_V520));
                    break;
                case V1314:
                    giftSurfaceView.setRunTime(GiftSurfaceView.LONG_TIME);
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_V1314));
                    break;

            }
            frame.addView(giftSurfaceView);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
```
以上为部分代码使用示例，更多详情请下载查看。


## 关于我
   Name: Jenly

   Email: jenly1314@gmail.com / jenly1314@vip.qq.com

   CSDN: http://blog.csdn.net/jenly121

   Github: https://github.com/jenly1314

   微信公众号:

   ![公众号](http://olambmg9j.bkt.clouddn.com/jenly666.jpg)
   
   加入QQ群: [20867961](http://shang.qq.com/wpa/qunwpa?idkey=8fcc6a2f88552ea44b1411582c94fd124f7bb3ec227e2a400dbbfaad3dc2f5ad)
   
   
