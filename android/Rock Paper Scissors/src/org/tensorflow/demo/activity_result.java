package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

        // Shared prefs
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        // Pull weights
        int rockWeight = preferences.getInt("rock", 0);
        int paperWeight = preferences.getInt("paper", 0);
        int scissorWeight = preferences.getInt("scissor", 0);
        int sumWeight = rockWeight + paperWeight + scissorWeight;

        // Use weights only if 12 user choices have been recorded.
        int pick;
        if(sumWeight >= 12){
            int rockPercent = (int)((rockWeight / (double)sumWeight)*100);
            int paperPercent = (int)((paperWeight / (double)sumWeight)*100);
            pick = getPick(rockPercent,paperPercent);
        }
        else {
            Random rand = new Random();
            pick = rand.nextInt(2);
        }

        // Have app choose
        switch (pick){
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
            editor.putInt("rock", rockWeight + 1);
        }
        else if (paperConf > 0.51){
            playerSelection = 'P';
            playerChoice.setText("You chose Paper.");
            playerImage.setImageResource(R.drawable.paper);
            editor.putInt("paper", paperWeight + 1);
        }
        else if(scissorConf > 0.51){
            playerSelection = 'S';
            playerChoice.setText("You chose Scissors.");
            playerImage.setImageResource(R.drawable.scissors);
            editor.putInt("scissor", scissorWeight + 1);
        }
        else {
            playerSelection = 'X';
            playerChoice.setText("Couldn't Recognize Your Choice");
        }
        editor.apply();

        winner.setText(getWinner());

    }

    public void rematch(View v){
        Intent rematchIntent = new Intent(this, ClassifierActivity.class);
        startActivity(rematchIntent);
    }

    // Percent of previous selections determines range that maps to rock, paper, or scissor.
    // For example if you pick rock 90% of the time a random from 1-90 will select paper.
    protected int getPick(int rp, int pp){
        Random rand = new Random();
        int rnd = rand.nextInt(100) + 1;

        if(rnd <= rp){
            return 1;
        }
        else if(rnd <= (rp + pp)){
            return 2;
        }
        else {
            return 0;
        }
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
