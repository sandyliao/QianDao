package com.gelan.Model;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by hp on 2016/12/19.
 */

public class UserOnJob {
    private Long Id;
    private Long UserId;
    private Long CustomerId;
    private String Longitude;
    private String Latitude;
    private Date AddDate;
    private String Status;
    private String ImageUrl;
    private String Memo;

    public Long getId(){
        return Id;
    }
    public Long getUserId(){
        return UserId;
    }
    public Long getCustomerId(){
        return CustomerId;
    }
    public void setId(Long Id){
        this.Id=Id;
    }
    public void setUserId(Long UserId){
        this.UserId=UserId;
    }
    public void setCustomerId(Long CustomerId){
        this.CustomerId=CustomerId;
    }
    public String getLongitude() { return Longitude; }
    public String getLatitude() { return Latitude; }
    public Date getAddDate() { return AddDate; }
    public String getStatus() { return Status; }
    public String getImageUrl() { return ImageUrl; }
    public void setLongitude(String Longitude)
    {
        this.Longitude = Longitude;
    }
    public void setLatitude(String Latitude)
    {
        this.Latitude = Latitude;
    }
    public void setAddDate(Date AddDate)
    {
        this.AddDate = AddDate;
    }
    public void setStatus(String Status)
    {
        this.Status = Status;
    }
    public void setImageUrl(String ImageUrl)
    {
        this.ImageUrl = ImageUrl;
    }
    public String getMemo(){return Memo;}
    public void setMemo(String Memo){this.Memo=Memo;}
    @Override
    public String toString() {
        return super.toString();
    }
}
