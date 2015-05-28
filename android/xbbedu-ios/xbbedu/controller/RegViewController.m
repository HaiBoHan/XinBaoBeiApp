//
//  RegViewController.m
//  xbbedu
//
//  Created by mz on 15/4/27.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "RegViewController.h"
#import "MBProgressHUD.h"

@interface RegViewController ()<MBProgressHUDDelegate,  UITextFieldDelegate>
{
    MBProgressHUD *mLoadView;
}
@property (weak, nonatomic) IBOutlet UITextField *account;
@property (weak, nonatomic) IBOutlet UITextField *telno;
@property (weak, nonatomic) IBOutlet UIButton *btnSubmit;
@property (weak, nonatomic) IBOutlet UITextField *pwd1;
@property (weak, nonatomic) IBOutlet UIButton *btnGetCode;
@property (weak, nonatomic) IBOutlet UISwitch *cbxAgree;
@property (weak, nonatomic) IBOutlet UITextField *pwd2;
- (IBAction)submit:(id)sender;
- (IBAction)btnBack:(id)sender;
- (IBAction)onclick_btnGetCode:(id)sender;

@end

@implementation RegViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    
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

- (IBAction)btnBack:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    if (![self validateNumber:string])
        return NO;
    
    if ([string isEqualToString:@"\n"])
    {
        return YES;
    }
    NSString * toBeString = [textField.text stringByReplacingCharactersInRange:range withString:string];
    
    if (self.telno == textField)
    {
        if ([toBeString length] > 11) {
            textField.text = [toBeString substringToIndex:11];
            return NO;
        }
    }
    return YES;
}

- (BOOL)validateNumber:(NSString*)number {
    BOOL res = YES;
    NSCharacterSet* tmpSet = [NSCharacterSet characterSetWithCharactersInString:@"0123456789"];
    int i = 0;
    while (i < number.length) {
        NSString * string = [number substringWithRange:NSMakeRange(i, 1)];
        NSRange range = [string rangeOfCharacterFromSet:tmpSet];
        if (range.length == 0) {
            res = NO;
            break;
        }
        i++;
    }
    return res;
}

- (IBAction)onclick_btnGetCode:(id)sender
{
    if (self.telno == nil || self.telno.text.length < 1) {
        [self showMessage:@"手机号不能为空"];
        return;
    }
    
    NSDictionary *parameter = @{@"PhoneNumber": _telno.text};
    
    [NetRequestClass NetRequestGETWithRequestURL:API_GETVERITYCODE
                                   WithParameter:parameter WithReturnValeuBlock:^(id returnValue) {
                                       DDLog(@"%@", returnValue);
                                       [self showHUDdelayHideWithMessage:@"获取验证码成功！"];}
                              WithErrorCodeBlock:^(id errorCode) {
                                  DDLog(@"%@", errorCode);
                                  [self showMessage:@"获取验证码，请重试！"];}
                                WithFailureBlock:^{
                                    [self showMessage:@"获取验证码，请重试！"];}];
}

- (IBAction)submit:(id)sender {
    if (_cbxAgree.on == NO) {
        [self showMessage:@"请同意条款"];
        return;
    }
    
    if (self.account == nil || self.account.text.length < 1) {
        [self showMessage:@"验证码不能为空"];
        return;
    }
    
    if (self.telno == nil || self.telno.text.length < 1) {
        [self showMessage:@"手机号不能为空"];
        return;
    }
    
    if (self.pwd1 == nil || self.pwd1.text.length < 1) {
        [self showMessage:@"密码不能为空"];
        return;
    }
    
    if ([self.pwd1.text isEqualToString:self.pwd2.text] == false)
    {
        [self showMessage:@"密码不一致，请重新输入！"];
        return;
    }
    
    [self showHUDWithMessage:@"提交中，请稍等..."];
    
    
    NSString *strParams = [NSString stringWithFormat:@"{\"pwd\":\"%@\",\"account\":\"%@\",\"telno\":\"%@\"}",self.pwd1.text,self.telno.text,self.telno.text];
    
    NSDictionary *parameter = @{@"user": strParams,@"IdentifyCode":_account.text};
    
    [NetRequestClass NetRequestGETWithRequestURL:API_USERREG
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
            //            NSString *resultJson = [returnValue objectForKey:@"ResultJson"];
            //缓存起来
            NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
            [defaults setObject:self.telno.text forKey:@"Account"];
            
            [self dismissViewControllerAnimated:TRUE completion:nil];
        }
        else
        {
            NSString *strReqMsg = [returnValue objectForKey:@"Message"];
            [self hideHUD];
            [self showMessage:strReqMsg];
        }
    }
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

-(void)errorCodeWithDic:(id) errorCode
{
    [self showHUDWithMessage:@"服务器异常，请稍候重试！"];
    [MBProgressHUD hideHUDForView:self.view animated:TRUE];
}

-(void)netFailure
{
    [self showHUDWithMessage:@"服务器异常，请稍候重试！"];
    [MBProgressHUD hideHUDForView:self.view animated:TRUE];
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

-(void)showMessage:(NSString *)msg
{
    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"提示" message:msg delegate:nil cancelButtonTitle:@"知道了" otherButtonTitles:nil];
    
    [alert show];
}
@end
