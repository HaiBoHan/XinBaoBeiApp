//
//  SubjectiveDetailVewController.h
//  xbbedu
//
//  Created by mz on 15/4/25.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <UIKit/UIKit.h>
@class SubjectiveQuestion;

@interface SubjectiveDetailVewController : UIViewController

@property (strong,nonatomic) SubjectiveQuestion *question;
@property (strong,nonatomic) NSArray *subQuestion;
@property (strong,nonatomic) NSArray *subSubQuestion;
- (IBAction)btnBack:(id)sender;
@end
