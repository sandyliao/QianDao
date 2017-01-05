package com.gelan.Model;

/**
 * Created by hp on 2016/12/26.
 */

public class CustomerInfo {
    private Long Id;
    private String CustomerName;
    private String Longitude;
    private String Latitude;
    private String Address;
    private String Status;

    public Long getId(){return Id;}
    public void setId(Long Id){this.Id=Id;}
    public String getCustomerName(){return CustomerName;}
    public void setCustomerName(String CustomerName){this.CustomerName=CustomerName;}
    public String getLongitude(){return Longitude;}
    public void setLongitude(String Longitude){this.Longitude=Longitude;}
    public String getLatitude(){return Latitude;}
    public void setLatitude(String Latitude){this.Latitude=Latitude;}
    public String getAddress(){return Address;}
    public void setAddress(String Address){this.Address=Address;}
    public String getStatus(){return Status;}
    public void setStatus(String Status){this.Status=Status;}
}
