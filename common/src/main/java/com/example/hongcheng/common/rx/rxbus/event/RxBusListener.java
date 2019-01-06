package com.example.hongcheng.common.rx.rxbus.event;

public class RxBusListener {
	public interface IRxBusListener {
		public void onRxBusStateChanged(int state);
	}
}
