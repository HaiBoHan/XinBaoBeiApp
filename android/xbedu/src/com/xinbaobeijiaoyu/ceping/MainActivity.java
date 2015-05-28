package com.xinbaobeijiaoyu.ceping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		
		new Handler().postDelayed(new Runnable() {  
            public void run() {  
//                Intent mainIntent = new Intent(MainActivity.this,  
//                        LoginActivity.class);  
                Intent mainIntent = new Intent(MainActivity.this,  
                        NewLoginActivity.class);  
                MainActivity.this.startActivity(mainIntent);  
                MainActivity.this.finish();  
            }  
        }, 5000);
		
		//本地验证登陆情况
		
	}
}
