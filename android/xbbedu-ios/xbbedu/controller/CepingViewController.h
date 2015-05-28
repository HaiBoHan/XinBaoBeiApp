//
//  CepingViewController.h
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CepingViewController : UIViewController<UIWebViewDelegate>

@property (nonatomic, weak) IBOutlet UIWebView *webView;
@property (weak, nonatomic) IBOutlet UIView *viewDisable;

@end
