package com.xinbaobeijiaoyu.ceping.fragment;

import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.util.Handler_Inject;
import com.xinbaobeijiaoyu.ceping.R;
import com.xinbaobeijiaoyu.ceping.model.Message.MessageInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class MessageDetailFragment extends Fragment {
	public static MessageDetailFragment newInstance(String strTitle,String strContent) {
		MessageDetailFragment f = new MessageDetailFragment();
		Bundle args = new Bundle();
		args.putString("title", strTitle);
		args.putString("content", strContent);
		f.setArguments(args);
		return f;
	}
	
	private View rootView;
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        if (container == null) {              
            return null;  
        }  
        
        rootView = inflater.inflate(R.layout.fragment_msgdetail, container, false);
		Handler_Inject.injectFragment(this, rootView);
		return rootView;
    }
	
	@InjectView
	private TextView tv_msgdetailtitle,tv_msgdetailcontent;
	
	@InjectInit
	private void init()
	{
		tv_msgdetailtitle.setText(getArguments().getString("title"));
		tv_msgdetailcontent.setText(getArguments().getString("content"));
	}
}
