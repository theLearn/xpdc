package com.example.hongcheng.common.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.hongcheng.common.R;
import com.example.hongcheng.common.view.spinkit.SpinKitView;
import com.example.hongcheng.common.view.spinkit.SpriteFactory;
import com.example.hongcheng.common.view.spinkit.Style;
import com.example.hongcheng.common.view.spinkit.sprite.SpriteContainer;

/**
 * Created by hongcheng on 17/8/21.
 */
public class LoadingFragment extends DialogFragment implements DialogInterface.OnKeyListener {
	protected Dialog dialogView;
	
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
		dialogView = new Dialog(getActivity(), R.style.Loading_Dialog);
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
		dialogView.setContentView(R.layout.loading_dialog);
		dialogView.setCanceledOnTouchOutside(false); // 外部点击取消
		// 设置宽度为屏宽, 靠近屏幕底部。
		final Window window = dialogView.getWindow();
		if (window != null)
		{
			window.setWindowAnimations(R.style.AnimTop);
			final WindowManager.LayoutParams lp = window.getAttributes();
			Point size = new Point();
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			display.getSize(size);
			lp.gravity = Gravity.CENTER_VERTICAL; // 紧贴底部
			lp.width = size.x * 1 / 2;
			lp.height = size.x * 1 / 2;
			window.setAttributes(lp);
		}

		dialogView.setOnKeyListener(this);

		initView();
		return dialogView;
	}
	
	private void initView()
	{
	    SpinKitView spinKitView = dialogView.findViewById(R.id.spin_kit_loading);
        Style style = Style.values()[7];
        SpriteContainer drawable = (SpriteContainer) SpriteFactory.create(style);
        drawable.setColor(getActivity().getResources().getColor(R.color.white));
        spinKitView.setIndeterminateDrawable(drawable);
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(dialogView.isShowing())
			{
				dismiss();
				if(getActivity() != null) {
					getActivity().finish();
				}
			}

			return true;
		}

		return false;
	}
}
