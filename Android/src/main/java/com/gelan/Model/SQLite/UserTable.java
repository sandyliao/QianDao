package com.gelan.Model.SQLite;

import java.util.Date;


/**
 * Created by hp on 2016/12/20.
 */

public class UserTable {
    private Integer id;
    private String loginName;
    private Long UserId;
    private String loginPassword;
    private Long companyId;
    private String companyName;
    private String Longitude;
    private String Latitude;
    private String statu;
    private Date updateTime;
    private String memo;//记录最近五条签到信息

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getloginName() {
        return loginName;
    }
    public void setloginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getUserId() {
        return UserId;
    }
    public void setUserId(Long UserId) {
        this.UserId = UserId;
    }

    public Long getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public String getLongitude() {
        return Longitude;
    }
    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }
    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
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
