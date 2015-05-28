package com.xinbaobeijiaoyu.ceping.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.AjaxCallBack;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.util.Handler_Inject;
import com.android.pc.util.Handler_Json;
import com.android.pc.util.Handler_SharedPreferences;
import com.xinbaobeijiaoyu.ceping.FrontActivity;
import com.xinbaobeijiaoyu.ceping.MyApplication;
import com.xinbaobeijiaoyu.ceping.R;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Qinzi;
import com.xinbaobeijiaoyu.ceping.model.Qinzi.QinziInfo;
import com.xinbaobeijiaoyu.ceping.model.Subjective;
import com.xinbaobeijiaoyu.ceping.model.Subjective.SubjectiveInfo;

public class QinziListFragment extends ListFragment {
	int mCurCheckPosition = 0;
	int mShownCheckPosition = -1;
	ArrayList<QinziInfo> mnData = null;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_qinzilist, container,
				false);
	};

	private ListView selfList;
	@InjectView
	private TextView tv_qinzilistfragment;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			mShownCheckPosition = savedInstanceState.getInt("shownChoice", -1);
		}

		Handler_Inject.injectFragment(this, this.getView());

		selfList = getListView();
		
//		setListViewGONE(true);

		int userid = ((MyApplication) this.getActivity().getApplication()).loginInfo.ResultJson
				.get(0).ID;

		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userid", String.valueOf(userid));
		InternetConfig config = new InternetConfig();
		config.setTimeout(3000);
		config.setCharset("utf-8");
		FastHttpHander.ajaxGet(Config.API_Qinzi, params, config, this);
	}

	@InjectHttp
	private void setMessage(ResponseEntity r) {
		switch (r.getStatus()) {
		case FastHttp.result_ok:

			String strResult = r.getContentAsString();
			Qinzi m_qinzi = new Qinzi();
			try {
				Handler_Json.JsonToBean(m_qinzi, strResult);

				if (m_qinzi.IsSuccess == false
						|| m_qinzi.ResultJson == null
						|| m_qinzi.ResultJson.size() < 1) {
					// Toast.makeText(getActivity().getApplicationContext(),
					// "获取问题列表失败，请重试！", Toast.LENGTH_LONG).show();
					setListViewGONE(true);
					return;
				}

				mnData = m_qinzi.ResultJson;
				if (mnData == null) {
					setListViewGONE(true);
				} else {
					setListViewGONE(false);
					QinziListAdapter adpter = new QinziListAdapter(
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
			tv_qinzilistfragment.setText("暂无数据！");
			tv_qinzilistfragment.setVisibility(View.VISIBLE);

			selfList.setVisibility(View.GONE);
		} else {
			tv_qinzilistfragment.setText("");
			tv_qinzilistfragment.setVisibility(View.GONE);
			selfList.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
		outState.putInt("shownChoice", mShownCheckPosition);
	}

//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		showDetails(position);
//	}

//	/**
//	 * 显示listview item 详情
//	 */
//	void showDetails(int index) {
//		mCurCheckPosition = index;
//		getListView().setItemChecked(index, true);
//
//		if (mShownCheckPosition != mCurCheckPosition) {
//			QinziInfo subjectiveInfo = mnData.get(index);
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
//			((FrontActivity) this.getActivity()).changeFragment(df,
//					"subjectivedetail", false);
//			mShownCheckPosition = index;
//
//		}
//	}

	private class QinziListAdapter extends ArrayAdapter<QinziInfo> {

		public QinziListAdapter(ArrayList<QinziInfo> mSubjectiveInfo) {
			super(getActivity(), 0, mSubjectiveInfo);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.adapter_qinzicell, null);
			}

			QinziInfo msg = getItem(position);

			TextView tv_stitle = (TextView) convertView
					.findViewById(R.id.tv_qinzititle);
			tv_stitle.setText(msg.Stage + ":" + msg.AboutAge);

			TextView tv_titlecontent = (TextView) convertView
					.findViewById(R.id.tv_qinzicontent);
			tv_titlecontent.setText(msg.Content);

			return convertView;
		}
	}
}
