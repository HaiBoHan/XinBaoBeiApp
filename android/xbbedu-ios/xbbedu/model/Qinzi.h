//
//  Qinzi.h
//  xbbedu
//
//  Created by mz on 15/4/22.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MQinzi : NSObject

@property(nonatomic)int ID;
@property(nonatomic,strong)NSString *Stage;
@property(nonatomic,strong)NSString *AboutAge;
@property(nonatomic,strong)NSString *Content;

-(MQinzi *)initWithDictionary:(NSDictionary *)dic;

@end
