package com.example.dictonary;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.dictonary.db.Database;
import com.example.dictonary.db.Entity;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.POWER_SERVICE;

public class MyWorker extends Worker {

    private final Context context;
    private SharedPreferences pref;
    private int i;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        List<Entity> list = Database
                .getInstance(context.getApplicationContext())
                .dao()
                .getAllQuestions();

        pref = PreferenceManager.getDefaultSharedPreferences(context);
        i = pref.getInt("id", 0);

        if ((list.size() - 1) < i) {
            i = 0;
        }

        if (list.size() > 0) {
            String eng = list.get(i).getEng();
            String uzb = list.get(i).getUzb();
            showNotification(eng, uzb);
        }

        return Result.success();
    }

    private void showNotification(String eng, String uzb) {
        createNotificationsChannel();

        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);

        assert powerManager != null;
        if (!powerManager.isInteractive()) {
            @SuppressLint("InvalidWakeLockTag")
            PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MH24_SCREENLOCK");
            wl.acquire(10000);
            @SuppressLint("InvalidWakeLockTag")
            PowerManager.WakeLock wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MH24_SCREENLOCK");
            wl_cpu.acquire(10000);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id01");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle(eng);
        builder.setContentText(uzb);
        builder.setCategory(Notification.CATEGORY_SERVICE);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(1, builder.build());

        pref.edit().putInt("id", (i+1)).apply();
    }

    private void createNotificationsChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Dictionary chanel";
            String description = "My notification description";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("channel_id01", name, importance);
            channel.setDescription(description);

            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(channel);
        }
    }
}
