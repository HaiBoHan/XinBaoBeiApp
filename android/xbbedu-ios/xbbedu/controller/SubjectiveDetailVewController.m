//
//  SubjectiveDetailVewController.m
//  xbbedu
//
//  Created by mz on 15/4/25.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "SubjectiveDetailVewController.h"
#import "SubjectiveQuestion.h"
#import "MJExtension.h"
#import "MBProgressHUD.h"

@interface SubjectiveDetailVewController ()<MBProgressHUDDelegate>
{
    int iRow;
    int iCurCol;
    
    int iMaxY;
    NSMutableArray *selectedSub;
    NSMutableArray *selectedSubSub;
    NSMutableArray *selectedSubSub_Id;
    
    NSString *submitKeyword_id;
    
    UIButton *btnSubmit;
    MBProgressHUD *mLoadView;
}
@property (weak, nonatomic) IBOutlet UILabel *lblTitle;
@property (weak, nonatomic) IBOutlet UILabel *lblSelectedKeyword;
//@property (weak, nonatomic) UIView *vLine;
//@property (weak, nonatomic) UILabel *lblSubSubKeyword;
@property (weak, nonatomic) IBOutlet UIScrollView *svMain;
@property (weak, nonatomic) IBOutlet UILabel *lblSubKeyword;

@end

@implementation SubjectiveDetailVewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //子，子子，两个分类
    selectedSub = [NSMutableArray new];
    selectedSubSub = [NSMutableArray new];
    selectedSubSub_Id = [NSMutableArray new];
    
    self.svMain.contentSize = CGSizeMake(SCREEN_WIDTH, SCREEN_HEIGHT - 140);
    
    //父分类（前一页）传来的值
    self.lblTitle.text = self.question.KeyWords;
    //选择结果Label 的 样式
    self.lblSelectedKeyword.layer.borderColor = [UIColor blackColor].CGColor;
    self.lblSelectedKeyword.layer.borderWidth = 1;
    self.lblSelectedKeyword.text = @"";
    
    //初始化子分类
    self.subQuestion = [SubjectiveQuestion objectArrayWithKeyValuesArray:self.question.Childs];
    [self initDataAndView:_subQuestion :30 :0 :1000];
    
    //最下面的关键字可以出现了：画面项（UILabel） + 一条线（UIView *vLine）
    CGRect rect = self.lblSubKeyword.frame;
    rect = CGRectMake(rect.origin.x, rect.origin.y + (iRow * 30 + 40), 230, 24);
    UILabel *lblSubSubKeyword = [[UILabel alloc] initWithFrame:rect];
    lblSubSubKeyword.text = @"问题关键字";
    [self.svMain addSubview:lblSubSubKeyword];
    //记录下最后的Y高度
    iMaxY = rect.origin.y + 30;
    rect = CGRectMake(rect.origin.x, iMaxY, 223, 1);
    UIView *vLine = [[UIView alloc]initWithFrame:rect];
    
    vLine.backgroundColor = [UIColor blackColor];
    [self.svMain addSubview:vLine];
}


-(void) initDataAndView:(NSArray *) array :(int) originalX :(int)originalY :(int) iBaseTagIndex
{
//    self.subQuestion = [SubjectiveQuestion objectArrayWithKeyValuesArray:self.question.Childs];
    int iX = originalX;
    int iY = originalY;
    int iHeight = 50;
    int iWidth = (SCREEN_WIDTH-50)/4;
    int iWitchPlus = iWidth + 10;
    int iHeightPlus = 30;
    iCurCol = 0;
    int iReQty = 0;
    iRow = 0;
    
    for (int i = 0;i<array.count; i++) {
        iReQty = (i + 1) % 4;
        iRow = i / 4 + 1;
        
        if (iReQty == 0)
            iCurCol = 4;
        else
            iCurCol = iReQty;
        int iRx = iX + ((iCurCol-1) * iWitchPlus);
        int iRy = iY + (iRow * iHeightPlus);
        SubjectiveQuestion *qTmp = [array objectAtIndex:i];
        [self createLabelWithText:qTmp.KeyWords :CGRectMake(iRx, iRy, iWidth, iHeight) :(iBaseTagIndex + i)];
        
        DDLog(@"row:%d  col:%d x:%d y:%d",iRow,iCurCol,iRx,iRy);
    }
}

-(void)createLabelWithText:(NSString *) strText :(CGRect) rect :(NSInteger)tag
{
    UILabel *label = [[ UILabel alloc] initWithFrame:rect];
    label.font = [UIFont boldSystemFontOfSize:14.0f];
    label.textAlignment = NSTextAlignmentLeft;
//    label.numberOfLines = 0;
    label.tag = tag;
    label.textColor = RGB(186, 194, 173);
    
    //事件响应
    label.userInteractionEnabled = YES;
    UITapGestureRecognizer *singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSingleTap:)];
    [label addGestureRecognizer:singleTap];
    
    //
//    CGSize size = [strText sizeWithFont:label.font constrainedToSize:CGSizeMake(label.frame.size.width, MAXFLOAT) lineBreakMode:NSLineBreakByWordWrapping];
//    //根据计算结果重新设置UILabel的尺寸
//    [label setFrame:CGRectMake(rect.origin.x, rect.origin.y, rect.size.width, size.height)];
    label.text = strText;
    
    
    //
    label.font = [UIFont systemFontOfSize:14];
    label.adjustsFontSizeToFitWidth = YES;
//    label.minimumFontSize = 6;
    
    
    [self.svMain addSubview:label];
}

- (void)handleSingleTap:(UIGestureRecognizer *)gestureRecognizer
{
    //点击事件处理
    UILabel *_lblAutoCreate=(UILabel*)gestureRecognizer.view;
    
    //第一部分的Keyword
    if (_lblAutoCreate && (_lblAutoCreate.tag > 999 && _lblAutoCreate.tag <2000)) {
        
        [self clearSubSubKeyword];
        
        //两个都清了，无所谓
        [selectedSub removeAllObjects];
        [selectedSubSub removeAllObjects];
        [selectedSubSub_Id removeAllObjects];
        
        submitKeyword_id = @"";
        
        //当前选择的加入
        [selectedSub addObject:_lblAutoCreate.text];
        
        int index = _lblAutoCreate.tag % 1000;
        
        SubjectiveQuestion *tmpSubsubQuestion = [self.subQuestion objectAtIndex:index];
        
        if (tmpSubsubQuestion) {
            self.subSubQuestion = [SubjectiveQuestion objectArrayWithKeyValuesArray:tmpSubsubQuestion.Childs];
            [self initDataAndView:self.subSubQuestion :20 :iMaxY-20 :2000];
            
            //重置scrollview contentsize
            self.svMain.contentSize = CGSizeMake(SCREEN_WIDTH, iMaxY + iRow*30 + 200);
        }
        self.lblSelectedKeyword.text = [self showSumTitle];
    }
    if (_lblAutoCreate && _lblAutoCreate.tag >= 2000) {
        
        //来个提交Button吧
        if (btnSubmit == nil) {
            btnSubmit = [UIButton buttonWithType:UIButtonTypeRoundedRect];           btnSubmit.frame = CGRectMake(SCREEN_WIDTH/2 - 15, iMaxY + iRow*30 + 40, 60, 30);
            [btnSubmit.layer setCornerRadius:5.0f];
            btnSubmit.tag = 555;
            [btnSubmit setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            [btnSubmit setTitleColor:[UIColor whiteColor] forState:UIControlStateHighlighted];
            [btnSubmit setTitle:@"提交" forState:UIControlStateNormal];
            [btnSubmit setTitle:@"提交" forState:UIControlStateHighlighted];
            [btnSubmit addTarget:self action:@selector(submitQuestion:) forControlEvents:UIControlEventTouchUpInside];
            btnSubmit.backgroundColor = [UIColor brownColor];
            [self.svMain addSubview:btnSubmit];
        }
        
        if ([selectedSubSub containsObject:_lblAutoCreate.text]) {
            [selectedSubSub removeObject:_lblAutoCreate.text];
            submitKeyword_id = @"";
        }
        else
        {
            if (selectedSubSub.count == 0)
            {
                [selectedSubSub addObject:_lblAutoCreate.text];
                NSInteger intId = ((SubjectiveQuestion *)[_subSubQuestion objectAtIndex:(_lblAutoCreate.tag - 2000)]).ID;
                submitKeyword_id = [NSString stringWithFormat:@"%ld",(long)intId];
            }
            else if (selectedSubSub.count == 1)
            {
                UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"提示" message:@"为确保专家为你解答问题的质量，仅限提交两个关键词。敬请谅解！" delegate:nil cancelButtonTitle:@"知道了" otherButtonTitles:nil];
                
                [alert show];
            }
        }
        self.lblSelectedKeyword.text = [self showSumTitle];
    }
}

-(void)submitQuestion:(UIGestureRecognizer *)gestureRecognizer
{
    BOOL isSubmitted = FALSE;
    //校验：
    //缓存下结果
    NSUserDefaults * defaults = [NSUserDefaults standardUserDefaults];
    NSDate *currentDate = [NSDate date];
    NSDate *lastSaveDate;
    NSDictionary *dicTimee = [defaults objectForKey:@"submitted"];
    lastSaveDate = [dicTimee objectForKey:@"time"];
    
    if (lastSaveDate != nil) {
        
        double timezoneFix = [NSTimeZone localTimeZone].secondsFromGMT;
        if (
            (int)(([currentDate timeIntervalSince1970] + timezoneFix)/(24*3600)) -
            (int)(([lastSaveDate timeIntervalSince1970] + timezoneFix)/(24*3600))
            == 0)
        {
            isSubmitted = YES;
        }
    }
    
    if (isSubmitted) {
      
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"温馨提示" message:@"每位用户一天只限一次提问！" delegate:nil cancelButtonTitle:@"知道了" otherButtonTitles:nil];
        
        [alert show];
        
        return;
    }
    
    
    //组织请求
    NSMutableString *strResultKeyword = [[NSMutableString alloc]init];
    [strResultKeyword appendString:@"\""];
    [strResultKeyword appendString:[selectedSubSub componentsJoinedByString:@"\",\""]];
    [strResultKeyword appendString:@"\""];
    
//    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *userid = [defaults valueForKey:@"userid"];
    NSString *age = [defaults valueForKey:@"Age"];
    NSString *strParams = [NSString stringWithFormat:@"{userid:%@,age:\"%@\",questionid:%@,questiontitle:\"%@\",keyword:[%@]}",userid,age,submitKeyword_id,self.question.KeyWords,strResultKeyword];
    
    DDLog(@"===主观问题提交的数据：：%@",strParams);
    
    [self showHUDWithMessage:@"提交中，请稍等..."];
    
    
    NSDictionary *parameter = @{@"question": strParams};
    
    [NetRequestClass NetRequestGETWithRequestURL:API_POSTSUBJECTIVE
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
            //缓存下结果
            NSUserDefaults * defaults = [NSUserDefaults standardUserDefaults];
            NSDate *currentDate = [NSDate date];
            NSDictionary *dicTimee = @{@"time":currentDate};
            [defaults setObject:dicTimee forKey:@"submitted"];
            
            [self dismissViewControllerAnimated:TRUE completion:nil];
        }
        else
        {
            NSString *strReqMsg = [returnValue objectForKey:@"Message"];
            [self hideHUD];
            [self showHUDdelayHideWithMessage:strReqMsg];
        }
    }
}

-(void)showHUDdelayHideWithMessage:(NSString *)msg
{
    mLoadView =[[MBProgressHUD alloc] init];
    [self.view addSubview:mLoadView];
    mLoadView.mode = MBProgressHUDModeCustomView;
    mLoadView.labelText = msg;
    [mLoadView show:YES];
    [mLoadView hide:YES afterDelay:1];
    mLoadView = nil;
}

-(void)errorCodeWithDic:(id) errorCode
{
    //[self showHUDWithMessage:@"服务器异常，请稍候重试！"];
    [MBProgressHUD hideHUDForView:self.view animated:TRUE];
}

-(void)netFailure
{
    [MBProgressHUD hideHUDForView:self.view animated:TRUE];
    [self showHUDWithMessage:@"服务器异常，请稍候重试！"];
}


-(void)showHUDWithMessage:(NSString *)msg
{
    mLoadView =[[MBProgressHUD alloc] init];
    [self.view addSubview:mLoadView];
    [self.view bringSubviewToFront:mLoadView];
    mLoadView.delegate = self;
    mLoadView.labelText = msg;
    [mLoadView show:YES];
}

-(void)hideHUD
{
    if (mLoadView)
    {
        [mLoadView removeFromSuperview];
        mLoadView = nil;
    }
}

- (void)hudWasHidden:(MBProgressHUD *)hud
{
    [self hideHUD];
}


-(void)clearSubSubKeyword
{
    for (UIView *subviews in [self.svMain subviews]) {
        if (subviews.tag >= 2000) {
            [subviews removeFromSuperview];
        }
    }
}

-(NSString *)showSumTitle
{
    NSString *strSub = [selectedSub componentsJoinedByString:@":"];
    NSString *strSubSub = [selectedSubSub componentsJoinedByString:@"  "];
    
    return [NSString stringWithFormat:@"%@：%@",strSub,strSubSub];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)btnBack:(id)sender
{
    [self dismissViewControllerAnimated:TRUE completion:nil];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
