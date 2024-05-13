package com.wizarpos.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wizarpos.mgr.AidlMgr;
import com.wizarpos.possys.aidl.IPosSystemService;
import com.wizarpos.util.TextViewUtil;

public class MainActivity extends com.wizarpos.activity.ConstantActivity implements OnClickListener {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_run3 = (Button) this.findViewById(R.id.btn_run3);
        Button btn_run4 = (Button) this.findViewById(R.id.btn_run4);
        Button btn_run5 = (Button) this.findViewById(R.id.btn_run5);
//		Button btn_run6 = (Button) this.findViewById(R.id.btn_run6);
        log_text = (TextView) this.findViewById(R.id.text_result);
        log_text.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.settings).setOnClickListener(this);
        btn_run3.setOnClickListener(this);
        btn_run4.setOnClickListener(this);
        btn_run5.setOnClickListener(this);
//		btn_run6.setOnClickListener(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == DEFAULT_LOG) {
                    log_text.append("\t" + msg.obj + "\n");
                } else if (msg.what == SUCCESS_LOG) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoBlueTextView(log_text, str);
                } else if (msg.what == FAILED_LOG) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoRedTextView(log_text, str);
                } else if (msg.what == CLEAR_LOG) {
                    log_text.setText("");
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View arg0) {
        int index = arg0.getId();
        if (index == R.id.btn_run3) {
            testUpdate();
        } else if (index == R.id.btn_run4) {

        } else if (index == R.id.btn_run5) {

        }/*else if (index == R.id.btn_run6) {
			
		}*/ else if (index == R.id.settings) {
            log_text.setText("");
        }
    }

    private void testUpdate() {
        boolean result = AidlMgr.getInstance().startBindSysUpdateService(this, new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IPosSystemService sysUpdateService = IPosSystemService.Stub.asInterface(service);
                try {
                    writerInLog("The upgrade process has be started");
                    int result = sysUpdateService.updateSystem("/sdcard/test.zip");
                    writerInLog("The upgrade process has be finshed， result is " + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    MainActivity.this.unbindService(this);
                    ;
                }
            }
        });
        writerInLog("start bind service , success is " + result);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onDestory() {
        super.onDestroy();
    }

}
