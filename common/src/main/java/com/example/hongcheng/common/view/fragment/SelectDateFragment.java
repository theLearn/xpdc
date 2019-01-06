package com.example.hongcheng.common.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.hongcheng.common.R;
import com.example.hongcheng.common.view.wheelview.WheelStyle;
import com.example.hongcheng.common.view.wheelview.WheelView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by hongcheng on 17/8/21.
 */

public class SelectDateFragment extends DialogFragment
{
	public static final int ALL_MODE = 0;
	public static final int YM_MODE = 1;
	
	private WheelView yearWheel;
	
	private WheelView monthWheel;
	
	private WheelView dayWheel;
	
	private int selectYear;
	
	private int selectMonth;
	
	protected Context context;
	
	protected Dialog dialogView;
	
	private int mode = ALL_MODE;
	
	private OnClickListener onClickListener;
	
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
		dialogView = new Dialog(getActivity(), R.style.BottomDialog);
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
		dialogView.setContentView(R.layout.fragment_select_data);
		dialogView.setCanceledOnTouchOutside(false); // 外部点击取消
		// 设置宽度为屏宽, 靠近屏幕底部。
		final Window window = dialogView.getWindow();
		if (window != null)
		{
			window.setWindowAnimations(R.style.AnimBottom);
			final WindowManager.LayoutParams lp = window.getAttributes();
			Point size = new Point();
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			display.getSize(size);
			lp.gravity = Gravity.BOTTOM; // 紧贴底部
			lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
			lp.height = size.y * 1 / 3;
			window.setAttributes(lp);
		}
		
		initView();
		return dialogView;
	}
	
	private void initView()
	{
		yearWheel = (WheelView) dialogView.findViewById(R.id.select_date_wheel_year_wheel);
		monthWheel = (WheelView) dialogView.findViewById(R.id.select_date_month_wheel);
		dayWheel = (WheelView) dialogView.findViewById(R.id.select_date_day_wheel);
		
		if(YM_MODE == mode)
		{
			dayWheel.setVisibility(View.GONE);
		}
		
		yearWheel.setWheelStyle(WheelStyle.STYLE_YEAR);
		yearWheel.setOnSelectListener(new WheelView.SelectListener()
		{
			@Override
			public void onSelect(int index, String text)
			{
				selectYear = index + WheelStyle.minYear;
				dayWheel.setWheelItemList(WheelStyle.createDayString(selectYear, selectMonth));
			}
		});
		
		monthWheel.setWheelStyle(WheelStyle.STYLE_MONTH);
		monthWheel.setOnSelectListener(new WheelView.SelectListener()
		{
			@Override
			public void onSelect(int index, String text)
			{
				selectMonth = index + 1;
				dayWheel.setWheelItemList(WheelStyle.createDayString(selectYear, selectMonth));
			}
		});
		
		TextView cancelBt = (TextView) dialogView.findViewById(R.id.tv_select_data_cancel);
		cancelBt.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (onClickListener != null)
				{
					if (!onClickListener.onCancel())
					{
						dialogView.dismiss();
					}
				}
				else
				{
					dialogView.dismiss();
				}
			}
		});
		TextView sureBt = (TextView) dialogView.findViewById(R.id.tv_select_data_conform);
		sureBt.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int year = yearWheel.getCurrentItem() + WheelStyle.minYear;
				int month = monthWheel.getCurrentItem();
				int day = dayWheel.getCurrentItem() + 1;
				int daySize = dayWheel.getItemCount();
				if (day > daySize)
				{
					day = day - daySize;
				}
				
				GregorianCalendar cal = new GregorianCalendar(year, month, day, 0, 0, 0);
				long setTime = cal.getTimeInMillis();
				
				if (onClickListener != null)
				{
					if (!onClickListener.onSure(year, month, day, setTime))
					{
						dialogView.dismiss();
					}
				}
				else
				{
					dialogView.dismiss();
				}
			}
		});
		
		show();
	}
	
	/**
	 * 显示选择日期对话框
	 *
	 * @param year 默认显示的年
	 * @param month 默认月
	 * @param day 默认日
	 */
	public void show(int year, int month, int day)
	{
		if (dialogView == null || dialogView.isShowing())
		{
			return;
		}
		dayWheel.setWheelItemList(WheelStyle.createDayString(year - WheelStyle.minYear, month + 1));
		yearWheel.setCurrentItem(year - WheelStyle.minYear);
		monthWheel.setCurrentItem(month);
		dayWheel.setCurrentItem(day - 1);
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
	}
	
	/**
	 * 显示选择日期对话框
	 */
	public void show()
	{
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		show(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * 选择日期对话框回调
	 *
	 * @param listener
	 */
	public void setOnClickListener(OnClickListener listener)
	{
		onClickListener = listener;
	}
	
	/**
	 * 选择日期对话框回调接口，调用者实现
	 */
	public interface OnClickListener
	{
		boolean onSure(int year, int month, int day, long time);
		
		boolean onCancel();
	}
}
