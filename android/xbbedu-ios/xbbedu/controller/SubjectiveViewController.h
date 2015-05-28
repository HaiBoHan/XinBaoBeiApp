//
//  SubjectiveViewController.h
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SubjectiveTableViewCell.h"

@interface SubjectiveViewController : UIViewController


@property (nonatomic, weak) IBOutlet UITableView *tableview;
@property (nonatomic, weak) IBOutlet SubjectiveTableViewCell *tableCell;
@property (nonatomic, weak) IBOutlet UIView *viewDisable;



@end
