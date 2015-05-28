//
//  SubjectiveHistoryViewController.m
//  xbbedu
//
//  Created by mz on 15/5/18.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "SubjectiveHistoryViewController.h"
#import "HistoryQuestion.h"
#import "MJExtension.h"
#import "SubjectiveHistoryTableViewCell.h"

@interface SubjectiveHistoryViewController ()
{
    NSMutableArray *recipes;
}
- (IBAction)btnBack:(id)sender;

@end

@implementation SubjectiveHistoryViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self loadHistoryData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) loadHistoryData
{
    if (!recipes) {
        recipes = [[NSMutableArray alloc]init];
    }
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *strUserId = [defaults stringForKey:@"userid"];
    NSDictionary *parameter = @{@"userid":strUserId
                                };
    [NetRequestClass NetRequestGETWithRequestURL:API_HISTORY
                                   WithParameter:parameter WithReturnValeuBlock:^(id returnValue) {
                                       DDLog(@"%@", returnValue);
                                       [self fetchValueSuccessWithDic:returnValue];
                                       }
                              WithErrorCodeBlock:^(id errorCode) {
                                    DDLog(@"%@", errorCode);
                                  }
                                WithFailureBlock:^{
                                    DDLog(@"网络异常");
                                }];
}

-(void)fetchValueSuccessWithDic:(NSDictionary *) returnValue
{
    if(returnValue != nil
       && ([[returnValue objectForKey:@"IsSuccess"] boolValue] == YES))
    {
        NSString *resultJson = [returnValue objectForKey:@"ResultJson"];
        if(resultJson != nil && resultJson.length > 0)
        {
            NSArray *qinziList = [resultJson JSONValue];
            for (NSDictionary *dic in qinziList) {
                if (dic) {
                    HistoryQuestion *question = [HistoryQuestion new];
                    question = [HistoryQuestion objectWithKeyValues:dic];
                    if (question.Solution.length > 0) {
                        [recipes addObject:question];
                }
            }
            
            [self.tableView reloadData];
        }
    }
}
}

#pragma mark - TableView Methods

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [recipes count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    //不用XIB的写法
    SubjectiveHistoryTableViewCell *cell1 = [tableView dequeueReusableCellWithIdentifier:@"historyQCell"];
    
    if (recipes == nil || recipes.count < 1) {
        cell1.lbl_answer.text = @"";
        cell1.lbl_date.text = @"";
        cell1.lbl_keyword.text=@"";
    }
    else
    {
        HistoryQuestion *tmp = [recipes objectAtIndex:indexPath.row];
        cell1.lbl_answer.text = tmp.Solution;
        cell1.lbl_date.text = tmp.CreatedTime;
        cell1.lbl_keyword.text = tmp.KeyWords;
    }
    return cell1;
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 88;
}



#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    id page2 = [segue destinationViewController];
    
    NSIndexPath *indexPath = [self.tableView indexPathForCell:sender];
    
    
    if (recipes == nil || recipes.count < 1) {
        [page2 setValue:@"" forKey:@"detail"];
    }
    else
    {
        HistoryQuestion *question = [recipes objectAtIndex:indexPath.row];
        if (question) {
            [page2 setValue:question forKey:@"detail"];
        }
        else
        {
            [page2 setValue:nil forKey:@"detail"];
        }
    }
}

- (IBAction)btnBack:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}
@end

