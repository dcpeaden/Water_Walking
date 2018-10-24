package com.example.kernelsanders.baseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConcernsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concerns_page);

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //alg.updateHydrationLevel(((TextView)findViewById(R.id.PrintHydration)));
                Intent intent = new Intent(ConcernsPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
