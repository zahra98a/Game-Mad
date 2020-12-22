package edu.zahra.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bRed, bGreen, bYellow, bBlue, fb;
    int n = 0, sequenceCount = 4;

        private final int BLUE = 1;
        private final int RED = 2;
        private final int YELLOW = 3;
        private final int GREEN = 4;
        private Object mutex = new Object();
        int[] gameSequence = new int[120];
        int arrayIndex = 0;

        CountDownTimer ct = new CountDownTimer(6900,  1900) {

            public void onTick(long millisUntilFinished) {
               // mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
               oneButton();
            }



            public void onFinish() {
              // mTextField.setText("done!");
                // we now have the game sequence

                for (int i = 0; i< arrayIndex; i++)
                    Log.d("game sequence", String.valueOf(gameSequence[i]));

                // start next activity

                // put the sequence into the next activity
                // stack overglow https://stackoverflow.com/questions/3848148/sending-arrays-with-intent-putextra

                Intent i = new Intent(MainActivity.this, PlayScreen.class);
                i.putExtra("numbers", gameSequence);
                startActivity(i);

                // start the next activity
                // int[] arrayB = extras.getIntArray("numbers");
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bRed = findViewById(R.id.btnRed);
        bGreen = findViewById(R.id.btnGreen);
        bYellow = findViewById(R.id.btnYellow);
        bBlue = findViewById(R.id.btnBlue);

    }
    public void doPlay(View view) {
      ct.start();
    }
    private void oneButton() {
        n = getRandom(sequenceCount);

        Toast.makeText(this, "Number = " + n, Toast.LENGTH_SHORT).show();

        switch (n) {
            case 1:
                flashButton(bBlue);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(bRed);
                gameSequence[arrayIndex++] = RED;
                break;
            case 3:
                flashButton(bYellow);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            case 4:
                flashButton(bGreen);
                gameSequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }   // end switch
    }
    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
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
                handler1.postDelayed(r1, 700);
            } // end runnable
        };
        handler.postDelayed(r, 700);
    }
  }