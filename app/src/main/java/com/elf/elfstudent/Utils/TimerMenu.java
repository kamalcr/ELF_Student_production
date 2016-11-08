package com.elf.elfstudent.Utils;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nandhu on 8/11/16.
 * A custom timer class used in {@link com.elf.elfstudent.Activities.TestWriteActivity }
 * as ActionBar countdown timer
 *
 * see source {@linkplain 'http://stackoverflow.com/questions/31120802/create-a-countdown-timer-in-android-action-bar'
 *   for further refererence
 * }
 */

public class TimerMenu extends TextView implements Runnable {
    long startTime=0L;
    long overallDuration=0L;
    long warningDuration=0L;
    boolean isRunning=false;

    public TimerMenu(Context context) {
        super(context);

        reset();
    }

    public TimerMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        reset();
    }

    @Override
    public void run() {
        isRunning=true;

        long elapsedSeconds=
                (SystemClock.elapsedRealtime() - startTime) / 1000;

        if (elapsedSeconds < overallDuration) {
            long remainingSeconds=overallDuration - elapsedSeconds;
            long minutes=remainingSeconds / 60;
            long seconds=remainingSeconds - (60 * minutes);

            setText(String.format("%d:%02d", minutes, seconds));

            if (warningDuration > 0 && remainingSeconds < warningDuration) {
                setTextColor(0xFFFF6600); // orange
            }
            else {
                setTextColor(Color.WHITE);
            }

            postDelayed(this, 1000);
        }
        else {
            setText("0:00");
            setTextColor(Color.RED);
            isRunning=false;
        }
    }

    public void reset() {
        startTime= SystemClock.elapsedRealtime();
        setText("--:--");
        setTextColor(Color.WHITE);
    }

    public void stop() {
        removeCallbacks(this);
        isRunning=false;
    }

    public boolean isRunning() {
        return(isRunning);
    }

    public void setOverallDuration(long overallDuration) {
        this.overallDuration=overallDuration;
    }

    public void setWarningDuration(long warningDuration) {
        this.warningDuration=warningDuration;
    }
}
