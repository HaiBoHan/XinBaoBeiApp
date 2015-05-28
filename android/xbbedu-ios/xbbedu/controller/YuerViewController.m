//
//  QinziViewController.m
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "YuerViewController.h"
#import "NetRequestClass.h"
#import "Qinzi.h"
#import "Login.h"
#import "UserInfo.h"
#import "Message.h"
#import "HUDUtil.h"

@interface YuerViewController ()
{
    NSMutableArray *recipes;
    int page;
}

@property(nonatomic, strong) NSTimer *timer;

@end

@implementation YuerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    CGFloat imageW = self.scrollview.frame.size.width;
    CGFloat imageH = self.scrollview.frame.size.height;
    CGFloat imageY = 0;
    NSInteger totalCount = 4;
    for (int i = 0; i < totalCount; i++) {
        UIImageView *imageView = [[UIImageView alloc] init];
        CGFloat imageX = i * imageW;
        imageView.frame = CGRectMake(imageX, imageY, imageW, imageH);
        NSString *name = [NSString stringWithFormat:@"new_title_hold%d", i + 1];
        imageView.image = [UIImage imageNamed:name];
        self.scrollview.showsHorizontalScrollIndicator = YES;
        
        [self.scrollview addSubview:imageView];
    }
    
    //创建书页控件
    UIPageControl *pageControl = [[UIPageControl alloc]init];
    pageControl.frame = CGRectMake( SCREEN_WIDTH/2, 190, 120, 20);
    pageControl.numberOfPages = totalCount;
    pageControl.currentPage = 0;
    pageControl.tag = 100;
    [self.view addSubview:pageControl];
    
    page = 0;
    
    //设置scrollView的代理为当前类对象
    self.scrollview.delegate = self;
    
    //添加定时器,使用scheuled方法创建的定时器,不需要用fird方法打开(自动开启的)
    //多线程启动定时器
//    [NSThread detachNewThreadSelector:@selector(addtimer) toTarget:self withObject:nil];
    
    [self addtimer];
    
    CGFloat contentW = totalCount *imageW;
    //不允许在垂直方向上进行滚动
    self.scrollview.contentSize = CGSizeMake(contentW, 0);
    self.scrollview.pagingEnabled = YES;
    
    NSUserDefaults *defaultUserInfo =[NSUserDefaults standardUserDefaults];
    NSString *strParentName = [defaultUserInfo stringForKey:@"curName"];
//  NSString *strChildName = [defaultUserInfo stringForKey:@"BabyName"];
    NSString *strAge = [defaultUserInfo stringForKey:@"Age"];
    
    self.lblParentDesc.text = [NSString stringWithFormat:@"%@家长",strParentName];
    self.lblChildDesc.text = [NSString stringWithFormat:@"您的宝宝已经%@",strAge];
    [self.view addSubview:self.lblChildDesc];
    [self.view addSubview:self.lblParentDesc];
    [self.view addSubview:self.imgView];
    [self loadTableViewData];
    
    //0428:没有修改过个人资料的，弹出提示框
    if ((nil == strParentName || strParentName.length < 1)
        || (nil == strAge || strAge.length < 1)) {
        [self alertWithMessage:@"为了更好的服务与宝宝，请尽快完善资料！" andTitle:@"您好"];
    }
 
}

//- (void)viewWillAppear:(BOOL)animated
//{
//    [super viewWillAppear:animated];
//    
//    //强制横屏
//    if ([[UIDevice currentDevice] respondsToSelector:@selector(setOrientation:)]) {
//        NSInteger a = UIInterfaceOrientationMaskPortrait;
//        id org = @(a);
//        [[UIDevice currentDevice] performSelector:@selector(setOrientation:)
//                                       withObject:org];
//    }
//}

//添加定时器方法
-(void) addtimer{
    _timer = [NSTimer scheduledTimerWithTimeInterval:5.0 target:self selector:@selector(nextPage) userInfo:nil repeats:YES];
    
    //返回当前的消息循环对象
    [[NSRunLoop currentRunLoop] addTimer:self.timer forMode:NSRunLoopCommonModes];
}

//删除定时器方法
-(void) deleteTimer{
    [_timer invalidate];
    _timer = nil;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(void)loadTableViewData{
    if (!recipes) {
        recipes = [[NSMutableArray alloc]init];
    }
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *strUserId = [defaults stringForKey:@"userid"];
    NSDictionary *parameter = @{@"userid":strUserId
                                };
    
    [NetRequestClass NetRequestGETWithRequestURL:API_Message
                                   WithParameter:parameter WithReturnValeuBlock:^(id returnValue) {
                                       DDLog(@"%@", returnValue);
                                       [self fetchValueSuccessWithDic:returnValue];}
                              WithErrorCodeBlock:^(id errorCode) {
                                  DDLog(@"%@", errorCode);
                                  [self errorCodeWithDic:errorCode];}
                                WithFailureBlock:^{
                                    [self netFailure];
                                    DDLog(@"网络异常");}];
}

-(void)fetchValueSuccessWithDic:(NSDictionary *) returnValue
{
    if(returnValue != nil)
    {
        //请求成功
        if ([[returnValue objectForKey:@"IsSuccess"] intValue] == 1) {
            NSString *resultJson = [returnValue objectForKey:@"ResultJson"];
            
            
            if(resultJson != nil && resultJson.length > 0)
            {
                NSArray *qinziList = [resultJson JSONValue];
                for (NSDictionary *dic in qinziList) {
                    if (dic) {
                        Message *msg = [Message new];
                        msg.ID = [[dic objectForKey:@"ID"] intValue];
                        msg.User_ID = [dic objectForKey:@"User_ID"];
                        msg.Poster_ID = [dic objectForKey:@"Poster_ID"];
                        msg.Message_Title = [dic objectForKey:@"Message_Title"];
                        msg.Message_Content = [dic objectForKey:@"Message_Content"];
                        msg.Subhead = [dic objectForKey:@"Subhead"];
                        msg.MessDate = [dic objectForKey:@"MessDate"];
                        msg.IsRead = [dic objectForKey:@"IsRead"];
                        
                        [recipes addObject:msg];
                    }
                }
            }
            [self.tableview reloadData];
            
            DDLog(@"[[UIScreen mainScreen] currentMode].size.height) == @%f",[[UIScreen mainScreen] currentMode].size.height);
            DDLog(@"[[UIScreen mainScreen] currentMode].size.width) == @%f",[[UIScreen mainScreen] currentMode].size.width);
            
            if (isRetina) {
                self.tableview.frame = CGRectMake(self.tableview.frame.origin.x, self.tableview.frame.origin.y, self.tableview.frame.size.width, self.tableview.frame.size.height - 88);
            }
        }
    }
}

-(void)errorCodeWithDic:(id) errorCode
{
//    [MBProgressHUD hideHUDForView:self.view animated:TRUE];
}

-(void)netFailure
{
//    [MBProgressHUD hideHUDForView:self.view animated:TRUE];
}



- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [recipes count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    //不用XIB的写法
    YuerCellTableViewCell *cell1 = [tableView dequeueReusableCellWithIdentifier:@"RecipeCell"];
    
    if (recipes == nil || recipes.count < 1) {
        cell1.lblTbTitle.text = @"";
        cell1.lblTbDate.text = @"";
        cell1.lblTbContent.text=@"";
    }
    else
    {
        Message *tmp = [recipes objectAtIndex:indexPath.row];
        cell1.lblTbTitle.text = tmp.Message_Title;
        cell1.lblTbDate.text = tmp.MessDate;
        cell1.lblTbContent.text = tmp.Message_Content;
    }
    return cell1;
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 75;
}


#pragma  - mark UIScrollViewDelegate
//监听滚动的位置,改变pageCotrol的currentPage的值.
-(void)scrollViewDidScroll:(UIScrollView *)scrollView{
    
    UIPageControl *pControl = (UIPageControl *)[self.view viewWithTag:100];
    CGFloat scrollW = scrollView.frame.size.width;
    
    page = (scrollView.contentOffset.x + scrollW * 0.5 )/ scrollW;
    pControl.currentPage = page;
}

-(void)scrollViewWillBeginDragging:(UIScrollView *)scrollView{
    [self deleteTimer];
}

-(void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate{
    [self addtimer];
}

-(void)nextPage{
    
//    [self performSelectorOnMainThread:@selector(handleSwitch) withObject:nil waitUntilDone:YES];

    [self handleSwitch];
}

-(void)handleSwitch
{
    UIPageControl *pControl = (UIPageControl *)[self.view viewWithTag:100];
    if (pControl.currentPage == 3) {
        page = 0;
    }else{
        page = pControl.currentPage + 1;
    }
    pControl.currentPage = page;
    
    //计算滚动的位置
    CGFloat offsetX = page * self.scrollview.frame.size.width;
    CGPoint offset = CGPointMake(offsetX, 0);
    [self.scrollview setContentOffset:offset animated:YES];
}

#pragma mark - Navigation

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    if ([sender isKindOfClass:[UIButton class]])
        return;
    
    NSIndexPath *indexPath = [self.tableview indexPathForCell:sender];
    
    id page2 = segue.destinationViewController;
    if (recipes == nil || recipes.count < 1) {
        [page2 setValue:@"" forKey:@"strTitle"];
        [page2 setValue:@"" forKey:@"strContent"];
    }
    else
    {
        Message *msg = [recipes objectAtIndex:indexPath.row];
        if (msg) {
            [page2 setValue:msg.Message_Title forKey:@"strTitle"];
            [page2 setValue:msg.Message_Content forKey:@"strContent"];
        }
        else
        {
            [page2 setValue:@"" forKey:@"strTitle"];
            [page2 setValue:@"" forKey:@"strContent"];
        }
    }
}


@end
