package com.wizarpos.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

public class SystemUtil {

    private static final String LOG_TAG = "SystemUtil";
    public static final String KERNEL_VERSION = "kernelVersion";
    public static final String SYSTEM_VERSION = "systemVersion";
    public static final String BOOTLOADER_VERSION = "bootloaderVersion";


    private static final String FILENAME_PROC_VERSION = "/proc/version";


    /**
     * Reads a line from the specified file.
     *
     * @param filename the file to read from
     * @return the first line, if any.
     * @throws IOException if the file couldn't be read
     */
    private static String readLine(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename), 256);
        try {
            return reader.readLine();
        } finally {
            reader.close();
        }
    }

    /**
     * 当前activity是否在前台
     *
     * @param context : 当前activity
     * @return 结果
     */
    public static boolean isRunningForeground(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(packageName)) {
            return true;
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
//            String version = info.versionName;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 通过包名查看pid
     */
    public static String getPackageNameByPid(String pid, ActivityManager activityManager) {
        try {
            int iPid = Integer.parseInt(pid);
            List<RunningAppProcessInfo> apps = activityManager.getRunningAppProcesses();
            for (RunningAppProcessInfo info : apps) {
                if (info.pid == iPid) {
                    Log.v(LOG_TAG, "pid 的包名" + info.processName);
                    return info.processName;
                }
            }
        } catch (Exception e) {
            Log.v(LOG_TAG, "未能找到该" + pid + "对应的程序包名！");
        }
        return null;
    }

    /**
     * 查看是否是eng版本的系统。
     */
    public static boolean checkPosVersion() {
//		com.wizarpos.android.core.util.POSSecurity.requireCheckCert()
        boolean isVerify = false;
        try {
//			获得反射对象的类,加载指定的类
            Class<?> pOSSecurity = Class.forName("com.wizarpos.android.core.util.POSSecurity");
            Object resultObj = pOSSecurity.getMethod("requireCheckCert").invoke(pOSSecurity);
            Log.d(LOG_TAG, "checkPosVersion : resultObj = " + resultObj);
            if (resultObj != null) {
                isVerify = Boolean.parseBoolean(resultObj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "checkPosVersion has failed . happen exception ");
            isVerify = false;
        }
        return isVerify;
    }

    public static final long MILLIS_OF_ONE_DAY = 24 * 60 * 60 * 1000;


}
