package com.wizarpos.mgr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;


public class AidlMgr {

    public static final String TAG = AidlMgr.class.getSimpleName();

    public static final String DESC_SCAN_SERVICE = "com.cloudpos.scanserver.aidl.IScanService";

    private AidlMgr() {
    }

    private static AidlMgr instance;

    public static AidlMgr getInstance() {
        if (instance == null) {
            instance = new AidlMgr();
        }
        return instance;
    }

    protected boolean startConnectService(Context mContext, String packageName, String className, ServiceConnection connection) {
        boolean isSuccess = startConnectService(mContext, new ComponentName(packageName, className), connection);
        return isSuccess;
    }

    protected boolean startConnectService(Context host, ComponentName comp, ServiceConnection connection) {
        Intent intent = new Intent();
        intent.setComponent(comp);
        boolean isSuccess = host.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        host.startService(intent);
        return isSuccess;
    }

    //	public boolean startBindScanService(Context mContext, ServiceConnection connection) {
//		ComponentName comp = new ComponentName(
//				"com.cloudpos.scanserver",
//				"com.cloudpos.scanserver.service.ScannerService");
//		boolean isSuccess = startConnectService(mContext,comp, connection);
//		Log.d(TAG, "bind scanner service");
//		return isSuccess;
//	}
    public boolean startBindSysUpdateService(Context mContext, ServiceConnection connection) {
        ComponentName comp = new ComponentName(
                "com.wizarpos.possys",
                "com.wizarpos.possys.service.PosSystemService");
        boolean isSuccess = startConnectService(mContext, comp, connection);
        Log.d(TAG, "bind scanner service");
        return isSuccess;

    }

}
