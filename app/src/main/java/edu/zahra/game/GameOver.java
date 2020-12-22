package edu.zahra.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

   TextView Score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

      Score =  findViewById(R.id.tvScore);

        int Uscore = getIntent().getIntExtra("score",-1);

        Score.setText(String.valueOf("Your Score is: " + Uscore));

    }

    public void doPlayAgain(View view) {

        //switch to Main activity
        Intent playAgain = new Intent(view.getContext(), MainActivity.class);
        startActivity(playAgain);
    }

    public void doHighScore(View view) {
        //switch to High Score activity
        Intent highScore = new Intent(view.getContext(), HighScore.class);
        startActivity(highScore);
    }
}