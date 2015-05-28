//
//  SubjectiveViewController.m
//  xbbedu
//
//  Created by mz on 15/4/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "SubjectiveViewController.h"
#import "SubjectiveTableViewCell.h"
#import "NetRequestClass.h"
#import "SubjectiveQuestion.h"
#import "MJExtension.h"

@interface SubjectiveViewController ()
{
    NSMutableArray *recipes;
    NSString *strImgURL;
}


@end

@implementation SubjectiveViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //0.初始化一些值
    strImgURL = @"list_item_subjective_";
    
    //判断：如果没有权限，显示另外一个View
    //缓存取用户信息
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *strJudge = [defaults valueForKey:@"Judge_user_account"];
   
    if(strJudge == nil || strJudge.length == 0)
    {
        self.viewDisable.hidden = FALSE;
    }
    else
    {
        self.viewDisable.hidden = TRUE;
        //1.加载网络数据
        [self loadSubjectiveListData];
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}



#pragma loadData

-(void)loadSubjectiveListData
{
    if (!recipes) {
        recipes = [[NSMutableArray alloc]init];
    }
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *strUserId = [defaults stringForKey:@"userid"];
    NSDictionary *parameter = @{@"userid":strUserId
                                };
    [NetRequestClass NetRequestGETWithRequestURL:API_SUBJECTIVE
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
    if(returnValue != nil
       && ([[returnValue objectForKey:@"IsSuccess"] intValue] == 1))
    {
        NSString *resultJson = [returnValue objectForKey:@"ResultJson"];
        if(resultJson != nil && resultJson.length > 0)
        {
            NSArray *qinziList = [resultJson JSONValue];
            for (NSDictionary *dic in qinziList) {
                if (dic) {
                    SubjectiveQuestion *question = [SubjectiveQuestion new];
                    question = [SubjectiveQuestion objectWithKeyValues:dic];
                    [recipes addObject:question];
                }
            }
            
            [self.tableview reloadData];
        }
    }
}

-(void)errorCodeWithDic:(id) errorCode
{
}
-(void)netFailure
{
}

#pragma TabelView handlers
//0. 返回行数
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [recipes count];
}

//1. 构建CELL数据
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    //不用XIB的写法
    SubjectiveTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"SubjectiveReCell"];
    
    
    int index = 1;
    NSInteger position = indexPath.row;
    if(position > 0)
    {
        int r = position % 6;
        if(r == 0)
            index = 7;
        else
            index = r + 1;
    }
    
    if (recipes == nil || recipes.count < 1) {
        cell.lblTitle.text = @"";
    }
    else
    {
        SubjectiveQuestion *tmp = [recipes objectAtIndex:indexPath.row];
        cell.imgTitle.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@%ld",strImgURL,(long)index]];
        cell.lblTitle.text = tmp.KeyWords;

    }
    return cell;
}

//2. 改变下行宽吧
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 40;
}


#pragma mark - Navigation

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    id page2 = segue.destinationViewController;
    
    if ([segue.identifier isEqualToString:@"showHistoryQ"]) {
    }
    else
    {
        NSIndexPath *indexPath = [self.tableview indexPathForCell:sender];
    
        if (recipes == nil || recipes.count < 1) {
            [page2 setValue:@"" forKey:@"strTitle"];
            [page2 setValue:@"" forKey:@"strContent"];
        }
        else
        {
            SubjectiveQuestion *question = [recipes objectAtIndex:indexPath.row];
            if (question) {
                [page2 setValue:question forKey:@"question"];
            }
            else
            {
                [page2 setValue:nil forKey:@"question"];
            }
        }
    }
}


@end
