package com.example.pomodoro;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Locale;

public class Session {

    private final Pomodoro pomodoro;
    private final TextView mTextViewCountDown;
    private final Button mButtonFinish;

    private final ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
    private final Integer totalTime;
    private CountDownTimer mCountDownTimer;
    private Integer currentTime;
    private boolean isRunning = false;

    private LocalTime created_at;
    private LocalTime stoped_at;
    private long elapsedMinutes;

    public Session(Pomodoro pomodoro, TextView textView, Button button) {
        this.pomodoro = pomodoro;
        this.mTextViewCountDown = textView;
        this.mButtonFinish = button;
        totalTime = get_total_time();
        currentTime = totalTime;
    }

    private void updateCountDownText(long timeLeftInMillis) {
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        int minutes = (int) ((timeLeftInMillis / (1000 * 60)) % 60);
        int hours = (int) ((timeLeftInMillis / (1000 * 60 * 60)) % 24);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    public void resetCountDownText() {
        updateCountDownText(totalTime);
    }

    public void start() {
        if (isRunning == false) {
            isRunning = true;
            created_at = LocalTime.now();
        }

        mCountDownTimer = new CountDownTimer(currentTime , 1000) {
            @Override
            public void onTick(long milliTillFinish) {
                currentTime = (int)milliTillFinish;
                if (milliTillFinish < 1000) {
                    toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                } else if (milliTillFinish < 4000) {
                    toneGen.startTone(ToneGenerator.TONE_SUP_ERROR, 200);
                }
                Log.v("SESSION:", String.valueOf(currentTime));
                updateCountDownText(milliTillFinish);
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    public void pause() {
        mCountDownTimer.cancel();
    }

    public void finish() {
        mButtonFinish.performClick();
        stoped_at = LocalTime.now();
        isRunning = false;
        mCountDownTimer.cancel();

        elapsedMinutes = Duration.between(created_at, stoped_at).toMinutes();
    }

    public Integer get_total_time() {
        return (pomodoro.getFocus() + pomodoro.getShort_break()) * pomodoro.getSets() + pomodoro.getLong_break() * (pomodoro.getSets() / pomodoro.getSets_until_long_break() - 1);
    }
}
