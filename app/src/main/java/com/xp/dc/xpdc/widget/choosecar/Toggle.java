package com.xp.dc.xpdc.widget.choosecar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xp.dc.xpdc.R;


public class Toggle extends LinearLayout {

    private static final int STYLE_BLUE = 0;
    private static final int STYLE_RED = 1;
    int[] styles = new int[]{STYLE_BLUE, STYLE_RED};

    public String getTopText() {
        return mTv_total.getText().toString().trim();
    }

    public String getBottonText() {
        return mTv_type.getText().toString().trim();
    }


    public enum Style {
        BLUE("#0086f1", "#9e9e9e", "#e9f3fd", "#edebeb"),
        RED("#FF3334", "#9e9e9e", "#ffe2e2", "#edebeb"),
        GREEN("#5EC64D", "#9e9e9e", "#d7fdd0", "#edebeb"),
        YELLOW("#F4B840", "#9e9e9e", "#fff7e6", "#edebeb"),
        ORANGE("#F48440", "#9e9e9e", "#ffede2", "#edebeb"),
        PINK("#A150BC", "#9e9e9e", "#F4D5FF", "#edebeb"),
        LIGHTBLUE("#36c1c6", "#9e9e9e", "#C0FBFD", "#edebeb"),
        BROWN("#955A0E", "#9e9e9e", "#DFD5C8", "#edebeb");
        // 成员变量
        private String on_stroke_color;
        private String off_stroke_color;
        private String on_background_color;
        private String off_background_color;
//        private String on_top_color;
//        private String off_top_color;
//        private String on_botton_color;
//        private String off_botton_color;
        // 构造方法


        Style(String on_stroke_color, String off_stroke_color, String on_background_color, String off_background_color) {
            this.on_stroke_color = on_stroke_color;
            this.off_stroke_color = off_stroke_color;
            this.on_background_color = on_background_color;
            this.off_background_color = off_background_color;
        }

        public String getOn_stroke_color() {
            return on_stroke_color;
        }

        public void setOn_stroke_color(String on_stroke_color) {
            this.on_stroke_color = on_stroke_color;
        }

        public String getOn_background_color() {
            return on_background_color;
        }

        public void setOn_background_color(String on_background_color) {
            this.on_background_color = on_background_color;
        }

        public String getOff_stroke_color() {
            return off_stroke_color;
        }

        public void setOff_stroke_color(String off_stroke_color) {
            this.off_stroke_color = off_stroke_color;
        }

        public String getOff_background_color() {
            return off_background_color;
        }

        public void setOff_background_color(String off_background_color) {
            this.off_background_color = off_background_color;
        }

    }

    private boolean isToggleOn;
    private ImageView mIv_icon;
    private ImageView mIv_text_icon;
    private LinearLayout mLl_toggle;
    private TextView mTv_total;
    private Context mContext;
    private TextView mTv_type;
    private String type = "";
    private String on_stroke_color = "#3F51B5";
    private String on_background_color = "#edebeb";
    private String off_stroke_color = "#edebeb";
    private String off_background_color = "#edebeb";
    private String on_top_color = "#3F51B5";
    private String off_top_color = "#3F51B5";
    private String on_botton_color = "#3F51B5";
    private String off_botton_color = "#3F51B5";
    private int on_text_res_id;
    private int off_text_res_id;
    private int on_res_id;
    private int off_res_id;
    private int position;
    private OnToggleChangeListener mOnToggleChangeListener;
    private ViewGroup.LayoutParams mLayoutParams;
    private View mToggleView;
    private int topTextSize = 16;
    private int buttonTextSize = 16;
    private int strokeWidth = 2;

//    public void setOnToggleChangeListener(OnToggleChangeListener onToggleChangeListener) {
//        mOnToggleChangeListener = onToggleChangeListener;
//    }\

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStyle(Style style) {
        setOn_main_color(style.getOn_stroke_color());
        setOff_main_color(style.getOff_stroke_color());
        setOn_background_color(style.getOn_background_color());
        setOff_background_color(style.getOff_background_color());
    }

    public void setStyles(int index, Toggle toggle) {
        switch (index) {
            case 1:
                toggle.setStyle(Style.BLUE);
                break;
            case 2:
                toggle.setStyle(Style.YELLOW);
                break;
            case 3:
                toggle.setStyle(Style.ORANGE);
                break;
            case 4:
                toggle.setStyle(Style.GREEN);
                break;
            case 5:
                toggle.setStyle(Style.RED);
                break;
            case 6:
                toggle.setStyle(Style.BROWN);
                break;
            case 7:
                toggle.setStyle(Style.LIGHTBLUE);
                break;
            case 8:
                toggle.setStyle(Style.PINK);
                break;
            default:
                toggle.setStyle(Style.LIGHTBLUE);
                break;
        }
    }

    /**
     * 在java代码中创建对象时调用   new Toggle(context);
     *
     * @param context
     */
    public Toggle(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * 在setContentView加载布局时创建对象,系统调用  可以在该构造方法中获取用户设置的属性和样式
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public Toggle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 在setContentView加载布局时创建对象,系统调用  可以在该构造方法中获取用户设置的属性
     *
     * @param context
     * @param attrs
     */
    public Toggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        this.setClickable(true);

        // getContext() = context

        mToggleView = View.inflate(context, R.layout.view_toggle, this);
        //添加儿子
//        this.addView(mToggleView);
//        mLayoutParams = mToggleView.getLayoutParams();
//        mLayoutParams.width = this.getWidth();
//        mToggleView.requestLayout();

        //获取自定义属性  参数1:系统传递给咱们的属性  用户设置的属性     参数2:自定义属性后会在R文件中生成styleable.Toggle
        //伪代码: String title = null;  title = attrs.title    String type = null;   type = attrs.type
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Toggle);
        boolean isToggle = typedArray.getBoolean(R.styleable.Toggle_isToggle, true);
        //使用完毕后记得回收哦
        typedArray.recycle();

        mLl_toggle = (LinearLayout) findViewById(R.id.ll_toggle);
        mTv_total = (TextView) findViewById(R.id.tv_total);
        mIv_icon = (ImageView) findViewById(R.id.iv_icon);
        mIv_text_icon = (ImageView) findViewById(R.id.iv_text_icon);
        mTv_type = (TextView) findViewById(R.id.tv_type);
        setPadding(10, 10, 10, 10);
//        if (!isToggle) {
//            mIv_icon.setVisibility(View.GONE);
//        }

//        mTv_total.setText(title);
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        mLayoutParams.width  = w;
//        mToggleView.setLayoutParams(mLayoutParams);
//    }


    public int getOn_text_res_id() {
        return on_text_res_id;
    }

    public void setOn_text_res_id(int on_text_res_id) {
        this.on_text_res_id = on_text_res_id;
    }

    public int getOff_text_res_id() {
        return off_text_res_id;
    }

    public void setOff_text_res_id(int off_text_res_id) {
        this.off_text_res_id = off_text_res_id;
    }

    public int getOn_res_id() {
        return on_res_id;
    }

    public void setOn_res_id(int on_res_id) {
        this.on_res_id = on_res_id;
    }

    public int getOff_res_id() {
        return off_res_id;
    }

    public void setOff_res_id(int off_res_id) {
        this.off_res_id = off_res_id;
    }

    public String getOn_top_color() {
        return on_top_color;
    }

    public void setOn_top_color(String on_top_color) {
        this.on_top_color = on_top_color;
    }

    public String getOff_top_color() {
        return off_top_color;
    }

    public void setOff_top_color(String off_top_color) {
        this.off_top_color = off_top_color;
    }

    public String getOn_botton_color() {
        return on_botton_color;
    }

    public void setOn_botton_color(String on_botton_color) {
        this.on_botton_color = on_botton_color;
    }

    public String getOff_botton_color() {
        return off_botton_color;
    }

    public void setOff_botton_color(String off_botton_color) {
        this.off_botton_color = off_botton_color;
    }

    public int getTopTextSize() {
        return topTextSize;
    }

    public void setTopTextSize(int topTextSize) {
        this.topTextSize = topTextSize;
        mTv_total.setTextSize(topTextSize);
    }


    public int getButtonTextSize() {
        return buttonTextSize;
    }

    public void setButtonTextSize(int buttonTextSize) {
        this.buttonTextSize = buttonTextSize;
        mTv_type.setTextSize(buttonTextSize);
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getOn_stroke_color() {
        return on_stroke_color;
    }

    public void setOn_stroke_color(String on_stroke_color) {
        this.on_stroke_color = on_stroke_color;
    }

    public String getOn_background_color() {
        return on_background_color;
    }

    public void setOn_background_color(String on_background_color) {
        this.on_background_color = on_background_color;
    }

    public String getOff_stroke_color() {
        return off_stroke_color;
    }

    public void setOff_stroke_color(String off_stroke_color) {
        this.off_stroke_color = off_stroke_color;
    }

    public String getOff_background_color() {
        return off_background_color;
    }

    public void setOff_background_color(String off_background_color) {
        this.off_background_color = off_background_color;
    }

    public void setTopText(String total) {
        mTv_total.setText(total);
    }

//    public void setIcon(int resId) {
//        if (0 != resId)
//            mIv_icon.setImageDrawable(mContext.getDrawable(resId));
//    }

    public void setBottonText(String type) {
        mTv_type.setText(type);
    }

    public void setOnTextColor(String color) {
        this.on_top_color = color;
        this.on_botton_color = color;
    }

    public void setOffTextColor(String color) {
        this.off_top_color = color;
        this.off_botton_color = color;
    }


    public void setOn_main_color(String on_main_color) {
        this.on_top_color = on_main_color;
        this.on_botton_color = on_main_color;
        this.on_stroke_color = on_main_color;
    }


    public void setOff_main_color(String off_main_color) {
        this.off_top_color = off_main_color;
        this.off_botton_color = off_main_color;
        this.off_stroke_color = off_main_color;
    }

    private Drawable getOnDrawable() {
        return   mContext.getResources().getDrawable(R.drawable.ic_choosed_bg);
    }

    private Drawable getOffDrawable() {
        return   mContext.getResources().getDrawable(R.mipmap.ic_unchoose);
    }

    /**
     * 开关功能
     */
    public void toggle() {
        setToggleOn(!isToggleOn);
//        if (mOnToggleChangeListener != null) {
//            mOnToggleChangeListener.isToggleOn(isToggleOn(), getPosition());
//        }
    }

    /**
     * 设置开关状态
     *
     * @param isToggleOn
     */
    public void setToggleOn(boolean isToggleOn) {
        this.isToggleOn = isToggleOn;
        if (isToggleOn) {
            mLl_toggle.setBackgroundDrawable(getOnDrawable());
            mTv_total.setTextColor(Color.parseColor(on_top_color));
            mTv_type.setTextColor(Color.parseColor(on_botton_color));
            mIv_icon.setImageDrawable(on_res_id == 0 ? null : mContext.getDrawable(on_res_id));
            mIv_text_icon.setImageDrawable(on_text_res_id == 0 ? null : mContext.getDrawable(on_text_res_id));
        } else {
            mLl_toggle.setBackgroundDrawable(getOffDrawable());
            mTv_total.setTextColor(Color.parseColor(off_top_color));
            mTv_type.setTextColor(Color.parseColor(off_botton_color));
            mIv_icon.setImageDrawable(off_res_id == 0 ? null : mContext.getDrawable(off_res_id));
            mIv_text_icon.setImageDrawable(off_text_res_id == 0 ? null : mContext.getDrawable(off_text_res_id));
        }
    }


    /**
     * 获取开关状态
     *
     * @return
     */
    public boolean isToggleOn() {
        return isToggleOn;
    }


    public interface OnToggleChangeListener {
        void isToggleOn(boolean isToggleOn, int position);
    }
}
