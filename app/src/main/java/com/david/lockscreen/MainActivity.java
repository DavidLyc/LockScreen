package com.david.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ComponentName mAdminName;
    DevicePolicyManager mDPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdminName = new ComponentName(this, BroadcastReceiver.class);
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (!mDPM.isAdminActive(mAdminName)) {
            showAdminManagement(mAdminName);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        lockScreen();
        this.finish();
    }

    //执行锁屏
    public void lockScreen() {
        if (mDPM.isAdminActive(mAdminName)) {
            mDPM.lockNow();
        }
    }

    //激活设备管理器
    private void showAdminManagement(ComponentName mAdminName) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "activity device");
        startActivityForResult(intent, 0);
    }
}
