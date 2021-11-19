package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageButton;

public class Settings extends AppCompatActivity implements BackToLast {
    public ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        button.findViewById(R.id.backFromSet);
        goBack();
    }

    public void goBack(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                startActivity(new Intent(Settings.this,MainActivity.class));

            }
        });


    }
}