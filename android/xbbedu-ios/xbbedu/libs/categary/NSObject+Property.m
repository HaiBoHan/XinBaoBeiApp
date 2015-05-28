//
//  NSObject+Property.m
//  xbbedu
//
//  Created by mz on 15/4/21.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "NSObject+Property.h"
#import <objc/runtime.h>

@implementation NSObject (Property)

#pragma mark--Public method

-(void)setDataDictionary:(NSDictionary *)dataDictionary
{
    [self setAttributes:dataDictionary obj:self];
}
-(NSDictionary*)dataDictionary
{
    //获取属性列表
    NSArray *properties = [self propertyNames:[self class]];
    //根据属性列表获取属性值
    return [self propertiesAndValuesDictionary:self properties:properties];
}

#pragma mark--- Private method
//通过属性名字拼凑 setter 方法
-(SEL)getSetterSelWithAttibuteName:(NSString *)attributeName
{
    NSString *captial = [[attributeName substringToIndex:1]uppercaseString];
    NSString *setterSelStr = [NSString stringWithFormat:@"set%@%@:",captial,[attributeName substringFromIndex:1]];
    return NSSelectorFromString(setterSelStr);
}
//通过字典设置属性
-(void)setAttributes:(NSDictionary *)dataDic obj:(id)obj
{
    //获取所有的key值
    NSEnumerator *keyEnum = [dataDic keyEnumerator];
    //字典的key值（与model的属性值一一对应）
    id attrbuteName = nil;
    while (attrbuteName = [keyEnum nextObject]) {
        //获取 拼凑的setter方法
        SEL sel = [obj getSetterSelWithAttibuteName:attrbuteName];
        //验证setter方法是否能回应
        if ([obj respondsToSelector:sel]) {
            id value = nil;
            id tmpValue = dataDic[attrbuteName];
            if ([tmpValue isKindOfClass:[NSNull class]]) {
                //如果是NSNull 类型，则 value值为空
                value = nil;
            }
            else
            {
                value = tmpValue;
            }
            //执行setter 方法
            [obj performSelectorOnMainThread:sel withObject:value waitUntilDone:[NSThread isMainThread]];
        }
    }
}
//获取一个类的属性名字列表
-(NSArray *)propertyNames:(Class)class
{
    NSMutableArray *propertyNames = [[NSMutableArray alloc]init];
    unsigned int propertyCount = 0;
    objc_property_t *properties = class_copyPropertyList(class, &propertyCount);
    for (unsigned int i =0; i<propertyCount; ++i) {
        objc_property_t property = properties[i];
        const char *name = property_getName(property);
        [propertyNames addObject:[NSString stringWithUTF8String:name]];
    }
    free(properties);
    return propertyNames;
}
//根据属性数组获取该属性的值
-(NSDictionary*)propertiesAndValuesDictionary:(id)obj properties:(NSArray *)properties
{
    NSMutableDictionary *propertiesValueDic = [NSMutableDictionary dictionary];
    for (NSString *property in properties) {
        SEL getSel =NSSelectorFromString(property);
        if ([obj respondsToSelector:getSel]) {
            NSMethodSignature *signature = nil;
            signature = [obj methodSignatureForSelector:getSel];
            NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:signature];
            [invocation setTarget:obj];  
            [invocation setSelector:getSel];  
            NSObject *__unsafe_unretained valueObj = nil;  
            [invocation invoke];  
            [invocation getReturnValue:&valueObj];  
            //assign to @"" string  
            if (valueObj == nil) {  
                valueObj = @"";  
            }  
            propertiesValueDic[property] = valueObj;  
        }  
    }  
    return propertiesValueDic;  
}

@end
