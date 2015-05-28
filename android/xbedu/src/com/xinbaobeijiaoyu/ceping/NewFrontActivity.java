package com.xinbaobeijiaoyu.ceping;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.xinbaobeijiaoyu.ceping.fragment.AboutUsFragment;
import com.xinbaobeijiaoyu.ceping.fragment.BaseFragmentActivity;
import com.xinbaobeijiaoyu.ceping.fragment.CepingFragment;
import com.xinbaobeijiaoyu.ceping.fragment.FrontFragment;
import com.xinbaobeijiaoyu.ceping.fragment.MessageDetailFragment;
import com.xinbaobeijiaoyu.ceping.fragment.NewFrontFragment;
import com.xinbaobeijiaoyu.ceping.fragment.NoAuthFragment;
import com.xinbaobeijiaoyu.ceping.fragment.QuestionHistoryListFragment;
import com.xinbaobeijiaoyu.ceping.fragment.SubjectiveFragment;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;

public class NewFrontActivity extends BaseFragmentActivity {
	//片断管理器
	private FragmentManager fragmentManager;
	
	//四个Fragment：
	private CepingFragment fg_ceping;//测评
	private SubjectiveFragment fg_subjective;//你问我答
//	private MessageListFragment fg_yuer;//育儿经
	private NewFrontFragment fg_yuer;//育儿经
	private AboutUsFragment fg_aboutus;//关于我们
	private NoAuthFragment fg_noauth; // 没有权限

//	private QinziFragment fg_qinzi;
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
		,ll_tab_bottom_yuer,ll_tab_bottom_about;	// ,ll_tab_bottom_home

	//四个底部imagebutton
	@InjectView
	private ImageButton ib_tab_bottom_ceping,ib_tab_bottom_subjective
		,ib_tab_bottom_yuer,ib_tab_bottom_about;	// ,ib_tab_bottom_home

	//四个底部textview
	@InjectView
	private TextView tv_tab_bottom_ceping,tv_tab_bottom_subjective
		,tv_tab_bottom_yuer,tv_tab_bottom_about;	// ,tv_tab_bottom_home

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_newfront);
		
		if(fragmentManager == null)
			fragmentManager = getSupportFragmentManager();
	}
	
	@InjectInit
	private void init()
	{
		setTabSelection(0);
		// setTabSelectionWithAuth(0);
		
		// 首次登陆，如果没有完善资料，新用户注册以后在每次登录的时候都要有一个提示语，“为了更好的服务与宝宝，请尽快完善资料”如果资料完善的就不需要在提醒。
		setInitUserHintInfo();
	}
	
	// 首次登陆，如果没有完善资料，新用户注册以后在每次登录的时候都要有一个提示语，“为了更好的服务与宝宝，请尽快完善资料”如果资料完善的就不需要在提醒。
	private void setInitUserHintInfo() {
		// TODO Auto-generated method stub

		UserInfo loginUser = ((MyApplication)this.getApplication()).loginInfo.ResultJson.get(0);
		
		if(loginUser == null
			|| loginUser.BabyName == null
			|| loginUser.BabyName.length() == 0			
				){

			String msg = "为了更好的服务与宝宝，请尽快完善资料.";
			Toast.makeText(getApplicationContext(),msg ,Toast.LENGTH_LONG).show();
		}
			
	}

	// 传入的index设置tab页。
	public void setTabSelectionOld(int index)
	{
		// 重置按钮
		resetBtn();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index)
		{
		case 0:
			// TODO:高亮，改变控件的图片和文字颜色
			setClicked(ll_tab_bottom_yuer,tv_tab_bottom_yuer
					,ib_tab_bottom_yuer,R.drawable.new_yuer_click);
			if (fg_yuer == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fg_yuer = new NewFrontFragment();
				transaction.add(R.id.fl_mainContainer, fg_yuer);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg_yuer);
			}
			break;
		case 1:
			// TODO:高亮，改变控件的图片和文字颜色
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
		case 2:
			// TODO:高亮，改变控件的图片和文字颜色
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
		case 3:
			// TODO:高亮，改变控件的图片和文字颜色
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
			break;
		}
		transaction.commit();
	}
	

	// 传入的index设置tab页。
	public void setTabSelection(int index)
	{
		// 重置按钮
		resetBtn();

		Boolean blAuth = true;

		// 如果没有权限，提示 只有VIP才有查看权限		tv_messageinfo
		UserInfo loginUser = ((MyApplication)this.getApplication()).loginInfo.ResultJson.get(0);
		
		// 如果没有权限，提示 只有VIP才有查看权限		tv_messageinfo
		if(loginUser.Judge_user_account == "" || loginUser.Judge_user_account.length() == 0){
			blAuth = false;
		}

//		// 开启一个Fragment事务
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
//		hideFragments(transaction);
		
		switch (index)
		{
		case 0:
			// TODO:高亮，改变控件的图片和文字颜色
			setClicked(ll_tab_bottom_yuer,tv_tab_bottom_yuer
					,ib_tab_bottom_yuer,R.drawable.new_yuer_click);
//			if (fg_yuer == null)
//			{
//				// 如果MessageFragment为空，则创建一个并添加到界面上
//				fg_yuer = new NewFrontFragment();
//				transaction.add(R.id.fl_mainContainer, fg_yuer);
//			} else
//			{
//				// 如果MessageFragment不为空，则直接将它显示出来
//				transaction.show(fg_yuer);
//			}
			if (fg_yuer == null){
				fg_yuer = new NewFrontFragment();
				changeFragment(fg_yuer);
			}
			else{
				changeFragment(fg_yuer);
				fg_yuer.showLowerHalf(0);
			}
			break;
		case 1:
			// TODO:高亮，改变控件的图片和文字颜色
			setClicked(ll_tab_bottom_ceping,tv_tab_bottom_ceping
					,ib_tab_bottom_ceping,R.drawable.new_ceping_click);
			
			if(!blAuth){
				if (fg_yuer != null){
					changeFragment(fg_yuer);
					fg_yuer.showLowerHalf(1);
				}
				
				if (fg_noauth == null){
					fg_noauth = new NoAuthFragment();
					changeFragment(fg_noauth);
				}
				else{
					changeFragment(fg_noauth);
				}
			}else{
				if (fg_ceping == null){
					fg_ceping = new CepingFragment();
					changeFragment(fg_ceping);
				}
				else{
					changeFragment(fg_ceping);
				}
			}
			break;
		case 2:
			// TODO:高亮，改变控件的图片和文字颜色
			setClicked(ll_tab_bottom_subjective,tv_tab_bottom_subjective
					,ib_tab_bottom_subjective,R.drawable.new_subjective_click);

			if(!blAuth){
				if (fg_yuer != null){
					changeFragment(fg_yuer);
					fg_yuer.showLowerHalf(1);
				}
				
				if (fg_noauth == null){
					fg_noauth = new NoAuthFragment();
					changeFragment(fg_noauth);
				}
				else{
					changeFragment(fg_noauth);
				}
			}else{
				if (fg_subjective == null){
					fg_subjective = new SubjectiveFragment();
					changeFragment(fg_subjective);
				}
				else{
					changeFragment(fg_subjective);
				}
			}
			break;
		// 关于
		case 3:
			// TODO:高亮，改变控件的图片和文字颜色
			setClicked(ll_tab_bottom_about,tv_tab_bottom_about
					,ib_tab_bottom_about,R.drawable.new_about_click);

//			if (fg_yuer != null){
//				changeFragment(fg_yuer);
//				fg_yuer.showLowerHalf(2);
//			}

			if (fg_aboutus == null){
				fg_aboutus = new AboutUsFragment();
				changeFragment(fg_aboutus);
			}
			else{
				changeFragment(fg_aboutus);
			}
			break;
		}
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

	ArrayList<Fragment> listAdded = new ArrayList<Fragment>();
	public void changeFragment(Fragment frag) {
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(fragmentTransaction);
		if (frag != null)
		{
			if(!listAdded.contains(frag)){
				listAdded.add(frag);
				fragmentTransaction.add(R.id.fl_mainContainer, frag);				
			}
			
			// 如果MessageFragment不为空，则直接将它显示出来
			fragmentTransaction.show(frag);
		}
		fragmentTransaction.commit();
//		fragmentManager.executePendingTransactions();
	}
	
	//清除掉所有的选中状态。
	private void resetBtn()
	{
		//TODO:
		setUnClicked(ll_tab_bottom_ceping,tv_tab_bottom_ceping
				,ib_tab_bottom_ceping,R.drawable.new_ceping);

		setUnClicked(ll_tab_bottom_subjective,tv_tab_bottom_subjective
				,ib_tab_bottom_subjective,R.drawable.new_subjective);

		setUnClicked(ll_tab_bottom_yuer,tv_tab_bottom_yuer
				,ib_tab_bottom_yuer,R.drawable.new_yuer);

		setUnClicked(ll_tab_bottom_about,tv_tab_bottom_about
				,ib_tab_bottom_about,R.drawable.new_about);

//		setUnClicked(ll_tab_bottom_home,tv_tab_bottom_home
//				,ib_tab_bottom_home,R.drawable.new_home);
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
		if (fg_yuer != null)
		{
			transaction.hide(fg_yuer);
		}
		if (fg_aboutus != null)
		{
			transaction.hide(fg_aboutus);
		}
		if (fg_front != null)
		{
			transaction.hide(fg_front);
		}
		if (fg_yuer != null)
		{
			transaction.hide(fg_yuer);
		}
		if(fg_qhistory != null){
			transaction.hide(fg_qhistory);
		}
		if(fg_noauth != null){
			transaction.hide(fg_noauth);
		}
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
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.addCategory(Intent.CATEGORY_HOME);
						this.startActivity(intent);
						finish();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
	}

	public void click(View view) {
		switch (view.getId()) {
		case R.id.ll_tab_bottom_yuer:
//		case R.id.btn_msg:
			setTabSelection(0);
			break;
		case R.id.ll_tab_bottom_ceping:
//		case R.id.btn_ceping:
			setTabSelection(1);
			break;
		case R.id.ll_tab_bottom_subjective:
//		case R.id.btn_selfq:
			setTabSelection(2);
			break;
		case R.id.ll_tab_bottom_about:
//		case R.id.btn_aboutus:
			setTabSelection(3);
			break;
//		case R.id.ll_tab_bottom_home:
//			setTabSelection(-1);
//			break;
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
	
	
}
