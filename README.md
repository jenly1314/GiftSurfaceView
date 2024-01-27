
# GiftSurfaceView
[![Download](https://img.shields.io/badge/download-App-blue.svg)](https://raw.githubusercontent.com/jenly1314/GiftSurfaceView/master/app/app-release.apk)
[![](https://jitpack.io/v/jenly1314/GiftSurfaceView.svg)](https://jitpack.io/#jenly1314/GiftSurfaceView)
[![API](https://img.shields.io/badge/API-15%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)

GiftSurfaceView 最初出自于2014年开发HalloStar项目时所写，主要用于HalloStar项目直播间的送礼物动画。现在想来，那夕阳下的奔跑，是我逝去的青春。因高仿全民TV项目时想起，所以抽空整理了下，以此记录。

## Gif展示

![gif](GIF.gif)

> 你也可以直接下载 [演示App](https://raw.githubusercontent.com/jenly1314/GiftSurfaceView/master/app/app-release.apk) 体验效果

## 引入

### Gradle:

1. 在Project的 **build.gradle** 或 **setting.gradle** 中添加远程仓库

    ```gradle
    repositories {
        //...
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    ```

2. 在Module的 **build.gradle** 里面添加引入依赖项

    ```gradle
    implementation 'com.github.jenly1314:GiftSurfaceView:1.1.0'
    ```

## 使用

代码示例
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

更多使用详情，请查看[app](app)中的源码使用示例或直接查看[API帮助文档](https://jitpack.io/com/github/jenly1314/GiftSurfaceView/latest/javadoc/)

## 赞赏
如果您喜欢GiftSurfaceView，或感觉GiftSurfaceView帮助到了您，可以点右上角“Star”支持一下，您的支持就是我的动力，谢谢 :smiley:
<p>您也可以扫描下面的二维码，请作者喝杯咖啡 :coffee:

<div>
   <img src="https://jenly1314.github.io/image/page/rewardcode.png">
</div>

## 关于我

| 我的博客                                                                                | GitHub                                                                                  | Gitee                                                                                  | CSDN                                                                                 | 博客园                                                                            |
|:------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------|
| <a title="我的博客" href="https://jenly1314.github.io" target="_blank">Jenly's Blog</a> | <a title="GitHub开源项目" href="https://github.com/jenly1314" target="_blank">jenly1314</a> | <a title="Gitee开源项目" href="https://gitee.com/jenly1314" target="_blank">jenly1314</a>  | <a title="CSDN博客" href="http://blog.csdn.net/jenly121" target="_blank">jenly121</a>  | <a title="博客园" href="https://www.cnblogs.com/jenly" target="_blank">jenly</a>  |

## 联系我

| 微信公众号        | Gmail邮箱                                                                          | QQ邮箱                                                                              | QQ群                                                                                                                       | QQ群                                                                                                                       |
|:-------------|:---------------------------------------------------------------------------------|:----------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------|
| [Jenly666](http://weixin.qq.com/r/wzpWTuPEQL4-ract92-R) | <a title="给我发邮件" href="mailto:jenly1314@gmail.com" target="_blank">jenly1314</a> | <a title="给我发邮件" href="mailto:jenly1314@vip.qq.com" target="_blank">jenly1314</a> | <a title="点击加入QQ群" href="https://qm.qq.com/cgi-bin/qm/qr?k=6_RukjAhwjAdDHEk2G7nph-o8fBFFzZz" target="_blank">20867961</a> | <a title="点击加入QQ群" href="https://qm.qq.com/cgi-bin/qm/qr?k=Z9pobM8bzAW7tM_8xC31W8IcbIl0A-zT" target="_blank">64020761</a> |

<div>
   <img src="https://jenly1314.github.io/image/page/footer.png">
</div>
