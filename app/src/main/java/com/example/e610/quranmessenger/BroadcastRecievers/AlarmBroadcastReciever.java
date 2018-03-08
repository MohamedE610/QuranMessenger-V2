package com.example.e610.quranmessenger.BroadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.e610.quranmessenger.Fragments.SettingsAzkarFragment;
import com.example.e610.quranmessenger.Services.AzkarService;

/**
 * Created by E610 on 2/17/2018.
 */
public class AlarmBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String azkarAction="azkar_b";

        if(azkarAction.equals(intent.getAction())) {
            int azkar_type = intent.getIntExtra("azkar_type",0);
            int h = intent.getIntExtra("h", 0);
            int m = intent.getIntExtra("m", 0);
            int id = intent.getIntExtra("id", 0);
            Intent intent1 = new Intent(context, AzkarService.class);
            intent1.setAction("azkar");
            intent1.putExtra("azkar_type", azkar_type);
            intent1.putExtra("h", h);
            intent1.putExtra("m", m);
            intent1.putExtra("id", id);

            SettingsAzkarFragment.ctx=context;
            SettingsAzkarFragment.startHeadService(h,m,id,azkar_type);
            context.startService(intent1);
        }
    }
}
