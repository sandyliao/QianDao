package com.gelan.Utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.UUID;


/**
 * Created by hp on 2016/12/26.
 */

public class Utils {

    private final Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public String getPhoneNumber() {
        // 获取手机号 MSISDN，很可能为空
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer inf = new StringBuffer();
        switch (tm.getSimState()) { //getSimState()取得sim的状态  有下面6中状态
            case TelephonyManager.SIM_STATE_ABSENT:
                inf.append("无卡");
                return inf.toString();
            case TelephonyManager.SIM_STATE_UNKNOWN:
                inf.append("未知状态");
                return inf.toString();
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                inf.append("需要NetworkPIN解锁");
                return inf.toString();
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                inf.append("需要PIN解锁");
                return inf.toString();
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                inf.append("需要PUK解锁");
                return inf.toString();
            case TelephonyManager.SIM_STATE_READY:
                break;
        }

        String phoneNumber = tm.getLine1Number();
        return phoneNumber;
    }

    public String getPhoneIP() {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    public String intToIp(int i) {

        return ((i >> 24) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                (i & 0xFF);
    }
    public static String getUniquePsuedoID() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
