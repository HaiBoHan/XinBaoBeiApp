//
//  LoginViewController.m
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "LoginViewController.h"
#import "MBProgressHUD.h"
#import "NetRequestClass.h"
#import "SBJsonParser.h"
#import "Login.h"
#import "UserInfo.h"

@interface LoginViewController ()<MBProgressHUDDelegate>
{
    MBProgressHUD *mLoadView;
}
- (IBAction)touchView:(id)sender;
@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSUserDefaults * defaults = [NSUserDefaults standardUserDefaults];
    NSString *strAccount = [defaults stringForKey:@"Account"];
    self.txtName.text = strAccount;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)login:(id)sender
{
    //关闭键盘
    [self.view endEditing:YES];
    
    if (self.txtName.text.length == 0) {
        [self showHUDdelayHideWithMessage:@"用户名不能为空"];
        return;
    }
    if (self.txtPwd.text.length == 0) {
        [self showHUDdelayHideWithMessage:@"密码不能为空"];
        return;
    }
    
    NSDictionary *parameter = @{@"Account": [self.txtName text],
                                @"Pwd": [self.txtPwd text]
                                };
    
    [self showHUDWithMessage:@"登录中..."];
    
    [NetRequestClass NetRequestGETWithRequestURL:API_LOGIN
                                   WithParameter:parameter WithReturnValeuBlock:^(id returnValue) {
        DDLog(@"%@", returnValue);
        [self fetchValueSuccessWithDic:returnValue];}
                              WithErrorCodeBlock:^(id errorCode) {
        DDLog(@"%@", errorCode);
        [self errorCodeWithDic:errorCode];}
                                WithFailureBlock:^{
        [self netFailure];
        DDLog(@"网络异常");}];
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
                        UserInfo * loginUser = [UserInfo new];
                        loginUser.ID = [[dic objectForKey:@"ID"] intValue];
                        loginUser.Account = [dic objectForKey:@"Account"];
                        loginUser.Name = [dic objectForKey:@"Name"];
                        loginUser.Region = [dic objectForKey:@"Region"];
                        loginUser.Passwd = [dic objectForKey:@"Passwd"];
                        loginUser.Sex = [dic objectForKey:@"Sex"];
                        loginUser.Age = [dic objectForKey:@"Age"];
                        loginUser.Birthday = [dic objectForKey:@"Birthday"];
                        loginUser.Pic = [dic objectForKey:@"Pic"];
                        loginUser.Address = [dic objectForKey:@"Address"];
                        loginUser.Judge_user_account = [dic objectForKey:@"Judge_user_account"];
                        loginUser.SelfSign = [dic objectForKey:@"SelfSign"];
                        loginUser.BabyName = [dic objectForKey:@"BabyName"];
                        
                        //缓存起来
                        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
                        [defaults setObject:resultJson forKey:@"resultJSON"];
                        [defaults setInteger:loginUser.ID forKey:@"userid"];
                        [defaults setObject:loginUser.Name forKey:@"curName"];
                        [defaults setObject:loginUser.BabyName forKey:@"BabyName"];
                        [defaults setObject:loginUser.Age forKey:@"Age"];
                        [defaults setObject:loginUser.Account forKey:@"Account"];
                        [defaults setObject:loginUser.Judge_user_account forKey:@"Judge_user_account"];
                        
                        [self hideHUD];
                        [self performSegueWithIdentifier:@"segue_login" sender:self];
                    }
                }
            }
            
        }
        else
        {
            NSString *strReqMsg = [returnValue objectForKey:@"Message"];
            [self hideHUD];
            [self showHUDdelayHideWithMessage:strReqMsg];
        }
    }
}

-(void)errorCodeWithDic:(id) errorCode
{
    [MBProgressHUD hideHUDForView:self.view animated:TRUE];
}

-(void)netFailure
{
    [MBProgressHUD hideHUDForView:self.view animated:TRUE];
}

-(void)showHUDdelayHideWithMessage:(NSString *)msg
{
    mLoadView =[[MBProgressHUD alloc] init];
    [self.view addSubview:mLoadView];
    mLoadView.mode = MBProgressHUDModeCustomView;
    mLoadView.labelText = msg;
    [mLoadView show:YES];
    [mLoadView hide:YES afterDelay:1];
    mLoadView = nil;
}

-(void)showHUDWithMessage:(NSString *)msg
{
    mLoadView =[[MBProgressHUD alloc] init];
    [self.view addSubview:mLoadView];
    [self.view bringSubviewToFront:mLoadView];
    mLoadView.delegate = self;
    mLoadView.labelText = msg;
    [mLoadView show:YES];
}

-(void)hideHUD
{
    if (mLoadView)
    {
        [mLoadView removeFromSuperview];
        mLoadView = nil;
    }
}

- (void)hudWasHidden:(MBProgressHUD *)hud
{
    [self hideHUD];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/


- (IBAction)touchView:(id)sender {
    [self.view endEditing:YES];
}
@end
