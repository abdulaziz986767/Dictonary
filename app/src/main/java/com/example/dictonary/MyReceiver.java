package com.example.dictonary;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MyReceiver extends BroadcastReceiver {

    @SuppressLint({"UnsafeProtectedBroadcastReceiver", "CommitPrefEdits"})
    @Override
    public void onReceive(Context context, Intent intent) {

        Constraints c = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        OneTimeWorkRequest wr = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(c)
                .build();

        WorkManager.getInstance(context).enqueue(wr);

        context.unregisterReceiver(this);
    }
}