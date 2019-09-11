package me.cup.deliver;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class RService extends Service {
    public static final String ALIVE = "android.intent.action.ALIVE";
    private static final int ID = 1000;
    private RDeliver rdeliver;
    private RDeliverConnection connection;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return rdeliver;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        rdeliver = new RDeliver();
        connection = new RDeliverConnection();
        startForeground(ID,new Notification());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Intent intent = new Intent(this,  LService.InnerService.class);
            intent.setPackage(getPackageName());
            startService(intent);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i = new Intent(RService.this, LService.class);
        i.setPackage(getPackageName());
        bindService(i,connection,BIND_AUTO_CREATE);
        sendBroadcast(new Intent(ALIVE));
        return super.onStartCommand(intent, flags, startId);
    }

    class RDeliverConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Intent intent = new Intent(RService.this, LService.class);
            intent.setPackage(getPackageName());
            startService(intent);
            bindService(intent,connection,BIND_AUTO_CREATE);
        }
    }

    public static class InnerService extends Service{
        @Override
        public void onCreate() {
            super.onCreate();
            startForeground(ID,new Notification());
            stopSelf();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
