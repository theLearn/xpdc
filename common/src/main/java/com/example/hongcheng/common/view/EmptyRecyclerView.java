package com.example.hongcheng.common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * Created by hongcheng on 17/7/25.
 */

public class EmptyRecyclerView extends RecyclerView
{
	private View emptyView;
	private static final String TAG = EmptyRecyclerView.class.getName();
	
	final private AdapterDataObserver observer = new AdapterDataObserver() {
		@Override
		public void onChanged() {
			checkIfEmpty();
		}
		
		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			checkIfEmpty();
		}
		
		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			checkIfEmpty();
		}
	};
	
	public EmptyRecyclerView(Context context) {
		super(context);
	}
	
	public EmptyRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public EmptyRecyclerView(Context context, AttributeSet attrs,
	                         int defStyle) {
		super(context, attrs, defStyle);
	}
	
	private void checkIfEmpty() {
		if (emptyView != null && getAdapter() != null) {
			final boolean emptyViewVisible =
					getAdapter().getItemCount() == 0;
			emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
			setVisibility(emptyViewVisible ? GONE : VISIBLE);
		}
	}
	
	@Override
	public void setAdapter(Adapter adapter) {
		final Adapter oldAdapter = getAdapter();
		if (oldAdapter != null) {
			oldAdapter.unregisterAdapterDataObserver(observer);
		}
		super.setAdapter(adapter);
		if (adapter != null) {
			adapter.registerAdapterDataObserver(observer);
		}
		
		checkIfEmpty();
	}
	
	/**
	 * 如果EmptyRecyclerView的父布局是SwipeRefreshLayout的话调用该方法需要在设置SwipeRefreshLayout属性之前
	 * @param emptyView
	 */
	public void setEmptyView(View emptyView) {
		this.emptyView = emptyView;
		
		ViewGroup.LayoutParams lp = getLayoutParams();
		ViewParent parent = getParent();
		
		FrameLayout container = new FrameLayout(getContext());
		ViewGroup group = (ViewGroup) parent;
		int index = group.indexOfChild(this);
		group.removeView(this);
		group.addView(container, index, lp);
		container.addView(this);
		container.addView(emptyView);
		
		checkIfEmpty();
	}
}