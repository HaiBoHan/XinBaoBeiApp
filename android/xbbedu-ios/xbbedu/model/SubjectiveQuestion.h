//
//  SubjectiveQuestion.h
//  xbbedu
//
//  Created by mz on 15/4/25.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SubjectiveQuestion : NSObject

@property (nonatomic,assign)int ID;
@property (nonatomic,strong)NSString *KeyWords;
@property (nonatomic,strong)NSArray *Childs;

@end
