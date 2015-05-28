package com.xinbaobeijiaoyu.ceping;

import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.util.Handler_Json;
import com.xinbaobeijiaoyu.ceping.app.Config;
import com.xinbaobeijiaoyu.ceping.model.About;

@InjectLayer(R.layout.activity_aboutus)
public class AboutUsActivity extends BaseActivity {
	
	@InjectView
	TextView tv_aboutusContent;

	@InjectInit
	private void init() {
		InternetConfig config = new InternetConfig();
		config.setCharset("utf-8");

		// 异步请求
		FastHttpHander.ajaxGet(Config.API_AboutUs, null ,config, this);
//		setHUBStatus(this ,1, "请稍等...");
	}
	
	@InjectHttp
	private void result(ResponseEntity r) {
//		switch (r.getStatus()) {
//		case FastHttp.result_ok:
//			setHUBStatus(this, 0, "");
//			
//			String strResult = r.getContentAsString();
//			About m_about = new About();
//			try{
//				Handler_Json.JsonToBean(m_about, strResult);
//				
//				if(m_about.IsSuccess == false
//						|| m_about.ResultJson == null
//						|| m_about.ResultJson.size() < 1)
//				{
//					tv_aboutusContent.setText("鑫宝贝教育科技有限公司是一家大型综合教育服务机构，以创客文化为依托，专注于幼少儿科学启蒙教育以及创造性思维的开发，致力于为孩子量身打造符合年龄特征、发展特征的科学学习平台和创意实践空间，以中国儿童的科学兴趣养成和创造能力提高为已任，以关爱儿童、开启儿童美好未来为企业发展目标。");
//				}
//				else
//					tv_aboutusContent.setText(m_about.ResultJson.get(0).AboutUs_Content);
//			}
//			catch(Exception ex)
//			{
//				//TODO:从缓存中取数据
//				tv_aboutusContent.setText("鑫宝贝教育科技有限公司是一家大型综合教育服务机构，以创客文化为依托，专注于幼少儿科学启蒙教育以及创造性思维的开发，致力于为孩子量身打造符合年龄特征、发展特征的科学学习平台和创意实践空间，以中国儿童的科学兴趣养成和创造能力提高为已任，以关爱儿童、开启儿童美好未来为企业发展目标。");
//				
//			}
//			
//			break;
//		}
		switch (r.getStatus()) {
		case FastHttp.result_ok:
//			setHUBStatus(this, 0, "");
			
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
				else
					tv_aboutusContent.setText(m_about.ResultJson.get(0).AboutUs_Content);
			}
			catch(Exception ex)
			{
				//TODO:从缓存中取数据
				//tv_aboutusContent.setText("鑫宝贝教育科技有限公司是一家大型综合教育服务机构，以创客文化为依托，专注于幼少儿科学启蒙教育以及创造性思维的开发，致力于为孩子量身打造符合年龄特征、发展特征的科学学习平台和创意实践空间，以中国儿童的科学兴趣养成和创造能力提高为已任，以关爱儿童、开启儿童美好未来为企业发展目标。");
				tv_aboutusContent.setText("网络异常！请重试。");
			}
			
			break;
		}
	}
}
