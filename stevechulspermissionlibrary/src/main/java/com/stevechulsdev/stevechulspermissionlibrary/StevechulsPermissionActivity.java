package com.stevechulsdev.stevechulspermissionlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StevechulsPermissionActivity extends AppCompatActivity {

    private String[] permissions;
    private StevechulsPermissionListener listener;
    private static Deque<StevechulsPermissionListener> StevechulsPermissionListenerStack;
    private final int REQUEST_PERMISSION_CODE = 90;

    public static void startActivity(Context context, Intent intent, StevechulsPermissionListener listener)
    {
        if(StevechulsPermissionListenerStack == null)
        {
            StevechulsPermissionListenerStack = new ArrayDeque<>();
        }

        StevechulsPermissionListenerStack.push(listener);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        if(getIntent() != null)
        {
            permissions = getIntent().getStringArrayExtra("permissions");
        }

        if(permissions != null && permissions.length > 0)
        {
            if(StevechulsPermissionListenerStack != null)
            {
                this.listener = StevechulsPermissionListenerStack.pop();
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermission(REQUEST_PERMISSION_CODE, permissions);
            }
            else
            {
                finish();
                overridePendingTransition(0, 0);

                listener.onPermissionGranted();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(int requestCode, String... permissions)
    {
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        finish();
        overridePendingTransition(0, 0);

        switch (requestCode)
        {
            case REQUEST_PERMISSION_CODE:
                if(grantResults.length > 0)
                {
                    boolean isDenied = false;

                    for (int i = 0; i < grantResults.length; i++) {
                        if(grantResults[i] == PackageManager.PERMISSION_DENIED)
                        {
                            List<String> deniedPermissions = new ArrayList<>();
                            deniedPermissions.add(permissions[i]);
                            listener.onPermissionDenied(deniedPermissions);
                            isDenied = true;
                        }
                    }

                    if(!isDenied)
                    {
                        listener.onPermissionGranted();
                    }
                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
