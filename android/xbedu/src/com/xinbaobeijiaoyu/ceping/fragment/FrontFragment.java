package com.xinbaobeijiaoyu.ceping.fragment;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.android.pc.util.Handler_System;
import com.xinbaobeijiaoyu.ceping.MyApplication;
import com.xinbaobeijiaoyu.ceping.R;
// import com.xinbaobeijiaoyu.ceping.FrontActivity.MyAdapter;
import com.xinbaobeijiaoyu.ceping.model.Login;
import com.xinbaobeijiaoyu.ceping.model.Login.UserInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FrontFragment extends Fragment implements OnPageChangeListener {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_front, container, false);
		Handler_Inject.injectFragment(this, rootView);
		return rootView;
	}
	
	private View rootView;
	
	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private Button btn_aboutus, btn_ceping, btn_msg, btn_selfq,btn_qinzi;
	
	@InjectView(binders = @InjectBinder(method = "click", listeners = { OnClick.class }))
	private TextView tv_personinfo;

	protected void click(View view) {
		((com.xinbaobeijiaoyu.ceping.FrontActivity)this.getActivity()).click(view);
	}
	


	@InjectView
	private ViewPager vp_banner;
	@InjectView
	private ViewGroup ll_points;
	@InjectView
	private TextView tv_des;

	private ImageView[] tips;
	private ImageView[] mImageViews;
	private int[] imgIdArray;


	@InjectInit
	protected void init() {
		Login loginInfo = ((MyApplication)this.getActivity().getApplication()).loginInfo;
		if(loginInfo != null)
		{
			UserInfo user = loginInfo.ResultJson.get(0);
			if(user != null)
			{
				String str_title_dev = user.Name + "家长 \r\n您的宝宝已经"+ user.Age + "了";
				tv_des.setText(str_title_dev);
			}
		}

		// 载入图片资源ID
//		imgIdArray = new int[] { R.drawable.bg_title_hold02,
//				R.drawable.bg_title_hold01, R.drawable.bg_title_hold };
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
		vp_banner.setCurrentItem((mImageViews.length) * 100);
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
			// ((ViewPager)container).removeView(mImageViews[position %
			// mImageViews.length]);
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
			return mImageViews[position % mImageViews.length];
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
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
}
