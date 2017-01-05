package com.gelan.www;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.gelan.DAO.BaseDAO;
import com.gelan.DAO.StandardSQL;
import com.gelan.Model.CustomerInfo;
import com.gelan.Model.EmailCode;
import com.gelan.Model.SQLite.UserTable;
import com.gelan.Model.UserOnJob;
import com.gelan.Model.UserRegister;

import java.util.Date;
import java.util.List;

import static com.gelan.Utils.DateUtils.dateToString;
import static com.gelan.Utils.StringUtils.getCounts;
import static java.lang.Math.*;


public class MainActivity extends AppCompatActivity{
    TextView tvbuttom;
    private Button registerButtom;
    String LogName;
    Long UserId;
    Long CompanyId;
    Double CustomerLong;
    Double CustomerLat;
    Double CustomerLocationLong;
    Double CustomerLocationLat;
    String Memo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        tvbuttom = (TextView) findViewById(R.id.textView3);
        TextView txt_top = (TextView) findViewById(R.id.txt_top);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                BaseDAO dao = new BaseDAO();
//                //测试建表操作
//                StandardSQL standardSQL = new StandardSQL();
//                String sql = null;
//                try {
//                    sql = standardSQL.create(new CustomerInfo());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println(sql);
//                dao.executeUpdate(sql);
//            }
//        }).start();

        Button bt=(Button) findViewById(R.id.tuiChu);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null)
        {
            //接收name值
            LogName=bundle.getString("name");
            String name ="签到系统:"+ bundle.getString("name");
            txt_top.setText(String.valueOf(name));
            bt.setVisibility(View.VISIBLE);
        }
        if(tabbleIsExist("UserTable"))
        {
//            UserTable userTable = getLogName();
//            tvbuttom.setText(userTable.getmemo().toString());
//            LogName=userTable.getloginName();
//            CustomerLong = Double.valueOf(userTable.getLongitude()).doubleValue();
//            CustomerLat = Double.valueOf(userTable.getLatitude()).doubleValue();
//            UserId=userTable.getUserId();
//            CompanyId=userTable.getCompanyId();
            UserTable userTable = new UserTable();
            StandardSQL standardSQL = new StandardSQL();
            Cursor cursor = null;
            SQLiteDatabase db = openOrCreateDatabase("gelan.db",MODE_PRIVATE,null);
            try {
                cursor = db.rawQuery(standardSQL.query(userTable), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (cursor.moveToNext()) {
                LogName=cursor.getString(cursor.getColumnIndex("loginName"));
                UserId=new Long((long)cursor.getInt(cursor.getColumnIndex("userId")));
                CompanyId=new Long((long)cursor.getInt(cursor.getColumnIndex("companyId")));
                CustomerLat = Double.valueOf(cursor.getString(cursor.getColumnIndex("latitude"))).doubleValue();
                CustomerLong = Double.valueOf(cursor.getString(cursor.getColumnIndex("longitude"))).doubleValue();
                tvbuttom.setText(cursor.getString(cursor.getColumnIndex("memo")));
            }
            db.close();
        }

        registerButtom=(Button)findViewById(R.id.button2);
        registerButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                registerGPS(view);
            }
        });
    }
    public void MyCenter(View v){
        //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
        Intent intent =new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void tuiChu(View v)
    {
        SQLiteDatabase db2 = openOrCreateDatabase("gelan.db",MODE_PRIVATE,null);
        String s= "drop table if exists UserTable ";
        db2.execSQL(s);
        db2.close();
        Intent intent =new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }


//    public UserTable getLogName(){
//        UserTable userTable = new UserTable();
//        StandardSQL standardSQL = new StandardSQL();
//        Cursor cursor = null;
//        SQLiteDatabase db = openOrCreateDatabase("gelan.db",MODE_PRIVATE,null);
//        try {
//              cursor = db.rawQuery(standardSQL.query(userTable), null);
//        } catch (Exception e) {
//              e.printStackTrace();
//        }
//        while (cursor.moveToNext()) {
//              userTable.setloginName(cursor.getString(cursor.getColumnIndex("loginName")));
//              userTable.setmemo(cursor.getString(cursor.getColumnIndex("memo")));
//              userTable.setUserId(new Long((long)cursor.getInt(cursor.getColumnIndex("userId"))));
//              userTable.setCompanyId(new Long((long)cursor.getInt(cursor.getColumnIndex("companyId"))));
//              userTable.setcompanyName(cursor.getString(cursor.getColumnIndex("companyName")));
//              userTable.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
//              userTable.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
//        }
//        return userTable ;
//    }
    public boolean tabbleIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.openOrCreateDatabase("gelan.db",MODE_PRIVATE,null);;
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

    public CustomerInfo getCustomerInfo(Long CustomerId){
        BaseDAO dao = new BaseDAO();
        List<CustomerInfo> list = null;
        CustomerInfo customerInfo=new CustomerInfo();
        customerInfo.setId(CustomerId);
        try {
            list=dao.query(customerInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

    public void registerGPS(final View v) {
        if(LogName!=null&&LogName.length()>0){
            String serviceString = Context.LOCATION_SERVICE;
            LocationManager locationManager = (LocationManager) getSystemService(serviceString);

            //String provider = LocationManager.GPS_PROVIDER;
            String provider = LocationManager.NETWORK_PROVIDER;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);
            //Location location = new Location("");
            getLocationInfo(location);
            //locationManager.requestLocationUpdates(provider, 2000, 0, locationListener);
            //double a = GetDistance(CustomerLat,CustomerLong,CustomerLocationLat,CustomerLocationLong);
            double a = distance(CustomerLat,CustomerLong,CustomerLocationLat,CustomerLocationLong);
            //tvbuttom.setText(String.valueOf(a)+"|"+CustomerLocationLat+"|"+CustomerLocationLong);
            if(a <= 0.1 && a >= -0.1)
            {
                final UserOnJob userOnJob = new UserOnJob();
                userOnJob.setUserId(UserId);
                userOnJob.setLongitude(CustomerLocationLong.toString());
                userOnJob.setLatitude(CustomerLocationLat.toString());
                userOnJob.setAddDate(new Date());
                userOnJob.setCustomerId(CompanyId);
                UserTable userTable = new UserTable();
                StandardSQL standardSQL = new StandardSQL();
                Cursor cursor = null;
                SQLiteDatabase db = openOrCreateDatabase("gelan.db",MODE_PRIVATE,null);
                try {
                    cursor = db.rawQuery(standardSQL.query(userTable), null);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                while (cursor.moveToNext()) {
                    Memo=cursor.getString(cursor.getColumnIndex("memo"));
                    //userTable.setloginName(cursor.getString(cursor.getColumnIndex("loginName")));
                    //userTable.setmemo(cursor.getString(cursor.getColumnIndex("memo")));
                    //userTable.setUserId(new Long((long)cursor.getInt(cursor.getColumnIndex("userId"))));
                    //userTable.setCompanyId(new Long((long)cursor.getInt(cursor.getColumnIndex("companyId"))));
                    //userTable.setcompanyName(cursor.getString(cursor.getColumnIndex("companyName")));
                    //userTable.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                    //userTable.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                    //userTable.setloginPassword(cursor.getString(cursor.getColumnIndex("loginPassword")));
                    //userTable.setstatu(cursor.getString(cursor.getColumnIndex("statu")));
                    //userTable.setupdateTime(new Date());
                    //userTable.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                }
                String Memo2;
                if(getCounts(Memo,"\n")>4){
                    Memo2 = dateToString(userOnJob.getAddDate())+"-"+userOnJob.getLatitude()+"-"+userOnJob.getLongitude()+"\n" + Memo.substring(0,Memo.lastIndexOf("\n",Memo.length()-2)+2);
                }else {
                    Memo2 = dateToString(userOnJob.getAddDate())+"-"+userOnJob.getLatitude()+"-"+userOnJob.getLongitude()+"\n" + Memo;
                }
                db.execSQL("update UserTable set memo = '" +Memo2+"'");

                db.close();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BaseDAO dao = new BaseDAO();
                        try {
                            if(dao.add(userOnJob)>0){
                                v.post(new Runnable() {
                                    public void run() {
                                        new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("签到成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                MyCenter(v);
                                            }}).show();
                                    }
                                });
                            }else {
                                v.post(new Runnable() {
                                    public void run() {
                                        new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("签到失败").show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            v.post(new Runnable() {
                                public void run() {
                                    new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("签到失败").show();
                                }
                            });
                        }
                    }
                }).start();
            }else
            {
                new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("签到失败:GPS大于100米").show();
            }

        }else{
            v.post(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(v.getContext()).setTitle("提示框").setMessage("签到失败：未登入").show();
                }
            });
        }

    }

    private void getLocationInfo(Location location) {
        String latLongInfo;
        //TextView locationText = (TextView) findViewById(R.id.textView3);
        if (location != null) {
            double latitude = location.getLatitude();//维度
            double longitude = location.getLongitude();//经度
            latLongInfo = "维度：" + latitude + "\n精度:" + longitude;
            CustomerLocationLong = longitude;
            CustomerLocationLat = latitude;
        }else{
            latLongInfo = "No location found";
        }

//        CustomerLocationLong = 121.356904;
//        CustomerLocationLat = 31.254996;
        //locationText.setText("Your current position is:\n" + latLongInfo);
    }


    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            getLocationInfo(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            getLocationInfo(null);
        }

        @Override
        public void onProviderEnabled(String provider) {
            getLocationInfo(null);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private static double EARTH_RADIUS = 6378.137;
    private static double rad(double d){
        return d * Math.PI / 180.0;
    }
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2){
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


    double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        double miles = dist * 60 * 1.1515*1.609344;
        return miles;
    }
    //将角度转换为弧度
    static double deg2rad(double degree) {
        return degree / 180 * Math.PI;
    }
    //将弧度转换为角度
    static double rad2deg(double radian) {
        return radian * 180 / Math.PI;
    }


}
