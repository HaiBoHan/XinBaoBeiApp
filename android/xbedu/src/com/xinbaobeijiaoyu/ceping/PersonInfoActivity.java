package com.xinbaobeijiaoyu.ceping;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
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
import com.android.pc.util.Handler_SharedPreferences;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;

@InjectLayer(R.layout.activity_personinfo)
public class PersonInfoActivity extends BaseActivity {
	private final int Const_NullYear = 1900;
	
	@InjectInit
	private void init()
	{
		Login loginInfo = ((MyApplication)this.getApplication()).loginInfo;
		if(loginInfo != null)
		{
			UserInfo user = loginInfo.ResultJson.get(0);
			if(user != null)
			{
				et_personinfo_name.setText(user.Name);
				et_personinfo_childname.setText("");
				if(user.Sex.equals("男"))
				{
					rb_personinfo_sex_man.setChecked(true);
					rb_personinfo_sex_wman.setChecked(false);
				}
				else if(user.Sex.equals("女"))
				{
					rb_personinfo_sex_man.setChecked(false);
					rb_personinfo_sex_wman.setChecked(true);
				}
				et_personinfo_region.setText(user.Region);
				et_personinfo_selfsign.setText(user.SelfSign);
				et_personinfo_childname.setText(user.BabyName);

				if(user.Birthday != null 
						&& user.Birthday.length() > 0
						&& user.Birthday != "null"
						){
					// 如果null时赋值，那么文本显示 null
					et_personinfo_birth.setText(user.Birthday);
					
					Date birthday = stringToDate(user.Birthday);
					Calendar cal=Calendar.getInstance();  
					cal.setTime(birthday);
					year = cal.get(Calendar.YEAR);
					if(year > Const_NullYear){
						year = year;
						monthOfYear = cal.get(Calendar.MONTH);
						dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
					}
					
				}
			}
		}

		// 如果为空，则取当年1月份?
		if(year <= 1900){
			Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR); 
		}		
		
		datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					
					et_personinfo_birth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
					PersonInfoActivity.this.year = year;
					PersonInfoActivity.this.monthOfYear = monthOfYear;
					PersonInfoActivity.this.dayOfMonth = dayOfMonth;
				}
		    }, year, monthOfYear, dayOfMonth){
			
				@Override
				public void show(){
					super.show();
				}
			
		};
	}
	
	@InjectView
	private EditText et_personinfo_name,et_personinfo_childname,et_personinfo_region,et_personinfo_selfsign;
	
	@InjectView
	private RadioButton rb_personinfo_sex_man,rb_personinfo_sex_wman;

	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private Button btn_personinfo_return,btn_personinfo_save,btn_logout;
	
	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private TextView et_personinfo_birth;

	protected void click(View view) {
		switch (view.getId()) {
		case R.id.btn_personinfo_return:
			this.finish();
			break;
		case R.id.et_personinfo_birth:{
				
				datePickerDialog.show();
			}
			break;
		case R.id.btn_personinfo_save:
			
			String strUserName = et_personinfo_name.getText().toString().trim(); 
			String strBabyName = et_personinfo_childname.getText().toString().trim(); 
			
			String strSex = "";
			if(rb_personinfo_sex_man.isChecked()){
				strSex = "男";
			}
			if(rb_personinfo_sex_wman.isChecked()){
				strSex = "女";
			}
			String strBirthday = et_personinfo_birth.getText().toString().trim(); 
			String strRegion = et_personinfo_region.getText().toString().trim(); 
			String strSelfSign = et_personinfo_selfsign.getText().toString().trim(); 
			
			// 暂时这样做，以后再优化
			UserInfo loginUser = ((MyApplication) this.getApplication()).loginInfo.ResultJson
					.get(0);
			String strID = String.valueOf(loginUser.ID);
			String strSysVersion = String.valueOf(loginUser.SysVersion);
			String strTel = loginUser.Tel;
			String strPic = loginUser.Pic;
			String strAddress = loginUser.Address;
			String strAccount = loginUser.Account;
			String strPassword = loginUser.Passwd;
			
			//1.校验
			Boolean bl = Validate4NotNull(strUserName,"用户名不可为空!");
			bl = bl && Validate4NotNull(strBabyName,"宝宝名不可为空!");
			bl = bl && Validate4NotNull(strSex,"性别不可为空!");
			bl = bl && Validate4NotNull(strBirthday,"宝宝生日不可为空!");
			
			// 如果发现异常,返回，不执行保存
			if(!bl){
				return;
			}

			//2.HTTP请求
			LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
//			params.put("Account", txt_username.getText().toString().trim());
//			params.put("Pwd", txt_password.getText().toString().trim());
			String strUserInfo = "{\"ID\":\"" + strID 
							+ "\",\"SysVersion\":" + strSysVersion					
							+ ",\"Address\":\"" + strAddress
							+ "\",\"Region\":\"" + strRegion
							+ "\",\"Account\":\"" + strAccount
							+ "\",\"Name\":\"" + strUserName
							+ "\",\"Sex\":\"" + strSex
							+ "\",\"Tel\":\"" + strTel
							+ "\",\"Pic\":\"" + strPic
							+ "\",\"Passwd\":\"" + strPassword
							+ "\",\"Birthday\":\"" + strBirthday
							+ "\",\"BabyName\":\"" + strBabyName
							+ "\",\"SelfSign\":\"" + strSelfSign
							+ "\"}";

			// 做了一次utf-8的转码,要不提交到数据库是乱码
			String strnewParams = null;
			try {
				strnewParams = java.net.URLEncoder.encode(strUserInfo,"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			params.put("user", strnewParams);

			
			InternetConfig config = new InternetConfig();
			config.setCharset("utf-8");
			
			FastHttpHander.ajaxGet(Config.API_SaveUser, params,config, this);
			setHUBStatus(this,1, "请稍后...");
			break;
		case R.id.btn_logout:
			//1.清除缓存
			Handler_SharedPreferences.removeSharedPreferences("XBB_SP", "user");
			//2.启动登陆页，关赛闭其它所有页
			 Intent loginIntent = new Intent(this, NewLoginActivity.class);
			 loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(loginIntent);
			break;
		}
	}

	private DatePickerDialog datePickerDialog;
	private int year, monthOfYear = 0, dayOfMonth = 1;
	
	@InjectHttp
	private void result(ResponseEntity r) {
		setHUBStatus(this, 0, "");
		
		switch (r.getStatus()) {
		case FastHttp.result_ok:
			String strResult = r.getContentAsString();
			
			Login m_login = new Login();
			try{
				Handler_Json.JsonToBean(m_login, strResult);
				
				if(m_login.IsSuccess == false
						|| m_login.ResultJson == null
						|| m_login.ResultJson.size() < 1)
				{
					// "登录失败，请重试！"
					String msg = MessageFormat.format("资料修改异常，{0}", m_login.Message);
					showErrorMessage(msg);
					return;
				}
				
				//缓存起来，下次直接取
				Handler_SharedPreferences.saveObject("XBB_SP", "user", m_login);
				((MyApplication)this.getApplication()).loginInfo = m_login;
				
				showMessage("资料修改成功!");
				
//				startActivity(new Intent(this, NewFrontActivity.class));
				this.finish();
			}
			catch(Exception ex)
			{
				showErrorMessage("登录失败，请重试!");
			}
		}
	}
	
	private Boolean Validate4NotNull(String str, String message) {
		// TODO Auto-generated method stub
		
		if(str == null || str.length() == 0){
			showErrorMessage(message);
			return false;
		}		
		return true;
	}
	
	private void showErrorMessage(String message){
		Toast.makeText(getApplicationContext()
				, message
				,Toast.LENGTH_LONG
				).show();
		return;
	}
	
	private void showMessage(String message){
		Toast.makeText(getApplicationContext()
				, message
				,Toast.LENGTH_LONG
				).show();
		return;
	}
	
	public Date stringToDate(String   str){
		Date rcreate=new Date();
		java.text.SimpleDateFormat newstr=new java.text.SimpleDateFormat("yyyy-MM-dd");
		      
		try {
			rcreate=newstr.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rcreate;
	}
}
