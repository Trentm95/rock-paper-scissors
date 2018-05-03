package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class activity_result extends Activity {

    private TextView playerChoice;
    private TextView appChoice;
    private TextView winner;
    private ImageView playerImage;
    private ImageView appImage;

    private float rockConf;
    private float paperConf;
    private float scissorConf;

    private char appSelection;
    private char playerSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Plumbing
        appChoice = (TextView) findViewById(R.id.appChoice);
        playerChoice = (TextView) findViewById(R.id.playerChoice);
        winner = (TextView) findViewById(R.id.winner);
        playerImage = (ImageView) findViewById(R.id.playerImage);
        appImage = (ImageView) findViewById(R.id.appImage);

        // Set Confidence values from intent
        rockConf = getIntent().getFloatExtra("R",0);
        paperConf = getIntent().getFloatExtra("P",0);
        scissorConf = getIntent().getFloatExtra("S",0);

        // Have app choose
        switch (getPick()){
            case 0:
                appSelection ='R';
                appChoice.setText("I chose Rock.");
                appImage.setImageResource(R.drawable.rock);
                break;
            case 1:
                appSelection ='P';
                appChoice.setText("I chose Paper.");
                appImage.setImageResource(R.drawable.paper);
                break;
            case 2:
                appSelection ='S';
                appChoice.setText("I chose Scissors.");
                appImage.setImageResource(R.drawable.scissors);
                break;
        }

        // Determine Player Choice
        if(rockConf > 0.51){
            playerSelection = 'R';
            playerChoice.setText("You chose Rock.");
            playerImage.setImageResource(R.drawable.rock);
        }
        else if (paperConf > 0.51){
            playerSelection = 'P';
            playerChoice.setText("You chose Paper.");
            playerImage.setImageResource(R.drawable.paper);
        }
        else if(scissorConf > 0.51){
            playerSelection = 'S';
            playerChoice.setText("You chose Scissors.");
            playerImage.setImageResource(R.drawable.scissors);
        }
        else {
            playerSelection = 'X';
            playerChoice.setText("Couldn't Recognize Your Choice");
        }

        winner.setText(getWinner());

    }

    public void rematch(View v){
        Intent rematchIntent = new Intent(this, ClassifierActivity.class);
        startActivity(rematchIntent);
    }

    protected int getPick(){
        Random rand = new Random();
        return rand.nextInt(2);
    }

    protected String getWinner(){
        int res = -1;

        switch (playerSelection){
            case 'R':
                switch (appSelection){
                    case 'R':
                        res = 1;
                        break;
                    case 'P':
                        res = 0;
                        break;
                    case 'S':
                        res = 2;
                        break;
                }
                break;
            case 'P':
                switch (appSelection){
                    case 'R':
                        res = 2;
                        break;
                    case 'P':
                        res = 1;
                        break;
                    case 'S':
                        res = 0;
                        break;
                }
                break;
            case 'S':
                switch (appSelection){
                    case 'R':
                        res = 0;
                        break;
                    case 'P':
                        res = 2;
                        break;
                    case 'S':
                        res = 1;
                        break;
                }
                break;
            default: return "Sorry Try Again.";

        }

        switch (res){
            case 0:
                return "You Lose.";
            case 1:
                return "Draw.";
            case 2:
                return "You Win.";
            default: return "Woops Something Happened";
        }
    }
}
