//
//  Login.h
//  xbbedu
//
//  Created by mz on 15/4/21.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Login : NSObject

@property(nonatomic,strong)NSString *ResultJson;
@property(nonatomic)BOOL IsSuccess;
@property(nonatomic,strong)NSString *Message;

-(Login *)initWithDictionary:(NSDictionary *)dic;

@end
