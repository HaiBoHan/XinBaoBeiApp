//
//  Message.h
//  xbbedu
//
//  Created by mz on 15/4/23.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Message : NSObject

@property (nonatomic,assign)int ID;
@property (nonatomic,assign)int User_ID;
@property (nonatomic,assign)int Poster_ID;
@property (nonatomic,assign)BOOL IsRead;
@property (nonatomic,strong)NSString *Message_Title;
@property (nonatomic,strong)NSString *Message_Content;
@property (nonatomic,strong)NSString *MessDate;
@property (nonatomic,strong)NSString *Subhead;

@end
