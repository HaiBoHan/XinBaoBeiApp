//
//  HistoryQDetailViewController.m
//  xbbedu
//
//  Created by mz on 15/5/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "HistoryQDetailViewController.h"
#import "HistoryQuestion.h"

@interface HistoryQDetailViewController ()
@property (weak, nonatomic) IBOutlet UITextView *txt_detail;

@end

@implementation HistoryQDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    _txt_detail.text = _detail.Solution;
}
- (IBAction)btnBack:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
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
