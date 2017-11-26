package a.itcast.mobileplayer95.lyrics;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by divinermagic on 2017/11/21.
 * @author Diviner
 */
@SuppressLint("AppCompatCustomView")
public class LyricsView extends TextView {

    private Paint paint;

    private int mHighlightColor;

    private int mNormalColor;

    private int mHighlightSize;

    private int mNormalSize;

    private int mWidth;

    private int mHeight;

    private ArrayList<Lyric> lyrics;

    private int centerIndex;

    private int mLineH;

    private int mDuration;

    private int mPosition;


    public LyricsView(Context context) {
        super(context);
        initView();
    }

    public LyricsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LyricsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        mHighlightSize = getResources().getDimensionPixelSize(R.dimen.highlight_size);

        mNormalSize = getResources().getDimensionPixelSize(R.dimen.normal_size);

        //行高
        mLineH = getResources().getDimensionPixelSize(R.dimen.line_size);

        //获取 res/color/的颜色
        mHighlightColor = getResources().getColor(R.color.hightLightColor);

        mNormalColor = getResources().getColor(R.color.white_text);

        paint = new Paint();

        paint.setColor(mHighlightColor);

        paint.setTextSize(mHighlightSize);

        //抗锯齿 [必须有]
        paint.setAntiAlias(true);

        //伪造 歌词列表数据
//        lyrics = new ArrayList<>();
//         for (int i = 0;i<30;i++){
//            lyrics.add(new Lyric(2000*i,"当前歌词的行数为:"+i));
//        }
//        centerIndex = 15;


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas:帆布


        if (lyrics == null||lyrics.size() == 0){
            //没有数据的时候 显示 ["正在加载歌词..."]
            drawSingleText(canvas);
        }else {
            //有数据的时候 加载多行歌词
            drawMuliteText(canvas);
        }
        //drawSingleText(canvas);
    }

    /**
     * 在屏幕水平居中绘制多行文本
     */
    private void drawMuliteText(Canvas canvas) {

        // 获取居中行的数据
        Lyric lyric = lyrics.get(centerIndex);

        // 偏移的Y = 已消耗时间百分比 * 行高
        // 已消耗时间百分比 = 已消耗时间 / 行可用时间
        // 已消耗时间 = 当前播放进度 - 行起始时间
        // 行可用时间 = 下一行起始时间 - 行起始时间


        //下一行起始时间
        int nextStartPoint;
        if (centerIndex!=lyrics.size()-1){
            //非最后一行
            Lyric nextLyric = lyrics.get(centerIndex + 1);
            nextStartPoint = nextLyric.getStartPoint();
        }else {
            nextStartPoint = mDuration;
        }

        //行可用时间
        int lineTime = nextStartPoint - lyric.getStartPoint();

        //已消耗时间
        int pastTime = mPosition - lyric.getStartPoint();

        //已消耗时间百分比
        float pastPercent = (float) pastTime / lineTime;

        //偏移的 Y
        int offsetY = (int) (pastPercent *mLineH);

        //获取居中行的高度
        Rect bounds = new Rect();
        paint.getTextBounds(lyric.content,0,lyric.content.length(),bounds);

        //计算文本绘制坐标
        float centerY = mHeight / 2 + bounds.height() / 2 - offsetY;

        // y = 居中行Y + 行高 * (绘制行 - 居中行)

        for (int i = 0; i < lyrics.size() ; i++) {

            if (i == centerIndex){
                //居中行 需要 高亮
                paint.setColor(mHighlightColor);
                paint.setTextSize(mHighlightSize);
            }else if (i == centerIndex -1||i==centerIndex+1){
                // TODO: 2017/11/22 给歌词 设置渐变颜色
                paint.setColor(getResources().getColor(R.color.hightLightColor1));

                paint.setTextSize(mNormalSize);
                // TODO: 2017/11/22 给歌词 设置渐变颜色
            }else if (i == centerIndex - 2 ||i == centerIndex +2){
                paint.setColor(getResources().getColor(R.color.hightLightColor2));

                paint.setTextSize(mNormalSize);
            }else {
                //普通行 需要 变暗
                paint.setColor(mNormalColor);
                paint.setTextSize(mNormalSize);
            }

            float drawY = centerY + mLineH * (i - centerIndex);

            drawHorizontalText(canvas, lyrics.get(i).content, drawY);

        }

    }

    /**
     * 水平居中 绘制一行文本
     * @param canvas
     * @param text
     * @param drawY
     */
    private void drawHorizontalText(Canvas canvas, String text, float drawY) {
        //获取文本大小 Rect:[矩形]
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);

        //计算文本绘制坐标
        float drawX = mWidth / 2 - bounds.width() / 2;

        canvas.drawText(text,drawX,drawY,paint);
    }

    /**
     * 在屏幕中间绘制一行文本
     * @param canvas 帆布
     */
    private void drawSingleText(Canvas canvas) {

        // x = View 宽度的一半 - 文字宽度的一半
        // y = View 高度的一半 + 文字高度的一半

        String text = "正在加载歌词...";

        //获取文本大小 Rect:[矩形]
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);

        //计算文本绘制坐标
        float drawX = mWidth / 2 - bounds.width() / 2;
        float drawY = mHeight / 2 + bounds.height() / 2;
        canvas.drawText(text,drawX,drawY,paint);
    }

    /**
     * 根据当前歌曲播放进度
     * 计算'居中行'的所在'行数'
     */
    public void computeCenterIndex(int position,int duration){

        mPosition = position;

        mDuration = duration;

        // 当前歌曲播放进度 >= 当前行起始时间 && 当前播放进度 < 下一行起始时间
        for (int i = 0; i < lyrics.size() ; i++) {

            //当前行的起始时间
            int startPaint = lyrics.get(i).getStartPoint();

            //下一行的起始时间
            int nextStartPoint;

            if (i!=lyrics.size()-1){
                //非最后一行
                Lyric nextLyric = lyrics.get(i + 1);
                nextStartPoint = nextLyric.getStartPoint();
            }else {
                //最后一行
                nextStartPoint = duration;
            }

            if (position >= startPaint && position < nextStartPoint){

                centerIndex = i;

                break;
            }
        }

        //使用新的 居中行 刷新 界面
        invalidate();
    }

    /**
     * 接收歌词文件 生成歌词列表
     * @param file 歌词文件
     */
    public void setLyricFile(File file){

        lyrics = LyricsParser.parseFile(file);

        centerIndex = 0;
    }
}
