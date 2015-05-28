//
//  UserInfo.h
//  xbbedu
//
//  Created by mz on 15/4/21.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UserInfo : NSObject

@property(nonatomic)int ID;
@property(nonatomic,strong)NSString *Account;
@property(nonatomic,strong)NSString *Name;
@property(nonatomic,strong)NSString *Passwd;
@property(nonatomic,strong)NSString *Sex;
@property(nonatomic,strong)NSString *Age;
@property(nonatomic,strong)NSString *Birthday;
@property(nonatomic,strong)NSString *Region;
@property(nonatomic,strong)NSString *Address;
@property(nonatomic,strong)NSString *SelfSign;
@property(nonatomic,strong)NSString *Pic;
@property(nonatomic,strong)NSString *Branch_ID;
@property(nonatomic,strong)NSString *Branch_Name;
@property(nonatomic,strong)NSString *Unit_id;
@property(nonatomic,strong)NSString *SysVersion;
@property(nonatomic,strong)NSString *HintMessage;
@property(nonatomic,strong)NSString *BabyName;
@property(nonatomic,strong)NSString *Judge_user_account;

-(UserInfo *)initWithDictionary:(NSDictionary *)dic;

@end
