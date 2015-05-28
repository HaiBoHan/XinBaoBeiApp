package com.xinbaobeijiaoyu.ceping;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_System;
import com.xinbaobeijiaoyu.ceping.fragment.AboutUsFragment;
import com.xinbaobeijiaoyu.ceping.fragment.BaseFragmentActivity;
import com.xinbaobeijiaoyu.ceping.fragment.CepingFragment;
import com.xinbaobeijiaoyu.ceping.fragment.FrontFragment;
import com.xinbaobeijiaoyu.ceping.fragment.MessageDetailFragment;
import com.xinbaobeijiaoyu.ceping.fragment.MessageListFragment;
//import com.xinbaobeijiaoyu.ceping.fragment.NoAuthFragment;
import com.xinbaobeijiaoyu.ceping.fragment.QinziFragment;
import com.xinbaobeijiaoyu.ceping.fragment.QinziListFragment;
import com.xinbaobeijiaoyu.ceping.fragment.QuestionHistoryListFragment;
import com.xinbaobeijiaoyu.ceping.fragment.SubjectiveFragment;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;

public class FrontActivity extends BaseFragmentActivity {
	//片断管理器
	private FragmentManager fragmentManager;
	
	//四个Fragment：
	private CepingFragment fg_ceping;//测评
	private SubjectiveFragment fg_subjective;//你问我答
	private MessageListFragment fg_msg;//育儿经
	private AboutUsFragment fg_aboutus;//关于我们
//	private NoAuthFragment fg_noauth; // 没有权限

	private QinziFragment fg_qinzi;
	private FrontFragment fg_front;
	
	private QuestionHistoryListFragment fg_qhistory;
	private MessageDetailFragment fg_msgDetail;
	
	private boolean isFgFront = false;
	
	private long mExitTime;

	//内容layout
	@InjectView
	private FrameLayout fl_mainContainer;

	//四个底部layout
	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private LinearLayout ll_tab_bottom_ceping,ll_tab_bottom_subjective
		,ll_tab_bottom_qinzi,ll_tab_bottom_about,ll_tab_bottom_home;

	//四个底部imagebutton
	@InjectView
	private ImageButton ib_tab_bottom_ceping,ib_tab_bottom_subjective
		,ib_tab_bottom_qinzi,ib_tab_bottom_about,ib_tab_bottom_home;

	//四个底部textview
	@InjectView
	private TextView tv_tab_bottom_ceping,tv_tab_bottom_subjective
		,tv_tab_bottom_qinzi,tv_tab_bottom_about,tv_tab_bottom_home;
	
//	// 原来的按钮
//	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
//	private Button btn_msg,btn_selfq,btn_aboutus,btn_ceping,btn_qinzi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_front);
		
		if(fragmentManager == null)
			fragmentManager = getSupportFragmentManager();
	}
	
	@InjectInit
	private void init()
	{
		setTabSelection(-1);
	}
	
	// 传入的index设置tab页。
	public void setTabSelection(int index)
	{
		// 重置按钮
		resetBtn();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index)
		{
		case -1:
			// TODO:高亮，改变控件的图片和文字颜色
//			ib_tab_bottom_home.setImageResource(R.drawable.new_home_click);
////			ib_tab_bottom_home.setBackgroundResource(R.color.common_toolbar_btnclicked);
////			tv_tab_bottom_home.setBackgroundResource(R.color.common_toolbar_btnclicked);
			setClicked(ll_tab_bottom_home,tv_tab_bottom_home
					,ib_tab_bottom_home,R.drawable.new_home_1);
			if (fg_front == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fg_front = new FrontFragment();
				transaction.add(R.id.fl_mainContainer, fg_front);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg_front);
			}
			break;
		case 0:
			// TODO:高亮，改变控件的图片和文字颜色
//			ib_tab_bottom_ceping.setImageResource(R.drawable.new_ceping_click);
////			ib_tab_bottom_ceping.setBackgroundResource(R.color.common_toolbar_btnclicked);
////			tv_tab_bottom_ceping.setBackgroundResource(R.color.common_toolbar_btnclicked);
			setClicked(ll_tab_bottom_ceping,tv_tab_bottom_ceping
					,ib_tab_bottom_ceping,R.drawable.new_ceping_click);
			if (fg_ceping == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fg_ceping = new CepingFragment();
				transaction.add(R.id.fl_mainContainer, fg_ceping);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg_ceping);
			}
			break;
		case 1:
			// TODO:高亮，改变控件的图片和文字颜色
//			ib_tab_bottom_subjective.setImageResource(R.drawable.new_subjective_click);
////			ib_tab_bottom_subjective.setBackgroundResource(R.color.common_toolbar_btnclicked);
////			tv_tab_bottom_subjective.setBackgroundResource(R.color.common_toolbar_btnclicked);
			setClicked(ll_tab_bottom_subjective,tv_tab_bottom_subjective
					,ib_tab_bottom_subjective,R.drawable.new_subjective_click);
			if (fg_subjective == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fg_subjective = new SubjectiveFragment();
				transaction.add(R.id.fl_mainContainer, fg_subjective);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg_subjective);
			}
			break;
		case 2:
			// TODO:高亮，改变控件的图片和文字颜色
//			ib_tab_bottom_qinzi.setImageResource(R.drawable.new_yuer_click);
////			ib_tab_bottom_qinzi.setBackgroundResource(R.color.common_toolbar_btnclicked);
////			tv_tab_bottom_qinzi.setBackgroundResource(R.color.common_toolbar_btnclicked);
			setClicked(ll_tab_bottom_qinzi,tv_tab_bottom_qinzi
					,ib_tab_bottom_qinzi,R.drawable.new_yuer_click);
			if (fg_msg == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fg_msg = new MessageListFragment();
				transaction.add(R.id.fl_mainContainer, fg_msg);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg_msg);
			}
			break;
		case 3:
			// TODO:高亮，改变控件的图片和文字颜色
//			ib_tab_bottom_about.setImageResource(R.drawable.new_about_click);
////			ib_tab_bottom_about.setBackgroundResource(R.color.common_toolbar_btnclicked);
////			tv_tab_bottom_about.setBackgroundResource(R.color.common_toolbar_btnclicked);
			setClicked(ll_tab_bottom_about,tv_tab_bottom_about
					,ib_tab_bottom_about,R.drawable.new_about_click);
			if (fg_aboutus == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fg_aboutus = new AboutUsFragment();
				transaction.add(R.id.fl_mainContainer, fg_aboutus);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg_aboutus);
			}
//			// TODO:高亮，改变控件的图片和文字颜色
//			ib_tab_bottom_about.setImageResource(R.drawable.new_about_click);
//			if (fg_qhistory == null)
//			{
//				// 如果MessageFragment为空，则创建一个并添加到界面上
//				fg_qhistory = new QuestionHistoryListFragment();
//				transaction.add(R.id.fl_mainContainer, fg_qhistory);
//			} else
//			{
//				// 如果MessageFragment不为空，则直接将它显示出来
//				transaction.show(fg_qhistory);
//			}
			break;
		}
		transaction.commit();
	}

	public void changeFragment(Fragment frag,String tag,boolean isFirst) {
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(fragmentTransaction);
		if (frag != null)
		{
			// 如果MessageFragment不为空，则直接将它显示出来
			fragmentTransaction.show(frag);
		}
		fragmentTransaction.commit();
		fragmentManager.executePendingTransactions();
	}
	
	//清除掉所有的选中状态。
	private void resetBtn()
	{
		//TODO:
		setUnClicked(ll_tab_bottom_ceping,tv_tab_bottom_ceping
				,ib_tab_bottom_ceping,R.drawable.new_ceping);
//		ib_tab_bottom_ceping.setImageResource(R.drawable.new_ceping);
////		ll_tab_bottom_ceping.setBackgroundResource(R.color.common_toolbar_btninit);

		setUnClicked(ll_tab_bottom_subjective,tv_tab_bottom_subjective
				,ib_tab_bottom_subjective,R.drawable.new_subjective);
//		ib_tab_bottom_subjective.setImageResource(R.drawable.new_qanda);
////		ll_tab_bottom_subjective.setBackgroundResource(R.color.common_toolbar_btninit);

		setUnClicked(ll_tab_bottom_qinzi,tv_tab_bottom_qinzi
				,ib_tab_bottom_qinzi,R.drawable.new_yuer);
//		ib_tab_bottom_qinzi.setImageResource(R.drawable.new_yuer);
////		ll_tab_bottom_qinzi.setBackgroundResource(R.color.common_toolbar_btninit);

		setUnClicked(ll_tab_bottom_about,tv_tab_bottom_about
				,ib_tab_bottom_about,R.drawable.new_about);
//		ib_tab_bottom_about.setImageResource(R.drawable.new_about);
////		ll_tab_bottom_about.setBackgroundResource(R.color.common_toolbar_btninit);

		setUnClicked(ll_tab_bottom_home,tv_tab_bottom_home
				,ib_tab_bottom_home,R.drawable.new_home_1);
//		ib_tab_bottom_home.setImageResource(R.drawable.new_home_init);
////		ll_tab_bottom_home.setBackgroundResource(R.color.common_toolbar_btninit);
	}
	
	private void setUnClicked(LinearLayout layout,TextView tvTitle,ImageButton imgBtn,int imgID){
		imgBtn.setImageResource(imgID);
//		layout.setBackgroundResource(R.color.common_toolbar_btninit);
//		tvTitle.setBackgroundResource(R.color.common_toolbar_btninit);
		tvTitle.setTextAppearance(getBaseContext(), R.style.unClicked);
	}
	
	private void setClicked(LinearLayout layout,TextView tvTitle,ImageButton imgBtn,int imgID){
		imgBtn.setImageResource(imgID);
//		layout.setBackgroundResource(R.color.common_toolbar_btninit);
//		tvTitle.setBackgroundResource(R.color.common_toolbar_btninit);
		tvTitle.setTextAppearance(getBaseContext(), R.style.clicked);
	}

	// 将所有的Fragment都置为隐藏状态。
	private void hideFragments(FragmentTransaction transaction)
	{
		if (fg_ceping != null)
		{
			transaction.hide(fg_ceping);
		}
		if (fg_subjective != null)
		{
			transaction.hide(fg_subjective);
		}
		if (fg_qinzi != null)
		{
			transaction.hide(fg_qinzi);
		}
		if (fg_aboutus != null)
		{
			transaction.hide(fg_aboutus);
		}
		if (fg_front != null)
		{
			transaction.hide(fg_front);
		}
		if (fg_msg != null)
		{
			transaction.hide(fg_msg);
		}
		if(fg_qhistory != null){
			transaction.hide(fg_qhistory);
		}
		
//		if(fg_noauth != null){
//			transaction.hide(fg_noauth);
//		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK 
        		// && fg_front.isAdded() == true
        		) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        mExitTime = System.currentTimeMillis();

                } else {
//                        finish();
                	System.exit(0);
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
	}

	public void click(View view) {
		switch (view.getId()) {
		case R.id.ll_tab_bottom_ceping:
		case R.id.btn_ceping:
//			if(fg_ceping == null)
//				fg_ceping = new CepingFragment();
//			isFgFront = false;
//			changeFragment(fg_ceping,"ceping",false);
////			startActivity(new Intent(this, CePingActivity.class));
			setTabSelection(0);
			break;
		case R.id.ll_tab_bottom_subjective:
			case R.id.btn_selfq:
//			//fg_subjective
//			if(fg_subjective == null)
//				fg_subjective = new SubjectiveFragment();
//			isFgFront = false;
//			changeFragment(fg_subjective,"subjective",false);
			setTabSelection(1);
			break;
		case R.id.ll_tab_bottom_qinzi:
		case R.id.btn_msg:
//			if(fg_msg == null)
//				fg_msg = new MessageListFragment();
//			isFgFront = false;
//			changeFragment(fg_msg,"msg",false);
			setTabSelection(2);
			break;
		case R.id.ll_tab_bottom_about:
		case R.id.btn_aboutus:
//			if(fg_aboutus == null)
//				fg_aboutus = new AboutUsFragment();
//			isFgFront = false;
//			changeFragment(fg_aboutus,"aboutus",false);
////			startActivity(new Intent(this, AboutUsActivity.class));
			setTabSelection(3);
			break;
//		case R.id.ib_tab_bottom_qinzi:
		case R.id.btn_qinzi:
////			if(fg_qinzilist == null)
////				fg_qinzilist = new QinziListFragment();
////			isFgFront = false;
////			changeFragment(fg_qinzilist,"qinzi",false);
//////			startActivity(new Intent(this, QinziActivity.class));
//			
//			if(fg_qinzi == null)
//				fg_qinzi = new QinziFragment();
//			isFgFront = false;
//			changeFragment(fg_qinzi,"qinzi",false);
//			break;
			startActivity(new Intent(this, QinziActivity.class));
			break;
//		case R.id.btnPre:
//			if(fg_front == null)
//				fg_front = new FrontFragment();
//			isFgFront = true;
//			changeFragment(fg_front,"front",true);
//			break;
//		case R.id.btnPrivate:
//			startActivity(new Intent(this, PersonInfoActivity.class));
//			break;
		case R.id.ll_tab_bottom_home:
			setTabSelection(-1);
			break;
		}
	}
	
	public void StartMsgDetail(String strTitle,String strContent){

		Intent intent = new Intent(this, MessageDetailActivity.class);
		
		Bundle bundle = new Bundle();
        bundle.putString("title", strTitle);
        bundle.putString("content", strContent);
        intent.putExtras(bundle);
        
        startActivity(intent);
	}
	
	
	//------------------------------------旧版-----------------------------------
//	//btnPre
//	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
//	private Button btnPre,btnPrivate;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		this.setContentView(R.layout.activity_front);
//		
//		if(fragmentManager == null)
//			fragmentManager = getSupportFragmentManager();
//		
//		if(fg_front == null)
//			fg_front = new FrontFragment();
//		
//		isFgFront = true;
//		changeFragment(fg_front, "front", isFgFront);
//	}
//	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK 
//        		&& fg_front.isAdded() == true) {
//                if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                        mExitTime = System.currentTimeMillis();
//
//                } else {
//                        finish();
//                }
//                return true;
//        }
//        return super.onKeyDown(keyCode, event);
//}
//
//	
//	public void click(View view) {
//		switch (view.getId()) {
//		case R.id.btn_aboutus:
//			if(fg_aboutus == null)
//				fg_aboutus = new AboutUsFragment();
//			isFgFront = false;
//			changeFragment(fg_aboutus,"aboutus",false);
////			startActivity(new Intent(this, AboutUsActivity.class));
//			break;
//		case R.id.btn_ceping:
//			if(fg_ceping == null)
//				fg_ceping = new CepingFragment();
//			isFgFront = false;
//			changeFragment(fg_ceping,"ceping",false);
////			startActivity(new Intent(this, CePingActivity.class));
//			break;
//		case R.id.btn_msg:
//			if(fg_msg == null)
//				fg_msg = new MessageListFragment();
//			isFgFront = false;
//			changeFragment(fg_msg,"msg",false);
//			break;
//		case R.id.btn_selfq:
//			//fg_subjective
//			if(fg_subjective == null)
//				fg_subjective = new SubjectiveFragment();
//			isFgFront = false;
//			changeFragment(fg_subjective,"subjective",false);
//			break;
//		case R.id.btn_qinzi:
////			if(fg_qinzilist == null)
////				fg_qinzilist = new QinziListFragment();
////			isFgFront = false;
////			changeFragment(fg_qinzilist,"qinzi",false);
//////			startActivity(new Intent(this, QinziActivity.class));
//			
//			if(fg_qinzi == null)
//				fg_qinzi = new QinziFragment();
//			isFgFront = false;
//			changeFragment(fg_qinzi,"qinzi",false);
//			break;
//		case R.id.btnPre:
//			if(fg_front == null)
//				fg_front = new FrontFragment();
//			isFgFront = true;
//			changeFragment(fg_front,"front",true);
//			break;
//		case R.id.btnPrivate:
//			startActivity(new Intent(this, PersonInfoActivity.class));
//			break;
//		}
//	}
//
//	public void changeFragment(Fragment frag,String tag,boolean isFirst) {
//		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//		fragmentTransaction.replace(R.id.fl_mainContainer,frag,tag);
//		if(!isFirst){
//			fragmentTransaction.addToBackStack(null);  
//        }
//		fragmentTransaction.commit();
//		fragmentManager.executePendingTransactions();
//	}
	
}
