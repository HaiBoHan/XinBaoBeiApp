//
//  SubjectiveHistoryViewController.h
//  xbbedu
//
//  Created by mz on 15/5/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "BaseViewController.h"

@interface SubjectiveHistoryViewController : BaseViewController<UITableViewDelegate, UITableViewDataSource>

@property (weak, nonatomic) IBOutlet UITableView *tableView;

@end
