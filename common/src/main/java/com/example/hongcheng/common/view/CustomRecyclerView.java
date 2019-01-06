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

public class CustomRecyclerView extends RecyclerView
{
	private View emptyView;
	private boolean isLoadMore = false;
	private boolean enableLoadMore = true;
	
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

	public CustomRecyclerView(Context context) {
		super(context);
	}
	
	public CustomRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CustomRecyclerView(Context context, AttributeSet attrs,
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
		if (adapter == null) {
			return;
		}

		LoadMoreWrapper loadMoreWrapper = new LoadMoreWrapper(adapter);
		final Adapter oldAdapter = getAdapter();
		if (oldAdapter != null) {
			oldAdapter.unregisterAdapterDataObserver(observer);
		}

		super.setAdapter(loadMoreWrapper);
		loadMoreWrapper.registerAdapterDataObserver(observer);
		
		checkIfEmpty();
	}
	
	/**
	 * 如果CustomRecyclerView的父布局是SwipeRefreshLayout的话调用该方法需要在设置SwipeRefreshLayout属性之前
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

	public void setLoadMoreListener(final OnLoadMoreListener onLoadMoreListener) {
		addOnScrollListener(new EndlessRecyclerOnScrollListener() {
			@Override
			public void onUpward() {
				if(!enableLoadMore || isLoadMore) {
					return;
				}

				setLoadState(LoadMoreWrapper.LOADING);
				if(onLoadMoreListener != null) {
					onLoadMoreListener.onLoadMore();
				}
			}
		});
	}

	public void enableLoadMore(boolean enable) {
		if (enable) {
			isLoadMore = false;
			enableLoadMore = true;
			if (getAdapter() != null) {
                getAdapter().notifyDataSetChanged();
            }
		} else {
			setLoadState(LoadMoreWrapper.LOADING_END);
		}
	}

	public void loadError() {
		setLoadState(LoadMoreWrapper.LOADING_FAIL);
	}

	private void setLoadState(int state) {
		if(LoadMoreWrapper.LOADING == state) {
			isLoadMore = true;
		}else if(LoadMoreWrapper.LOADING_FAIL == state) {
			isLoadMore = false;
		} else if (LoadMoreWrapper.LOADING_END == state) {
			isLoadMore = false;
			enableLoadMore = false;
		}

		Adapter adapter = getAdapter();
		if (adapter instanceof LoadMoreWrapper) {
			LoadMoreWrapper wrapper = (LoadMoreWrapper) adapter;
			wrapper.setLoadState(state);
		}

	}

	public interface OnLoadMoreListener{
		void onLoadMore();
	}
}