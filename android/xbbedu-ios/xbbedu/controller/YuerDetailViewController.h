//
//  YuerDetailViewController.h
//  xbbedu
//
//  Created by mz on 15/4/24.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YuerDetailViewController : UIViewController

@property (nonatomic,strong) NSString *strTitle;
@property (nonatomic,strong) NSString *strContent;

@property (nonatomic,weak) IBOutlet UILabel *lblTitle;
@property (nonatomic,weak) IBOutlet UITextView *txtContent;
@property (nonatomic,weak) IBOutlet UIScrollView *svContent;

- (IBAction)btnBack:(id)sender;

@end
