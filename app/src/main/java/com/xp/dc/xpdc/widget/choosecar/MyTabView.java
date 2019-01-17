package com.xp.dc.xpdc.widget.choosecar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.bean.CarClassfyInfo;
import com.xp.dc.xpdc.bean.CarInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 2018/4/13.
 */

public class MyTabView extends LinearLayout {
    private Activity mContext;
    private View view;
    private RadioGroup radioGroup;
    private List<CarClassfyInfo> typeList;
    private List<CarInfo> carList = new ArrayList<>();
    private StatisticsListView mSl;
    List<Toggle> toggleList = new ArrayList<>();

    int selectBtnId = -1;

    private OnTabChangeListener mOnTabChangeListener;

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        mOnTabChangeListener = onTabChangeListener;
    }

    public MyTabView(Context context) {
        super(context);
        this.mContext = (Activity) context;
        initView();
    }

    public MyTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (Activity) context;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_mytab, null);
        radioGroup = view.findViewById(R.id.radioGroup);
        mSl = (StatisticsListView) view.findViewById(R.id.sl);
        initTab();
        addView(view);
    }

    private void initTab() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                Log.e("mytabview", "onCheckedChanged");
                if (id == selectBtnId || typeList == null || typeList.size() == 0) {
                    return;
                }
                selectBtnId = id;
                RadioButton button = radioGroup.findViewById(id);
                int pos = -1;
                for (int i = 0; i < typeList.size(); i++) {
                    if (typeList.get(i).getType().equals(button.getText().toString())) {
                        pos = i;
                        break;
                    }
                }
                Log.e("mytabview", "pos=" + pos);
                if (pos < 0) return;
                mSl.clear();
                toggleList.clear();
                CarClassfyInfo bean = typeList.get(pos);
                carList.clear();
                carList.addAll(bean.getCarInfo());
                for (int i = 0; i < carList.size(); i++) {
                    CarInfo baseDataTypeBean = carList.get(i);
                    final Toggle toggle = new Toggle(mContext);
                    toggle.setType(baseDataTypeBean.getCarName());
                    toggle.setPosition(i);
                    toggle.setTopTextSize(12);
                    toggle.setButtonTextSize(12);
                    toggle.setStrokeWidth(3);
                    toggle.setTopText(baseDataTypeBean.getCarName());
                    toggle.setBottonText(baseDataTypeBean.getPrice());
                    toggle.setOn_text_res_id(baseDataTypeBean.getCarIcon());
                    toggle.setOff_text_res_id(baseDataTypeBean.getCarIcon());
                    toggle.setStyle(Toggle.Style.GREEN);
                    toggle.setToggleOn(false);
                    toggle.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toggle curToggle = null;
                            for (Toggle tog : toggleList) {
                                if (tog.isToggleOn()) {
                                    curToggle = tog;
                                    break;
                                }

                            }
                            toggle.toggle();
                            if (null != mOnTabChangeListener) {
                                if (toggle.equals(curToggle)) {
                                    mOnTabChangeListener.onToggleSingle(toggle, null);
                                } else {
                                    if (curToggle != null)
                                        curToggle.toggle();
                                    mOnTabChangeListener.onToggleSingle(toggle, curToggle);
                                }
                            }
                        }
                    });
                    mSl.addView(toggle);
                    LayoutParams layoutParams = ((LayoutParams) toggle.getLayoutParams());
                    if (carList.size() < 4) {
                        layoutParams.width = mContext.getWindowManager().getDefaultDisplay().getWidth() / carList.size();
                    } else {
                        layoutParams.width = mContext.getWindowManager().getDefaultDisplay().getWidth() / 4;
                    }
                    toggle.setLayoutParams(layoutParams);
                    toggleList.add(toggle);
                }
                if (mOnTabChangeListener != null) {
                    mOnTabChangeListener.onTabSelected(pos, bean.getType(), toggleList);
                }
                if (toggleList.size() > 0) {
                    for (Toggle toggle : toggleList) {
                        if (!"0".equals(toggle.getTopText())) {
                            toggle.setToggleOn(true);
                            if (mOnTabChangeListener != null) {
                                mOnTabChangeListener.onToggleSingle(toggle, null);
                            }
                            break;
                        }
                    }
                }
            }
        });
    }

    public void setToggleTopText(ArrayList<CarInfo> list) {
        if (null == toggleList || toggleList.size() == 0) {
            return;
        }
        for (Toggle toggle : toggleList) {
            int i = 0;
            toggle.setTopText(String.valueOf(i));
        }
    }

    public int getResource(String imageName) {
        Context ctx = mContext.getBaseContext();
        int resId = mContext.getResources().getIdentifier(imageName, "mipmap", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    public void initContent(List<CarClassfyInfo> list) {
        selectBtnId = -1;
        radioGroup.clearCheck();
        typeList = list;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCarInfo().size() > 0) {
                RadioButton button = (RadioButton) radioGroup.getChildAt(i);
                button.setChecked(true);
                return;
            }

        }
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) MyTabView.this.radioGroup.getLayoutParams();
        //获取当前控件的布局对象
        params.width = width;//设置当前控件布局的高度
        MyTabView.this.radioGroup.setLayoutParams(params);//将设置好的布局参数应用到控件中
    }

    public interface OnTabChangeListener {
        void onTabSelected(int pos, String title, List<Toggle> toggleList);

        void onToggleSingle(Toggle openToggle, Toggle closeToggle);
    }
}
