package com.isaac012.ninequaters;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button [] buttons = new Button[9];
    private Button resetGame;


    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;

    //pl 1 => 0
    //pl 2 => 1
    //vacio => 2

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPosition = {

            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };

//    Integer a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] =  (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId()); //btn2 toda la id
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length())); //2 numero

        if(activePlayer){
            ((Button) view).setText("X");
            gameState [gameStatePointer] = 0;
        }else{
            ((Button) view).setText("O");
            gameState [gameStatePointer] = 1;
        }
        rountCount ++;

        if (checkWinner()){
            if (activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Jugador Uno acaba de ganar!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Jugador Dos acaba de ganar!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rountCount == 9){
            playAgain();
            Toast.makeText(this, "No hay ganador en esta ronda!", Toast.LENGTH_SHORT).show();
        }else{
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("El jugador Uno esta ganando!");
        } else if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("El jugador Dos esta ganando!");
        }else{
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("Ambos jugadores son igual de buenos!");
                updatePlayerScore();
            }
        });


    }
    public boolean checkWinner(){
        boolean winnerResult = false;

        for (int [] winningPosion :  winningPosition){
            if (gameState[winningPosion[0]] == gameState[winningPosion[1]] &&
                    gameState[winningPosion[1]] == gameState[winningPosion[2]] &&
                        gameState[winningPosion[0]] != 2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for(int i = 0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}

//        Button ga = (Button) view;
//
//        if (a == 0){
//
//            ga.setText("O");
//            a = 1;
//
//        } else {
//
//            ga.setText("X");
//            a = 0;
//
//        }