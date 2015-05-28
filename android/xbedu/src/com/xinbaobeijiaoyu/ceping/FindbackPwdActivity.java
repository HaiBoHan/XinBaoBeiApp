package com.xinbaobeijiaoyu.ceping;

import java.util.LinkedHashMap;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Json;
import com.android.pc.util.Handler_SharedPreferences;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindbackPwdActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_findbackpwd);
	}

	@InjectInit
	private void init() {

	}

	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private Button findbackpwd, btn_findbackpwd_back,btn_findbackpwd_getcheckno;
	@InjectView
	private EditText et_findbackpwd_pwd, et_findbackpwd_repwd,tv_findbackpwd_telno;

	protected void click(View view) {
		switch (view.getId()) {
			//验证码
		case R.id.btn_findbackpwd_getcheckno:
			

			break;
		case R.id.btn_findbackpwd:
			//发请求：
			String strPwd = et_findbackpwd_pwd.getText().toString();
			String strPwd_re = et_findbackpwd_repwd.getText().toString();
			if(strPwd.equals("") || strPwd_re.equals(""))
			{
				Toast.makeText(getApplicationContext(), "密码不能为空！",Toast.LENGTH_LONG).show();
				return;
			}
			
			if(strPwd.equals(strPwd_re) == false)
			{
				Toast.makeText(getApplicationContext(), "密码不一致，请重新输入！",Toast.LENGTH_LONG).show();
				return;
			}
			
			LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
			String strParams = "{\"pwd\":\""+ et_findbackpwd_pwd.getText() +"\",\"telno\":\""+ tv_findbackpwd_telno.getText() +"\"}";
			params.put("user", strParams);
			InternetConfig config = new InternetConfig();
			config.setCharset("utf-8");

			// 异步请求
			FastHttpHander.ajaxGet(Config.API_LOGIN, params,config, this);
			setHUBStatus(this,1, "注册中...");
			
			break;
		case R.id.btn_register_back:
			finish();
			break;
		}
	}
	
	@InjectHttp
	private void result(ResponseEntity r) {
		switch (r.getStatus()) {
		case FastHttp.result_ok:
			setHUBStatus(this, 0, "");
			
//			String strResult = r.getContentAsString();
//			Login m_login = new Login();
//			try{
//				Handler_Json.JsonToBean(m_login, strResult);
//				
//				if(m_login.IsSuccess == false
//						|| m_login.ResultJson == null
//						|| m_login.ResultJson.size() < 1)
//				{
//					Toast.makeText(getApplicationContext(), "注册不成功，请重试！",Toast.LENGTH_LONG).show();
//					return;
//				}
//				
//				//缓存起来，下次直接取
//				Handler_SharedPreferences.saveObject("XBB_SP", "user", m_login);
//				((MyApplication)this.getApplication()).loginInfo = m_login;
//				
//				Toast.makeText(getApplicationContext(), "注册成功！",Toast.LENGTH_LONG).show();
//				this.finish();
//			}
//			catch(Exception ex)
//			{
//				setHUBStatus(this, 0, "");
//				Toast.makeText(getApplicationContext(), "异常，请重试！",
//						Toast.LENGTH_LONG).show();
//			}
//			
//			break;
//		case FastHttp.result_net_err:
//			setHUBStatus(this, 0, "");
//			Toast.makeText(getApplicationContext(), "服务器错误，请重试！",
//					Toast.LENGTH_LONG).show();
//			break;
//		default:
//			break;
		}
	}
}
