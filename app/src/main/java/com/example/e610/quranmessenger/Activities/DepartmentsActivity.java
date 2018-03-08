package com.example.e610.quranmessenger.Activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.e610.quranmessenger.R;
import com.example.e610.quranmessenger.Utils.MySharedPreferences;

import static com.example.e610.quranmessenger.Activities.Main2Activity.FACEBOOK_PAGE_ID;
import static com.example.e610.quranmessenger.Activities.Main2Activity.FACEBOOK_URL;
import static com.example.e610.quranmessenger.Activities.Main2Activity.viewPager;

public class DepartmentsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    public static AppBarLayout appBarLayout;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    int PERMISSION_ALL = 11;
    String[] PERMISSIONS = {Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.INTERNET, Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.custom_bar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        boolean isNeedPermissions = hasPermissions(this, PERMISSIONS);
        if (!isNeedPermissions) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        LinearLayout fhrsLinear = (LinearLayout) findViewById(R.id.fhrs);
        fhrsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentsActivity.this, FahrsActivity.class));
            }
        });

        LinearLayout moveToLinear = (LinearLayout) findViewById(R.id.move_to);
        moveToLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentsActivity.this, Main2Activity.class));
                String pageNumber = MySharedPreferences.getUserSetting("pageNumber");
                if(pageNumber!= null){
                try {
                    viewPager.setCurrentItem(Integer.valueOf(pageNumber));
                } catch (Exception e) {
                    viewPager.setCurrentItem(0);
                }
                }
                else
                    viewPager.setCurrentItem(0);

            }
        });
        LinearLayout azkarLinear = (LinearLayout) findViewById(R.id.azkarLinear);
        azkarLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentsActivity.this, AzkarActivity.class));
            }
        });

        LinearLayout settingLinear = (LinearLayout) findViewById(R.id.settingLinear);
        settingLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentsActivity.this, SettingsActivity.class);
                intent.setAction("main_settings");
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_alfahrs) {
            // Handle the camera action
            startActivity(new Intent(this, FahrsActivity.class));
            finish();
        } else if (id == R.id.nav_alazan) {
            startActivity(new Intent(this, PrayerTimesActivity.class));
        } else if (id == R.id.nav_alazkar) {
            startActivity(new Intent(this, AzkarActivity.class));

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.setAction("main_settings");
            startActivity(intent);
        } else if (id == R.id.nav_facebook) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getFacebookPageURL(this);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        } else if (id == R.id.nav_insta) {
            Uri uri = Uri.parse("https://www.instagram.com/quranmessengerofficial/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");
            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/quranmessengerofficial/")));
            }
        } else if (id == R.id.nav_share) {
            share();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = FACEBOOK_URL;

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onBackPressed() {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DepartmentsActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.logo);
        builder.setMessage("هل تريد الخروج من التطبيق؟")
                .setCancelable(false)
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
