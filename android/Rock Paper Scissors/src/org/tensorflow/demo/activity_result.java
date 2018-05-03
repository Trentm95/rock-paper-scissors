package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_result extends Activity {

    private TextView yourChoice;
    private TextView myChoice;
    private TextView winner;
    private ImageView playerImage;
    private ImageView appImage;

    private float rockConf;
    private float paperConf;
    private float scissorConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Plumbing
        myChoice = (TextView) findViewById(R.id.myChoice);
        yourChoice = (TextView) findViewById(R.id.yourChoice);
        winner = (TextView) findViewById(R.id.winner);
        playerImage = (ImageView) findViewById(R.id.appImage);
        appImage = (ImageView) findViewById(R.id.playerImage);

        // Set Confidence values from intent
        rockConf = getIntent().getFloatExtra("R",0);
        paperConf = getIntent().getFloatExtra("P",0);
        scissorConf = getIntent().getFloatExtra("S",0);

        winner.setText("Scissors: " +  Float.toString(scissorConf));


        appImage.setImageResource(R.drawable.scissors);

        if(rockConf > 0.51){
            yourChoice.setText("You chose Rock.");
            playerImage.setImageResource(R.drawable.rock);
        }
        else if (paperConf > 0.51){
            yourChoice.setText("You chose Paper.");
            playerImage.setImageResource(R.drawable.paper);
        }
        else if(scissorConf > 0.51){
            yourChoice.setText("You chose Scissors.");
            playerImage.setImageResource(R.drawable.scissors);
        }
        else {
            yourChoice.setText("Couldn't Recognize Your Choice");
        }

    }

    public void rematch(View v){
        Intent rematchIntent = new Intent(this, ClassifierActivity.class);
        startActivity(rematchIntent);
    }
}
