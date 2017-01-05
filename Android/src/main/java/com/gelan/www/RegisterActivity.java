package com.gelan.www;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gelan.DAO.BaseDAO;
import com.gelan.DAO.DB;
import com.gelan.DAO.StandardSQL;
import com.gelan.Model.EmailCode;
import com.gelan.Model.EmailConfig;
import com.gelan.Model.UserRegister;
import com.gelan.Utils.Email.MailSenderInfo;
import com.gelan.Utils.Email.SimpleMailSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.gelan.Utils.MD5Utils.getMD5;

/**
 * Created by hp on 2016/12/15.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText email;
    private EditText passWd;
    private EditText passWd2;
    private EditText checkEmail;
    private Button sendCodeBt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiyt_register);
        sendCodeBt=(Button)findViewById(R.id.buttonEmail);
        sendCodeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendCode(view);
            }
        });
    }

    public void BackLoginClick(View v)
    {
        Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void RegisterClick(final View v)
    {
        email=(EditText)findViewById(R.id.editTextEmail);
        passWd=(EditText)findViewById(R.id.editTextPassWd);
        passWd2=(EditText)findViewById(R.id.editTextPassWd2);
        checkEmail=(EditText)findViewById(R.id.editTextCheckEmail);
        if(!checkEmail(email.getText().toString())){
            new AlertDialog.Builder(this).setTitle("提示框").setMessage("邮箱格式不正确").show();
            return;
        }
        if(passWd.getText().toString()== null || passWd.getText().toString().isEmpty())
        {
            new AlertDialog.Builder(this).setTitle("提示框").setMessage("密码不能为空").show();
            return;
        }
        if(passWd.getText().toString().length()<6)
        {
            new AlertDialog.Builder(this).setTitle("提示框").setMessage("密码长度不能小于6位").show();
            return;
        }
        if(!passWd.getText().toString().equals(passWd2.getText().toString()))
        {
            new AlertDialog.Builder(this).setTitle("提示框").setMessage("确认密码与密码不一致").show();
            return;
        }
        if(checkEmail.getText().toString()== null || checkEmail.getText().toString().isEmpty())
        {
            new AlertDialog.Builder(this).setTitle("提示框").setMessage("验证码不能为空").show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseDAO dao = new BaseDAO();
                List<UserRegister> list = null;
                List<EmailCode> listCode = null;
                UserRegister ur = new UserRegister();
                ur.setEmail(email.getText().toString());
                try {
                    list = dao.query(ur);
                    if(list.size()>0){
                        v.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("邮箱已被使用").show();
                            }
                        });
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    v.post(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("注册失败").show();
                        }
                    });
                    return;
                }
                EmailCode ec = new EmailCode();
                ec.setEmail(email.getText().toString());
                Calendar calendar = Calendar.getInstance();
                String today = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" 00:00:00";
                String tomorrow = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" 00:00:00";
                try {
                    listCode = dao.query(ec,"AddDate between '"+ today +"' and '"+tomorrow+"'");
                    if (listCode.size() > 0) {
                        if (!(listCode.get(0).getCode().toString().equals(checkEmail.getText().toString())&&(listCode.get(0).getEmail().toString().equals(email.getText().toString())))) {
                            v.post(new Runnable() {
                                public void run() {
                                    new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("验证码不正确，请重新输入").show();
                                }
                            });
                            return;
                        }
                    }else
                    {
                        v.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("今天还未获取过验证码，请先获取验证码。").show();
                            }
                        });
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    v.post(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("注册失败").show();
                        }
                    });
                    return;
                }
                try {
                    ur.setPassword(getMD5(passWd.getText().toString()));
                    ur.setLogName(ur.getEmail().toString());
                    ur.setAddDate(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                    v.post(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("注册失败").show();
                        }
                    });
                    return;
                }
                try {
                    if(dao.add(ur)>0){
                        v.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("注册成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        BackLoginClick(v);
                                    }}).show();
                            }
                        });
                    }else {
                        v.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("注册失败").show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    v.post(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("注册失败").show();
                        }
                    });
                }
            }
        }).start();
    }

    public static boolean checkEmail(String email)  {
        // 验证邮箱的正则表达式
        String format = "\\p{ASCII}\\w{2,15}[@][a-z0-9]{2,}[.]\\p{Lower}{2,}";
        //p{Alpha}:内容是必选的，和字母字符[\p{Lower}\p{Upper}]等价。如：200896@163.com不是合法的。
        //w{2,15}: 2~15个[a-zA-Z_0-9]字符；w{}内容是必选的。 如：dyh@152.com是合法的。
        //[a-z0-9]{3,}：至少三个[a-z0-9]字符,[]内的是必选的；如：dyh200896@16.com是不合法的。
        //[.]:'.'号时必选的； 如：dyh200896@163com是不合法的。
        //p{Lower}{2,}小写字母，两个以上。如：dyh200896@163.c是不合法的。
        if (email.matches(format)){
            return true;// 邮箱名合法，返回true
        }else{
            return false;// 邮箱名不合法，返回false
        }
    }

    public void SendCode(final View v){
        email=(EditText)findViewById(R.id.editTextEmail);
        final String code=String.valueOf((int) (Math.random()*9000+1000));

        if(!checkEmail(email.getText().toString())){
            new AlertDialog.Builder(this).setTitle("提示框").setMessage("邮箱格式不正确").show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseDAO dao = new BaseDAO();
                StandardSQL standardSQL = new StandardSQL();
                DB db = new DB();
                Connection conn = null;
                List<UserRegister> list = null;
                List<EmailCode> listCode = null;
                UserRegister ur = new UserRegister();
                ur.setEmail(email.getText().toString());
                try {
                    list = dao.query(ur);
                    if (list.size() > 0) {
                        v.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("邮箱已被使用").show();
                            }
                        });
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    v.post(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("验证码发送失败").show();
                        }
                    });
                    return;
                }
                EmailCode ec = new EmailCode();
                ec.setEmail(email.getText().toString());
                Calendar calendar = Calendar.getInstance();
                String today = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" 00:00:00";
                String tomorrow = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" 00:00:00";
                try {
                    listCode = dao.query(ec,"AddDate between '"+ today +"' and '"+tomorrow+"'");
                    if (listCode.size() > 0) {
                        v.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("今天已发送过验证码，请不要重复发送").show();
                            }
                        });
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    v.post(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("验证码发送失败").show();
                        }
                    });
                    return;
                }
                ec.setCode(code);
                ec.setAddDate(new Date());
                try {
                    conn=db.getConnection();
                    conn.setAutoCommit(false);
                    String sql = standardSQL.add(ec);
                    PreparedStatement pstam=conn.prepareStatement(sql);
                    pstam.executeUpdate();
                    //dao.add(ec);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                try
                {
                    MailSenderInfo mailInfo = new MailSenderInfo();
                    EmailConfig emailConfig=getEmailConfig(Long.valueOf("2"));
                    mailInfo.setMailServerHost(emailConfig.getMailServerHost());
                    mailInfo.setMailServerPort(emailConfig.getMailServerPort());
                    mailInfo.setUserName(emailConfig.getMailUserName());  //你的邮箱地址
                    mailInfo.setPassword(emailConfig.getMailPassword());//您的邮箱密码
                    mailInfo.setFromAddress(emailConfig.getMailFromAddress());
                    mailInfo.setValidate(true);
//                    mailInfo.setMailServerHost("smtp.qq.com");
//                    mailInfo.setMailServerPort("25");
//                    mailInfo.setUserName("840530852@qq.com");  //你的邮箱地址
//                    mailInfo.setPassword("hvaqewblolsnbajh");//您的邮箱密码
//                    mailInfo.setFromAddress("840530852@qq.com");
                    mailInfo.setToAddress(email.getText().toString());
                    mailInfo.setSubject("签到系统注册验证码");
                    mailInfo.setContent("验证码："+code);

                    //这个类主要来发送邮件
                    SimpleMailSender sms = new SimpleMailSender();
                    if(sms.sendTextMail(mailInfo)) {//发送文体格式
                        conn.commit();
                        v.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("验证码已发送").show();
                            }
                        });
                    }else {
                        conn.rollback();
                        v.post(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("验证码发送失败").show();
                            }
                        });
                    }
                    //sms.sendHtmlMail(mailInfo);//发送html格式
                }
                catch (Exception e) {
                    e.printStackTrace();
                    //Log.e("SendMail", e.getMessage(), e);
                }
            }
        }).start();
    }

    public EmailConfig getEmailConfig(Long CustomerId){
        BaseDAO dao = new BaseDAO();
        List<EmailConfig> list = null;
        EmailConfig emailConfig=new EmailConfig();
        emailConfig.setCustomerId(CustomerId);
        try {
            list=dao.query(emailConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

}

