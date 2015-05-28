//
//  RootTabbarController.m
//  xbbedu
//
//  Created by mz on 15/4/30.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "RootTabbarController.h"
#import "CepingViewController.h"

@interface RootTabbarController ()

@end

@implementation RootTabbarController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self customTabbarItemStyle];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) customTabbarItemStyle
{
    NSArray *items = self.tabBar.items;
    UITabBarItem *homeItem = items[0];
    homeItem.image = [[UIImage imageNamed:@"yuer.png"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    homeItem.selectedImage = [[UIImage imageNamed:@"yuerclick.png"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    
    UITabBarItem *cepingItem = items[1];
    cepingItem.image = [[UIImage imageNamed:@"ceping.png"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    cepingItem.selectedImage = [[UIImage imageNamed:@"cepingclick.png"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    
    
    UITabBarItem *subjectiveItem = items[2];
    subjectiveItem.image = [[UIImage imageNamed:@"subjective.png"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    subjectiveItem.selectedImage = [[UIImage imageNamed:@"subjectiveclick.png"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    
    UITabBarItem *aboutItem = items[3];
    aboutItem.image = [[UIImage imageNamed:@"about.png"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    aboutItem.selectedImage = [[UIImage imageNamed:@"aboutclick.png"] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    
    [[UITabBarItem appearance] setTitleTextAttributes:
     [NSDictionary dictionaryWithObjectsAndKeys:RGB(161,161,161),                                                                                                              UITextAttributeTextColor, nil] forState:UIControlStateNormal];
    [[UITabBarItem appearance] setTitleTextAttributes:
     [NSDictionary dictionaryWithObjectsAndKeys:RGB(255,164,33),UITextAttributeTextColor, nil] forState:UIControlStateSelected];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/


//- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation
//{
////    return toInterfaceOrientation != UIDeviceOrientationPortraitUpsideDown;
//    if ([[self selectedViewController] isKindOfClass:[CepingViewController class]]) { // 如果是这个 vc 则支持自动旋转
//        return YES;
//    }
//    return NO;
//}
//
//- (BOOL)shouldAutorotate
//{
//    if ([[self selectedViewController] isKindOfClass:[CepingViewController class]]) { // 如果是这个 vc 则支持自动旋转
//        return YES;
//    }
//    return NO;
//}
//
//- (NSUInteger)supportedInterfaceOrientations
//{
//    return UIInterfaceOrientationMaskAllButUpsideDown;
//}

@end
