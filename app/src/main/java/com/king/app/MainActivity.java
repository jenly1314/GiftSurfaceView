package com.king.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.king.view.giftsurfaceview.GiftSurfaceView;
import com.king.view.giftsurfaceview.util.PointUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    /** 随机 */
    public static final int RANDOM = 0X0001;
    /** V型 */
    public static final int V = 0X0002;
    /** 心型 */
    public static final int HEART = 0X0003;
    /** 笑脸 */
    public static final int SMILE = 0X0004;
    /** LOVE */
    public static final int LOVE = 0X0005;
    /** 比翼双飞  */
    public static final int X = 0X0006;
    /** 一见钟情  */
    public static final int V520 = 0X0007;
    /** 一生一世  */
    public static final int V1314 = 0X0008;

    private static final String ASSET_HEART = "assets/json/heart.json";
    private static final String ASSET_V = "assets/json/v.json";
    private static final String ASSET_LOVE = "assets/json/love.json";
    private static final String ASSET_SMILE = "assets/json/smile.json";
    private static final String ASSET_X = "assets/json/x.json";
    private static final String ASSET_V520 = "assets/json/v520.json";
    private static final String ASSET_V1314 = "assets/json/v1314.json";


    private FrameLayout frame;

    private Bitmap bitmap;

    private Context context;

    private int width,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;

        frame = (FrameLayout)findViewById(R.id.frame);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.rose);

        updateGiftSurfaceView(RANDOM);

    }

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


    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btnRandom:
                updateGiftSurfaceView(RANDOM);
                break;
            case R.id.btnV:
                updateGiftSurfaceView(V);
                break;
            case R.id.btnHeart:
                updateGiftSurfaceView(HEART);
                break;
            case R.id.btnLove:
                updateGiftSurfaceView(LOVE);
                break;
            case R.id.btnSmile:
                updateGiftSurfaceView(SMILE);
                break;
            case R.id.btnX:
                updateGiftSurfaceView(X);
                break;
            case R.id.btn520:
                updateGiftSurfaceView(V520);
                break;
            case R.id.btn1314:
                updateGiftSurfaceView(V1314);
                break;

        }
    }
}
