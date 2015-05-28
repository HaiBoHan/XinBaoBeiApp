package com.xinbaobeijiaoyu.ceping.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.util.Handler_Inject;
import com.android.pc.util.Handler_Json;
import com.xinbaobeijiaoyu.ceping.MyApplication;
import com.xinbaobeijiaoyu.ceping.R;
import com.xinbaobeijiaoyu.ceping.SubjectiveAnswerActivity;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.KeywordsQuestion;
import com.xinbaobeijiaoyu.ceping.model.KeywordsQuestion.KeywordsInfo;
import com.xinbaobeijiaoyu.ceping.ui.ProgressHUD;

public class SubjectiveFragment extends ListFragment {
	public ProgressHUD mProgressHUD;
	int mCurCheckPosition = 0;
	int mShownCheckPosition = -1;
	ArrayList<KeywordsInfo> mnData = null;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_subjectivelist, container,
				false);
	};

	private ListView selfList;
	@InjectView
	private TextView tv_subjectivelistfragment;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			mShownCheckPosition = savedInstanceState.getInt("shownChoice", -1);
		}

		Handler_Inject.injectFragment(this, this.getView());

		selfList = getListView();

		int userid = ((MyApplication) this.getActivity().getApplication()).loginInfo.ResultJson
				.get(0).ID;

		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userid", String.valueOf(userid));
		InternetConfig config = new InternetConfig();
		config.setTimeout(3000);
		config.setCharset("utf-8");
		FastHttpHander.ajaxGet(Config.API_Subjective, params, config, this);
		
		this.setHUBStatus(this.getActivity(), 1, "数据加载中...");
	}

	@InjectHttp
	private void setMessage(ResponseEntity r) {
		this.setHUBStatus(this.getActivity(), 0, "");
		switch (r.getStatus()) {
		// switch(FastHttp.result_ok){
		case FastHttp.result_ok:

			String strResult = r.getContentAsString();
			KeywordsQuestion m_subjective = new KeywordsQuestion();
			try {
				Handler_Json.JsonToBean(m_subjective, strResult);

				if (m_subjective.IsSuccess == false
						|| m_subjective.ResultJson == null
						|| m_subjective.ResultJson.size() < 1) {
					// Toast.makeText(getActivity().getApplicationContext(),
					// "获取问题列表失败，请重试！", Toast.LENGTH_LONG).show();
					setListViewGONE(true);
					return;
				}

				mnData = m_subjective.ResultJson;
				((MyApplication)this.getActivity().getApplication()).subjectiveData = mnData;
				
				// Test
				// SetTestData();
				if (mnData == null) {
					setListViewGONE(true);
				} else {
					setListViewGONE(false);
					SubjectiveListAdapter adpter = new SubjectiveListAdapter(
							mnData);
					setListAdapter(adpter); // 使用静态数组填充列表
				}

				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			} catch (Exception ex) {
				// Toast.makeText(getActivity().getApplicationContext(),
				// "获取问题列表失败，请重试！", Toast.LENGTH_LONG).show();
				setListViewGONE(true);
			}

			break;
		default:
			setListViewGONE(true);
			break;
		}
	}

	private void setListViewGONE(boolean isGone) {
		if (isGone) {
			tv_subjectivelistfragment.setText("暂无数据！");
			tv_subjectivelistfragment.setVisibility(View.VISIBLE);

			selfList.setVisibility(View.GONE);
		} else {
			tv_subjectivelistfragment.setText("");
			tv_subjectivelistfragment.setVisibility(View.GONE);
			selfList.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
		outState.putInt("shownChoice", mShownCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
//		showDetails(position);
		Intent intent = new Intent (this.getActivity(), SubjectiveAnswerActivity.class);
		intent.putExtra("selectIndex", position);
		startActivity(intent);
	}
	
//	/**
//	 * 解析Keyword
//	 */
//	@SuppressWarnings("unused")
//	public static KeywordsQuestion getSpecailPOIJSON(String json) {
//		KeywordsQuestion keywords = new KeywordsQuestion();
//		List<KeywordsInfo> kwInfos = new ArrayList<KeywordsInfo>();
//		try {
//			JSONObject jsonObject = new JSONObject(json);
//			String IsSuccess = jsonObject.getString("IsSuccess");
//			String Message = jsonObject.getString("Message");
//			String ResultJson = jsonObject.getString("ResultJson");
//			JSONArray jsonArray = new JSONArray(ResultJson);
//			if(jsonArray == null){
//				return keywords;
//			}
//			//第一层：结果List
//			for(int i = 0;i < jsonArray.length(); i++){
//				JSONObject keywordFirst = jsonArray.getJSONObject(i);
//				String ID = keywordFirst.getString("ID");
//				String KeyWords = keywordFirst.getString("KeyWords");
//				String Childs = keywordFirst.getString("Childs");
//				
//				//第二层：keyword大类别
//				JSONArray childsJson = new JSONArray(Childs);
//				if(childsJson != null){
//					for(int j = 0;j < childsJson.length(); j++){
//						JSONObject keywordSecond = childsJson.getJSONObject(i);
//						
//						String ID1 = keywordFirst.getString("ID");
//						String KeyWords1 = keywordFirst.getString("KeyWords");
//						String Childs1 = keywordFirst.getString("Childs");
//						
//						JSONArray childsJson2 = new JSONArray(Childs1);
//						if(childsJson != null){
//							for(int k = 0;k < childsJson.length(); k++){
//									
//							}
//						}
//				}
//				
//				//ArrayList<KeywordsInfoSecond> 
//				KeywordsInfo kwInfo = new KeywordsInfo();
//				kwInfo.ID = ID;
//				kwInfo.KeyWords = KeyWords;
//				kwInfo.Childs = null;
//				kwInfos.add(specialPoi);
//			}
//			specialPoiORAreaOverview.setVersion(version);
//			specialPoiORAreaOverview.setSpecialPOI(specialPoiList);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return specialPoiORAreaOverview;
//	}
	

	/**
	 * 显示listview item 详情
	 */
	void showDetails(int index) {
		mCurCheckPosition = index;
		getListView().setItemChecked(index, true);

//		HistoryInfo subjectiveInfo = mnData.get(index);

//		String id = "-1";
//		String strKeyWord = "";
//		String strContent = "";
//		if (subjectiveInfo != null) {
//			strKeyWord = subjectiveInfo.KeyWords;
//			strContent = subjectiveInfo.Solution;
//			id = subjectiveInfo.QuestionID;
//		}
//
//		SubjectiveDetailFragment df = SubjectiveDetailFragment.newInstance(
//				id, strKeyWord, strContent);
//
////		((FrontActivity) this.getActivity()).changeFragment(df,
////				"subjectivedetail", false);
//		mShownCheckPosition = index;
//		
//		if (subjectiveInfo.Answer == null || subjectiveInfo.Answer.equals("")) 
//		{
//			String id = "-1";
//			String strKeyWord = "";
//			String strContent = "";
//			if (subjectiveInfo != null) {
//				strKeyWord = subjectiveInfo.KeyWords;
//				strContent = subjectiveInfo.Question_Title;
//				id = subjectiveInfo.ID;
//			}
//
//			SubjectiveDetailFragment df = SubjectiveDetailFragment.newInstance(
//					id, strKeyWord, strContent);
//
////			((FrontActivity) this.getActivity()).changeFragment(df,
////					"subjectivedetail", false);
//			mShownCheckPosition = index;
//		}
//		else
//		{
//			Toast.makeText(this.getActivity(), "专家已回答，请您选择其他问题！",
//					Toast.LENGTH_SHORT).show();
//		}
	}

	private class SubjectiveListAdapter extends ArrayAdapter<KeywordsInfo> {

		public SubjectiveListAdapter(ArrayList<KeywordsInfo> mSubjectiveInfo) {
			super(getActivity(), 0, mSubjectiveInfo);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.adapter_subjectivecell, null);
			}

			KeywordsInfo msg = getItem(position);

			TextView tv_titlecontent = (TextView) convertView
					.findViewById(R.id.tv_titlecontent);
			tv_titlecontent.setText(msg.KeyWords);
			
			ImageView iv_subjectivelist_item_icon = (ImageView) convertView.findViewById(R.id.iv_subjectivelist_item_icon);
			int index = 1;
			if(position > 0)
			{
				int r = position % 6;
				if(r == 0)
					index = 6;
				else
					index = r;
			}
				
			int imgId = getResources().getIdentifier("list_item_subjective_" + index, "drawable",Config.PackageName);
			iv_subjectivelist_item_icon.setImageResource(imgId);
			
			return convertView;
		}
	}
	
	private void SetTestData(){
//		mnData = new ArrayList<SubjectiveInfo>();
//		
//		for(int i = 0 ; i < 10 ; i++)
//		{
//			SubjectiveInfo sub = new SubjectiveInfo();
//			sub.ID = String.valueOf(i);
//			sub.AboutAge = "AboutAge" + i;
//			sub.AboutAgeBegin = "AboutAgeBegin" + i;
//			sub.AboutAgeEnd = "AboutAgeEnd" + i;
////			sub.Answer = "Answer" + i;
//			sub.KeyWords = "非常差,比较差,一般差,可以忍受,一般,还可以,不错,挺好,非常差2,比较差2,一般差2,可以忍受2,一般2,还可以2,不错2,挺好2";
//			sub.Question_Title = "问题测试" + i;
//			sub.Question_Title_Part1 = "本次测试问题" + i + "方案";
//			sub.Question_Title_Part2 = "是否可行?";
//			
//			mnData.add(sub);
//		}
	}

	public void setHUBStatus(Context ctx, int status, String msg) {
		// 显示
		if (status == 1)
			mProgressHUD = ProgressHUD.show(ctx, msg, true,
					true, null);
		if (status == 0)
			if (mProgressHUD != null)
				mProgressHUD.dismiss();
	}
}
