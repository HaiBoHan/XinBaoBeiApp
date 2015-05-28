//
//  YuerDetailViewController.m
//  xbbedu
//
//  Created by mz on 15/4/24.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "YuerDetailViewController.h"

@implementation YuerDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.lblTitle.text = self.strTitle;
    self.txtContent.text = self.strContent;
}

- (IBAction)btnBack:(id)sender {
    [self dismissViewControllerAnimated:TRUE completion:nil];
}

@end
