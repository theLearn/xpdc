package com.xp.dc.xpdc.utils;

import android.os.CountDownTimer;

/**
 * 倒计时工具类
 * @author Jincai Liu
 * @date 2017-08-12
 */

public class TimerUtil {
	CountDownTimer timer;
	private TimerListener mListener;
	
	/**
	 * 开始倒计时
	 * @param time
	 */
	public void startTiemr(long time){
		timer = new CountDownTimer(time*1000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				if(mListener != null)
					mListener.onTick(millisUntilFinished);
			}
			
			@Override
			public void onFinish() {
				if(mListener != null)
					mListener.onFinish();
			}
		};
		timer.start();
	}
	
	/*
	 * 结束倒计时
	 */
	public void cancel(){
		if(timer != null)
			timer.cancel();
	//	timer = null;
		mListener = null;
	}
	
	
	
	public interface TimerListener{
		public void onTick(long millisUntilFinished);
		public void onFinish();
	}
	
	public void setTiemrListener(TimerListener listener){
		this.mListener = listener;
	}
}
