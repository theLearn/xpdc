package com.example.hongcheng.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.example.hongcheng.common.R;

/*
 * 自定义的滑动开关
 */
public class SlipSwitchView extends View implements OnTouchListener
{
    
    private Bitmap bgOn, bgOff, slipperBtn;
    
    // 按下时的x和当前的x
    private float downX, nowX;
    
    // 记录用户是否在滑动
    private boolean onSlip = false;
    
    // 当前的状态
    private boolean nowStatus = false;
    
    // 滑动前的状态
    private boolean preStatus = false;
    
    // 是否响应onTouchListener事件
    private boolean disabled = false;
    
    public boolean isNowStatus()
    {
        return nowStatus;
    }
    
    public boolean isDisabled()
    {
        return disabled;
    }
    
    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }
    
    public void setNowStatus(boolean nowStatus)
    {
        this.nowStatus = nowStatus;
    }
    
    // 监听接口
    private OnChangedListener listener;
    
    public SlipSwitchView(Context context)
    {
        super(context);
        init();
    }
    
    public SlipSwitchView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    
    public void init()
    {
        // 载入图片资源
        bgOn = BitmapFactory.decodeResource(getResources(), R.drawable.btn_on);
        bgOff = BitmapFactory.decodeResource(getResources(), R.drawable.btn_off);
        slipperBtn = BitmapFactory.decodeResource(getResources(), R.drawable.btn_white);
        
        setOnTouchListener(this);
    }
    
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        // 滑动按钮的左边坐标
        float x = 0;
        
        if (nowStatus)
        {
            canvas.drawBitmap(bgOn, matrix, paint);// 画出打开时的背景
        }
        else
        {
            canvas.drawBitmap(bgOff, matrix, paint);// 画出关闭时的背景
        }
        
        if (onSlip)
        {
            // 是否是在滑动状态,
            if (nowX >= bgOn.getWidth())
            {
                // 是否划出指定范围,不能让滑块跑到外头,必须做这个判断
                x = bgOn.getWidth() - slipperBtn.getWidth() / 2;// 减去滑块1/2的长度
            }
            else
            {
                x = nowX - (float) slipperBtn.getWidth() / 2;
            }
        }
        else
        {
            if (nowStatus)
            {// 根据当前的状态设置滑块的x值
                x = bgOn.getWidth() - slipperBtn.getWidth();
            }
            else
            {
                x = 0;
            }
        }
        // 对滑块滑动进行异常处理，不能让滑块出界
        if (x < 0)
        {
            x = 0;
        }
        else if (x > bgOn.getWidth() - slipperBtn.getWidth())
        {
            x = bgOn.getWidth() - slipperBtn.getWidth();
        }
        
        // 画出滑块
        canvas.drawBitmap(slipperBtn, x, 0, paint);
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        // TODO Auto-generated method stub
        if (!isDisabled())
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    if (event.getX() > bgOff.getWidth() || event.getY() > bgOff.getHeight())
                    {
                        return false;
                    }
                    else
                    {
                        onSlip = true;
                        downX = event.getX();
                        nowX = downX;
                    }
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    nowX = event.getX();
                    break;
                }
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                {
                    onSlip = false;
                    if (nowStatus)
                    {
                        nowStatus = false;
                        nowX = 0;
                    }
                    else
                    {
                        nowStatus = true;
                        nowX = bgOn.getWidth() - slipperBtn.getWidth();
                    }
                    // 当滑动前状态与当前状态不一致时才触发onChange事件
                    if (listener != null && nowStatus != preStatus)
                    {
                        preStatus = nowStatus;
                        listener.onChanged(SlipSwitchView.this, nowStatus);
                    }
                    break;
                }
                default:
                    break;
            }
            // 刷新界面
            invalidate();
        }
        
        return true;
    }
    
    /**
     * 开关 的 状态发生改变 的 调用 的 外部 接口
     * @param listener
     */
    public void setOnChangedListener(OnChangedListener listener)
    {
        this.listener = listener;
    }
    
    /**
     * 设置滑动开关的初始状态，供外部调用
     * 
     * @param checked
     */
    public void setChecked(boolean checked)
    {
        if (checked)
        {
            nowX = bgOff.getWidth();
        }
        else
        {
            nowX = 0;
        }
        
        nowStatus = checked;
        preStatus = nowStatus;
        invalidate();
    }
    
    public interface OnChangedListener
    {
        public void onChanged(SlipSwitchView slipSwitch, boolean checkState);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bgOn.getWidth(), bgOn.getHeight());
    }
}
