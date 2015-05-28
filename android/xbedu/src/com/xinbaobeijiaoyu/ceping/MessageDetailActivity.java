package com.xinbaobeijiaoyu.ceping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.xinbaobeijiaoyu.ceping.fragment.MessageDetailFragment;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;

@InjectLayer(R.layout.activity_msgdetail)
public class MessageDetailActivity extends BaseActivity {
	
	@InjectInit
	private void init()
	{
		Intent intent = getIntent(); //用于激活它的意图对象
		
		Bundle bundle = intent.getExtras();        
        
        String title = bundle.getString("title");
        String content = bundle.getString("content");

		tv_msgdetailtitle.setText(title);
		tv_msgdetailcontent.setText(content);
	}
	
	@InjectView
	private TextView tv_msgdetailtitle,tv_msgdetailcontent;
	
	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private Button btn_msgdetail_back;

	protected void click(View view) {
		switch (view.getId()) {
		case R.id.btn_msgdetail_back:
			finish();
			break;
		}
	}
}
