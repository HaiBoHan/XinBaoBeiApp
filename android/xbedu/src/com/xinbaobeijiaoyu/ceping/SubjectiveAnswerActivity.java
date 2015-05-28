package com.xinbaobeijiaoyu.ceping;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.android.pc.ioc.app.Ioc;
import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_System;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.fragment.SubjectiveDetailFragment;
import com.xinbaobeijiaoyu.ceping.model.KeywordsQuestion.KeywordsInfo;
import com.xinbaobeijiaoyu.ceping.model.KeywordsQuestion.KeywordsInfoSecond;
import com.xinbaobeijiaoyu.ceping.model.KeywordsQuestion.KeywordsInfoTh;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectiveAnswerActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_subjectiveanswer);
	}
	
	private ArrayList<String> selectedItems = new ArrayList<String>(); //最终将要提交的数据
	private HashMap<String,ArrayList<KeywordsInfoTh>> keywordFirst = new HashMap<String,ArrayList<KeywordsInfoTh>>(); //上半部分的关键字，及，对应用的 子key
	private HashMap<String,String> keywordSecond = new HashMap<String,String>(); //下半部分的关键字，及，对应用的 keyword
	private String strSelectedId = ""; //当前选择的 上面的关键字
	private String strSelectedName = ""; //当前选择的 上面的关键字：Name
	private ArrayList<KeywordsInfo> sData; //全局的主观问题
	private KeywordsInfo keywords; //选择进入的 条目
	private String selectedKeyWordID = "";
	
	@InjectView
	private LinearLayout ll_scrollview_h, ll_scrollview_v,ll_scrollview_h_final,ll_scrollview_v_final;
	@InjectView
	private TextView tv_subjectiveanswer_title,tv_subjectiveanswer_selectcontent;
	@InjectView
	private Button btn_subjective_back,btn_subjectiveCommit;
	
	@InjectInit
	private void init()
	{
		MyApplication myapp = ((MyApplication)this.getApplication());
		if(myapp == null)
			return;
		
		Intent intent = getIntent();
		int selectIndex = intent.getIntExtra("selectIndex",-1);
		if(selectIndex < 0)
			return;
		
		btn_subjective_back.setOnClickListener(onclick);
		btn_subjectiveCommit.setOnClickListener(onclick);

		sData = myapp.subjectiveData;
		keywords = sData.get(selectIndex);
		tv_subjectiveanswer_title.setText(keywords.KeyWords);
		
		ArrayList<KeywordsInfoSecond> listKeywords = keywords.Childs;
		if (listKeywords != null && listKeywords.size() > 0) {
			int width = Handler_System.dip2px(80);
			int height = Handler_System.dip2px(30);
			int margin5 = Handler_System.dip2px(5);
			for (int i = 0; i < listKeywords.size(); i++) {
				if ((i > 0) && ((i + 1) % 4 == 1)) {
					ll_scrollview_h = new LinearLayout(this);
					LinearLayout.LayoutParams lpT = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.MATCH_PARENT);
					lpT.topMargin = margin5;
					ll_scrollview_h.setOrientation(LinearLayout.HORIZONTAL);
					ll_scrollview_h.setLayoutParams(lpT);
					ll_scrollview_v.addView(ll_scrollview_h);
				}

				TextView tv_keyword = new TextView(this);
				tv_keyword.setText(listKeywords.get(i).KeyWords);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						width, height);
				lp.rightMargin = 10;
				tv_keyword.setLayoutParams(lp);
				tv_keyword.setTag(listKeywords.get(i).ID);
				tv_keyword.setTextColor(Color.rgb(186, 194, 173));
				tv_keyword.setGravity(Gravity.CENTER);
				ll_scrollview_h.addView(tv_keyword);
				tv_keyword.setOnClickListener(onclick);
			
				keywordFirst.put(listKeywords.get(i).ID.toString(), listKeywords.get(i).Childs);
			}
		}
	}
	
	OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.btn_subjective_back)
			{	
				finish();
				return;
			}
			else//提交
			if (v.getId() == R.id.btn_subjectiveCommit) {
				
				setHUBStatus(SubjectiveAnswerActivity.this,1,"努力提交中...");
				//提交选择结果
				InternetConfig config = new InternetConfig();
//				config.setCharset("gb2312");
				config.setCharset("utf-8");
				LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
				
				Login loginInfo = ((MyApplication)SubjectiveAnswerActivity.this.getApplication()).loginInfo;
				String strParams = "";
				String strResultKeyword = "";
				for(int i=0;i<selectedItems.size();i++)
				{
					strResultKeyword += ("\"" + keywordSecond.get(selectedItems.get(i)) + "\"");
					if(i != (selectedItems.size() - 1))
						strResultKeyword += ",";
				}
				
				if(loginInfo != null && loginInfo.ResultJson.size() > 0)
				{
					UserInfo userInfo = loginInfo.ResultJson.get(0);
					strParams = "{userid:" + userInfo.ID 
							+ ",age:\"" + userInfo.Age + "\""
							+ ",questionid:" + selectedKeyWordID
							+ ",questiontitle:\"" +  keywords.KeyWords + "\""
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
				FastHttpHander.ajaxGet(Config.API_PostSubjective, params ,config, SubjectiveAnswerActivity.this);
			}
			//上半部分 关键字
			else if(keywordFirst.containsKey(v.getTag().toString()))
			{
				if(strSelectedId.equals(v.getTag().toString()))
					return;
				else
					strSelectedId = v.getTag().toString();
				
				ll_scrollview_h_final.removeAllViews();
				ll_scrollview_v_final.removeAllViews();
				
				TextView tv = (TextView)v;
				
				strSelectedName = tv.getText().toString();
				tv_subjectiveanswer_selectcontent.setText(strSelectedName + ":");
				selectedItems.clear();//切换了关键字，这个值也要清空
				btn_subjectiveCommit.setVisibility(View.GONE);
				
				
				ArrayList<KeywordsInfoTh> keyFinal = (ArrayList<KeywordsInfoTh>)keywordFirst.get(v.getTag()); 

				if (keyFinal != null && keyFinal.size() > 0) {
					int width = Handler_System.dip2px(80);
					int height = Handler_System.dip2px(30);
					int margin5 = Handler_System.dip2px(5);
					for (int i = 0; i < keyFinal.size(); i++) {
						if ((i > 0) && ((i + 1) % 4 == 1)) {
							ll_scrollview_h_final = new LinearLayout(SubjectiveAnswerActivity.this);
							LinearLayout.LayoutParams lpT = new LinearLayout.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.MATCH_PARENT);
							lpT.topMargin = margin5;
							ll_scrollview_h_final.setOrientation(LinearLayout.HORIZONTAL);
							ll_scrollview_h_final.setLayoutParams(lpT);
							ll_scrollview_v_final.addView(ll_scrollview_h_final);
						}

						TextView tv_keyword_final = new TextView(SubjectiveAnswerActivity.this);
						tv_keyword_final.setText(keyFinal.get(i).KeyWords);
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
								width, height);
						lp.rightMargin = 10;
						tv_keyword_final.setLayoutParams(lp);
						tv_keyword_final.setTag("subkey_" + keyFinal.get(i).ID);
						tv_keyword_final.setTextColor(Color.rgb(186, 194, 173));
						tv_keyword_final.setGravity(Gravity.CENTER);
						ll_scrollview_h_final.addView(tv_keyword_final);
						tv_keyword_final.setOnClickListener(onclick);
						
						keywordSecond.put("subkey_" + keyFinal.get(i).ID, keyFinal.get(i).KeyWords);
					}
				}
			}
			//选关键字
			else {
				String strTag = v.getTag().toString();
				if (selectedItems.contains(strTag)) {
					selectedItems.remove(strTag);
					selectedKeyWordID = "";
					if(selectedItems.size() < 1)
					{
						btn_subjectiveCommit.setVisibility(View.GONE);
						tv_subjectiveanswer_selectcontent.setText(strSelectedName + ":");
					}
					else
					{
						tv_subjectiveanswer_selectcontent.setText(strSelectedName + ":" + keywordSecond.get(selectedItems.get(0)));
					}
				} else if (selectedItems.size() == 0) {
					selectedItems.add(strTag);
					selectedKeyWordID = strTag.replace("subkey_", "");
					
					tv_subjectiveanswer_selectcontent.append(" " + keywordSecond.get(strTag));
					btn_subjectiveCommit.setVisibility(View.VISIBLE);
				}
				else
					Toast.makeText(SubjectiveAnswerActivity.this, "为确保专家为你解答问题的质量，仅限提交两个关键词。敬请谅解！",
							Toast.LENGTH_LONG).show();
			
			}
		}
	};
	
	@InjectHttp
	private void handlerHttpResult(ResponseEntity r)
	{
		Ioc.getIoc().getLogger().d("======||||提交数据||||||======" + r.getContentAsString());
		setHUBStatus(SubjectiveAnswerActivity.this, 0, "");
		switch (r.getStatus()) {
		case FastHttp.result_ok:
			Toast.makeText(SubjectiveAnswerActivity.this, "提交成功！",Toast.LENGTH_LONG).show();
			finish();
			break;
		case FastHttp.result_net_err:
			Toast.makeText(SubjectiveAnswerActivity.this, "提交失败，请重试！",
					Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}
}
