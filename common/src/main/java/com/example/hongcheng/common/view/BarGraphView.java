package com.example.hongcheng.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by hongcheng on 16/4/13.
 */
public class BarGraphView extends View
{

    /**默认线的宽度*/
    private static final int STROKEWIDTH = 1;
    /**默认字的大小*/
    private static final int TEXTSIZE = 28;
    /**默认横线条数*/
    private static final int HLINENUM = 5;
    /**默认竖线条数*/
    private static final int VLINENUM = 288;
    /**默认线的间隔*/
    private float lineMargin;
    /**判断绘图顺序*/
    private boolean order;

    private Paint grayLinePaint;
    private Paint defaultLinePaint;
    private List<Float> data;
    private Paint defaultTextPaint;

    private int hLineNum;
    private int vLineNum;
    private float lineWidth;
    private Rect bounds;

    public BarGraphView(Context context)
    {
        this(context, null);
    }

    public BarGraphView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public BarGraphView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        grayLinePaint = getLinePaint(Color.GRAY, STROKEWIDTH);
        defaultLinePaint = getLinePaint(Color.BLUE, STROKEWIDTH);
        defaultTextPaint = getTextPaint(Color.BLUE, TEXTSIZE);

        bounds = new Rect();
    }

    private Paint getLinePaint(int color, int size)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        return paint;
    }

    private Paint getTextPaint(int color, int size)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(color);
        paint.setTextSize(size);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (0 == lineWidth)
        {
            lineWidth = STROKEWIDTH;
        }

        if (hLineNum <= 1)
        {
            hLineNum = HLINENUM;
        }

        if (vLineNum <= 1)
        {
            vLineNum = VLINENUM;
        }

        int width = getWidth();
        int height = getHeight();

        canvas.drawColor(Color.WHITE);

        if (data != null && !data.isEmpty())
        {
            int size = data.size();

            int maxCount = 0;
            float theMax = 0;

            for (int i = 0; i < size; i++)
            {
                float f = data.get(i);

                if (theMax < f)
                {
                    theMax = f;
                    maxCount = i;
                }
            }

            if (!order && vLineNum > size)
            {
                maxCount = vLineNum - size + maxCount;
            }

            String maxStr = "";
            if(theMax > 0)
            {
                maxStr = getFlowSpeed(theMax);
            }
            else
            {
                return;
            }
                    

            defaultTextPaint.getTextBounds(maxStr, 0, maxStr.length(), bounds);

            float textRecH = 2 * bounds.height();

            float barHeight = height - textRecH;

            if (0 == lineMargin)
            {
                lineMargin = (width - vLineNum * defaultLinePaint.getStrokeWidth()) / vLineNum;
            }

            float defaultCell = lineMargin + defaultLinePaint.getStrokeWidth();
            float bottomWidth = size * defaultCell;
            float totalWidth = bottomWidth > width ? bottomWidth : width;

            for (int j = 0; j < hLineNum; j++)
            {
                canvas.drawLine(0, j * barHeight / (hLineNum - 1) + textRecH, totalWidth, j * barHeight
                        / (hLineNum - 1) + textRecH, grayLinePaint);
                if ((hLineNum - 1) == j)
                {
                    if (order)
                    {
                        defaultLinePaint.setStrokeWidth(2 * lineWidth);
                        canvas.drawLine(0, height - lineWidth, bottomWidth, height - lineWidth, defaultLinePaint);
                        defaultLinePaint.setStrokeWidth(lineWidth);
                        canvas.drawLine(bottomWidth, height - lineWidth, totalWidth, height - lineWidth, grayLinePaint);
                    }
                    else
                    {
                        defaultLinePaint.setStrokeWidth(2 * lineWidth);
                        canvas.drawLine(totalWidth - bottomWidth, height - lineWidth, totalWidth, height - lineWidth,
                                defaultLinePaint);
                        defaultLinePaint.setStrokeWidth(lineWidth);
                        canvas.drawLine(0, height - lineWidth, totalWidth - bottomWidth, height - lineWidth,
                                grayLinePaint);
                    }

                }
            }

            for (int i = 1; i <= size; i++)
            {
                float value = data.get(i - 1) / theMax > 1 ? 1 : data.get(i - 1) / theMax;
                if (order)
                {
                    canvas.drawLine(i * defaultCell, height - lineWidth, i * defaultCell, barHeight - value * barHeight
                            + textRecH, defaultLinePaint);
                }
                else
                {
                    canvas.drawLine(i * defaultCell + totalWidth - bottomWidth, height - lineWidth, i * defaultCell
                            + totalWidth - bottomWidth, barHeight - value * barHeight + textRecH, defaultLinePaint);
                }
            }

            float textPLace = 0;

            if ((maxCount * defaultCell + bounds.width() / 2) >= width)
            {
                textPLace = width - bounds.width();
            }
            else if ((maxCount * defaultCell - bounds.width() / 2) <= 0)
            {
                textPLace = 0;
            }
            else
            {
                textPLace = maxCount * defaultCell - bounds.width() / 2;
            }

            canvas.drawText(maxStr, textPLace, 3 * textRecH / 4, defaultTextPaint);
        }
    }

    public void setData(List<Float> data)
    {
        setData(data, true);
    }

    /**
     * true代表正序
     * false带面反序
     * <br>
     * @see    [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @since  LinkHome, 2016-4-15
     * @param data
     * @param order
     */
    public void setData(List<Float> data, boolean order)
    {
        this.order = order;
        this.data = data;
    }

    public void setBackColor(int color)
    {
        grayLinePaint.setColor(color);
    }

    public void setDefaultColor(int color)
    {
        defaultLinePaint.setColor(color);
        defaultTextPaint.setColor(color);
    }

    public void setTextSize(float size)
    {
        defaultTextPaint.setTextSize(size);
    }

    public void setLineWidth(float size)
    {
        this.lineWidth = size;
        defaultLinePaint.setStrokeWidth(size);
        grayLinePaint.setStrokeWidth(size);
    }

    /**
     * 设置横线的条数
     * <br>
     * @see    [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @since  LinkHome, 2016-4-14
     * @param count
     */
    public void setHLineNum(int count)
    {
        hLineNum = count;
    }

    /**
     * 设置竖线的条数
     * <br>
     * @see    [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @since  LinkHome, 2016-4-14
     * @param count
     */
    public void setVLineNum(int count)
    {
        vLineNum = count;
    }
    
    /**
     * 获取竖线的条数
     * <br>
     * @see    [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @since  LinkHome, 2016-4-14
     */
    public int getVLineNum()
    {
        if(0 == vLineNum)
        {
            return VLINENUM;
        }
        
        return vLineNum;
    }

    /**
     * 设置竖线之间的间隔
     * <br>
     * @see    [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @since  LinkHome, 2016-4-14
     * @param lineMargin
     */
    public void setLineMargin(float lineMargin)
    {
        this.lineMargin = lineMargin;
    }

    /**
     * 重绘
     * <br>
     * @see    [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @since  LinkHome, 2016-4-14
     */
    public void notifyDataChanged()
    {
        invalidate();
    }

    /**
     * 显示最大值，定制流量速度
     * <br>
     * @see    [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @since  LinkHome, 2016-4-16
     * @param max
     * @return
     */
    private String getFlowSpeed(float max)
    {
        return Formatter.formatFileSize(getContext(), (long) (max * 1024 / 300)) + "/s";
    }
}
