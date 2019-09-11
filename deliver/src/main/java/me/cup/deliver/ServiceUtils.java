package me.cup.deliver;

import android.content.Context;
import android.content.Intent;

public class ServiceUtils {
    public static void start(Context context){
        if (context != null) {
            Intent l = new Intent(context, LService.class);
            l.setPackage(context.getPackageName());
            context.startService(l);

            Intent r = new Intent(context, RService.class);
            r.setPackage(context.getPackageName());
            context.startService(r);
        }
    }
}
