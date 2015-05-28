package com.xinbaobeijiaoyu.ceping;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;

@InjectLayer(R.layout.activity_regedit)
public class RegeditActivity extends BaseActivity {
	@InjectInit
	private void init()
	{
//		Login loginInfo = ((MyApplication)this.getApplication()).loginInfo;
//		if(loginInfo != null)
//		{
//			UserInfo user = loginInfo.ResultJson.get(0);
//			if(user != null)
//			{
//				tv_personinfo_name.setText(user.Name);
//				tv_personinfo_sex.setText(user.Sex);
//				tv_personinfo_birth.setText(user.Birthday);
//				tv_personinfo_region.setText(user.Region);
//				tv_personinfo_selfsign.setText(user.SelfSign);
//			}
//		}
	}

	@InjectView
	private TextView tv_personinfo_name,tv_personinfo_sex,tv_personinfo_birth,tv_personinfo_region,tv_personinfo_selfsign;

	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private Button btnReturn,btnGetCertifyCode,btnOK;

	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private EditText et_regedit_phonenum,et_regedit_password,et_regedit_repassword,et_regeidt_certifycode;
	

	
	protected void click(View view) {
		switch (view.getId()) {
		case R.id.btnReturn:
			this.finish();
			break;
		case R.id.btnGetCertifyCode:
//			Toast.makeText(getApplicationContext(), "性别点击，测试成功！",
//					Toast.LENGTH_LONG).show();
			break;
		case R.id.btnOK:
//			Toast.makeText(getApplicationContext(), "地区点击，测试成功！",
//					Toast.LENGTH_LONG).show();
			break;
		case R.id.et_regedit_phonenum:
			break;
		case R.id.et_regedit_password:
			break;
		case R.id.et_regedit_repassword:
			break;
		case R.id.et_regeidt_certifycode:
			break;
		}
	}
	
}
