package com.example.duyquoc.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by duyquoc on 10/23/15.
 */
public class ChoseLevel extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_level);
    }

    public void easyAI(View view) {
        Intent intent = new Intent(this, PlayWithAI.class);
        intent.putExtra("level", 0);
        startActivity(intent);
    }

    public void mediumAI(View view) {
        Intent intent = new Intent(this, PlayWithAI.class);
        intent.putExtra("level", 1);
        startActivity(intent);
    }

    public void hardAI(View view) {
        Intent intent = new Intent(this, PlayWithAI.class);
        intent.putExtra("level", 2);
        startActivity(intent);
    }
}
