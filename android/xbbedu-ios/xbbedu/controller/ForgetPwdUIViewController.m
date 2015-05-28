//
//  ForgetPwdUIViewController.m
//  xbbedu
//
//  Created by mz on 15/5/22.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "ForgetPwdUIViewController.h"

@interface ForgetPwdUIViewController ()
@property (weak, nonatomic) IBOutlet UIButton *btnBack;
- (IBAction)onclick_btnBack:(id)sender;

@end

@implementation ForgetPwdUIViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (IBAction)onclick_btnBack:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}
@end
