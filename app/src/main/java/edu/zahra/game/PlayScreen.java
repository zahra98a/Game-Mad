package edu.zahra.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class PlayScreen extends AppCompatActivity  implements SensorEventListener {

    // experimental values for hi and lo magnitude limits
    private final double NORTH_MOVE_FORWARD = 9.0;  // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 6.0;  // lower mag limit
    boolean highLimit = false;  // detect high limit

    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;
    Button bRed, bGreen, bYellow, bBlue, fb;
    int[] gameSequence = new int[120];

    int score = 0;
    int arrayIndex = 0;
    int[] arrayB;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        Bundle extras = getIntent().getExtras();
         arrayB = extras.getIntArray("numbers");
        // 1 2 1 1  (N N W N)

        bRed = findViewById(R.id.btnRed);
        bGreen = findViewById(R.id.btnGreen);
        bYellow = findViewById(R.id.btnYellow);
        bBlue = findViewById(R.id.btnBlue);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
     // When the app is brought to the foreground - using app on screen
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
     // App running but not on screen - in the background
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

       // if(arrayIndex < 4) {
            if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) { // south
                flashButton(bRed);
                gameSequence[arrayIndex++] = RED;
                highLimit = true;
            }
            if ((x < NORTH_MOVE_BACKWARD) && (highLimit == true)) { // nourth
                flashButton(bYellow);
                gameSequence[arrayIndex++] = YELLOW;
                highLimit = false;
            }
            if ((y > NORTH_MOVE_FORWARD) && (highLimit == false)) { //west
                flashButton(bGreen);
                gameSequence[arrayIndex++] = GREEN;
                highLimit = true;
            }
            if ((y < NORTH_MOVE_BACKWARD) && (highLimit == true)) { //east
                flashButton(bBlue);
                gameSequence[arrayIndex++] = BLUE;
                highLimit = false;
            }
       // }

       /* for (int i = 0; i< arrayIndex; i++){
        Log.d("game sequence", String.valueOf(gameSequence[i]));
            if (gameSequence[i] == arrayB[i]){
                score =+ 4;

                // Go to MainActivity Screan
                Intent M = new Intent(PlayScreen.this, MainActivity.class);
                startActivity(M);
            }
            else {
                // Go to Game over Screan
                 Intent G = new Intent(PlayScreen.this, GameOver.class);
                 G.putExtra("score", score);
                startActivity(G);
                 }
        }*/

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    private void flashButton(Button button) {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);
            } // end runnable
        };
        handler.postDelayed(r, 600);
    }
}
