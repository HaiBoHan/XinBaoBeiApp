package com.xinbaobeijiaoyu.ceping;

import java.util.ArrayList;

import com.android.pc.ioc.app.Ioc;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.KeywordsQuestion.KeywordsInfo;

import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		Ioc.getIoc().init(this);
		super.onCreate();
	}
	
	public Login loginInfo;
	
	public ArrayList<KeywordsInfo> subjectiveData;
}
