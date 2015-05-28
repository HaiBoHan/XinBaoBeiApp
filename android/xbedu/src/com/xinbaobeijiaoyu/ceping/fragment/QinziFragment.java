package com.xinbaobeijiaoyu.ceping.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.util.Handler_Inject;
import com.android.pc.util.Handler_SharedPreferences;
import com.xinbaobeijiaoyu.ceping.MyApplication;
import com.xinbaobeijiaoyu.ceping.R;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.Login;

public class QinziFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_qinzi, container, false);
		Handler_Inject.injectFragment(this, rootView);
		return rootView;
	}

	private View rootView;

	@InjectView
	private WebView wv_qinzi;

	@InjectInit
	protected void init() {

		if (wv_qinzi != null)
		{
			WebSettings webSettings = wv_qinzi.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
			webSettings.setUseWideViewPort(true);//可任意比例缩放
			webSettings.setSupportZoom(true);
			webSettings.setBuiltInZoomControls(true);
			wv_qinzi.setInitialScale(80);//初始缩放值
			wv_qinzi.loadUrl(Config.URL_Qinzi);
			wv_qinzi.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
			webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		}
	}
}
