package com.stevechulsdev.stevechulspermissionlibrary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class StevechulsPermissionManager {

    private static StevechulsPermissionManager stevechulsPermissionManager;
    private Context context;
    private StevechulsPermissionListener listener;
    private String[] permissions;

    private StevechulsPermissionManager(@NonNull Context context, @NonNull StevechulsPermissionListener listener, @NonNull String... permissions)
    {
        this.context = context;
        this.listener = listener;
        this.permissions = permissions;

        checkPermission();
    }

    public static StevechulsPermissionManager getInstance(Context context, StevechulsPermissionListener listener, String... permissions)
    {
        return new StevechulsPermissionManager(context, listener, permissions);
    }

    private void checkPermission()
    {
        Intent intent = new Intent(context, StevechulsPermissionActivity.class);
        intent.putExtra("permissions", permissions);
        StevechulsPermissionActivity.startActivity(context, intent, listener);
    }
}
