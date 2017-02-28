//
//  ViewController.m
//  demo
//
//  Created by ji on 17/1/11.
//  Copyright © 2017年 ji. All rights reserved.
//

#import "ViewController.h"
#import "ViewController2.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake([UIScreen mainScreen].bounds.size.width*0.03, [UIScreen mainScreen].bounds.size.height*0.03, [UIScreen mainScreen].bounds.size.width*0.94, [UIScreen mainScreen].bounds.size.height*0.94)];
    view.backgroundColor=[UIColor whiteColor];
    [self.view addSubview:view];
    
    UIView *view1 = [[UIView alloc]initWithFrame:CGRectMake(0,0,view.bounds.size.width, view.bounds.size.height*0.2)];
    view1.backgroundColor=[UIColor whiteColor];
    [view addSubview:view1];
    UILabel *label1=[[UILabel alloc] init];
    label1.frame=CGRectMake(0, 0, view1.bounds.size.width, view1.bounds.size.height*0.5);
    label1.backgroundColor=[UIColor whiteColor];
    label1.text=@"签到系统";
    label1.textAlignment=NSTextAlignmentCenter;
    [view1 addSubview:label1];
    UIButton *but1=[[UIButton alloc] init];
    but1.frame=CGRectMake(view1.bounds.size.width/4, view1.bounds.size.height*0.6,
                          view1.bounds.size.width/2, view1.bounds.size.height*0.3);
    but1.backgroundColor=[UIColor grayColor];
    [but1 setTitle:@"签到" forState:UIControlStateNormal];
    [but1 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    //[but1 addTarget:self action:@selector(jump:) forControlEvents:UIControlEventTouchUpInside];
    [view1 addSubview:but1];
    
    UIView *view2 = [[UIView alloc]initWithFrame:CGRectMake(0,view.bounds.size.height*0.2,view.bounds.size.width, view.bounds.size.height*0.6)];
    view2.backgroundColor=[UIColor whiteColor];
    [view addSubview:view2];
    
    UIView *view3 = [[UIView alloc]initWithFrame:CGRectMake(0,view.bounds.size.height*0.8,view.bounds.size.width, view.bounds.size.height*0.2)];
    view3.backgroundColor=[UIColor whiteColor];
    [view addSubview:view3];
    UIButton *but2=[[UIButton alloc] init];
    but2.frame=CGRectMake(view1.bounds.size.width*0.07, view1.bounds.size.height*0.6,
                          view1.bounds.size.width*0.4, view1.bounds.size.height*0.3);
    but2.backgroundColor=[UIColor grayColor];
    [but2 setTitle:@"首页" forState:UIControlStateNormal];
    [but2 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    //[but2 addTarget:self action:@selector(jump:) forControlEvents:UIControlEventTouchUpInside];
    [view3 addSubview:but2];
    UIButton *but3=[[UIButton alloc] init];
    but3.frame=CGRectMake(view1.bounds.size.width*0.56, view1.bounds.size.height*0.6,
                          view1.bounds.size.width*0.4, view1.bounds.size.height*0.3);
    but3.backgroundColor=[UIColor grayColor];
    [but3 setTitle:@"用户" forState:UIControlStateNormal];
    [but3 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [but3 addTarget:self action:@selector(jump:) forControlEvents:UIControlEventTouchUpInside];
    [view3 addSubview:but3];
    
    UIView *superview=view.superview;
    superview.backgroundColor=[UIColor grayColor];
}

- (void)connectHost:(NSString *)host connectUser:(NSString *)user connectPassword:(NSString *)password connectName:(NSString *)name
{
    myconnect = mysql_init(nil);
    mysql_options(myconnect, MYSQL_SET_CHARSET_NAME, "gb2312");
    myconnect = mysql_real_connect(myconnect, [host UTF8String],[user UTF8String],[password UTF8String],[name UTF8String], MYSQL_PORT, NULL, 0);
    
    if(!myconnect)
    {
        printf("error code=%i", mysql_errno(myconnect));
        return;
    }
    
    NSLog(@"connect to Mysql");
}

- (void)disconnect{
    mysql_close(myconnect);
    NSLog(@"Close From Mysql");
}



-(IBAction)jump:(id)sender{
    ViewController2 *v2=[[ViewController2 alloc]init];
    [self presentViewController:v2 animated:YES completion:nil];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
