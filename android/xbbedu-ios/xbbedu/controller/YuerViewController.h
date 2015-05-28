//
//  QinziViewController.h
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "YuerCellTableViewCell.h"
#import "BaseViewController.h"

@interface YuerViewController : BaseViewController<UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, weak) IBOutlet UIScrollView *scrollview;
@property (nonatomic, weak) IBOutlet UIPageControl *pageControl;
@property (nonatomic, weak) IBOutlet UILabel *lblParentDesc;
@property (nonatomic, weak) IBOutlet UILabel *lblChildDesc;
@property (nonatomic, weak) IBOutlet UIImageView *imgView;
@property (nonatomic, weak) IBOutlet YuerCellTableViewCell *cell;
@property (nonatomic, weak) IBOutlet UITableView *tableview;

@end
