package com.gelan.Model;


import java.util.Date;

/**
 * Created by hp on 2016/12/22.
 */

public class EmailCode {
    private Long Id;
    private String Email;
    private String Code;
    private Date AddDate;

    public Long getId(){return Id;}
    public void setId(Long Id){this.Id=Id;}
    public String getEmail(){return Email;}
    public void setEmail(String Email){this.Email=Email;}
    public String getCode(){return Code;}
    public void setCode(String Code){this.Code=Code;}
    public Date getAddDate(){return AddDate;}
    public void setAddDate(Date AddDate){this.AddDate=AddDate;}
}
