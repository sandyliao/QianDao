package com.gelan.Model;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by hp on 2016/12/14.
 */

public class UserRegister {
    private Long Id;
    private Long companyId;
    private String RealName;
    private String Email;
    private String Mobile;
    private String Address;
    private String Sex;
    private Date AddDate;
    private String Status;
    private String Role;
    private String LogName;
    private String Password;

    public Long getId(){
        return Id;
    }
    public void setId(Long Id){
        this.Id=Id;
    }
    public String getRealName(){
        return RealName;
    }
    public void setRealName(String RealName){
        this.RealName=RealName;
    }
    public String getEmail(){
        return Email;
    }
    public void setEmail(String Email){
        this.Email=Email;
    }
    public String getMobile(){
        return Mobile;
    }
    public void setMobile(String Mobile){
        this.Mobile=Mobile;
    }
    public String getAddress(){
        return Address;
    }
    public void setAddress(String Address){
        this.Address=Address;
    }
    public String getSex(){
        return Sex;
    }
    public void setSex(String Sex){
        this.Sex=Sex;
    }
    public Date getAddDate(){
        return AddDate;
    }
    public void setAddDate(Date AddDate){
        this.AddDate=AddDate;
    }
    public String getStatus(){
        return Status;
    }
    public void setStatus(String Status){
        this.Status=Status;
    }
    public String getRole(){
        return Role;
    }
    public void setRole(String Role){
        this.Role=Role;
    }
    public String getLogName(){
        return LogName;
    }
    public void setLogName(String LogName){
        this.LogName=LogName;
    }
    public String getPassword(){
        return Password;
    }
    public void setPassword(String Password){
        this.Password=Password;
    }
    public Long getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
