package com.gelan.SqlLite.util.Model;

import java.util.Date;
import java.sql.Time;

/**
 * Created by hp on 2016/12/20.
 */

public class UserTable {
    private int id;
    private String loginName;
    private String loginPassword;
    private String companyName;
    private String gpsAdress;
    private String statu;
    private Date updateTime;
    private String memo;//记录最近五条签到信息

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getloginName() {
        return loginName;
    }
    public void setloginName(String loginName) {
        this.loginName = loginName;
    }

    public String getloginPassword() {
        return loginPassword;
    }
    public void setloginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getcompanyName() {
        return companyName;
    }
    public void setcompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getgpsAdress() {
        return gpsAdress;
    }
    public void setgpsAdress(String gpsAdress) {
        this.gpsAdress = gpsAdress;
    }

    public String getstatu() {
        return statu;
    }
    public void setstatu(String statu) {
        this.statu = statu;
    }

    public Date getupdateTime() {
        return updateTime;
    }
    public void setupdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getmemo() {
        return memo;
    }
    public void setmemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
