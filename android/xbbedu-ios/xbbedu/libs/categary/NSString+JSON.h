//
//  NSString+JSON.h
//  AirPreparation
//
//  Created by Dong on 14-7-11.
//  Copyright (c) 2014年 aero-com. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (JSON)
-(NSMutableDictionary *)jsonValue;
//由数组获取日期字符串格式
- (NSString *)dataValueWithArray:(NSArray*)dataArray;
-(id)JSONValue;

//-(NSMutableDictionary *)jsonValueWithData:(NSData *)data;
-(NSString *) md5;
@end
