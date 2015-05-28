//
//  AboutUsViewController.h
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AboutUsViewController : UIViewController<UIWebViewDelegate>

@property (weak, nonatomic) IBOutlet UITextView *tvContent;
@property (nonatomic, strong) IBOutlet UIWebView *webView;
@end
