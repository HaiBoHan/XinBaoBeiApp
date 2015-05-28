package com.xinbaobeijiaoyu.ceping;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Json;
import com.android.pc.util.Handler_Properties;
import com.android.pc.util.Handler_SharedPreferences;
import com.android.pc.util.Handler_System;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.ui.ProgressHUD;

@InjectLayer(R.layout.activity_newlogin)
public class NewLoginActivity extends BaseActivity {

	@InjectView
	EditText txt_username, txt_password;

	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	TextView tv_forgotpassword,tv_regedit;
	
	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	Button btn_login;
	
	ProgressHUD mProgressHUD;
	
	@InjectInit
	protected void init(){
		Login m_login = Handler_SharedPreferences.readObject("XBB_SP", "user");
		((MyApplication)this.getApplication()).loginInfo = m_login;
		if(m_login != null){
			txt_username.setText(m_login.ResultJson.get(0).Account);
			
			txt_password.setFocusable(true);
			txt_password.setFocusableInTouchMode(true);
			txt_password.requestFocus();
			txt_password.requestFocusFromTouch();
		}
	}
	
	protected void click(View view) {
		switch (view.getId()) {
		case R.id.btn_login:
			if (txt_username.getText().toString().trim().equals("") == true
					|| txt_password.getText().toString().trim().equals("") == true)
				Toast.makeText(getApplicationContext(), "请输入正确的用户名及密码！",
						Toast.LENGTH_LONG).show();
			else {
				LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();

				String strUserName = txt_username.getText().toString().trim();
				// 做了一次utf-8的转码,要不提交到数据库是乱码
				try {
					strUserName = java.net.URLEncoder.encode(strUserName,"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				params.put("Account",strUserName );
				params.put("Pwd", txt_password.getText().toString().trim());
				InternetConfig config = new InternetConfig();
//				config.setCharset("GB2312");
				config.setCharset("utf-8");

				// 异步请求
				FastHttpHander.ajaxGet(Config.API_LOGIN, params,config, this);
				setHUBStatus(this,1, "努力登陆中...");
			}
			break;
		case R.id.tv_forgotpassword:
			startActivity(new Intent(this, FindbackPwdActivity.class));			
			break;
		case R.id.tv_regedit:
			startActivity(new Intent(this, RegisterActivity.class));			
			break;
		}
	}

	@InjectHttp
	private void result(ResponseEntity r) {
		switch (r.getStatus()) {
		case FastHttp.result_ok:
			setHUBStatus(this, 0, "");
			
			String strResult = r.getContentAsString();
			Login m_login = new Login();
			try{
				Handler_Json.JsonToBean(m_login, strResult);
				
				if(m_login.IsSuccess == false
						|| m_login.ResultJson == null
						|| m_login.ResultJson.size() < 1)
				{
					// "登录失败，请重试！"
					String msg = MessageFormat.format("登录失败，{0}", m_login.Message);
					Toast.makeText(getApplicationContext(),msg ,Toast.LENGTH_LONG).show();
					return;
				}
				
				//缓存起来，下次直接取
				Handler_SharedPreferences.saveObject("XBB_SP", "user", m_login);
				((MyApplication)this.getApplication()).loginInfo = m_login;
				
				startActivity(new Intent(this, NewFrontActivity.class));
				this.finish();
				
			}
			catch(Exception ex)
			{
				setHUBStatus(this, 0, "");
				Toast.makeText(getApplicationContext(), "登录失败，请重试！",
						Toast.LENGTH_LONG).show();
			}
			
			break;
		case FastHttp.result_net_err:
			setHUBStatus(this, 0, "");
			Toast.makeText(getApplicationContext(), "登录失败，请重试！",
					Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

}
