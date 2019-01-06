package com.example.hongcheng.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.example.hongcheng.common.R;

public class BaseLoadWebView extends NestedScrollWebView
{
	private ProgressBar progressbar;
	private WebSettings webSettings;
	private boolean mClearHistory;
	private WebCallBack callBack;
	
	public BaseLoadWebView(Context context)
	{
		this(context, null);
	}
	
	public BaseLoadWebView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}
	
	public BaseLoadWebView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		Drawable drawable = context.getResources().getDrawable(
				R.drawable.progress_bar_states);
		progressbar.setProgressDrawable(drawable);
		progressbar.setMax(100);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 15, 0, 0));
		addView(progressbar);
		init();
	}
	
	private void init()
	{
		webSettings = getSettings();
		webSettings.setAllowFileAccess(true);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setUseWideViewPort(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			webSettings.setAllowFileAccessFromFileURLs(false);
			webSettings.setAllowUniversalAccessFromFileURLs(false);
		}
		setWebChromeClient(new ProgressWebChromeClient());
		setWebViewClient(new LazyWebViewClient());
	}
	
	private class ProgressWebChromeClient extends WebChromeClient
	{
		@Override
		public void onReceivedTitle(WebView view, String title)
		{
			super.onReceivedTitle(view, title);
			if(callBack != null)
			{
				callBack.onReceivedTitle(view, title);
			}
		}
		
		@Override
		public void onProgressChanged(WebView view, int newProgress)
		{
			if (newProgress == 100)
			{
				progressbar.setVisibility(GONE);
			}
			else
			{
				if (progressbar.getVisibility() == GONE)
				{
					progressbar.setVisibility(VISIBLE);
				}
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	}
	
	private class LazyWebViewClient extends WebViewClient
	{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			if (url.startsWith("tel:"))
			{
				return true;
			}
			return false;
		}
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{
			super.onPageStarted(view, url, favicon);
			webSettings.setBlockNetworkImage(true);
		}
		
		@Override
		public void onPageFinished(WebView view, String url)
		{
			super.onPageFinished(view, url);
			//这里必须要,不然回退有问题.
			if (mClearHistory)
			{
				//mClearHistry在用到Webview的Activity的onCreate中设为true
				mClearHistory = false;
				view.clearHistory();
			}
			webSettings.setBlockNetworkImage(false);
		}
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	public void setWebCallBack(WebCallBack callBack)
	{
		this.callBack = callBack;
	}
	
	public interface WebCallBack
	{
		void onReceivedTitle(WebView view, String title);
	}
}
