//
//  CepingViewController.m
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "CepingViewController.h"

@interface CepingViewController ()

@end

@implementation CepingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //判断：如果没有权限，显示另外一个View
    //缓存取用户信息
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *strJudge = [defaults valueForKey:@"Judge_user_account"];
    if(strJudge)
    {
        if([strJudge isEqualToString:@""] == FALSE)
            self.viewDisable.hidden = TRUE;
        else
        {
            self.viewDisable.hidden = FALSE;
            return;
        }
    }
    
    //测评URLusertype=account=admin
    NSString *strCePingURL = @"http://ceping.xinbaobeijiaoyu.com/Interface_login1.php?usertype=%E6%B5%8B%E8%AF%95%E8%80%85&account=";
    NSString *str = [strCePingURL stringByAppendingString:strJudge];
    
    NSURL *url =[NSURL URLWithString:str];
    NSURLRequest *request =[NSURLRequest requestWithURL:url];
    [self.webView loadRequest:request];
    
//    if (isRetina) {
        self.webView.delegate = self;
        DDLog(@"self.webView.frame=%f,%f,%f,%f",self.webView.frame.origin.x,self.webView.frame.origin.y,self.webView.frame.size.width,self.webView.frame.size.height);
//        self.webView.frame = CGRectMake(self.webView.frame.origin.x, self.webView.frame.origin.y, self.webView.frame.size.width, self.webView.frame.size.height - 200);
//        
//        DDLog(@"self.webView.frame=%f,%f,%f,%f",self.webView.frame.origin.x,self.webView.frame.origin.y,self.webView.frame.size.width,self.webView.frame.size.height);
//    }
}

- (void)webViewDidFinishLoad:(UIWebView *)aWebView {
    if (isRetina) {
        self.webView.frame = CGRectMake(self.webView.frame.origin.x, self.webView.frame.origin.y, self.webView.frame.size.width, 380);
    }
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

//- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation
//{
////    return toInterfaceOrientation != UIDeviceOrientationPortraitUpsideDown;
//    return FALSE;
//}
//
//- (BOOL)shouldAutorotate
//{
//    return YES;
//}

//- (NSUInteger)supportedInterfaceOrientations
//{
//    return UIInterfaceOrientationMaskAllButUpsideDown;
//}

@end
