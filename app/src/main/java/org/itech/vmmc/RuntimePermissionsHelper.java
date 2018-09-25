package org.itech.vmmc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public final class RuntimePermissionsHelper {

    private static final int READ_PHONE_STATE_REQUEST_CODE = 28123;
    private static final int LOCATION_REQUEST_CODE = 28124;
    private static final int EXTERNAL_STORAGE_REQUEST_CODE = 28125;

    private MainActivity activity;

    public RuntimePermissionsHelper(MainActivity activity) {
        this.activity = activity;
    }

    public void requestPermissions() {
        if (ensurePhoneStatePermissions() && ensureLocationPermissions() && ensureStoragePermissions()) {

            this.activity.permissionsGranted();
        }
    }

    private boolean ensurePhoneStatePermissions() {
        if (ContextCompat.checkSelfPermission(this.activity,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    READ_PHONE_STATE_REQUEST_CODE);

            return false;
        }
        return true;
    }

    private boolean ensureLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this.activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);

            return false;
        }
        return true;
    }

    private boolean ensureStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this.activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQUEST_CODE);

            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_REQUEST_CODE:
            case EXTERNAL_STORAGE_REQUEST_CODE:
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    // permission denied - close the app
                    System.exit(0);
                }
            }
        }
    }
}
