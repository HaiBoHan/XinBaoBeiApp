//
//  NSString+JSON.m
//  AirPreparation
//
//  Created by Dong on 14-7-11.
//  Copyright (c) 2014年 aero-com. All rights reserved.
//

#import "NSString+JSON.h"
#import "CommonCrypto/CommonDigest.h"

@implementation NSString (JSON)
-(NSMutableDictionary *)jsonValue {
    
    NSData *data=[self dataUsingEncoding:NSUTF8StringEncoding];
    NSError *er;
    NSMutableDictionary *dic= [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:&er];
    NSLog(@"%@",er);
    return dic;
}

-(NSMutableDictionary *)jsonValueWithData:(NSData *)data{
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
    
//    NSMutableDictionary *dic1 = [[NSMutableDictionary alloc] initWithDictionary:dic];
//    NSArray *arr = [dic1 allKeys];
//    for (int i = 0; i<arr.count; i++) {
//        if ([[dic1 objectForKey:[arr objectAtIndex:i]] isKindOfClass:[NSNumber class]]) {
//            [dic1 setValue:[NSString stringWithFormat:@"%d",[[dic1 objectForKey:[arr objectAtIndex:i]] intValue]] forKey:[arr objectAtIndex:i]];
//        }
//        if ([[dic1 objectForKey:[arr objectAtIndex:i]] isKindOfClass:[NSNull class]]) {
//            [dic1 setValue:@"" forKey:[arr objectAtIndex:i]];
//        }
//
//    }
    return [[NSMutableDictionary alloc] initWithDictionary:dic];
}
//由数组获取日期字符串格式
- (NSString *)dataValueWithArray:(NSArray*)dataArray
{
    NSString *str=[[NSString alloc]init];

    return str;
}
//修改此处的md5 为16位的md5
-(NSString *) md5
{
    const char *cStr = [self UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    CC_MD5(cStr, strlen(cStr), result);
    NSString *str=[[NSString stringWithFormat:
                   @"%02X%02X%02X%02X%02X%02X%02X%02X",
                   result[4], result[5], result[6], result[7],
                    result[8], result[9], result[10], result[11]]lowercaseString];
    
    return str;
    return [[NSString stringWithFormat:@"%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X",
             result[0], result[1], result[2], result[3],
             result[4], result[5], result[6], result[7],
             result[8], result[9], result[10], result[11],
             result[12], result[13], result[14], result[15]
             ] lowercaseString];
}
-(NSString *) myMd5
{
    const char *cStr = [self UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    CC_MD5(cStr, strlen(cStr), result);
    NSString *str=[[NSString stringWithFormat:
                    @"%02X%02X%02X%02X%02X%02X%02X%02X",
                    result[4], result[5], result[6], result[7],
                    result[8], result[9], result[10], result[11]]lowercaseString];
    
    return str;
}




@end
