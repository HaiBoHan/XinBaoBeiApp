package com.xinbaobeijiaoyu.ceping;

import com.xinbaobeijiaoyu.ceping.ui.ProgressHUD;

import android.app.Activity;
import android.content.Context;

public class BaseActivity extends Activity {
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
