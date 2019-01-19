package com.example.hongcheng.common.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.widget.TextView;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.example.hongcheng.common.R;
import com.example.hongcheng.common.util.LoggerUtils;
import com.example.hongcheng.common.view.wheelview.CustomWheelView;
import com.example.hongcheng.common.view.wheelview.WheelStyle;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hongcheng on 17/8/21.
 */

public class SelectPreTimeFragment extends DialogFragment
{
	private CustomWheelView threeDayWheel;
	private CustomWheelView hourWheel;
	private CustomWheelView minuteWheel;

	private static final String[] DAY_STRS = new String[]{"今天", "明天", "后天"};

	private ArrayWheelAdapter threeDayAdapter;
	private ArrayWheelAdapter hourAdapter;
	private ArrayWheelAdapter minuteAdapter;

	private int dayIndex;
	private int selectHour;
	private int selectMinute;

	protected Context context;
	protected Dialog dialogView;
	
	private OnSelectListener onClickListener;
	
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
		dialogView = new Dialog(getActivity(), R.style.BottomDialog);
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
		dialogView.setContentView(R.layout.fragment_select_pre_time);
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
		threeDayWheel = dialogView.findViewById(R.id.select_pre_wheel_day_wheel);
		hourWheel = dialogView.findViewById(R.id.select_date_hour_wheel);
		minuteWheel = dialogView.findViewById(R.id.select_date_minute_wheel);

		threeDayWheel.setCyclic(false);
		threeDayAdapter = new ArrayWheelAdapter(WheelStyle.createThreeDayString());
		threeDayWheel.setAdapter(threeDayAdapter);
		threeDayWheel.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				dayIndex = index;
				hourAdapter = new ArrayWheelAdapter(WheelStyle.createHourString(index));
				hourWheel.setAdapter(hourAdapter);
				minuteAdapter = new ArrayWheelAdapter(WheelStyle.createMinuteString(index, selectHour));
				minuteWheel.setAdapter(minuteAdapter);
			}
		});

		hourWheel.setCyclic(false);
		hourAdapter = new ArrayWheelAdapter(WheelStyle.createHourString(0));
		hourWheel.setAdapter(hourAdapter);
		hourWheel.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				selectHour = index;
				minuteAdapter = new ArrayWheelAdapter(WheelStyle.createMinuteString(dayIndex, index));
				minuteWheel.setAdapter(minuteAdapter);
			}
		});

		minuteWheel.setCyclic(false);
		minuteAdapter = new ArrayWheelAdapter(WheelStyle.createMinuteString(0, 0));
		minuteWheel.setAdapter(minuteAdapter);
		minuteWheel.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				selectMinute = index;
			}
		});
		
		TextView cancelBt = (TextView) dialogView.findViewById(R.id.tv_select_pre_cancel);
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
		TextView sureBt = (TextView) dialogView.findViewById(R.id.tv_select_pre_conform);
		sureBt.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (onClickListener != null)
				{
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.DATE, dayIndex);
					String hour = (String) hourAdapter.getItem(selectHour);
					String minute = (String) minuteAdapter.getItem(selectMinute);

					try {
						calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
						calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
					}catch (NumberFormatException e) {
						LoggerUtils.error("NumberFormatException", e.getMessage());
					}

					long setTime = calendar.getTimeInMillis();

					StringBuilder sb = new StringBuilder();
					sb.append(DAY_STRS[dayIndex]);
					sb.append("  ");
					sb.append(hour);
					sb.append(" : ");
					sb.append(minute);

					if (!onClickListener.onSure(setTime, sb.toString()))
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
	}

	
	/**
	 * 选择日期对话框回调
	 *
	 * @param listener
	 */
	public void setOnClickListener(OnSelectListener listener)
	{
		onClickListener = listener;
	}
	
	/**
	 * 选择日期对话框回调接口，调用者实现
	 */
	public interface OnSelectListener
	{
		boolean onSure(long time, String timeStr);
		
		boolean onCancel();
	}
}
