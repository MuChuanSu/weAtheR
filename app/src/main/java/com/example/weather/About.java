package com.example.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity implements BackToLast{
    public ImageButton btn;
    public Button webBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btn = findViewById(R.id.backFromAbt);
        webBtn = findViewById(R.id.toWebButton);
        goBack();
        toWeb();


    }

    public void goBack(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                startActivity(new Intent(About.this,MainActivity.class));
            }
        });
    }
    public void toWeb(){
        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                String url ="https://muchuansu.github.io/weArtheRWebsite/index.html";
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}