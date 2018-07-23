package com.example.android.myapplication;


import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ticTacArea extends Fragment {
    int[][] ticTac = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
    int playCount = 0;

    public ticTacArea() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tic_tac_area, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("playCount",playCount);
        int[] outArray = new int[9];
        for(int i = 0; i< 9; i++){
            outArray[i] = ticTac[i/3][i%3];
        }
        outState.putIntArray("outArray",outArray);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null) {
            playCount = savedInstanceState.getInt("playCount", 0);
            int[] inArray = savedInstanceState.getIntArray("outArray");
            getTicTacState(inArray);
        }

        final Activity act = (Activity) getActivity();
        for(int i = 0; i<9; i++){
            int id = act.getResources().getIdentifier("button" + i,"id",act.getApplicationContext().getPackageName());
            ImageButton ib = (ImageButton) act.findViewById(id);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Switch s = (Switch) act.findViewById(R.id.playerSwitch);
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
                    if (checkWin(ticTac, index)) {
                        if (player == 0) {
                            ((MainActivity)getActivity()).updateScore(0);
                        } else {
                            ((MainActivity)getActivity()).updateScore(1);
                        }
                        endGame();
                    }
                    if(playCount == 9){
                        endGame();
                        playCount = 0;
                        Toast.makeText(act.getApplicationContext(),"Tie",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    public void getTicTacState(int[] state){
        Activity act = (Activity) getActivity();
        for (int i = 0; i < 9; i++) {
            ImageButton imgButt = (ImageButton) act.findViewById(getResources().getIdentifier(
                    "button" + i,
                    "id",
                    act.getPackageName()));
            if(state[i] == 0){
                imgButt.setImageResource(R.drawable.o);
            } else if(state[i] == 1){
                imgButt.setImageResource(R.drawable.x);
            } else{
                imgButt.setImageResource(R.drawable.blank);
            }
            ticTac[i/3][i%3] = state[i];
        }
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
        Activity act = (Activity) getActivity();
        for (int i = 0; i < 9; i++) {
            ImageButton imgButt = (ImageButton) act.findViewById(getResources().getIdentifier(
                    "button" + i,
                    "id",
                    act.getPackageName()));
            imgButt.setClickable(false);
        }
        int[][] cleared = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
        ticTac = cleared;
        playCount = 0;
    }
    public void clear() {
        Activity act = (Activity) getActivity();
        for(int i = 0; i<9; i++){
            ImageButton img = (ImageButton) act.findViewById(getResources().getIdentifier(
                    "button" + i,
                    "id",
                    act.getPackageName()));
                img.setImageResource(R.drawable.blank);
            img.setClickable(true);
            int[][] cleared = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
            ticTac = cleared;
            playCount = 0;
        }
    }
}
