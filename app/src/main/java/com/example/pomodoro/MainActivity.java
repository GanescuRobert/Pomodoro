package com.example.pomodoro;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final int MIN_DISTANCE = 150;
    private final Pomodoro selected_pomodoro = new Pomodoro();
    private final ArrayList<Pomodoro> pomodoros = new ArrayList<>(Arrays.asList(selected_pomodoro));
    private final ArrayList<Session> sessions = new ArrayList<>();
    private GestureDetector gestureDetector;
    private Button mButtonStart;
    private Button mButtonPause;
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.values[0] + sensorEvent.values[1] + sensorEvent.values[2] != 0.0f) {
                mButtonPause.performClick();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    private Button mButtonFinish;
    private TextView mTextViewCountDown;
    private boolean isRunning = false;
    private Session session;
    private ViewPager viewPager;
    private SwipeAdapter myAdapter;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(MainActivity.this, this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStart = findViewById(R.id.start_button);
        mButtonPause = findViewById(R.id.pause_button);
        mButtonFinish = findViewById(R.id.finish_button);

        //pick pomo
        viewPager = findViewById(R.id.viewPager);
        loadCards();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String title = pomodoros.get(position).getName();
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mButtonStart.performClick();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mButtonPause.performClick();
        mSensorManager.unregisterListener(sensorEventListener);
    }

    private void sendNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification");
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, builder.build());
    }

    private void loadCards() {

        if (getIntent().hasExtra("key")) {
            Pomodoro pmd = (Pomodoro) getIntent().getSerializableExtra("key");
            pomodoros.add(pmd);
        }

        myAdapter = new SwipeAdapter(this, pomodoros);
        viewPager.setAdapter(myAdapter);
    }

    public void openStatisticsActivity(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.swipe_left_start, R.anim.swipe_left_end);
    }

    public void openCreatePomodoroActivity(View view) {
        Intent intent = new Intent(this, CreatePomodoroActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.swipe_right_start, R.anim.swipe_right_end);
    }

    public void startTimer(View view) {
        if (isRunning == false) {
            isRunning = true;
            session = new Session(selected_pomodoro, mTextViewCountDown, mButtonFinish);
        }
        session.start();


        mButtonStart.setVisibility(View.GONE);
        mButtonPause.setVisibility(View.VISIBLE);
        mButtonFinish.setVisibility(View.GONE);
    }

    public void pauseTimer(View view) {
        session.pause();

        mButtonStart.setVisibility(View.VISIBLE);
        mButtonPause.setVisibility(View.GONE);
        mButtonFinish.setVisibility(View.VISIBLE);
    }

    public void finishTimer(View view) {
        isRunning = false;
        session.resetCountDownText();
        sessions.add(session);
        sendNotification("Finish pomodoro session", "Your " + selected_pomodoro.getName() + " finish!");
        mButtonStart.setVisibility(View.VISIBLE);
        mButtonPause.setVisibility(View.GONE);
        mButtonFinish.setVisibility(View.GONE);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x1 = 0, x2 = 0;
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float valueX = x2 - x1;

                if (Math.abs(valueX) > MIN_DISTANCE) {
                    // Swipe Right
                    if (valueX > 0) {
                        Intent intent = new Intent(this, StatisticsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.swipe_left_start, R.anim.swipe_left_end);
                    }
                    //Swipe Left
                    else {
                        Intent intent = new Intent(this, CreatePomodoroActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.swipe_right_start, R.anim.swipe_right_end);
                    }
                }

                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


}