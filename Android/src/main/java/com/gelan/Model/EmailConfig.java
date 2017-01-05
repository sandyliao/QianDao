package com.gelan.Model;

/**
 * Created by hp on 2016/12/26.
 */

public class EmailConfig {
    private Long Id;
    private Long CustomerId;
    private String MailServerHost;
    private String MailServerPort;
    private String MailUserName;
    private String MailPassword;
    private String MailFromAddress;
    private String MailType;

    public Long getId(){return Id;}
    public Long getCustomerId(){return CustomerId;}
    public String getMailServerHost(){return MailServerHost;}
    public String getMailServerPort(){return MailServerPort;}
    public String getMailUserName(){return MailUserName;}
    public String getMailPassword(){return MailPassword;}
    public String getMailFromAddress(){return MailFromAddress;}
    public String getMailType(){return MailType;}
    public void setId(Long Id){this.Id=Id;}
    public void setCustomerId(Long CustomerId){this.CustomerId=CustomerId;}
    public void setMailServerHost(String MailServerHost){this.MailServerHost=MailServerHost;}
    public void setMailServerPort(String MailServerPort){this.MailServerPort=MailServerPort;}
    public void setMailUserName(String MailUserName){this.MailUserName=MailUserName;}
    public void setMailPassword(String MailPassword){this.MailPassword=MailPassword;}
    public void setMailFromAddress(String MailFromAddress){this.MailFromAddress=MailFromAddress;}
    public void setMailType(String MailType){this.MailType=MailType;}
}
