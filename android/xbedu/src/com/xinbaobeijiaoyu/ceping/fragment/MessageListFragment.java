package com.xinbaobeijiaoyu.ceping.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.android.pc.ioc.app.Ioc;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.util.Handler_Inject;
import com.android.pc.util.Handler_Json;
import com.xinbaobeijiaoyu.ceping.FrontActivity;
import com.xinbaobeijiaoyu.ceping.MessageDetailActivity;
import com.xinbaobeijiaoyu.ceping.MyApplication;
import com.xinbaobeijiaoyu.ceping.R;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.About;
import com.xinbaobeijiaoyu.ceping.model.Message;
import com.xinbaobeijiaoyu.ceping.model.Message.MessageInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MessageListFragment extends ListFragment {
	int mCurCheckPosition = 0;
	int mShownCheckPosition = -1;
	ArrayList<MessageInfo> mnData = null;
	MessageListAdapter adpter;
	
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_messagelist, container,
				false);
	};

	private ListView selfList;
	@InjectView
	private TextView tv_messagelistfragment;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			mShownCheckPosition = savedInstanceState.getInt("shownChoice", -1);
		}
		
		Handler_Inject.injectFragment(this, this.getView());
		
		selfList = getListView();
		
		//网络请求：消息列表
		InternetConfig config = new InternetConfig();
		config.setCharset("utf-8");
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		int userid =  ((MyApplication)this.getActivity().getApplication()).loginInfo.ResultJson.get(0).ID;
		params.put("userid",String.valueOf(userid));
		FastHttpHander.ajaxGet(Config.API_Message, params ,config, this);
	}
	
	@InjectHttp
	private void httpHandle(ResponseEntity r)
	{
		switch (r.getStatus()) {
		case FastHttp.result_ok:
			String strResult = r.getContentAsString();
			Message m_about = new Message();
			try{
				Handler_Json.JsonToBean(m_about, strResult);
				
				if(m_about.IsSuccess == false
						|| m_about.ResultJson == null
						|| m_about.ResultJson.size() < 1)
				{
					setListViewGONE(true);
				}
				else
				{
					mnData = m_about.ResultJson;
					if(mnData == null || mnData.size() < 1)
						setListViewGONE(true);
					else
						setListViewGONE(false);
					
					adpter = new MessageListAdapter(mnData);
					setListAdapter(adpter);
					getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				}
			}
			catch(Exception ex)
			{
				setListViewGONE(true);
			}
			break;
		}
	}
	
	private void setListViewGONE(boolean isGone) {
		if (isGone) {
			tv_messagelistfragment.setText("暂无数据！");
			tv_messagelistfragment.setVisibility(View.VISIBLE);

			selfList.setVisibility(View.GONE);
		} else {
			tv_messagelistfragment.setText("");
			tv_messagelistfragment.setVisibility(View.GONE);
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
		showDetails(position);
	}

	/**
	 * 显示listview item 详情
	 */
	void showDetails(int index) {
		mCurCheckPosition = index;

//		if (mShownCheckPosition != mCurCheckPosition) 
		if(mCurCheckPosition >= 0)
		{
			getListView().setItemChecked(index, true);
			
			MessageInfo msgInfo = mnData.get(index);
			String strTitle = "";
			String strContent = "";
			if(msgInfo != null)
			{
				strTitle = msgInfo.Message_Title;
				strContent = msgInfo.Message_Content;
			}
			
//			MessageDetailFragment df = MessageDetailFragment.newInstance(strTitle,strContent);
			
//			((FrontActivity)this.getActivity()).changeFragment(df, "msgdetail", false);
			
			((FrontActivity)this.getActivity()).StartMsgDetail(strTitle,strContent);
			
			mShownCheckPosition = index;
			
		}
	}

	private class MessageListAdapter extends ArrayAdapter<MessageInfo> {

		public MessageListAdapter(ArrayList<MessageInfo> mMsgInfo) {
			super(getActivity(), 0, mMsgInfo);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.adapter_messagecell, null);
			}

			MessageInfo msg = getItem(position);

			TextView tv_stitle = (TextView) convertView.findViewById(R.id.tv_stitle);
			tv_stitle.setText(msg.Subhead);

			TextView tv_titlecontent = (TextView) convertView.findViewById(R.id.tv_titlecontent);
			tv_titlecontent.setText(msg.Message_Title);

			TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			tv_date.setText(msg.MessDate);

			return convertView;
		}
	}
}
