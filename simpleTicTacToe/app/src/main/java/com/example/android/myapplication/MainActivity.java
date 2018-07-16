package com.example.android.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int[][] ticTac = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
    int player1Score = 0;
    int player2Score = 0;
    int playCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private boolean checkWin(int[][] board,int index){
        int x = index/3;
        int y = index%3;
        if(board[x][y] == -1){
            return false;
        }
        int value = board[x][y];
        int countH = 0;
        int countV = 0;
        int countD = 0;
        for(int i =0; i<3; i++){
            if(board[i][i] == value){
                countD++;
            }
            if(board[i][y] ==value){
                countH++;
            }
            if(board[x][i] == value){
                countV++;
            }
        }
        if(countH == 3 || countV == 3 || countD == 3){
            return true;
        }
        return false;
    }

    private void endGame(){
        for (int i = 0; i < 9; i++) {
            ImageButton imgButt = (ImageButton) findViewById(getResources().getIdentifier(
                    "button" + i,
                    "id",
                    getPackageName()));
            imgButt.setClickable(false);
        }
        int[][] cleared = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
        ticTac = cleared;
    }

    public void buttonClick(View view) {
        Switch s = (Switch) findViewById(R.id.playerSwitch);
        int player = s.isChecked() ? 1 : 0;
        int index = Integer.valueOf(view.getTag().toString());
        int x = index/3;
        int y = index % 3;
        ImageButton img = (ImageButton) view;
        if ((img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.blank).getConstantState())) {
            playCount++;
            if (player == 0) {
                img.setImageResource(R.drawable.o);
                s.setChecked(true);
                ticTac[x][y] = 0;
            } else {
                img.setImageResource(R.drawable.x);
                s.setChecked(false);
                ticTac[x][y] = 1;
            }
        }
        if ((img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.blank2).getConstantState())) {
            playCount++;
            if (player == 0) {
                img.setImageResource(R.drawable.o2);
                s.setChecked(true);
                ticTac[x][y] = 0;
            } else {
                img.setImageResource(R.drawable.x2);
                s.setChecked(false);
                ticTac[x][y] = 1;
            }
        }
        if (checkWin(ticTac, index)) {
            if (player == 0) {
                player1Score++;
                TextView txt = (TextView) findViewById(R.id.player1);
                txt.setText("oScore : " + player1Score);
                playCount = 0;
                Toast.makeText(getApplicationContext(),"Player 1 Wins",Toast.LENGTH_SHORT).show();
            } else {
                player2Score++;
                TextView txt = (TextView) findViewById(R.id.player2);
                txt.setText("xScore : " + player2Score);
                playCount = 0;
                Toast.makeText(getApplicationContext(),"Player 2 Wins",Toast.LENGTH_SHORT).show();

            }
            endGame();
        }
        if(playCount == 9){
            endGame();
            playCount = 0;
            Toast.makeText(getApplicationContext(),"Tie",Toast.LENGTH_SHORT).show();

        }
    }

    public void restart(View view) {
        int ori = view.getResources().getConfiguration().orientation;
        for(int i = 0; i<9; i++){
            ImageButton img = (ImageButton) findViewById(getResources().getIdentifier(
                    "button" + i,
                    "id",
                    getPackageName()));
            if(ori == 1) {
                img.setImageResource(R.drawable.blank);
            }
            else{
                img.setImageResource(R.drawable.blank2);
            }
            img.setClickable(true);
        }
    }
}
