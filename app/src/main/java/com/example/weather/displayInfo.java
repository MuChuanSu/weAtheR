package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class displayInfo extends AppCompatActivity implements BackToLast,Observer{
    private TextView testInfo;
    private double temp,temp_max,temp_min,temp_feel,pressure,humidity,visibility,speed,deg,timezone;
    private String mainDescription,subDescription,city;
    public ImageView weatherGIF;
    public ImageButton backToMainButton,arButton;
    public TextView cityNameTv,tempTv,minTempTv,maxTempTv,feelsLikeTv,descriptionTv,pressureTv,
            humidityTv,windSpeedTv,windDegTv;
    public LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);



        weatherGIF =findViewById(R.id.gifPlaceId);
        backToMainButton =findViewById(R.id.backToMainId);
        arButton = findViewById(R.id.arButtonId);
        ll = findViewById(R.id.infoLayoutId);
        goToArMethod();
        goBack();
        update();

    }

    public void update(){
        Bundle extras = getIntent().getExtras();
        temp =(int)Math.round(extras.getDouble("temp"));//
        temp_max =(int)Math.round(extras.getDouble("tempMax"));//
        temp_min =(int)Math.round(extras.getDouble("tempMin"));//
        temp_feel =Math.round(extras.getDouble("tempFeel"));
        pressure =extras.getDouble("pressure");
        humidity = extras.getDouble("humidity");
        visibility = extras.getDouble("visibility");
        speed = extras.getDouble("speed");
        deg = extras.getDouble("deg");
        timezone = extras.getDouble("timezone");
        mainDescription = extras.getString("mainD");
        subDescription =extras.getString("subD");
        city = extras.getString("city");
        ///
        cityNameTv = findViewById(R.id.cityNameTVId);
        cityNameTv.setText(city);

        tempTv=findViewById(R.id.tempTVId);
        tempTv.setText(""+temp+"°C");

        maxTempTv=findViewById(R.id.maxTempTVId);
        maxTempTv.setText("Max"+temp_max+"°C");


        minTempTv=findViewById(R.id.minTempTVId);
        minTempTv.setText("Min"+temp_min+"°C");

        feelsLikeTv=findViewById(R.id.feelsLikeTVId);
        feelsLikeTv.setText("Feels like: "+temp_feel+"°C");

        descriptionTv = findViewById(R.id.descriptionTVId);
        descriptionTv.setText(mainDescription+":"+"\n("+subDescription+")");

        pressureTv = findViewById(R.id.pressureTVId);
        pressureTv.setText("Pressure:\n"+pressure+"hPa");

        humidityTv=findViewById(R.id.humidityTVId);
        humidityTv.setText("Humidity:\n"+humidity+"%");

        windSpeedTv = findViewById(R.id.windSpeedTVId);
        windSpeedTv.setText("WindSpeed:\n"+speed+"meter/sec");

        windDegTv = findViewById(R.id.windDegTVId);
        windDegTv.setText("Wind Direction:\n"+deg+"°");
        showGif();
    }



    private void goToArMethod() {
        arButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                startActivity(new Intent(displayInfo.this,ArActivity.class));
            }
        });
    }

    public void goBack() {
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                startActivity(new Intent(displayInfo.this,MainActivity.class));
            }
        });
    }
    public void showGif(){
        switch(mainDescription){
            case "Clear":
                Glide.with(this).asGif().load(R.raw.sunnyday2).into(weatherGIF);
                break;
            case "Rain":
                Glide.with(this).asGif().load(R.raw.rain2).into(weatherGIF);
                break;
            case"Clouds":
                Glide.with(this).asGif().load(R.raw.cloudy).into(weatherGIF);
                break;
            case"Snow":
                Glide.with(this).asGif().load(R.raw.snow).into(weatherGIF);
                break;
            case"Drizzle":
                Glide.with(this).asGif().load(R.raw.drizzle).into(weatherGIF);
                break;
            case"Thunderstorm":
            case"Squall":
                Glide.with(this).asGif().load(R.raw.thunderrain).into(weatherGIF);
                break;
            case"Fog":
            case"Mist":
                Glide.with(this).asGif().load(R.raw.fog).into(weatherGIF);
                break;
            case"Tornado":
                Glide.with(this).asGif().load(R.raw.tornado).into(weatherGIF);
                break;
            case"Smoke":
            case"Haze":
            case"Dust":
            case"Sand":
            case"Ash":
                Glide.with(this).asGif().load(R.raw.smoke).into(weatherGIF);
                break;
        }
    }
}