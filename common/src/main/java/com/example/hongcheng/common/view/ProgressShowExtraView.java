package com.example.hongcheng.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import java.util.List;

/**
 * Created by hongcheng on 16/4/13.
 */
public class ProgressShowExtraView extends View
{
	/**
	 * 默认线的宽度
	 */
	private static final int STROKEWIDTH = 2;
	
	/**
	 * 默认字的大小
	 */
	private static final int TEXTSIZE = 40;
	
	/**
	 * 默认圆的大小
	 */
	private static final int CYCLESIZE = 20;
	
	private Paint grayLinePaint;
	
	private Paint defaultLinePaint;
	
	private Paint grayCyclePaint;
	
	private Paint defaultCyclePaint;
	
	private Paint defaultTextPaint;
	
	private List<Pair<List<String>, Boolean>> data;
	
	private float cycleSize = CYCLESIZE;
	
	private float lineWidth = STROKEWIDTH;
	
	private List<Integer> colors;
	private List<Integer> sizes;
	
	public ProgressShowExtraView(Context context)
	{
		this(context, null);
	}
	
	public ProgressShowExtraView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}
	
	public ProgressShowExtraView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init()
	{
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
	
	private Paint getTextPaint(int color, float size)
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
		
		if (data == null || data.size() < 2)
		{
			return;
		}
		
		int width = getWidth();
		int height = getHeight();
		
		float startTextWidth = 0, endTextWidth = 0, spaceWidth = 0, sumTextWidth = 0, spaceHeight = 0, sumTextHeight = 0;
		
		for (int i = 0; i < data.size(); i++)
		{
			float tempSumTextWidth = 0;
			Pair<List<String>, Boolean> item = data.get(i);
			
			for (int j = 0; j < item.first.size(); j++)
			{
				if (colors.size() >= item.first.size() && sizes.size() >= item.first.size())
				{
					defaultTextPaint = getTextPaint(colors.get(j), sizes.get(j));
				}
				
				Rect bounds = new Rect();
				defaultTextPaint.getTextBounds(item.first.get(j), 0, item.first.get(j).length(), bounds);
				
				float textWidth = bounds.width();
				float textHeight = bounds.height();
				tempSumTextWidth += textWidth;
				if(sumTextHeight <= 0)
				{
					sumTextHeight += textHeight;
				}
				
				if (0 == i)
				{
					if (textWidth > startTextWidth)
					{
						startTextWidth = textWidth;
					}
				}
				
				if (data.size() - 1 == i)
				{
					if (textWidth > endTextWidth)
					{
						endTextWidth = textWidth;
					}
				}
			}
			
			if (tempSumTextWidth > sumTextWidth)
			{
				sumTextWidth = tempSumTextWidth;
			}
		}
		
		if (sumTextWidth > width)
		{
			return;
		}
		
		if (sumTextHeight + 2 * cycleSize > height)
		{
			return;
		}
		
		spaceWidth = (width - (startTextWidth + endTextWidth) / 2) / (data.size() - 1);
		spaceHeight = (height - 2 * cycleSize - sumTextHeight) / (data.get(0).first.size());
		
		for (int i = 0; i < data.size(); i++)
		{
			Pair<List<String>, Boolean> item = data.get(i);
			
			float centerWidth = spaceWidth * i + startTextWidth / 2;
			
			if (item.second)
			{
				canvas.drawCircle(centerWidth, cycleSize, cycleSize, defaultCyclePaint);
			}
			else
			{
				canvas.drawCircle(centerWidth, cycleSize, cycleSize, grayCyclePaint);
			}
			
			if (i < data.size() - 1)
			{
				Pair<List<String>, Boolean> next = data.get(i + 1);
				
				Path path = new Path();
				path.moveTo(centerWidth + cycleSize, cycleSize);
				path.lineTo(centerWidth - cycleSize + spaceWidth, cycleSize);
				canvas.drawPath(path, next.second ? defaultLinePaint : grayLinePaint);
			}
			
			for (int k = 0; k < item.first.size(); k++)
			{
				String content = item.first.get(k);
				Rect bounds = new Rect();
				if (colors.size() >= item.first.size() && sizes.size() >= item.first.size())
				{
					defaultTextPaint = getTextPaint(colors.get(k), sizes.get(k));
				}
				defaultTextPaint.getTextBounds(content, 0, content.length(), bounds);
				canvas.drawText(content, centerWidth - bounds.centerX(), 2 * cycleSize + (k + 1) * spaceHeight, defaultTextPaint);
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
	
	public void setCycleSize(float size)
	{
		this.cycleSize = size;
	}
	
	public void setTextColor(List<Integer> colors)
	{
		this.colors = colors;
	}
	
	public void setTextSize(List<Integer> sizes)
	{
		this.sizes = sizes;
	}
	
	
	/**
	 * 重绘 <br>
	 *
	 * @see [相关类，可选、也可多条，对于重要的类或接口建议注释]
	 */
	public void notifyDataChanged()
	{
		invalidate();
	}
	
	public void setData(List<Pair<List<String>, Boolean>> data)
	{
		this.data = data;
		notifyDataChanged();
	}
}
