package com.xinbaobeijiaoyu.ceping.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.pc.ioc.app.Ioc;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.util.Handler_Inject;
import com.android.pc.util.Handler_System;
import com.xinbaobeijiaoyu.ceping.FrontActivity;
import com.xinbaobeijiaoyu.ceping.MyApplication;
import com.xinbaobeijiaoyu.ceping.R;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;
import com.xinbaobeijiaoyu.ceping.ui.ProgressHUD;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectiveDetailFragment extends Fragment {
	public static SubjectiveDetailFragment newInstance(String id,
			String strTitle, String strContent) {
		SubjectiveDetailFragment f = new SubjectiveDetailFragment();
		Bundle args = new Bundle();
		args.putString("id", id);
		args.putString("title", strTitle);
		args.putString("content", strContent);
		f.setArguments(args);
		return f;
	}

	private View rootView;
	private Context cxt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		rootView = inflater.inflate(R.layout.fragment_subjectivedetail,
				container, false);
		Handler_Inject.injectFragment(this, rootView);
		return rootView;
	}

	@InjectView
	private TextView tv_subjectivedetailcontent;
	@InjectView
	private HorizontalScrollView sv_keyword;
	@InjectView
	private LinearLayout ll_scrollview_h, ll_scrollview_v;
	@InjectView
	private Button btn_subjectiveCommit;
	
	private String strOriginal = "";
	private String[] strSplitQ = null;
	private String strStart = "";
	private String strEnd = "";
	
	@InjectInit
	private void init() {
		tv_subjectivedetailcontent.setText(getArguments().getString("content"));

		strOriginal = tv_subjectivedetailcontent.getText().toString();
		strSplitQ = strOriginal.replaceAll("[_]+","_").split("_");
		if(strSplitQ != null && strSplitQ.length > 1)
		{
			strStart = strSplitQ[0];
			strEnd = strSplitQ[1];
		}
		
		cxt = getActivity().getApplicationContext();

		strKeyword = getArguments().getString("title").split(",");
		if (strKeyword != null && strKeyword.length > 0) {
			int width = Handler_System.dip2px(68);
			int height = Handler_System.dip2px(30);
			int margin5 = Handler_System.dip2px(5);
			for (int i = 0; i < strKeyword.length; i++) {
				if ((i > 0) && ((i + 1) % 4 == 1)) {
					ll_scrollview_h = new LinearLayout(cxt);
					LinearLayout.LayoutParams lpT = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.MATCH_PARENT);
					lpT.topMargin = margin5;
					ll_scrollview_h.setOrientation(LinearLayout.HORIZONTAL);
					ll_scrollview_h.setLayoutParams(lpT);
					ll_scrollview_v.addView(ll_scrollview_h);
				}

				TextView tv_keyword = new TextView(cxt);
				tv_keyword.setText(strKeyword[i]);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						width, height);
				lp.rightMargin = 10;
				tv_keyword.setLayoutParams(lp);
				tv_keyword.setTag(i);
				tv_keyword
						.setBackgroundResource(R.drawable.shap_label_unselected);
				tv_keyword.setTextColor(Color.rgb(255, 255, 255));
				tv_keyword.setGravity(Gravity.CENTER);
				ll_scrollview_h.addView(tv_keyword);
				tv_keyword.setOnClickListener(onclick);
			}
		}

		btn_subjectiveCommit.setOnClickListener(onclick);
		if(selectedItems == null || selectedItems.size() < 1)
			btn_subjectiveCommit.setVisibility(View.GONE);
	}

	private ArrayList<String> selectedItems = new ArrayList<String>();
	private String[] strKeyword;

	OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//提交
			if (v.getId() == R.id.btn_subjectiveCommit) {
				
				setHUBStatus(SubjectiveDetailFragment.this.getActivity(),1,"努力提交中...");
				//提交选择结果
				InternetConfig config = new InternetConfig();
//				config.setCharset("gb2312");
				config.setCharset("utf-8");
				LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
				
				Login loginInfo = ((MyApplication)SubjectiveDetailFragment.this.getActivity().getApplication()).loginInfo;
				String strParams = "";
				String strResultKeyword = "";
				for(int i=0;i<selectedItems.size();i++)
				{
					strResultKeyword += ("\"" + strKeyword[i] + "\"");
					if(i != (selectedItems.size() - 1))
						strResultKeyword += ",";
				}
				
				if(loginInfo != null && loginInfo.ResultJson.size() > 0)
				{
					UserInfo userInfo = loginInfo.ResultJson.get(0);
					strParams = "{userid:" + userInfo.ID 
							+ ",age:\"" + userInfo.Age + "\""
							+ ",questionid:" + getArguments().getString("id")
							+ ",questiontitle:\"" +  getArguments().getString("content") + "\""
							+ ",keyword:[" + strResultKeyword + "]"
							+ "}";
					
					Ioc.getIoc().getLogger().d("======提交数据======" + strParams);
				}
				// params.put("question",strParams);
				// 做了一次utf-8的转码,要不提交到数据库是乱码
				String strnewParams = null;
				try {
					strnewParams = java.net.URLEncoder.encode(strParams,"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				params.put("question",strnewParams);
				FastHttpHander.ajaxGet(Config.API_PostSubjective, params ,config, SubjectiveDetailFragment.this);
			}
			//选关键字
			else {
				String strTag = v.getTag().toString();
				if (selectedItems.contains(strTag)) {
					selectedItems.remove(strTag);
					if(selectedItems.size() < 1)
						btn_subjectiveCommit.setVisibility(View.GONE);
					v.setBackgroundResource(R.drawable.shap_label_unselected);
				} else if (selectedItems.size() < 2) {
					selectedItems.add(strTag);
					btn_subjectiveCommit.setVisibility(View.VISIBLE);
					v.setBackgroundResource(R.drawable.shap_label_selected);
				}
				else
					Toast.makeText(SubjectiveDetailFragment.this.getActivity(), "为确保专家为你解答问题的质量，仅限提交两个关键词。敬请谅解！",
							Toast.LENGTH_LONG).show();
				
				if(selectedItems.size() == 0)
				{
					tv_subjectivedetailcontent.setText(strStart + "_________" + strEnd);
				}
				
				if(selectedItems.size() == 1)
				{
					tv_subjectivedetailcontent.setText(strStart);
					tv_subjectivedetailcontent.append(Html.fromHtml("<u>" + strKeyword[Integer.parseInt(selectedItems.get(0))]+ "</u>"));
					tv_subjectivedetailcontent.append(strEnd);
				}
				if(selectedItems.size() == 2)
				{
					tv_subjectivedetailcontent.setText(strStart);
					tv_subjectivedetailcontent.append(Html.fromHtml("<u>" + strKeyword[Integer.parseInt(selectedItems.get(0))] 
							+ "、" + strKeyword[Integer.parseInt(selectedItems.get(1))]
							+ "</u>"));
					tv_subjectivedetailcontent.append(strEnd);
				}
			}
		}
	};
	
	@InjectHttp
	private void handlerHttpResult(ResponseEntity r)
	{
		Ioc.getIoc().getLogger().d("======||||提交数据||||||======" + r.getContentAsString());
		setHUBStatus(SubjectiveDetailFragment.this.getActivity(), 0, "");
		switch (r.getStatus()) {
		case FastHttp.result_ok:
			Toast.makeText(SubjectiveDetailFragment.this.getActivity(), "提交成功！",Toast.LENGTH_LONG).show();
			pop();
			break;
		case FastHttp.result_net_err:
			Toast.makeText(SubjectiveDetailFragment.this.getActivity(), "提交失败，请重试！",
					Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}
	
	private void pop()
	{
//		FragmentTransaction ttt = this.getFragmentManager().beginTransaction();
//		ttt.remove(this);
//		ttt.commit();
		
		FrontFragment df = new FrontFragment();
//		((FrontActivity) this.getActivity()).changeFragment(df,
//				"front", true);
	}
	
	
	
	private ProgressHUD mProgressHUD;

	private void setHUBStatus(Context ctx, int status, String msg) {
		// 显示
		if (status == 1)
			mProgressHUD = ProgressHUD.show(ctx, msg, true,
					false, null);
		if (status == 0)
			if (mProgressHUD != null)
				mProgressHUD.dismiss();
	}
}
