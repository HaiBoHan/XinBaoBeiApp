//
//  Login.m
//  xbbedu
//
//  Created by mz on 15/4/21.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "Login.h"

@implementation Login

-(Login *)initWithDictionary:(NSDictionary *)dic
{
    if(self = [super init])
    {
        self.IsSuccess = [dic objectForKey:@"IsSuccess"];
        self.Message = [dic objectForKey:@"Message"];
        self.ResultJson = [dic objectForKey:@"ResultJson"];
    }
    
    return self;
}

@end
