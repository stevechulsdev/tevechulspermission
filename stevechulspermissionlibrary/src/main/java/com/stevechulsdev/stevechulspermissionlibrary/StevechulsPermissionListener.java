package com.stevechulsdev.stevechulspermissionlibrary;

import java.util.List;

public interface StevechulsPermissionListener {

    void onPermissionGranted();

    void onPermissionDenied(List<String> deniedPermissions);
}
