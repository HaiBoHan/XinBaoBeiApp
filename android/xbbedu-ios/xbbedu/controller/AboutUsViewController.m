//
//  AboutUsViewController.m
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "AboutUsViewController.h"

@interface AboutUsViewController ()<UIScrollViewDelegate>

@property (nonatomic, strong) NSTimer *timer;

@end

@implementation AboutUsViewController

@synthesize webView;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //1. request data
    [NetRequestClass NetRequestGETWithRequestURL:API_ABOUTUS
                                   WithParameter:nil WithReturnValeuBlock:^(id returnValue) {
                                       DDLog(@"%@", returnValue);
                                       [self fetchValueSuccessWithDic:returnValue];}
                              WithErrorCodeBlock:^(id errorCode) {
                                  DDLog(@"%@", errorCode);
                                  [self errorCodeWithDic:errorCode];}
                                WithFailureBlock:^{
                                    [self netFailure];
                                    DDLog(@"网络异常");}];
    //2. else :loading localhost data
}

-(void)loadingLocalAboutusContent
{
    NSString *path = [[NSBundle mainBundle] pathForResource:@"aboutus.txt" ofType:nil];
    NSString *htmlString = [[NSString alloc] initWithContentsOfFile:path encoding:NSUTF8StringEncoding error:nil];
    [webView loadHTMLString: htmlString baseURL: [NSURL fileURLWithPath:[[NSBundle mainBundle] bundlePath]]];
}


-(void)fetchValueSuccessWithDic:(NSDictionary *) returnValue
{
    if(returnValue != nil)
    {
        //请求成功
        if ([[returnValue objectForKey:@"IsSuccess"] intValue] == 1) {
            NSString *resultJson = [returnValue objectForKey:@"ResultJson"];
            
            if(resultJson != nil && resultJson.length > 0)
            {
                NSArray *userInfos = [resultJson JSONValue];
                if (userInfos != nil && userInfos.count > 0) {
                    NSDictionary *dic = (NSDictionary *)[userInfos objectAtIndex:0];
                    if (dic) {
                        [webView loadHTMLString:[dic objectForKey:@"AboutUs_Content"] baseURL: [NSURL fileURLWithPath:[[NSBundle mainBundle] bundlePath]]];
                        self.tvContent.text = [dic objectForKey:@"AboutUs_Content"];
                    }
                }
            }
            
        }
    }
}

-(void)errorCodeWithDic:(id) errorCode
{
    [self loadingLocalAboutusContent];
}

-(void)netFailure
{
    [self loadingLocalAboutusContent];
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

@end
