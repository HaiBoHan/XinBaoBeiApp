package com.xinbaobeijiaoyu.ceping.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.android.pc.util.Handler_Json;
import com.android.pc.util.Handler_System;
import com.xinbaobeijiaoyu.ceping.FrontActivity;
import com.xinbaobeijiaoyu.ceping.MyApplication;
import com.xinbaobeijiaoyu.ceping.NewFrontActivity;
import com.xinbaobeijiaoyu.ceping.PersonInfoActivity;
import com.xinbaobeijiaoyu.ceping.R;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.About;
// import com.xinbaobeijiaoyu.ceping.FrontActivity.MyAdapter;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Message;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;
import com.xinbaobeijiaoyu.ceping.model.Message.MessageInfo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

// ListFragment
public class NewFrontFragment extends ListFragment implements OnPageChangeListener  {

	final int Const_GetYuerListIndex = 0;
	final int Const_GetAboutusIndex = 1;
	
	protected boolean isAutoPlay = true;
	protected int interval = 5000;
	protected int currentItem  = 0;
	
	public Handler handler = new ImageHandler(new WeakReference<NewFrontFragment>(this)); 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_newfront, container, false);
		Handler_Inject.injectFragment(this, rootView);
		return rootView;
	}
	
	private View rootView;

	@InjectView
	private ViewPager vp_banner;
	@InjectView
	private ViewGroup ll_points;
	@InjectView
	private TextView tv_des;
	
//	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
//	private TextView tv_personinfo;

	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private ImageView iv_personinfo;

	private ImageView[] tips;//点的ImageView数组
	private ImageView[] mImageViews;//装ImageView数组
	private int[] imgIdArray;//图片资源id 


	@InjectInit
	protected void init() {
		//开始轮播效果  
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY); 
	}
	
	protected void click(View view)
	{
		switch (view.getId()) {
		case R.id.iv_personinfo:{
			this.getActivity().startActivity(new Intent(this.getActivity(),PersonInfoActivity.class));
			}
			break;
		}
	}

	// 加载轮循图片
	protected void loadCircleImage() {
		Login loginInfo = ((MyApplication)this.getActivity().getApplication()).loginInfo;
		if(loginInfo != null)
		{
			UserInfo user = loginInfo.ResultJson.get(0);
			if(user != null)
			{
				String strUser = user.Name;
				if(strUser == null || strUser.length() == 0){
					strUser = user.Account;
				}
				
				String strAge = user.Age; 
				if(strAge == null || strAge.length() == 0){
					strAge = "__岁";
				}
				
				String str_title_dev = strUser + "家长 \r\n您的宝宝已经"+ strAge + "了";
				tv_des.setText(str_title_dev);
			}
		}

		// 载入图片资源ID
		imgIdArray = new int[] { R.drawable.new_title_hold1,
				R.drawable.new_title_hold2, R.drawable.new_title_hold3
				,R.drawable.new_title_hold4
				};
		// 将点点加入到ViewGroup中
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this.getActivity());
			int f = Handler_System.dip2px(5);
			imageView.setLayoutParams(new LayoutParams(f, f));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.vp_selected);
			} else {
				tips[i].setBackgroundResource(R.drawable.vp_normal);
			}

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			ll_points.addView(imageView, layoutParams);
		}

		// 将图片装载到数组中
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(this.getActivity());
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}

		// 设置Adapter
		vp_banner.setAdapter(new MyAdapter());
		// 设置监听，主要是设置点点的背景
		vp_banner.setOnPageChangeListener(this);
		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		currentItem = (mImageViews.length) * 100;
		vp_banner.setCurrentItem(currentItem);
		
		
//		final AnimationDrawable animationDrawable = (AnimationDrawable)vp_banner.getBackground();
//		vp_banner.post(new Runnable() {
//		    @Override
//		        public void run()  {
//		            animationDrawable.start();
//		            
//		        }
//		});
	}

	public class MyAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			try {
				((ViewPager) container).addView(mImageViews[position
						% mImageViews.length], 0);
			} catch (Exception e) {
				// handler something
			}
			currentItem = position;
			return mImageViews[position % mImageViews.length];
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
//		switch (arg0) {
//		case 1:// 手势滑动，空闲中
//			isAutoPlay = false;
//			break;
//		case 2:// 界面切换中
//			isAutoPlay = true;
//			break;
//		case 0:// 滑动结束，即切换完毕或者加载完毕
//			// 当前为最后一张，此时从右向左滑，则切换到第一张
//			if (vp_banner.getCurrentItem() == vp_banner.getAdapter().getCount() - 1 && !isAutoPlay) {
//				vp_banner.setCurrentItem(0);
//			}
//			// 当前为第一张，此时从左向右滑，则切换到最后一张
//			else if (vp_banner.getCurrentItem() == 0 && !isAutoPlay) {
//				vp_banner.setCurrentItem(vp_banner.getAdapter().getCount() - 1);
//			}
//			break;
//		}
		
		switch (arg0) {  
        case ViewPager.SCROLL_STATE_DRAGGING:  
            handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);  
            break;  
        case ViewPager.SCROLL_STATE_IDLE:  
            handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);  
            break;  
        default:  
            break;  
        } 
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.vp_selected);
			} else {
				tips[i].setBackgroundResource(R.drawable.vp_normal);
			}
		}
	}

	// /*
	

	// 育儿消息获取
	int mCurCheckPosition = 0;
	int mShownCheckPosition = -1;
	ArrayList<MessageInfo> mnData = null;
	MessageListAdapter adpter;
	
	// 图片消息：关心宝宝成长每一天从育儿开始
	@InjectView
	ImageView iv_message;

	// 温馨提示
	@InjectView
	TextView tv_messageinfo;
	// 温馨提示layout
	@InjectView
	LinearLayout rl_Reminder;
	// 关于我们
	@InjectView
	ScrollView sv_aboutus;
	// 关于Text
	@InjectView
	TextView tv_aboutusContent;
	
	String strAboutus;
	

	// 获取育儿消息


	private ListView selfList;
//	@InjectView
//	private TextView tv_messagelistfragment;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			mShownCheckPosition = savedInstanceState.getInt("shownChoice", -1);
		}
		
		Handler_Inject.injectFragment(this, this.getView());
		
		// 加载轮循图片,在init里加载，会因为上一句调用两次， Handler_Inject.injectFragment(this, this.getView());
		loadCircleImage();
		
		selfList = getListView();
		
		//网络请求：消息列表
		
		// 如果没有权限，提示 只有VIP才有查看权限		tv_messageinfo
		
		UserInfo loginUser = ((MyApplication)this.getActivity().getApplication()).loginInfo.ResultJson.get(0);
//		
//		if(loginUser.Judge_user_account == "" || loginUser.Judge_user_account.length() == 0){
//			showNoAuthority();
//		}else 
		{
			InternetConfig config = new InternetConfig();
			config.setCharset("utf-8");
			config.setKey(Const_GetYuerListIndex);
			
			LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
			int userid =  loginUser.ID;
			params.put("userid",String.valueOf(userid));
			FastHttpHander.ajaxGet(Config.API_Message, params ,config, this);
		}
	}
	
	public void showLowerHalf(int lowerType){
		switch(lowerType){
			// 0,育儿经信息
			case (0):{
				rl_Reminder.setVisibility(View.GONE);
				selfList.setVisibility(View.VISIBLE);
				sv_aboutus.setVisibility(View.GONE);
				iv_message.setVisibility(View.VISIBLE);
			}
			break;
			// 1,没有权限
			case (1):{
				tv_messageinfo.setText(R.string.front_reminder_noauth);
				rl_Reminder.setVisibility(View.VISIBLE);
				selfList.setVisibility(View.GONE);
				sv_aboutus.setVisibility(View.GONE);
				iv_message.setVisibility(View.VISIBLE);
			}
			break;
			// 2,关于
			case (2):{
				rl_Reminder.setVisibility(View.GONE);
				selfList.setVisibility(View.GONE);
				iv_message.setVisibility(View.GONE);
				
				// 如果没有关于数据，取数据
				if(strAboutus == null || strAboutus.length() == 0){
					InternetConfig config = new InternetConfig();
					config.setCharset("utf-8");
					config.setKey(Const_GetAboutusIndex);

					// 异步请求
					FastHttpHander.ajaxGet(Config.API_AboutUs, null ,config, this);
				}
				sv_aboutus.setVisibility(View.VISIBLE);
			}
			break;
			// 3,没有数据
			case (3):{
				tv_messageinfo.setText(R.string.front_reminder_nodata);
				rl_Reminder.setVisibility(View.VISIBLE);
				selfList.setVisibility(View.GONE);
				sv_aboutus.setVisibility(View.GONE);
				iv_message.setVisibility(View.VISIBLE);
			}
			break;
		}
	}
	
	// 如果没有权限，提示 只有VIP才有查看权限
	private void showNoAuthority() {
		// TODO Auto-generated method stub
		// tv_messageinfo.setText("亲爱的宝爸宝妈您好，此功能仅限于VIP用户 \n具体详情请咨询电话  400-808-5311");
		tv_messageinfo.setText(R.string.front_reminder_noauth);
		rl_Reminder.setVisibility(View.VISIBLE);
		// tv_messageinfo.setVisibility(View.GONE);
		
		selfList.setVisibility(View.GONE);
	}
	
	// 如果没有权限，提示 只有VIP才有查看权限
	private void showMsgList() {
		// TODO Auto-generated method stub
		// tv_messageinfo.setText("温馨提示: \n亲爱的宝爸宝妈您好，此功能仅限于VIP用户 \n具体详情请咨询电话  400-808-5311");
		tv_messageinfo.setText(R.string.front_reminder_noauth);
		rl_Reminder.setVisibility(View.GONE);
		// tv_messageinfo.setVisibility(View.GONE);
		
		selfList.setVisibility(View.VISIBLE);
	}

	@InjectHttp
	private void httpHandle(ResponseEntity r)
	{
		switch (r.getStatus()) {
		case FastHttp.result_ok:
			switch(r.getKey()){
				case Const_GetYuerListIndex:{
					SetYuerList(r);
				}
				break;
				case Const_GetAboutusIndex:{
					SetAboutusList(r);
				}
				break;
			}
			break;
		}
	}
	
	private void SetAboutusList(ResponseEntity r) {
		// TODO Auto-generated method stub

		String strResult = r.getContentAsString();
		About m_about = new About();
		try{
			Handler_Json.JsonToBean(m_about, strResult);
			
			if(m_about.IsSuccess == false
					|| m_about.ResultJson == null
					|| m_about.ResultJson.size() < 1)
			{
				//tv_aboutusContent.setText("鑫宝贝教育科技有限公司是一家大型综合教育服务机构，以创客文化为依托，专注于幼少儿科学启蒙教育以及创造性思维的开发，致力于为孩子量身打造符合年龄特征、发展特征的科学学习平台和创意实践空间，以中国儿童的科学兴趣养成和创造能力提高为已任，以关爱儿童、开启儿童美好未来为企业发展目标。");
				tv_aboutusContent.setText("网络异常！请重试。");
			}
			else{
				strAboutus = m_about.ResultJson.get(0).AboutUs_Content;
				tv_aboutusContent.setText(strAboutus);
			}
		}
		catch(Exception ex)
		{
			//TODO:从缓存中取数据
			//tv_aboutusContent.setText("鑫宝贝教育科技有限公司是一家大型综合教育服务机构，以创客文化为依托，专注于幼少儿科学启蒙教育以及创造性思维的开发，致力于为孩子量身打造符合年龄特征、发展特征的科学学习平台和创意实践空间，以中国儿童的科学兴趣养成和创造能力提高为已任，以关爱儿童、开启儿童美好未来为企业发展目标。");
			tv_aboutusContent.setText("网络异常！请重试。");
		}
		
	}

	private void SetYuerList(ResponseEntity r) {
		// TODO Auto-generated method stub

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
	}

	private void setListViewGONE(boolean isGone) {
		if (isGone) {
//			tv_messageinfo.setText("暂无数据！");
//			tv_messageinfo.setVisibility(View.VISIBLE);
//
//			selfList.setVisibility(View.GONE);
			showLowerHalf(2);
		} else {
//			tv_messageinfo.setText("");
//			tv_messageinfo.setVisibility(View.GONE);
//			selfList.setVisibility(View.VISIBLE);
			showLowerHalf(0);
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

	 //显示listview item 详情
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
			
			((NewFrontActivity)this.getActivity()).StartMsgDetail(strTitle,strContent);
			
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
			tv_stitle.setText(msg.Message_Title);

			TextView tv_titlecontent = (TextView) convertView.findViewById(R.id.tv_titlecontent);
			tv_titlecontent.setText(msg.Message_Content);

			TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			tv_date.setText(msg.MessDate);

			return convertView;
		}
	}
	
	// */
	
	/**
	 * 
	 */
	private static class ImageHandler extends Handler{
        
        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE  = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT   = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT  = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED  = 4;
         
        //轮播间隔时间
        protected static final long MSG_DELAY = 5000;
         
        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<NewFrontFragment> weakReference;
        private int currentItem = 400;
         
        protected ImageHandler(WeakReference<NewFrontFragment> wk){
            weakReference = wk;
        }
         
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            NewFrontFragment activity = weakReference.get();
            if (activity==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (activity.handler.hasMessages(MSG_UPDATE_IMAGE)){
                activity.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
            case MSG_UPDATE_IMAGE:
            	currentItem ++;
                activity.vp_banner.setCurrentItem(currentItem);
                activity.setImageBackground(currentItem % activity.mImageViews.length);
                //准备下次播放
                activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            case MSG_KEEP_SILENT:
                //只要不发送消息就暂停了
                break;
            case MSG_BREAK_SILENT:
                activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            case MSG_PAGE_CHANGED:
                //记录当前的页号，避免播放的时候页面显示不正确。
                currentItem = msg.arg1;
                break;
            default:
                break;
            } 
        }
    }
}
