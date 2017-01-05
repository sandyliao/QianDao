package com.gelan.www;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gelan.DAO.BaseDAO;
import com.gelan.DAO.StandardSQL;
import com.gelan.Model.CustomerInfo;
import com.gelan.Model.UserOnJob;
import com.gelan.Model.UserRegister;
import com.gelan.Model.SQLite.UserTable;

import java.util.Date;
import java.util.List;

import static com.gelan.Utils.DateUtils.dateToString;
import static com.gelan.Utils.MD5Utils.getMD5;

/**
 * Created by hp on 2016/12/15.
 */

public class LoginActivity extends AppCompatActivity {
    private UserTable model=new UserTable();
    SQLiteDatabase db;
    SQLiteDatabase db2;
    StandardSQL standardSQL;

    //mysql
    BaseDAO mydao = new BaseDAO();
    UserRegister ur = new UserRegister();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = this.openOrCreateDatabase("gelan.db",MODE_PRIVATE,null);
        standardSQL = new StandardSQL();
        try {
            String s=standardSQL.createSQLite(model);
            db.execSQL(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //sqllite登录检测
        Cursor c = null;
        try {
            c=db.rawQuery(standardSQL.query(model),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (c.moveToNext()) {
            //登录成功,进入主页
            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
            //用Bundle携带数据
            Bundle bundle=new Bundle();
            //传递name参数为tinyphp
            String sn=c.getString(c.getColumnIndex("loginName"));
            bundle.putString("name",sn);
            String s=bundle.getString("name");
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    public void LoginClick(final View v)
    {
        //获取输入的用户名/密码
        final EditText loginName = (EditText) findViewById(R.id.loginName);
        final EditText loginPassword = (EditText) findViewById(R.id.loginPassword);

        //sqllite登录检测
        model.setloginName(loginName.getText().toString());
        model.setloginPassword(loginPassword.getText().toString());
        model.setupdateTime(new Date());
        Cursor c = null;
        try {
            db2=openOrCreateDatabase("gelan.db",MODE_PRIVATE,null);
            c=db2.rawQuery(standardSQL.query(model),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果sqllite没有登录
        if (c.getCount() == 0)  {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ur.setLogName(loginName.getText().toString());
                    try {
                        ur.setPassword(getMD5(loginPassword.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    List<UserRegister> list = null;
                    try {
                        //musql登录查询
                        list = mydao.query(ur);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (list.size() == 1) {
                        //mysql登录成功
//                        for (UserRegister u : list) {
//                            model.setcompanyName("null");
//                            model.setgpsAdress(u.getAddress());
//                            model.setstatu(u.getStatus());
//                            model.setmemo("null");
//                        }


                        model.setUserId(list.get(0).getId());
                        model.setCompanyId(list.get(0).getCompanyId());

                        List<CustomerInfo> CustomerInfoList=null;
                        CustomerInfo customerInfo=new CustomerInfo();
                        customerInfo.setId(list.get(0).getCompanyId());
                        try {
                            CustomerInfoList=mydao.query(customerInfo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (CustomerInfoList.size()>0)
                        {
                            for (CustomerInfo ci : CustomerInfoList) {
                                model.setcompanyName(ci.getCustomerName());
                                model.setLongitude(ci.getLongitude());
                                model.setLatitude(ci.getLatitude());
                            }
                        }
                        else {
                            model.setcompanyName("null");
                        }

                        model.setstatu(list.get(0).getStatus());

                        List<UserOnJob> UserOnJobList=null;
                        UserOnJob userOnJob=new UserOnJob();
                        userOnJob.setUserId(list.get(0).getId());
                        try {
                            UserOnJobList=mydao.query(userOnJob," 1=1 order by AddDate desc",5);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (UserOnJobList.size()>0)
                        {
                            String str="";
                            for (UserOnJob uo : UserOnJobList) {
                                str+=(dateToString(uo.getAddDate())+"-"+uo.getLatitude()+"-"+uo.getLongitude()+"\n");
                            }
                            model.setmemo(str);
                        }
                        else {
                            model.setmemo("null");
                        }
                        //插入sqllite登录信息
                        SQLiteDatabase db1 = openOrCreateDatabase("gelan.db",MODE_PRIVATE,null);
                        int row=0;
                        try {
                            db1.execSQL(standardSQL.add(model));
                            row=1;
                            db1.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            row=0;
                        }
                        if (row > 0) {
                            v.post(
                                    new Runnable() {
                                        public void run() {
                                            new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("登录成功！").setPositiveButton("是", null).show();
                                        }
                                    }
                            );
                            //登录成功,进入主页
                            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                            //用Bundle携带数据
                            Bundle bundle=new Bundle();
                            //传递name参数为tinyphp
                            bundle.putString("name", model.getloginName());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            v.post(
                                    new Runnable() {
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setTitle("确认");
                                            builder.setMessage("登录失败1！");
                                            builder.setPositiveButton("是", null);
                                            builder.show();
                                        }
                                    }
                            );
                        }
                    } else {
                        v.post(
                                new Runnable() {
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setTitle("确认");
                                        builder.setMessage("登录失败2！");
                                        builder.setPositiveButton("是", null);
                                        builder.show();
                                    }
                                }
                        );
                    }
                }
            }).start();

        }
        else//sqllite已登录,更新登录信息
        {
            try {
                db.execSQL(standardSQL.update(model));
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                db.close();
            }
        }
        if(db!=null)
        {
            db.close();
        }
        if(db2!=null)
        {
            db2.close();
        }
    }

    public void MyMain(View v)
    {
        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void RegisterClick(View v)
    {
        Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

}
