//
//  ViewControllerRegister.m
//  demo
//
//  Created by ji on 17/1/12.
//  Copyright © 2017年 ji. All rights reserved.
//

#import "ViewControllerRegister.h"
#import "ViewController2.h"

@interface ViewControllerRegister ()

@end

@implementation ViewControllerRegister

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake([UIScreen mainScreen].bounds.size.width*0.03, [UIScreen mainScreen].bounds.size.height*0.03, [UIScreen mainScreen].bounds.size.width*0.94, [UIScreen mainScreen].bounds.size.height*0.94)];
    view.backgroundColor=[UIColor whiteColor];
    [self.view addSubview:view];
    
    UIView *view1 = [[UIView alloc]initWithFrame:CGRectMake(0,0,view.bounds.size.width, view.bounds.size.height*0.2)];
    view1.backgroundColor=[UIColor whiteColor];
    [view addSubview:view1];
    UILabel *label1=[[UILabel alloc] init];
    label1.frame=CGRectMake(0, 0, view1.bounds.size.width, view1.bounds.size.height*0.5);
    label1.backgroundColor=[UIColor whiteColor];
    label1.text=@"注册系统";
    label1.textAlignment=NSTextAlignmentCenter;
    [view1 addSubview:label1];
    
    
    
    UIView *view2 = [[UIView alloc]initWithFrame:CGRectMake(0,view.bounds.size.height*0.2,view.bounds.size.width, view.bounds.size.height*0.6)];
    view2.backgroundColor=[UIColor whiteColor];
    [view addSubview:view2];
    
    UILabel *label2=[[UILabel alloc] init];
    label2.frame=CGRectMake(view2.bounds.size.width*0.1, view2.bounds.size.height*0.1,
                            view2.bounds.size.width*0.2, view2.bounds.size.height*0.2);
    label2.backgroundColor=[UIColor whiteColor];
    label2.text=@"用户名";
    label2.textAlignment=NSTextAlignmentCenter;
    [view2 addSubview:label2];
    
    UITextField *txt1=[[UITextField alloc] init];
    txt1.frame=CGRectMake(view2.bounds.size.width*0.3, view2.bounds.size.height*0.15,
                          view2.bounds.size.width*0.45, view2.bounds.size.height*0.1);
    txt1.backgroundColor=[UIColor grayColor];
    txt1.textAlignment=NSTextAlignmentLeft;
    [view2 addSubview:txt1];
    
    UILabel *label3=[[UILabel alloc] init];
    label3.frame=CGRectMake(view2.bounds.size.width*0.1, view2.bounds.size.height*0.45,
                            view2.bounds.size.width*0.2, view2.bounds.size.height*0.1);
    label3.backgroundColor=[UIColor whiteColor];
    label3.text=@"密  码";
    label3.textAlignment=NSTextAlignmentCenter;
    [view2 addSubview:label3];
    
    UITextField *txt2=[[UITextField alloc] init];
    txt2.frame=CGRectMake(view2.bounds.size.width*0.3, view2.bounds.size.height*0.45,
                          view2.bounds.size.width*0.45, view2.bounds.size.height*0.1);
    txt2.backgroundColor=[UIColor grayColor];
    txt2.textAlignment=NSTextAlignmentLeft;
    [view2 addSubview:txt2];
    
    UILabel *label33=[[UILabel alloc] init];
    label33.frame=CGRectMake(view2.bounds.size.width*0.1, view2.bounds.size.height*0.45,
                            view2.bounds.size.width*0.2, view2.bounds.size.height*0.1);
    label33.backgroundColor=[UIColor whiteColor];
    label33.text=@"确认密码";
    label33.textAlignment=NSTextAlignmentCenter;
    [view2 addSubview:label33];
    
    UITextField *txt23=[[UITextField alloc] init];
    txt23.frame=CGRectMake(view2.bounds.size.width*0.3, view2.bounds.size.height*0.45,
                          view2.bounds.size.width*0.45, view2.bounds.size.height*0.1);
    txt23.backgroundColor=[UIColor grayColor];
    txt23.textAlignment=NSTextAlignmentLeft;
    [view2 addSubview:txt23];
    
    UIButton *but1=[[UIButton alloc] init];
    but1.frame=CGRectMake(view2.bounds.size.width*0.1, view2.bounds.size.height*0.7,
                          view2.bounds.size.width*0.3, view2.bounds.size.height*0.1);
    but1.backgroundColor=[UIColor grayColor];
    [but1 setTitle:@"返回登录" forState:UIControlStateNormal];
    [but1 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [but1 addTarget:self action:@selector(jump:) forControlEvents:UIControlEventTouchUpInside];
    [view2 addSubview:but1];
    
    UIButton *but2=[[UIButton alloc] init];
    but2.frame=CGRectMake(view2.bounds.size.width*0.6, view2.bounds.size.height*0.7,
                          view2.bounds.size.width*0.3, view2.bounds.size.height*0.1);
    but2.backgroundColor=[UIColor grayColor];
    [but2 setTitle:@"点击注册" forState:UIControlStateNormal];
    [but2 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    //[but1 addTarget:self action:@selector(jump:) forControlEvents:UIControlEventTouchUpInside];
    [view2 addSubview:but2];
    
    
    UIView *superview=view.superview;
    superview.backgroundColor=[UIColor grayColor];
}


-(IBAction)jump:(id)sender{
    ViewController2 *v2=[[ViewController2 alloc]init];
    [self presentViewController:v2 animated:YES completion:nil];
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

@end
