package org.itech.vmmc;

import android.os.AsyncTask;

import org.itech.vmmc.APICommunication.putMySQLTableVolley;

/**
 * Created by Caleb on 2018-03-15.
 */

public class SyncAuditThread extends AsyncTask<String, String, String> {

    putMySQLTableVolley tableSyncAuditPutter;

    public SyncAuditThread(putMySQLTableVolley putterTable) {
        tableSyncAuditPutter = putterTable;
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            //Thread.sleep(4000); // 4 secs
            //Thread.sleep(10000);
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tableSyncAuditPutter.putSyncAuditTable();
        return null;
    }
}
