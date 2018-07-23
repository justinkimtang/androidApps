package com.example.android.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int player1Score = 0;
    int player2Score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("player1Score",player1Score);
        outState.putInt("player2Score",player2Score);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        player1Score = savedInstanceState.getInt("player1Score",0);
        player2Score = savedInstanceState.getInt("player2Score",0);
        TextView txt1 = (TextView) findViewById(R.id.player1);
        TextView txt2 = (TextView) findViewById(R.id.player2);
        txt1.setText("OScore: " + player1Score);
        txt2.setText("XScore: " + player2Score);
    }

    public void clear(View view){
        ticTacArea fragment = (ticTacArea) getSupportFragmentManager().findFragmentById(R.id.playArea);
        fragment.clear();
    }
    public void updateScore(int player){
        TextView txt1 = (TextView) findViewById(R.id.player1);
        TextView txt2 = (TextView) findViewById(R.id.player2);
        TextView txt = player == 0 ? txt1 : txt2;
        if(player == 0){
            player1Score++;
        }
        if(player == 1){
            player2Score++;
        }
        int score = player == 0 ? player1Score : player2Score;
        txt.setText("oScore : " + score);
        Toast.makeText(getApplicationContext(),"Player " + (player + 1) + " Wins",Toast.LENGTH_SHORT).show();
    }
}
