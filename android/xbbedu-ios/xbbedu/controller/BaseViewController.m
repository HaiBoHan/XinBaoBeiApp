//
//  BaseViewController.m
//  xbbedu
//
//  Created by mz on 15/4/29.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "BaseViewController.h"

@interface BaseViewController ()
{
    UIAlertView * alertView;
}

@end

@implementation BaseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - alert as toast

- (void)alertWithMessage:(NSString *)message andTitle:(NSString *)title {
    alertView = [[UIAlertView alloc] initWithTitle:title
                                           message:message
                                          delegate:nil
                                 cancelButtonTitle:nil
                                 otherButtonTitles:nil, nil];
    
    [NSTimer scheduledTimerWithTimeInterval:3.0f
                                     target:self
                                   selector:@selector(dismissAlertView:)
                                   userInfo:nil
                                    repeats:NO];
    
    [alertView show];
}

- (void)dismissAlertView:(NSTimer*)timer {
    [alertView dismissWithClickedButtonIndex:0 animated:YES];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

#pragma mark - keyboardHight

//-(void)registerForKeyboardNotifications
//{
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWasShow:) name:UIKeyboardDidShowNotification object:nil];
//    
//    
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWasHidden:) name:UIKeyboardDidHideNotification object:nil];
//}
//
//-(void)keyboardWasShow
//{
//    
//}

@end
