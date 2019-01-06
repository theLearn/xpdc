package com.example.hongcheng.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import java.util.List;

/**
 * Created by hongcheng on 16/4/13.
 */
public class ProgressShowView extends View
{
    /** 默认线的宽度 */
    private static final int STROKEWIDTH = 2;
    
    /** 默认字的大小 */
    private static final int TEXTSIZE = 40;
    
    /** 默认圆的大小 */
    private static final int CYCLESIZE = 20;
    
    private Paint grayLinePaint;
    
    private Paint defaultLinePaint;
    
    private Paint grayDashPaint;
    
    private Paint defaultDashPaint;
    
    private Paint grayCyclePaint;
    
    private Paint defaultCyclePaint;
    
    private Paint defaultTextPaint;
    
    private List<Pair<String, Boolean>> data;
    
    private float cycleSize = CYCLESIZE;
    
    private float lineWidth = STROKEWIDTH;
    
    public ProgressShowView(Context context)
    {
        this(context, null);
    }
    
    public ProgressShowView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public ProgressShowView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init()
    {
        grayDashPaint = getDashPaint(Color.GRAY, STROKEWIDTH);
        defaultDashPaint = getDashPaint(Color.BLUE, STROKEWIDTH);
        grayLinePaint = getLinePaint(Color.GRAY, (int) lineWidth);
        defaultLinePaint = getLinePaint(Color.BLUE, (int) lineWidth);
        defaultTextPaint = getTextPaint(Color.BLUE, TEXTSIZE);
        defaultCyclePaint = getCyclePaint(Color.BLUE, STROKEWIDTH);
        grayCyclePaint = getCyclePaint(Color.GRAY, STROKEWIDTH);
    }
    
    private Paint getCyclePaint(int color, int size)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        return paint;
    }
    
    private Paint getLinePaint(int color, int size)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        return paint;
    }
    
    private Paint getDashPaint(int color, int size)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setAntiAlias(false);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        PathEffect effects = new DashPathEffect(new float[]{15,15,15,15},1);
        paint.setPathEffect(effects);
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
        
        if(data == null || data.size() < 2)
        {
            return;
        }
        
        int width = getWidth();
        int height = getHeight();
    
        float startTextWidth = 0, endTextWidth = 0, spaceWidth = 0, sumTextWidth = 0;
        
        for (int i = 0 ; i < data.size() ; i++)
        {
            Pair<String, Boolean> item = data.get(i);
    
            float textWidth = defaultTextPaint.measureText(item.first);
            sumTextWidth += textWidth;
            if(0 == i)
            {
                startTextWidth = textWidth;
            }
    
            if(data.size() - 1 == i)
            {
                endTextWidth = textWidth;
            }
        }
    
        if(sumTextWidth > width)
        {
            return;
        }
    
        spaceWidth = (width - (startTextWidth + endTextWidth) / 2) / (data.size() - 1);
    
        for (int i = 0 ; i < data.size() ; i++)
        {
            Pair<String, Boolean> item = data.get(i);
        
            float centerWidth = spaceWidth * i + startTextWidth / 2;
    
            if(item.second )
            {
                canvas.drawCircle(centerWidth, cycleSize + lineWidth, 4 * cycleSize / 5, defaultCyclePaint);
                canvas.drawCircle(centerWidth, cycleSize + lineWidth, cycleSize, defaultLinePaint);
            }
            else
            {
                canvas.drawCircle(centerWidth, cycleSize + lineWidth, 3 * cycleSize / 4, grayCyclePaint);
            }
            
            Rect bounds = new Rect();
            defaultTextPaint.getTextBounds(item.first, 0, item.first.length(), bounds);
            canvas.drawText(item.first, centerWidth - bounds.centerX(), height - bounds.height(), defaultTextPaint);
    
            if(i < data.size() - 1)
            {
                Pair<String, Boolean> next = data.get(i + 1);
                
                Path path = new Path();
                path.moveTo(centerWidth + CYCLESIZE, CYCLESIZE);
                path.lineTo(centerWidth - CYCLESIZE + spaceWidth,CYCLESIZE);
                canvas.drawPath(path, next.second ? defaultDashPaint : grayDashPaint);
            }
        }
    }
    
    
    public void setGrayCycleColor(int color)
    {
        grayCyclePaint.setColor(color);
    }
    public void setDefaultCycleColor(int color)
    {
        defaultCyclePaint.setColor(color);
    }
    
    public void setGrayLineColor(int color)
    {
        grayLinePaint.setColor(color);
    }
    public void setDefaultLineColor(int color)
    {
        defaultLinePaint.setColor(color);
    }
    
    public void setGrayDashColor(int color)
    {
        grayDashPaint.setColor(color);
    }
    public void setDefaultDashColor(int color)
    {
        defaultDashPaint.setColor(color);
    }
    
    public void setDefaultTextColor(int color)
    {
        defaultTextPaint.setColor(color);
    }
    
    public void setTextSize(float size)
    {
        defaultTextPaint.setTextSize(size);
    }
    
    public void setCycleSize(float size)
    {
        this.cycleSize = size;
    }
    
    public void setLineSize(float size)
    {
        grayLinePaint.setStrokeWidth(size);
        defaultLinePaint.setStrokeWidth(size);
    }
    
    /**
     * 重绘 <br>
     * @see [相关类，可选、也可多条，对于重要的类或接口建议注释]
     */
    public void notifyDataChanged()
    {
        invalidate();
    }
    
    public void setData(List<Pair<String, Boolean>> data)
    {
        this.data = data;
        notifyDataChanged();
    }
    
}
