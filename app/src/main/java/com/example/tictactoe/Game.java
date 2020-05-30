package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Game extends AppCompatActivity {

    ConstraintLayout gridlayout;
    LinearLayout grid;
    Button start, exit, playAgain;
    String XName, OName;
    char player;
    String[][] game = new String[3][3];
    Canvas mCanvas;
    Bitmap mBitmap;
    Paint xLine = new Paint();
    Paint yLine = new Paint();
    int width, height, gridW, gridH, roundCount = 0;
    ImageView img00, img01, img02, img10, img11, img12, img20, img21, img22;
    TextView playerX, playerO, playerTurn, turnText;
    MediaPlayer touchSound = new MediaPlayer();
    MediaPlayer winSound = new MediaPlayer();
    MediaPlayer drawSound = new MediaPlayer();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        touchSound = MediaPlayer.create(this, R.raw.popsound);
        winSound = MediaPlayer.create(this, R.raw.tada);
        drawSound = MediaPlayer.create(this, R.raw.fillsound);

        grid = findViewById(R.id.gridLayout);
        grid.setVisibility(View.INVISIBLE);
        gridlayout = findViewById(R.id.gameLayout);

        start = findViewById(R.id.gameStart);
        exit = findViewById(R.id.exit);
        playAgain = findViewById(R.id.playAgain);
        exit.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);

        img00 = findViewById(R.id.image00);
        img01 = findViewById(R.id.image01);
        img02 = findViewById(R.id.image02);
        img10 = findViewById(R.id.image10);
        img11 = findViewById(R.id.image11);
        img12 = findViewById(R.id.image12);
        img20 = findViewById(R.id.image20);
        img21 = findViewById(R.id.image21);
        img22 = findViewById(R.id.image22);

        playerX = findViewById(R.id.tv_PLAYERA);
        playerO = findViewById(R.id.tv_PLAYERB);
        playerTurn = findViewById(R.id.turns);
        turnText = findViewById(R.id.turnText);

        Intent intent = getIntent();
        XName = intent.getStringExtra("XName");
        OName = intent.getStringExtra("OName");
        playerX.setText(XName);
        playerO.setText(OName);
        playerTurn.setText(XName);
        player = 'X';
        newGame();

        //set paint
        xLine.setStrokeWidth(10);
        yLine.setStrokeWidth(10);
        xLine.setColor(Color.BLUE);
        yLine.setColor(Color.RED);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //measurements

                grid.setVisibility(View.VISIBLE);
                gridlayout.removeView(start);
                width = grid.getWidth();
                height = grid.getHeight();
                gridW = (width - 10) / 3;
                gridH = (height - 10) / 3;

                mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mBitmap);
            }
        });

        img00.setOnTouchListener(imgClicked);
        img01.setOnTouchListener(imgClicked);
        img02.setOnTouchListener(imgClicked);
        img10.setOnTouchListener(imgClicked);
        img11.setOnTouchListener(imgClicked);
        img12.setOnTouchListener(imgClicked);
        img20.setOnTouchListener(imgClicked);
        img21.setOnTouchListener(imgClicked);
        img22.setOnTouchListener(imgClicked);

    }

    View.OnTouchListener imgClicked = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView img_pressed = (ImageView) v;
            touchSound.start();
            int id = img_pressed.getId();
            img_pressed.setEnabled(false);
            if(player == 'X') {
                if (id == R.id.image00) {
                    game[0][0] = "X";
                } else if (id == R.id.image01) {
                    game[0][1] = "X";
                } else if (id == R.id.image02) {
                    game[0][2] = "X";
                } else if (id == R.id.image10) {
                    game[1][0] = "X";
                } else if (id == R.id.image11) {
                    game[1][1] = "X";
                } else if (id == R.id.image12) {
                    game[1][2] = "X";
                } else if (id == R.id.image20) {
                    game[2][0] = "X";
                } else if (id == R.id.image21) {
                    game[2][1] = "X";
                } else if (id == R.id.image22) {
                    game[2][2] = "X";
                }
            }

            if(player == 'O'){
                if (id == R.id.image00) {
                    game[0][0] = "O";
                } else if (id == R.id.image01) {
                    game[0][1] = "O";
                } else if (id == R.id.image02) {
                    game[0][2] = "O";
                } else if (id == R.id.image10) {
                    game[1][0] = "O";
                } else if (id == R.id.image11) {
                    game[1][1] = "O";
                } else if (id == R.id.image12) {
                    game[1][2] = "O";
                } else if (id == R.id.image20) {
                    game[2][0] = "O";
                } else if (id == R.id.image21) {
                    game[2][1] = "O";
                } else if (id == R.id.image22) {
                    game[2][2] = "O";
                }
            }
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                if(player == 'X') {
                    img_pressed.setImageResource(R.drawable.x);
                    roundCount++;
                    changePlayer();
                    if(checkEndedRow() == 1 || checkedEndedDiagonal() == 3 || checkEndedCol() == 2) {
                        turnText.setText("Winner :");
                        winSound.start();
                        playerTurn.setText(XName);
                        img00.setEnabled(false);
                        img01.setEnabled(false);
                        img02.setEnabled(false);
                        img10.setEnabled(false);
                        img11.setEnabled(false);
                        img12.setEnabled(false);
                        img20.setEnabled(false);
                        img21.setEnabled(false);
                        img22.setEnabled(false);
                    }
                }
                else if(player == 'O'){
                    img_pressed.setImageResource(R.drawable.o);
                    roundCount++;
                    changePlayer();
                    if(checkEndedCol() == 2 || checkEndedRow() == 1  || checkedEndedDiagonal() == 3  ){
                        turnText.setText("Winner :");
                        winSound.start();
                        playerTurn.setText(OName);
                        img00.setEnabled(false);
                        img01.setEnabled(false);
                        img02.setEnabled(false);
                        img10.setEnabled(false);
                        img11.setEnabled(false);
                        img12.setEnabled(false);
                        img20.setEnabled(false);
                        img21.setEnabled(false);
                        img22.setEnabled(false);
                    }
                }
                else if(roundCount == 9) {
                    drawSound.start();
                    turnText.setText("Result");
                    playerTurn.setText("Draw");
                    img00.setEnabled(false);
                    img01.setEnabled(false);
                    img02.setEnabled(false);
                    img10.setEnabled(false);
                    img11.setEnabled(false);
                    img12.setEnabled(false);
                    img20.setEnabled(false);
                    img21.setEnabled(false);
                    img22.setEnabled(false);
                }
            }

            exit.setVisibility(View.VISIBLE);
            playAgain.setVisibility(View.VISIBLE);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Game.this, MainActivity.class);
                    startActivity(i);
                }
            });
            playAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartGame();
                }
            });
            return true;
        }
    };

    public void newGame(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                game[0][0] = "";
            }
        }
    }

    public void changePlayer(){
        if(player == 'X'){
            player = 'O';
            playerTurn.setText(OName);
        }
        else if(player == 'O'){
            player = 'X';
            playerTurn.setText(XName);
        }
    }

    private int checkEndedRow(){
        for (int i = 0; i < 3; i++) {
            if(!game[i][0].equals("") && game[i][0].equals(game[i][1]) && game[i][0].equals(game[i][2])){
                return 1;
            }
        }
        return -1;
    }

    private int checkEndedCol(){
        for (int i = 0; i < 3; i++) {
            if(!game[0][i].equals("") && game[0][i].equals(game[1][i]) && game[0][i].equals(game[2][i])){
                return 2;
            }
        }
        return -1;
    }

    private int checkedEndedDiagonal(){
        if(!game[0][2].equals("") && game[0][2].equals(game[1][1]) && game[0][2].equals(game[2][0])){
            return 3;
        }

        if(!game[0][0].equals("") && game[0][0].equals(game[1][1]) && game[0][0].equals(game[2][2])){
            return 3;
        }

        return -1;
    }

    private void restartGame(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
