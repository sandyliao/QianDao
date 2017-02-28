//
//  ViewController2.m
//  demo
//
//  Created by ji on 17/1/11.
//  Copyright © 2017年 ji. All rights reserved.
//

#import "ViewController2.h"
#import "ViewController.h"
#import "ViewControllerRegister.h"

@interface ViewController2 ()

@end

@implementation ViewController2

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
    label1.text=@"登录系统";
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
    
    UIButton *but1=[[UIButton alloc] init];
    but1.frame=CGRectMake(view2.bounds.size.width*0.1, view2.bounds.size.height*0.7,
                          view2.bounds.size.width*0.3, view2.bounds.size.height*0.1);
    but1.backgroundColor=[UIColor grayColor];
    [but1 setTitle:@"登录" forState:UIControlStateNormal];
    [but1 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    //[but1 addTarget:self action:@selector(jump:) forControlEvents:UIControlEventTouchUpInside];
    [view2 addSubview:but1];
    
    UIButton *but2=[[UIButton alloc] init];
    but2.frame=CGRectMake(view2.bounds.size.width*0.6, view2.bounds.size.height*0.7,
                          view2.bounds.size.width*0.3, view2.bounds.size.height*0.1);
    but2.backgroundColor=[UIColor grayColor];
    [but2 setTitle:@"注册" forState:UIControlStateNormal];
    [but2 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [but2 addTarget:self action:@selector(jumpreg:) forControlEvents:UIControlEventTouchUpInside];
    [view2 addSubview:but2];
    
    
    
    
    
    UIView *view3 = [[UIView alloc]initWithFrame:CGRectMake(0,view.bounds.size.height*0.8,view.bounds.size.width, view.bounds.size.height*0.2)];
    view3.backgroundColor=[UIColor whiteColor];
    [view addSubview:view3];
    UIButton *but3=[[UIButton alloc] init];
    but3.frame=CGRectMake(view1.bounds.size.width*0.07, view1.bounds.size.height*0.6,
                          view1.bounds.size.width*0.4, view1.bounds.size.height*0.3);
    but3.backgroundColor=[UIColor grayColor];
    [but3 setTitle:@"首页" forState:UIControlStateNormal];
    [but3 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [but3 addTarget:self action:@selector(jump:) forControlEvents:UIControlEventTouchUpInside];
    [view3 addSubview:but3];
    
    UIButton *but4=[[UIButton alloc] init];
    but4.frame=CGRectMake(view1.bounds.size.width*0.56, view1.bounds.size.height*0.6,
                          view1.bounds.size.width*0.4, view1.bounds.size.height*0.3);
    but4.backgroundColor=[UIColor grayColor];
    [but4 setTitle:@"用户" forState:UIControlStateNormal];
    [but4 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    //[but3 addTarget:self action:@selector(jump:) forControlEvents:UIControlEventTouchUpInside];
    [view3 addSubview:but4];
    
    
    
    UIView *superview=view.superview;
    superview.backgroundColor=[UIColor grayColor];
}


-(IBAction)jump:(id)sender{
    ViewController *v1=[[ViewController alloc]init];
    [self presentViewController:v1 animated:YES completion:nil];
}

-(IBAction)jumpreg:(id)sender{
    ViewControllerRegister *vr=[[ViewControllerRegister alloc]init];
    [self presentViewController:vr animated:YES completion:nil];
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
