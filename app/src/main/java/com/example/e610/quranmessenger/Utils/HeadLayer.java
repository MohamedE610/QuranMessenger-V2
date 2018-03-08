package com.example.e610.quranmessenger.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e610.quranmessenger.Activities.Main2Activity;
import com.example.e610.quranmessenger.R;
import com.example.e610.quranmessenger.Services.HeadService;

/**
 * Creates the head layer view which is displayed directly on window manager.
 * It means that the view is above every application's view on your phone -
 * until another application does the same.
 */
public class HeadLayer extends View{

    private Context mContext;
    private FrameLayout mFrameLayout;
    private WindowManager mWindowManager;

    public HeadLayer(Context context) {
        super(context);

        mContext = context;
        mFrameLayout = new FrameLayout(mContext);

        addToWindowManager();
    }
    boolean isMoved;
    private void addToWindowManager() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT;

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mFrameLayout, params);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Here is the place where you can inject whatever layout you want.
        layoutInflater.inflate(R.layout.head, mFrameLayout);

         isMoved=false;
        // Support dragging the image view
        final ImageView imageView = (ImageView) mFrameLayout.findViewById(R.id.imageView);
        imageView.setVisibility(VISIBLE);
        imageView.setEnabled(true);

        imageView.setOnTouchListener(new OnTouchListener() {
            private int initX, initY;
            private int initTouchX, initTouchY;

            @Override public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getRawX();
                int y = (int)event.getRawY();


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initX = params.x;
                        initY = params.y;
                        initTouchX = x;
                        initTouchY = y;
                        return false;

                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initTouchX);
                        int Ydiff = (int) (event.getRawY() - initTouchY);


                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 5 && Ydiff < 5) {
                            Toast.makeText(mContext, "^_^", Toast.LENGTH_SHORT).show();
                            mContext.startActivity(new Intent(mContext,Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            mContext.stopService(new Intent(mContext,HeadService.class));
                        }
                        return false;

                    case MotionEvent.ACTION_MOVE:
                        params.x = initX + (x - initTouchX);
                        params.y = initY + (y - initTouchY);

                        // Invalidate layout
                        mWindowManager.updateViewLayout(mFrameLayout, params);
                        return false;

                }
                return false;
            }
        });



    }

    /**
     * Removes the view from window manager.
     */
    public void destroy() {
        mWindowManager.removeView(mFrameLayout);
    }


}
