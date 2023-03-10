package com.sumit.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> lvl = new ArrayList<>();
    Button btn11, btn12, btn13, btn21, btn22, btn23, btn31, btn32, btn33, newGame, resetGame;
    Spinner level;
    TextView x_score, o_score;
    int x_win=0, o_win=0;
    char arr[][] = new char[3][3];
    HashSet<Integer> data = new HashSet(Arrays.asList(11,12,13,21,22,23,31,32,33));
    char ch ='X';

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        level = findViewById(R.id.level);
        x_score = findViewById(R.id.x_score);
        o_score = findViewById(R.id.o_score);
        btn11 = findViewById(R.id.btn_11);
        btn12 = findViewById(R.id.btn_12);
        btn13 = findViewById(R.id.btn_13);
        btn21 = findViewById(R.id.btn_21);
        btn22 = findViewById(R.id.btn_22);
        btn23 = findViewById(R.id.btn_23);
        btn31 = findViewById(R.id.btn_31);
        btn32 = findViewById(R.id.btn_32);
        btn33 = findViewById(R.id.btn_33);
        newGame = findViewById(R.id.new_game);
        resetGame = findViewById(R.id.reset_game);

        lvl.add("Easy");
        lvl.add("Medium");
        lvl.add("Impossible");
        lvl.add("with Friend");
        ArrayAdapter<String> levelAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lvl);
        level.setAdapter(levelAdapter);

        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(11, true);
            }
        });

        btn12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                input(12, true);
            }
        });

        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(13, true);
            }
        });

        btn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(21, true);
            }
        });

        btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(22, true);
            }
        });

        btn23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(23, true);
            }
        });

        btn31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(31, true);
            }
        });

        btn32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(32, true);
            }
        });

        btn33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(33, true);
            }
        });

        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reset();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                x_score.setText("0");
                o_score.setText("0");
                x_win=0;
                o_win=0;
            }
        });

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    public void input(int position, boolean cpu){
        int x=position/10-1, y=position%10-1;
        if(arr[x][y]!=0 || ch=='0') return;
        arr[x][y]=ch;
        switch(position){
            case 11 : btn11.setText(Character.toString(ch)); break;
            case 21 : btn21.setText(Character.toString(ch)); break;
            case 12 : btn12.setText(Character.toString(ch)); break;
            case 13 : btn13.setText(Character.toString(ch)); break;
            case 22 : btn22.setText(Character.toString(ch)); break;
            case 23 : btn23.setText(Character.toString(ch)); break;
            case 31 : btn31.setText(Character.toString(ch)); break;
            case 32 : btn32.setText(Character.toString(ch)); break;
            case 33 : btn33.setText(Character.toString(ch)); break;
        }
        if(GameOver(x,y)){
            return;
        }
        ch = ch=='X'?'O':'X';
        data.remove(position);
        if(data.size()==0){
            dialog("DRAW");
            return;
        }
        if(level.getSelectedItem()==lvl.get(3) || !cpu){
            return;
        }

        if(level.getSelectedItem()==lvl.get(0)){
            input(random(),false);
        }
        else if(level.getSelectedItem()==lvl.get(1)){
            if(data.size()>7){
                input(random(),false);
            }
            else{
                input(impossible(x,y),false);
            }
        }
        else{
            input(impossible(x,y),false);
        }
    }

    public void reset(){
        arr = new char[3][3];
        data = new HashSet(Arrays.asList(11,12,13,21,22,23,31,32,33));
        btn11.setText("\0");
        btn12.setText("\0");
        btn13.setText("\0");
        btn21.setText("\0");
        btn22.setText("\0");
        btn23.setText("\0");
        btn31.setText("\0");
        btn32.setText("\0");
        btn33.setText("\0");
        for(int i:data){
            setColor(i,"#FFFFFF");
        }
        ch='X';
    }

    int random(){
        Object arr[] = data.toArray();
        Random rnd = new Random();
        return (int)arr[rnd.nextInt(arr.length)];
    }
    int impossible(int x, int y){
        int position=isGameOver(x,y);
        if(position!=0){
            return position;
        }
        switch(data.size()){
            case 8 : position = arr[1][1]=='X'?11:22; break;
            case 6 : {
                if(arr[1][1]==arr[2][2] && arr[1][1]=='X'){
                    position=13;
                }
                else if (arr[0][0]=='X' && (arr[1][2]=='X' || arr[2][1]=='X' || arr[2][2]=='X'))
                {
                    if (arr[1][2]=='X')
                    {
                        position = 13;
                    }
                    else if (arr[2][1]=='X')
                    {
                        position = 31;
                    }
                    else
                    {
                        position=12;
                    }
                }
                else if (arr[0][2]=='X' && (arr[1][0]=='X' || arr[2][1]=='X' || arr[2][0]=='X'))
                {
                    if (arr[1][0]=='X')
                    {
                        position=11;
                    }
                    else if (arr[2][1]=='X')
                    {
                        position=33;
                    }
                    else
                    {
                        position=12;
                    }
                }
                else if (arr[2][0]=='X' && (arr[0][1]=='X' || arr[0][2]=='X' || arr[1][2]=='X'))
                {
                    if (arr[0][1]=='X')
                    {
                        position=11;
                    }
                    else if(arr[1][2]=='X')
                    {
                        position=33;
                    }
                    else
                    {
                        position=12;
                    }
                }
                else if (arr[2][2]=='X' && (arr[1][0]=='X' || arr[0][0]=='X' || arr[0][1]=='X'))
                {
                    if (arr[1][0]=='X')
                    {
                        position=31;
                    }
                    else if(arr[0][1]=='X')
                    {
                        position=13;
                    }
                    else
                    {
                        position=12;
                    }
                }
                else if(arr[0][1]=='X' && (arr[1][0]=='X' || arr[1][2]=='X'))
                {
                    if (arr[1][0]=='X')
                    {
                        position=11;
                    }
                    else
                    {
                        position=13;
                    }
                }
                else if (arr[2][1]=='X' && (arr[1][0]=='X' || arr[1][2]=='X'))
                {
                    if(arr[1][0]=='X')
                    {
                        position=31;
                    }
                    else
                    {
                        position=33;
                    }
                }
                else if (arr[0][1]=='X' && arr[2][1]=='X')
                {
                    position=21;
                }
                else if (arr[1][0]=='X' && arr[1][2]=='X')
                {
                    position=12;
                }
                else
                {
                    position=random();
                }
                break;
            }
            default:
                position = random();
        }
        return position;
    }

    int isGameOver(int x, int y){
        if((arr[2][2]==0 && arr[0][0]==arr[1][1] && arr[0][0]=='O') || (arr[0][0]==0 && arr[2][2]==arr[1][1] && arr[2][2]=='O') || (arr[1][1]==0 && arr[0][0]==arr[2][2] && arr[0][0]=='O')){
            return arr[0][0]==0?11:arr[1][1]==0?22:33;
        }
        if((arr[0][2]==0 && arr[2][0]==arr[1][1] && arr[1][1]=='O') || (arr[2][0]==0 && arr[0][2]==arr[1][1] && arr[1][1]=='O') || (arr[1][1]==0 && arr[2][0]==arr[0][2] && arr[0][2]=='O')){
            return arr[0][2]==0?13:arr[1][1]==0?22:31;
        }
        for(int i=0; i<3; i++){
            if((arr[i][0]==0 && arr[i][1]==arr[i][2] && arr[i][1]=='O') || (arr[i][1]==0 && arr[i][0]==arr[i][2] && arr[i][0]=='O') || (arr[i][2]==0 && arr[i][1]==arr[i][0] && arr[i][1]=='O')){
                for(int j=0; j<3; j++){
                    if(arr[i][j]==0){
                        return (i+1)*10+(j+1);
                    }
                }
            }
            if((arr[0][i]==0 && arr[1][i]==arr[2][i] && arr[1][i]=='O') || (arr[1][i]==0 && arr[0][i]==arr[2][i] && arr[0][i]=='O') || (arr[2][i]==0 && arr[1][i]==arr[0][i] && arr[1][i]=='O')){
                for(int j=0; j<3; j++){
                    if(arr[j][i]==0){
                        return (j+1)*10+(i+1);
                    }
                }
            }
        }

        if((arr[2][2]==0 && arr[0][0]==arr[1][1] && arr[0][0]=='X') || (arr[0][0]==0 && arr[2][2]==arr[1][1] && arr[2][2]=='X') || (arr[1][1]==0 && arr[0][0]==arr[2][2] && arr[0][0]=='X')){
            return arr[0][0]==0?11:arr[1][1]==0?22:33;
        }
        if((arr[0][2]==0 && arr[2][0]==arr[1][1] && arr[1][1]=='X') || (arr[2][0]==0 && arr[0][2]==arr[1][1] && arr[1][1]=='X') || (arr[1][1]==0 && arr[2][0]==arr[0][2] && arr[0][2]=='X')){
            return arr[0][2]==0?13:arr[1][1]==0?22:31;
        }
        if((arr[x][0]==0 && arr[x][1]==arr[x][2] && arr[x][1]=='X') || (arr[x][1]==0 && arr[x][0]==arr[x][2] && arr[x][0]=='X') || (arr[x][2]==0 && arr[x][1]==arr[x][0] && arr[x][1]=='X')){
            for(int j=0; j<3; j++){
                if(arr[x][j]==0){
                    return (x+1)*10+(j+1);
                }
            }
        }
        if((arr[0][y]==0 && arr[1][y]==arr[2][y] && arr[1][y]=='X') || (arr[1][y]==0 && arr[0][y]==arr[2][y] && arr[0][y]=='X') || (arr[2][y]==0 && arr[1][y]==arr[0][y] && arr[1][y]=='X')){
            for(int j=0; j<3; j++){
                if(arr[j][y]==0){
                    return (j+1)*10+(y+1);
                }
            }
        }
        return 0;
    }
    boolean GameOver(int x, int y){
        if(arr[x][0]!=0 && arr[x][0]==arr[x][1] && arr[x][1]==arr[x][2]){
            return winning(x,1,x,1,x,2);
        }
        if(arr[0][y]!=0 && arr[0][y]==arr[1][y] && arr[1][y]==arr[2][y]){
            return winning(0,y,1,y,2,y);
        }
        if(arr[1][1]!=0){
            if(arr[0][0]==arr[1][1] && arr[2][2]==arr[1][1]){
                return winning(0,0,1,1,2,2);
            }
            if(arr[0][2]==arr[1][1] && arr[2][0]==arr[1][1]){
                return winning(0,2,1,1,2,0);
            }
        }
       return false;
    }
    boolean winning(int a, int b, int m, int n, int x, int y){
        if(ch=='X'){
            x_win++;
            x_score.setText(""+x_win);
        }
        else{
            o_win++;
            o_score.setText(""+o_win);
        }
        setColor((a+1)*10+(b+1),"#F1C40F");
        setColor((m+1)*10+(n+1),"#F1C40F");
        setColor((x+1)*10+(y+1),"#F1C40F");
        dialog("WINNER IS "+ch);
        return true;
    }
     void dialog(String str){
        ch='0';
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.game_over_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView winner = dialog.findViewById(R.id.winner_nqme);
        winner.setText(str);
        Button reset_button = dialog.findViewById(R.id.reset);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                dialog.dismiss();
            }
        });
        dialog.show();
     }

     void setColor(int btn,String color){
        switch(btn){
            case 11: btn11.setTextColor(Color.parseColor(color)); break;
            case 12: btn12.setTextColor(Color.parseColor(color)); break;
            case 13: btn13.setTextColor(Color.parseColor(color)); break;
            case 21: btn21.setTextColor(Color.parseColor(color)); break;
            case 22: btn22.setTextColor(Color.parseColor(color)); break;
            case 23: btn23.setTextColor(Color.parseColor(color)); break;
            case 31: btn31.setTextColor(Color.parseColor(color)); break;
            case 32: btn32.setTextColor(Color.parseColor(color)); break;
            case 33: btn33.setTextColor(Color.parseColor(color)); break;
        }
     }
}