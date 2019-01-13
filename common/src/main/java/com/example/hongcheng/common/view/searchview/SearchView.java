package com.example.hongcheng.common.view.searchview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.hongcheng.common.R;

/**
 * Created by Carson_Ho on 17/8/10.
 */

public class SearchView extends LinearLayout {

    /**
     * 初始化成员变量
     */
    private Context context;

    // 搜索框组件
    private EditText_Clear et_search; // 搜索按键
    private LinearLayout search_block; // 搜索框布局

    // 自定义属性设置
    // 1. 搜索字体属性设置：大小、颜色 & 默认提示
    private Float textSizeSearch;
    private int textColorSearch;
    private String textHintSearch;

    // 2. 搜索框设置：高度 & 颜色
    private int searchBlockHeight;
    private int searchBlockColor;

    private boolean showSearchIcon;
    private boolean hintCenter;

    // 回调接口
    private  ICallBack mCallBack;// 搜索按键回调接口
    /**
     * 构造函数
     * 作用：对搜索框进行初始化
     */
    public SearchView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs); // ->>关注a
        init();// ->>关注b
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }

    /**
     * 关注a
     * 作用：初始化自定义属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {

        // 控件资源名称
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Search_View);

        // 搜索框字体大小（dp）
        textSizeSearch = typedArray.getDimension(R.styleable.Search_View_textSizeSearch, 20);

        // 搜索框字体颜色（使用十六进制代码，如#333、#8e8e8e）
        int defaultColor = context.getResources().getColor(R.color.colorText); // 默认颜色 = 灰色
        textColorSearch = typedArray.getColor(R.styleable.Search_View_textColorSearch, defaultColor);

        // 搜索框提示内容（String）
        textHintSearch = typedArray.getString(R.styleable.Search_View_textHintSearch);

        // 搜索框高度
        searchBlockHeight = typedArray.getInteger(R.styleable.Search_View_searchBlockHeight, 120);

        // 搜索框颜色
        int defaultColor2 = context.getResources().getColor(R.color.colorDefault); // 默认颜色 = 白色
        searchBlockColor = typedArray.getColor(R.styleable.Search_View_searchBlockColor, defaultColor2);

        showSearchIcon = typedArray.getBoolean(R.styleable.Search_View_showSearchIcon, true);
        hintCenter = typedArray.getBoolean(R.styleable.Search_View_hintCenter, false);

        // 释放资源
        typedArray.recycle();
    }


    /**
     * 关注b
     * 作用：初始化搜索框
     */
    private void init(){
        // 1. 绑定R.layout.search_layout作为搜索框的xml文件
        LayoutInflater.from(context).inflate(R.layout.search_layout,this);

        // 2. 绑定搜索框EditText
        et_search = (EditText_Clear) findViewById(R.id.et_search);
        et_search.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeSearch);
        et_search.setTextColor(textColorSearch);
        et_search.setHintTextColor(getContext().getResources().getColor(R.color.text_gray_99));
        et_search.setHint(textHintSearch);
        et_search.setNeedClearIcon(showSearchIcon);
        if(hintCenter) {
            et_search.setGravity(Gravity.CENTER);
        }


        // 3. 搜索框背景颜色
        search_block = (LinearLayout)findViewById(R.id.search_block);
        LayoutParams params = (LayoutParams) search_block.getLayoutParams();
        params.height = searchBlockHeight;
//        search_block.setBackgroundColor(searchBlockColor);
        search_block.setLayoutParams(params);

        /**
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
        et_search.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!(mCallBack == null)){
                        mCallBack.queryData(et_search.getText().toString());
                    }
                }
                return false;
            }
        });


        /**
         * 搜索框的文本变化实时监听
         */
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                if (!(mCallBack == null)){
                    mCallBack.queryData(et_search.getText().toString());
                }

            }
        });

        et_search.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!(mCallBack == null)){
                    mCallBack.onFocusChange(hasFocus);
                }
            }
        });

        findViewById(R.id.iv_search).setVisibility(showSearchIcon ? View.VISIBLE : View.GONE);
        findViewById(R.id.iv_search).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mCallBack == null)){
                    mCallBack.queryData(et_search.getText().toString());
                }
            }
        });
    }

    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     */
    public void setOnSearchListener(ICallBack mCallBack){
        this.mCallBack = mCallBack;
    }

    public void setText(String name) {
        et_search.setText(name);
    }

    public String getText() {
        return et_search.getText().toString().trim();
    }
}
