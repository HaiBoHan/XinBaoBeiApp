//
//  HistoryQuestion.h
//  xbbedu
//
//  Created by mz on 15/5/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface HistoryQuestion : NSObject

@property (nonatomic,copy) NSString *UserID;
@property (nonatomic,copy) NSString *UserAccount;
@property (nonatomic,copy) NSString *QuestionID;
@property (nonatomic,copy) NSString *KeyWords;
@property (nonatomic,copy) NSString *CreatedTime;
@property (nonatomic,copy) NSString *Age;
@property (nonatomic,copy) NSString *Solution;

@end
