package com.xinbaobeijiaoyu.ceping.fragment;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.xinbaobeijiaoyu.ceping.ui.ProgressHUD;

public class BaseFragmentActivity extends FragmentActivity{
	public ProgressHUD mProgressHUD;

	public void setHUBStatus(Context ctx, int status, String msg) {
		// 显示
		if (status == 1)
			mProgressHUD = ProgressHUD.show(ctx, msg, true,
					false, null);
		if (status == 0)
			if (mProgressHUD != null)
				mProgressHUD.dismiss();
	}
}
