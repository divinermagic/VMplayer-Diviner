package com.itheima.vmplayer.lyric;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.itheima.vmplayer.R;
import com.itheima.vmplayer.bean.LyricBean;
import com.itheima.vmplayer.util.LoadLyricUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wschun on 2016/10/2.
 */

public class Lyrics extends TextView {

    private Paint paint;
    private float hightTextSize;
    private float nomalTextSize;
    private int hightTextColor;
    private int nomalTextColor;
    private int hightLyricLine;
    private int lyricHeight;
    private List<LyricBean> lyricBeans;

    public Lyrics(Context context) {
        super(context);
        initView();
    }

    public Lyrics(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Lyrics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        hightTextSize = getResources().getDimension(R.dimen.hight_text_size);
        nomalTextSize = getResources().getDimension(R.dimen.nomal_text_size);
        lyricHeight = getResources().getDimensionPixelOffset(R.dimen.lyricHeight);
        hightTextColor = getResources().getColor(R.color.hightLightColor);
        nomalTextColor = Color.WHITE;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(hightTextSize);
        paint.setColor(hightTextColor);

        lyricBeans = new ArrayList<>();

//        for (int i = 0; i < 30; i++) {
//            lyricBeans.add(new LyricBean("当前歌词" + i + "行", i * 2000));
//        }
//        hightLyricLine = 3;
    }

    private int viewHalfWidth, viewHalfHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHalfWidth = w / 2;
        viewHalfHeight = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lyricBeans.size() == 0) {
            drawSingleLine(canvas);
        } else
            drawMulitLine(canvas);


    }

    private void drawMulitLine(Canvas canvas) {

        LyricBean lyricBean = lyricBeans.get(hightLyricLine);
        int startPoint = (int) lyricBean.getStartPoint();
        int endPoint;
        if (hightLyricLine == lyricBeans.size() - 1) {
            endPoint = (int) duration;
        } else {
            LyricBean lyricBeanNext = lyricBeans.get(hightLyricLine + 1);
            endPoint = (int) lyricBeanNext.getStartPoint();
        }
        float precent = (currentPoint - startPoint) / (float) (endPoint - startPoint);
        int offest = (int) (lyricHeight * precent);

        Rect bounds = new Rect();
        paint.getTextBounds(lyricBean.getContent(), 0, lyricBean.getContent().length(), bounds);
        int centerY = viewHalfHeight + bounds.height() / 2 - offest;

        for (int i = 0; i < lyricBeans.size(); i++) {
            if (i == hightLyricLine) {
                paint.setTextSize(hightTextSize);
                paint.setColor(hightTextColor);
            } else {
                paint.setTextSize(nomalTextSize);
                paint.setColor(nomalTextColor);
            }

            int drawY = centerY + (i - hightLyricLine) * lyricHeight;
            drawHorizontoal(canvas, lyricBeans.get(i).getContent(), drawY);
        }


    }

    private void drawSingleLine(Canvas canvas) {
        String text = "正在加载歌词。。。";
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int halfLyricWidth = bounds.width() / 2;
        int halfLyricHeight = bounds.height() / 2;
        int drawY = viewHalfHeight + halfLyricHeight;
        drawHorizontoal(canvas, text, drawY);
    }

    private void drawHorizontoal(Canvas canvas, String text, int drawY) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int halfLyricWidth = bounds.width() / 2;
        int drawX = viewHalfWidth - halfLyricWidth;
        canvas.drawText(text, drawX, drawY, paint);
    }

    private long currentPoint, duration;

    public void scroll(long currentPosition, long duration) {
        this.currentPoint = currentPosition;
        this.duration = duration;
        for (int i = 0; i < lyricBeans.size(); i++) {

            LyricBean lyricBean = lyricBeans.get(i);
            long startPoint = (int) lyricBean.getStartPoint();
            long endPoint;
            if (i == lyricBeans.size() - 1) {
                endPoint = duration;
            } else {
                LyricBean lyricBeanNext = lyricBeans.get(i + 1);
                endPoint = (int) lyricBeanNext.getStartPoint();
            }

            if (currentPosition > startPoint && currentPosition <= endPoint) {
                hightLyricLine = i;
                break;
            }


        }
        invalidate();
    }


    public void setLyric(File file) {
        lyricBeans = LoadLyricUtil.loadLyricFromFile(file);
        hightLyricLine = 0;
    }
}
