//
//  NetRequestClass.h
//  xbbedu
//
//  Created by mz on 15/4/17.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#ifndef xbbedu_Config_h
#define xbbedu_Config_h

//定义返回请求数据的block类型
typedef void (^ReturnValueBlock) (id returnValue);
typedef void (^ErrorCodeBlock) (id errorCode);
typedef void (^FailureBlock)();
typedef void (^NetWorkBlock)(BOOL netConnetState);

#define DDLog(xx, ...)  NSLog(@"%s(%d): " xx, __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__)

#endif

// 获取RGB颜色
#define RGBA(r,g,b,a) [UIColor colorWithRed:r/255.0f green:g/255.0f blue:b/255.0f alpha:a]
#define RGB(r,g,b) RGBA(r,g,b,1.0f)
#define UIColorFromRGB(rgbValue) [UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 green:((float)((rgbValue & 0xFF00) >> 8))/255.0 blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]

//获取屏幕 宽度、高度
#define SCREEN_WIDTH ([UIScreen mainScreen].bounds.size.width)
#define SCREEN_HEIGHT ([UIScreen mainScreen].bounds.size.height)

//----------------------系统----------------------------
//获取系统版本
#define IOS_VERSION [[[UIDevice currentDevice] systemVersion] floatValue]
#define CurrentSystemVersion [[UIDevice currentDevice] systemVersion]

//判断是否 Retina屏、设备是否%fhone 5、是否是iPad
#define isRetina ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 960), [[UIScreen mainScreen] currentMode].size) : NO)
#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)
#define isPad (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad)
//----------------------系统----------------------------


//网络请求URL
//#define HOST_NAME @"211.149.198.209"
//#define PORT @"8012"
//#define FULL_URL
#define API_LOGIN @"http://211.149.198.209:8012/HBHHttpPost.aspx?Method=GetUser"
#define API_QINZI @"http://211.149.198.209:8012/HBHhttpPost.aspx?Method=GetParentChildCurriculum"

#define API_Message @"http://211.149.198.209:8012/HBHHttpPost.aspx?Method=GetMessage"

//关于我们
#define API_ABOUTUS @"http://211.149.198.209:8012/HBHHttpPost.aspx?Method=GetAboutUs"

//主观问题
#define API_SUBJECTIVE @"http://211.149.198.209:8012/HBHHttpPost.aspx?Method=GetKeywordsQuestion"

#define API_POSTSUBJECTIVE @"http://211.149.198.209:8012/HBHHttpPost.aspx?Method=SaveSubjectivityQuestion"

#define API_USERREG @"http://211.149.198.209:8012/HBHHttpPost.aspx?method=UserRegister"

#define API_GETVERITYCODE @"http://211.149.198.209:8012/HBHHttpPost.aspx?Method=GetSMS"


#define API_MYQUEST @"http://211.149.198.209:8012/HBHHttpPost.aspx?method=GetSubjectivityQuestion"

#define API_HISTORY @"http://211.149.198.209:8012/HBHHttpPost.aspx?method=GetKeywordsHistory"
