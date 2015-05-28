//
//  PersonInfoViewController.m
//  xbbedu
//
//  Created by mz on 15/5/22.
//  Copyright (c) 2015年 xinbaobeijiaoyu. All rights reserved.
//

#import "PersonInfoViewController.h"
#import "HZAreaPickerView.h"

@interface PersonInfoViewController ()<UITextFieldDelegate, HZAreaPickerDelegate>
@property (weak, nonatomic) IBOutlet UIButton *btnBack;
- (IBAction)onclick_btnBack:(id)sender;
@property (weak, nonatomic) IBOutlet UITextField *txtParent;
@property (weak, nonatomic) IBOutlet UITextField *txtChilden;
@property (weak, nonatomic) IBOutlet UITextField *txtBirthDate;
- (IBAction)popView:(id)sender;
@property (weak, nonatomic) IBOutlet UISegmentedControl *sex;
- (IBAction)txtEndEdit:(id)sender;
@property (weak, nonatomic) IBOutlet UIDatePicker *birthDate;
- (IBAction)onvaluechanged_datePk:(id)sender;
@property (weak, nonatomic) IBOutlet UITextField *areaText;
@property (weak, nonatomic) IBOutlet UITextView *txtSignContent;
@property (strong, nonatomic) HZAreaPickerView *locatePicker;

@property (strong, nonatomic) NSString *areaValue, *cityValue;

@end

@implementation PersonInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.birthDate.hidden = YES;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (IBAction)onclick_btnBack:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark - HZAreaPicker delegate
-(void)pickerDidChaneStatus:(HZAreaPickerView *)picker
{
    if (picker.pickerStyle == HZAreaPickerWithStateAndCityAndDistrict) {
        self.areaValue = [NSString stringWithFormat:@"%@ %@ %@", picker.locate.state, picker.locate.city, picker.locate.district];
    }
}

-(void)cancelLocatePicker
{
    [self.locatePicker cancelPicker];
    self.locatePicker.delegate = nil;
    self.locatePicker = nil;
}

#pragma mark - TextField delegate
- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField
{
    if ([textField isEqual:self.areaText]) {
        [self cancelLocatePicker];
        self.locatePicker = [[HZAreaPickerView alloc] initWithStyle:HZAreaPickerWithStateAndCityAndDistrict delegate:self];
        [self.locatePicker showInView:self.view];
    }
    return NO;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [super touchesBegan:touches withEvent:event];
    [self cancelLocatePicker];
}

- (IBAction)popView:(id)sender {
    self.birthDate.hidden = !self.birthDate.hidden;
}
- (IBAction)txtEndEdit:(id)sender {
    self.birthDate.hidden = !self.birthDate.hidden;
}
- (IBAction)onvaluechanged_datePk:(id)sender {
}
@end
