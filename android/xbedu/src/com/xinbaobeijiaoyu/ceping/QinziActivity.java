package com.xinbaobeijiaoyu.ceping;

import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.xinbaobeijiaoyu.ceping.app.Config;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class QinziActivity extends BaseActivity {

	@InjectView
	private WebView wv_qinzi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_qinzi);
	}

	@InjectInit
	private void init() {
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
