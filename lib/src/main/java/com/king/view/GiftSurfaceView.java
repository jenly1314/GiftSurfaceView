
/*
     The MIT License (MIT)
     Copyright (c) 2017 Jenly Yu
     https://github.com/jenly1314

     Permission is hereby granted, free of charge, to any person obtaining
     a copy of this software and associated documentation files
     (the "Software"), to deal in the Software without restriction, including
     without limitation the rights to use, copy, modify, merge, publish,
     distribute, sublicense, and/or sell copies of the Software, and to permit
     persons to whom the Software is furnished to do so, subject to the
     following conditions:

     The above copyright notice and this permission notice shall be included
     in all copies or substantial portions of the Software.

     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
     FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
     DEALINGS IN THE SOFTWARE.
 */
package com.king.view;


import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * GiftSurfaceView 最初出自于2014年开发HalloStar项目时所写，主要用于HalloStar项目直播间的送礼物动画。
 * 现在想来，那夕阳下的奔跑，是我逝去的青春。因前几天，刚高仿全民TV，所以想起，稍加改善，以此记录。
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @since 2017/3/28
 */
public class GiftSurfaceView extends SurfaceView implements Callback {

    /**
     * 最小速度
     */
    private static final int MIN_SPEED = 4;

    public static final int LONG_TIME = 0;
    /**
     * 随机模式默认睡眠时间
     */
    private static final int DEFAULT_RANDOM_SLEEP_TIME = 200;
    /**
     * 画图模式默认睡眠时间
     */
    private static final int DEFAULT_DRAWING_SLEEP_TIME = 20;

    private Bitmap bitmap;

    private Canvas canvas;

    private Paint paint;

    private Paint clearPaint;

    private Random random;

    private Point[] point;
    private Point[] endPoint;

    private int[] speeds;

    private List<Point> points;

    private float width,height;

    private int number;

    private int count;
    /** 缩放比例 */
    private float scale = 1;

    /** 偏移（非随机情况下有效） */
    private int offsetX;
    private int offsetY;

    /** 运行时间 */
    private int runTime;
    /** 开始时间 */
    private int startTime;
    /** 睡眠时间 */
    private int sleepTime;
    /** 是否处理（线程控制） */
    private boolean isDeal;

    private Thread threadDeal,threadDraw;

    private STATUS status = STATUS.DEFAULT;

    public enum STATUS {

        DEFAULT(0),
        /**
         * 画
         */
        DRAWING(1),
        /**
         * 清空（清屏）
         */
        CLEAR(2),
        /**
         * 停止
         */
        STOP(3);

        private int mValue;

        STATUS(int value){
            this.mValue = value;
        }

        public static STATUS getFromInt(int value){
            for(STATUS status : STATUS.values()){
                if(status.mValue == value)
                    return status;
            }
            return STATUS.DEFAULT;
        }

    }

    private MODE mode = MODE.RANDOM;

    public enum MODE{
        /**
         * 随机（随机递增）
         */
        RANDOM(0),
        /**
         * 键入（类似于打字拼成图案）
         */
        TYPING(1),
        /**
         * 移动（随机点初始位置，然后拼成图案）
         */
        MOVE(2);

        private int mValue;

        MODE(int value){
            this.mValue = value;
        }

        public static MODE getFromInt(int value){
            for(MODE status : MODE.values()){
                if(status.mValue == value)
                    return status;
            }
            return MODE.RANDOM;
        }
    }


    public GiftSurfaceView(Context context) {
        this(context,null);
    }

    public GiftSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GiftSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }


    private void initData(){

        paint = new Paint();
        //paint抗锯齿
        paint.setAntiAlias(true);

        clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR));
        random = new Random();

        //设置透明
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(this);

        setLayerType(View.LAYER_TYPE_NONE, null);

    }


    //----------------------

    private Bitmap getBitmapByResource(@DrawableRes int resId){
        return BitmapFactory.decodeResource(getResources(),resId);
    }

    public void setImageResource(@DrawableRes int resId){
        setImageBitmap(getBitmapByResource(resId));
    }

    /**
     *
     * @param resId
     * @param scale 缩放（图片）
     */
    public void setImageResource(@DrawableRes int resId,float scale){
        setImageBitmap(getBitmapByResource(resId),scale);
    }

    public void setImageBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    /**
     *
     * @param bitmap
     * @param scale 缩放（图片）
     */
    public void setImageBitmap(Bitmap bitmap,float scale){
        this.bitmap = scaleBitmap(bitmap,scale);
    }

    /**
     *  拼图的终点坐标的整体缩放
     *
     * @param scale 点坐标整体缩放
     *                 1.0f 表示坐标
     * @param offsetX X 轴偏移量
     * @param offsetY Y 轴偏移量
     */
    public void setPointScale(float scale,int offsetX,int offsetY){
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }


    /**
     * 运行时间
     *      LONG_TIME 表示长久运行（不会消失）
     * @param runTime
     */
    public void setRunTime(int runTime){
        this.runTime = runTime;
    }

    /**
     * 随机点
     * @param num 随机点的数量
     */
    public void setRandomPoint(int num){
        setRandomPoint(num,DEFAULT_RANDOM_SLEEP_TIME);
    }

    /**
     * 随机点
     * @param num 随机点的数量
     * @param sleepTime 线程睡眠时间
     */
    public void setRandomPoint(int num,int sleepTime){
        number = num;
        this.mode = MODE.RANDOM;
        this.sleepTime = sleepTime;
    }



    public void setListPoint(List<Point> list){
        setListPoint(list,DEFAULT_DRAWING_SLEEP_TIME,false);
    }

    public void setListPoint(List<Point> list,boolean isTyping){
        setListPoint(list,DEFAULT_DRAWING_SLEEP_TIME,isTyping);
    }

    /**
     * 设置点集合
     * @param list
     * @param sleepTime
     * @param isTyping
     */
    public void setListPoint(List<Point> list,int sleepTime,boolean isTyping){
        this.mode = isTyping ?  MODE.TYPING : MODE.MOVE;
        this.points = list;
        this.sleepTime = sleepTime;
        this.number = list.size();
    }

    public void updateGiftSurfaceViewParams(int width,int height){
        this.width = width;
        this.height = height;
        if(width==0 || height == 0){
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            this.width = displayMetrics.widthPixels;
            this.height = displayMetrics.heightPixels;
        }

        if(mode == MODE.MOVE){
            updatePoints(points);
        }else{
            this.count = 0;
            point = new Point[number];
            for(int i=0;i<number;i++){
                if(mode == MODE.TYPING){
                    point[i] = new Point((int)(points.get(i).x*scale)+offsetX,(int)((points.get(i).y+offsetY) * scale));
                }else{
                    point[i] = random();
                }

            }
        }
    }

    private void updatePoints(List<Point> points){
        point = new Point[number];
        endPoint = new Point[number];
        speeds = new int[number];
        count = number;
        for(int i=0;i<number;i++){
            endPoint[i] = new Point((int)(points.get(i).x*scale) + offsetX,(int)((points.get(i).y + offsetY) * scale));
            point[i]  = random();
            int temp1 = Math.abs(point[i].x-endPoint[i].x);
            int temp2 = Math.abs(point[i].y-endPoint[i].y);
            speeds[i] = (int)((Math.max(temp1, temp2)>>6)*scale);
            if(speeds[i]<MIN_SPEED){
                speeds[i] = MIN_SPEED;
            }
        }


    }



    /**
     * 移动逻辑
     */
    private void moveLogic(){

        for(int i=0;i<number;i++){

            if(Math.abs(point[i].x-endPoint[i].x)<speeds[i]){
                point[i].x = endPoint[i].x;
            }else if(point[i].x<endPoint[i].x){
                point[i].x+=speeds[i];
            }else if(point[i].x>endPoint[i].x){
                point[i].x-=speeds[i];
            }

            if(Math.abs(point[i].y-endPoint[i].y)<speeds[i]){
                point[i].y = endPoint[i].y;
            }else if(point[i].y<endPoint[i].y){
                point[i].y+=speeds[i];
            }else if(point[i].y>endPoint[i].y){
                point[i].y-=speeds[i];
            }
        }

    }


    /**
     * 随机一个点
     * @return
     */
    private Point random(){
        int x = 0;
        int y = 0;
        if(width >=bitmap.getWidth() && height >=bitmap.getWidth()){
            x = random.nextInt((int)(width-bitmap.getWidth()*2)) + bitmap.getWidth();
            y = random.nextInt((int)(height-bitmap.getHeight()*2))+ bitmap.getHeight();
        }
        return new Point(x,y);
    }

    /**
     * 画图
     */
    private void drawBitmap(){

        try{
            if(canvas!=null) {
                for (int i = 0; i < count; i++) {
                    canvas.drawBitmap(bitmap, point[i].x - bitmap.getWidth() * .5f, point[i].y - bitmap.getHeight() * .5f, paint);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 清屏
     */
    private void clearScreen(){
        if(canvas!=null){
            canvas.drawPaint(clearPaint);
        }
    }

    /**
     * 递增逻辑
     */
    private void increaseLogic(){
        if(count<point.length){
            count++;
        }
    }

    private void runLogic(MODE mode){
        switch (mode){
            case RANDOM:
            case TYPING:
                increaseLogic();
                break;
            case MOVE:
                moveLogic();
                break;
        }
    }

    /**
     * 停止线程逻辑
     */
    private void stopLogic(){
        isDeal = false;
        try {
            if(threadDeal!=null){
                threadDeal.join();
            }
            if(threadDraw!=null){
                threadDraw.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 画图Runnable
     */
    Runnable runDraw = new Runnable() {

        @Override
        public void run() {
            try{

                while (isDeal) {
                    canvas = getHolder().lockCanvas();
                    switch (status) {
                        case DRAWING:
                            if(mode==MODE.MOVE){
                                clearScreen();
                            }
                            drawBitmap();
                            break;
                        case CLEAR:
                            clearScreen();
                            status = STATUS.STOP;
                            break;
                        case STOP:
                            clearScreen();
                            break;
                        default:
                            break;
                    }
                    if(canvas!=null){
                        getHolder().unlockCanvasAndPost(canvas);
                    }

                    SystemClock.sleep(sleepTime);
                }

            }catch(Throwable e){
               e.printStackTrace();
            }
        }
    };

    /**
     * 逻辑Runnable
     */
    Runnable runDeal = new Runnable() {

        @Override
        public void run() {
            while (isDeal) {
                switch (status) {

                    case DRAWING:
                        runLogic(mode);
                        break;
                    case CLEAR:
                        break;
                    case STOP:
                        stopLogic();
                        break;
                    default:
                        break;
                }
                SystemClock.sleep(sleepTime);

                if(runTime!=LONG_TIME){//是否长久运行
                    startTime += sleepTime;

                    if(startTime>=runTime){//自动结束
                        clearAndStop();
                    }
                }

            }

        }
    };

    /**
     * 启动线程
     */
    private void start(){

        try{
            startTime = 0;
            isDeal = true;
            status = STATUS.DRAWING;
            threadDeal = new Thread(runDeal);
            threadDraw = new Thread(runDraw);
            threadDeal.start();
            threadDraw.start();
        }catch(Exception e){
           e.printStackTrace();
        }

    }

    /**
     * 清空并停止线程（通过状态）
     */
    public void clearAndStop(){
        status = STATUS.CLEAR;
    }

    /**
     * 停止线程（通过状态）
     */
    private void stop(){
        status = STATUS.STOP;
    }


    //----------------------


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        updateGiftSurfaceViewParams(width,height);
        start();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }



    /**
     * 图片按指定宽高缩放
     * @param bmp
     * @param width
     * @param height
     * @return
     */
    public Bitmap scaleBitmap(Bitmap bmp,int width,int height){


        Matrix m = new Matrix();
        m.postScale(width, height);

        return Bitmap.createScaledBitmap(bmp, width, height, true);
    }

    /**
     * 图片按比例缩放
     * @param bmp
     * @param scale
     * @return
     */
    private Bitmap scaleBitmap(Bitmap bmp,float scale){

        int width = (int)(bmp.getWidth()*scale);
        int height = (int)(bmp.getHeight()*scale);

        Matrix m = new Matrix();
        m.postScale(width, height);

        return Bitmap.createScaledBitmap(bmp, width, height, true);
    }






}
