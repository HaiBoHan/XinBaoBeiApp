//
//  UIColor+AddByDong.m
//  AirportMaster
//
//  Created by Dong on 14/10/29.
//  Copyright (c) 2014年 aero-com. All rights reserved.
//

#import "UIColor+AddByDong.h"

@implementation UIColor (AddByDong)
+ (UIColor *)randomColor
{
    UIColor *color=[UIColor colorWithRed:arc4random()%255/255.0f green:arc4random()%255/255.0f blue:arc4random()%255/255.0f alpha:1];
    return color;
}
@end
