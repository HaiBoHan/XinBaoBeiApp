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

public class CepingFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_ceping, container, false);
		Handler_Inject.injectFragment(this, rootView);
		return rootView;
	}

	private View rootView;

	@InjectView
	private WebView wv_ceping;

	@InjectInit
	protected void init() {
		
		Login m_login = ((MyApplication)this.getActivity().getApplication()).loginInfo;
		String strUser = "";
		int userId = -1;
		if(m_login != null)
		{
//			strUser = m_login.ResultJson.get(0).Account;
			strUser = m_login.ResultJson.get(0).Judge_user_account;
			userId = m_login.ResultJson.get(0).ID;
		}
			
		WebSettings webSettings = wv_ceping.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setUseWideViewPort(true);//可任意比例缩放
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		wv_ceping.setInitialScale(80);//初始缩放值
		wv_ceping.loadUrl(Config.API_Ceping + strUser);
		wv_ceping.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		wv_ceping.setWebChromeClient(new WebChromeClient() {
			 
		    @Override
		    public boolean onJsAlert(WebView view, String url, String message,
		            final android.webkit.JsResult result) {
		        AlertDialog.Builder builder = new AlertDialog.Builder(CepingFragment.this.getActivity());
		        builder.setMessage(message)
		                .setNeutralButton("确定", new OnClickListener() {
		                    @Override
		                    public void onClick(DialogInterface arg0, int arg1) {
		                        arg0.dismiss();
		                    }
		                }).show();
		        result.cancel();
		        return true;
		    }
		 
		    @Override
		    public boolean onJsConfirm(WebView view, String url,
		            String message, final android.webkit.JsResult result) {
		    	
		    	AlertDialog.Builder builder = new AlertDialog.Builder(CepingFragment.this.getActivity());
		    	builder.setTitle("温馨提示");
		    	builder.setMessage(message);
		    	builder.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    	result.confirm();
                    }
                });
		    	builder.setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    	result.cancel();
                    }
                }).show();
		        return true;
		    }
		});

	}
}
