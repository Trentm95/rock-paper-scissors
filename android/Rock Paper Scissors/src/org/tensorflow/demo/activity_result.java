package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class activity_result extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    public void rematch(View v){
        Intent rematchIntent = new Intent(this, ClassifierActivity.class);
        startActivity(rematchIntent);
    }
}
