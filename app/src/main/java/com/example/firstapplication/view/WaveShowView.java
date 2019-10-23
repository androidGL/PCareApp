package com.example.firstapplication.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: gl
 * @CreateDate: 2019/10/23
 * @Description:
 */
public class WaveShowView extends View {
    public  int SHOW_MODEL = 0;
    public final int SHOW_MODEL_ALL = 1;
    public final int SHOW_MODEL_DYNAMIC_SCROLL = 2;
    public final int SHOW_MODEL_DYNAMIC_REFRESH = 3;

    private float mWidth = 0;
    private float mHeight = 0;
    private Paint paint ;
    private Path path ;
    private String[] dataStrList;

    private int scrollIndex;
    private Timer timer;
    private TimerTask timerTask;
    private final float INTERVAL_SCROLL_REFRESH = 80;
    private ArrayList refreshList;
    private int showIndex = 0;

    private int intervalNumHeart;
    private float intervalRowHeart,intervalColumnHeart;
    private float[] data;
    private float mHeartLinestrokeWidth;
    private int row,column;
    private float intervalRow,intervalColumn;
    private float mGridLinestrokeWidth,mGridStrokeWidthAndHeight;

    //心电
    private float MAX_VALUE = 20;
    private float HEART_LINE_STROKE_WIDTH = 2.5f;
    //网络
    private float GRID_LINE_STROKE_WIDTH = 1.5f;
    private float GRID_WIDTH_AND_HEIGHT = 5f;


    public WaveShowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveShowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        paint = new Paint();
        path = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mGridLinestrokeWidth = dip2px(GRID_LINE_STROKE_WIDTH);
        mGridStrokeWidthAndHeight = dip2px(GRID_WIDTH_AND_HEIGHT);

        column = (int) (mWidth / mGridStrokeWidthAndHeight);
        
        intervalColumn = mWidth / column;
        row = (int) (mHeight / mGridStrokeWidthAndHeight);
        
        intervalRow = mHeight / row;
        
        mHeartLinestrokeWidth = dip2px(HEART_LINE_STROKE_WIDTH);
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制网格
        drawGrid(canvas);
        switch (SHOW_MODEL){
            case SHOW_MODEL_ALL:
                drawHeartAll(canvas);
                break;
            case SHOW_MODEL_DYNAMIC_SCROLL:
                drawHeartScroll(canvas);
                break;
            case SHOW_MODEL_DYNAMIC_REFRESH:
                drawHeartRefresh(canvas);
                break;
        }

    }

    private void drawHeartRefresh(Canvas canvas) {
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#31CE32"));
        paint.setStrokeWidth(mGridLinestrokeWidth);
        paint.setAntiAlias(true);
        path.moveTo(0f,mHeight/2);
        int nowIndex = null == refreshList ? 0 : refreshList.size();
        if(0 == nowIndex){
            return;
        }

        showIndex = nowIndex < intervalNumHeart ? (nowIndex-1) : ((nowIndex-1)%intervalNumHeart);

        for(int i=0;i<intervalNumHeart;i++){
            if(i > refreshList.size() - 1)
                break;
            if(nowIndex < intervalNumHeart)
                this.data[i] = (float) refreshList.get(i);
            else {
                int times = (nowIndex -1)/intervalNumHeart;
                int temp = times * intervalNumHeart + i;
                if(temp < nowIndex)
                    this.data[i] = (float) refreshList.get(temp);
            }
        }

        logdata();
        float nowX,nowY;
        for(int i=0;i<data.length;i++){
            nowX = i*intervalRowHeart;
            float dataValue = data[i];
            if(dataValue > 0){
                if(dataValue > MAX_VALUE * 0.8f){
                    dataValue = MAX_VALUE * 0.8f;
                }
            }else{
                if(dataValue < -MAX_VALUE * 0.8f){
                    dataValue = -MAX_VALUE * 0.8f;
                }
            }
            nowY = mHeight / 2 - dataValue * intervalColumnHeart;
            if(i-1 == showIndex){
                path.moveTo(nowX,nowY);
            }else {
                path.lineTo(nowX,nowY);
            }

        }

        canvas.drawPath(path,paint);
    }

    private void logdata() {
        String str = "";
        for(float temp : data){
            str += temp;
        }
    }

    private void drawGrid(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#D8D8D8"));
        paint.setStrokeWidth(mGridLinestrokeWidth);
        paint.setAntiAlias(true);
        for(int i = 0;i < column;i++){
            float iTempC = i*intervalColumn;
            path.moveTo(iTempC,0f);
            path.lineTo(iTempC,mHeight);
        }
        for(int i = 0;i < row;i++){
            path.moveTo(0f,i*intervalRow);
            path.lineTo(mWidth,i*intervalRow);
        }
        canvas.drawPath(path,paint);
    }

    private void drawHeartScroll(Canvas canvas){
        if(null == data || data.length == 0){
            return;
        }
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#31CE32"));
        paint.setStrokeWidth(mGridLinestrokeWidth);
        paint.setAntiAlias(true);
        path.moveTo(0f,mHeight/2);

        int scrollEndIndex = scrollIndex;
        int scrollStartIndex = scrollEndIndex - intervalNumHeart;
        if(scrollStartIndex < 0){
            scrollStartIndex = 0;
        }

        float nowX,nowY;
        for (int i = 0;i < data.length;i++){
            nowX = i * intervalRowHeart;

            float dataValue = data[i];
            if (dataValue > 0) {
                if (dataValue > MAX_VALUE * 0.8f) {
                    dataValue = (float) (MAX_VALUE * 0.8f);
                }
            } else {
                if (dataValue < -MAX_VALUE * 0.8f) {
                    dataValue = (float) -(MAX_VALUE * 0.8f);
                }
            }
            nowY = mHeight / 2 - dataValue * intervalColumnHeart;
            path.lineTo(nowX,nowY);
        }
        canvas.drawPath(path,paint);
        postInvalidate();
    }

    private void drawHeartAll(Canvas canvas){
        if(null == data || data.length == 0){
            return;
        }
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#31CE32"));
        paint.setStrokeWidth(mGridLinestrokeWidth);
        paint.setAntiAlias(true);
        path.moveTo(0f,mHeight/2);

        float nowX,nowY;
        for(int i=0;i<data.length;i++){
            nowX = i*intervalRowHeart;
            float dataValue = data[i];
            if(dataValue > 0){
                if(dataValue > MAX_VALUE * 0.8f){
                    dataValue = MAX_VALUE * 0.8f;
                }
            }else{
                if(dataValue < -MAX_VALUE * 0.8f){
                    dataValue = -MAX_VALUE * 0.8f;
                }
            }
            nowY = mHeight / 2 - dataValue * intervalColumnHeart;
            path.lineTo(nowX,nowY);

        }

        canvas.drawPath(path,paint);
    }
    public void setData(String dataStr,int model) {
        if(null != dataStr) dataStrList = dataStr.split(",");
        this.SHOW_MODEL = model;
        initData();
    }
    private void initData() {
        int dataLength;
        switch (SHOW_MODEL){
            case SHOW_MODEL_ALL:
                dataLength = dataStrList.length;
                if(dataLength > mWidth){
                    dataLength = (int) mWidth;
                }
                data = new float[dataLength];
                for(int i = 0;i<dataLength;i++){
                    data[i] = Float.valueOf(dataStrList[i]);
                }
                intervalNumHeart = data.length;
                 intervalRowHeart = mWidth / intervalNumHeart;
                intervalColumnHeart = mHeight / (MAX_VALUE * 2);
                break;
            case SHOW_MODEL_DYNAMIC_SCROLL:
                dataLength = dataStrList.length;
                data = new float[dataLength];
                for(int i = 0;i<dataLength;i++){
                    data[i] = Float.valueOf(dataStrList[i]);
                }
                intervalRowHeart = mWidth / dip2px(INTERVAL_SCROLL_REFRESH);
                intervalNumHeart = (int) (mWidth / intervalRowHeart);
                intervalColumnHeart = mHeight / (MAX_VALUE * 2);
                startScrollTimer();
                break;
            case SHOW_MODEL_DYNAMIC_REFRESH:
                intervalRowHeart = mWidth / dip2px(INTERVAL_SCROLL_REFRESH);
                intervalNumHeart = (int) (mWidth / intervalRowHeart);
                intervalColumnHeart = mHeight / (MAX_VALUE * 2);
                break;
        }

    }

    private void startScrollTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (scrollIndex < data.length) {
                    scrollIndex++;
                } else {
                    scrollIndex = 0;
                }
            }
        };
        timer.schedule(timerTask, 0, 50);
    }

    private int dip2px(float dipValue) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dipValue +0.5f);
    }

    public void showLine(float line) {
        if(null == refreshList){
            refreshList = new ArrayList();
            data = new float[intervalNumHeart];
        }
        refreshList.add(line);
        postInvalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(null != timer)
            timer.cancel();
    }
}
