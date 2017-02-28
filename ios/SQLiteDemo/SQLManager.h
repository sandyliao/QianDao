//
//  SQLManager.h
//  SQLiteDemo
//
//  Created by 陆亦游 on 2017/2/20.
//  Copyright © 2017年 陆亦游. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "sqlite3.h"
#import "StudentModel.h"

@interface SQLManager : NSObject{
    sqlite3 *db;
}

+(SQLManager *)shareManager;

-(StudentModel *)searchWithIdNum:(StudentModel *)model;

-(int)insert:(StudentModel *)model;


@end
