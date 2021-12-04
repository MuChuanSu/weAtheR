package com.example.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class displayInfo extends AppCompatActivity implements BackToLast{
    private String mainDescription,subDescription,city;
    private ImageView weatherGIF;
    private ImageButton backToMainButton,arButton;
    private TextView cityNameTv,tempTv,minTempTv,maxTempTv,feelsLikeTv,descriptionTv,pressureTv,
            humidityTv,windSpeedTv,windDegTv;
    private LinearLayout ll;
    private String finalUrl;
    private MainViewModel mvv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        mvv = new ViewModelProvider(this).get(MainViewModel.class);
        finalUrl = mvv.getCityNmURL().getValue();


        setUP();
        update();
        goToArMethod();
        goBack();
        setPopUps();

    }
    public void update(){
        RequestQueue rQ = Volley.newRequestQueue(getApplicationContext());
        //create a requestQueue to add our request into

        StringRequest sR = new StringRequest(Request.Method.POST, finalUrl, new Response.Listener<String>() {
            @Override
                public void onResponse(String response) {

                try {
                    JSONObject allJsonRes = new JSONObject(response);

                    String name = allJsonRes.getString("name");
                    double visibility = allJsonRes.getDouble("visibility");
                    int timeZone =allJsonRes.getInt("timezone");
                    //Creates a new JSONArray with values from the JSON string.
                    //try/catch are mandatory when creating JSONObject
                    //now we extract values from this JsonObject
                    JSONArray weatherJsonArr = allJsonRes.getJSONArray("weather");
                    //store []weather
                    //1.to get mainDescription and subDescription
                    //store the []weather part into weatherJsonArr
                    //inside this JsonArray,we store the only JsonObject as weatherBlock
                    //{}0
                    //then get different values from this subJsonObject
                    JSONObject weatherBlock = weatherJsonArr.getJSONObject(0);
                    //this includes id,main,description,icon
                    mainDescription = weatherBlock.getString("main");
                    //get the string under key "main" e.g. "rain"

                    String subDescription = weatherBlock.getString("description");
                    //e.g."moderate rain"
                    JSONObject mainBlock = allJsonRes.getJSONObject("main");
                    //access {}main
                    double temp_in_C = mainBlock.getDouble("temp");
                    //get temperature from {}main
                    double temp_feel = mainBlock.getDouble("feels_like");
                    double temp_min = mainBlock.getDouble("temp_min");
                    double temp_max = mainBlock.getDouble("temp_max");
                    double pressure = mainBlock.getDouble("pressure");
                    double humidity = mainBlock.getDouble("humidity");
                    JSONObject windBlock = allJsonRes.getJSONObject("wind");
                    //get wind{}
                    double windSpeed = windBlock.getDouble("speed");
                    double degree = windBlock.getDouble("deg");
                    ///
                    JSONObject sysBlock = allJsonRes.getJSONObject("sys");
                    String country = sysBlock.getString("country");


                    cityNameTv.setText(name);
                    tempTv.setText(+temp_in_C+"°C");
                    maxTempTv.setText("Max"+temp_max+"°");
                    minTempTv.setText("Min"+temp_min+"°");
                    feelsLikeTv.setText("Feels like: "+temp_feel+"°C");
                    descriptionTv.setText(mainDescription+":"+"\n("+subDescription+")");
                    pressureTv.setText("Pressure:\n"+pressure+"hPa");
                    humidityTv.setText("Humidity:\n"+humidity+"%");
                    windSpeedTv.setText("WindSpeed:\n"+windSpeed+"meter/sec");
                    windDegTv.setText("Wind Direction:\n"+degree+"°");
                    showGif();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                  Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),finalUrl,Toast.LENGTH_SHORT).show();

            }//note that .show() is necessary for the message to show
        });
        rQ.add(sR);
        //add the request into the queue,Volley will handle it and send it
        //and then onResponse() or onErrorResponse() will run
        //https://developer.android.com/training/volley/simple
    }


    private void goToArMethod() {
        arButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                Intent i = new Intent(displayInfo.this,ArActivity.class);
                i.putExtra("MainDescription",descriptionTv.getText().toString());
                startActivity(i);
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

    private void setUP(){
        weatherGIF =findViewById(R.id.gifPlaceId);
        backToMainButton =findViewById(R.id.backToMainId);
        arButton = findViewById(R.id.arButtonId);
        ll = findViewById(R.id.infoLayoutId);
        cityNameTv = findViewById(R.id.cityNameTVId);
        tempTv=findViewById(R.id.tempTVId);
        maxTempTv=findViewById(R.id.maxTempTVId);
        minTempTv=findViewById(R.id.minTempTVId);
        feelsLikeTv=findViewById(R.id.feelsLikeTVId);
        descriptionTv = findViewById(R.id.descriptionTVId);
        pressureTv = findViewById(R.id.pressureTVId);
        humidityTv=findViewById(R.id.humidityTVId);
        windSpeedTv = findViewById(R.id.windSpeedTVId);
        windDegTv = findViewById(R.id.windDegTVId);
    }
    private void setPopUps(){
        cityNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
                builder.setMessage("This is city name")
                        .setPositiveButton("OK",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        ///
        tempTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("This is current temperature in Celsius\n" +
                    "Celsius is a type of the scales to measure temperature.\n" +
                    "Do you know other scales?\n"+
                    "Which scale does your country use?")
                    .setPositiveButton("OK",null)
                    .setNeutralButton("Learn more", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url ="https://www.wikiwand.com/en/Scale_of_temperature";
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        ///
        maxTempTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("This is daily maximum temperature")
                    .setPositiveButton("OK",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        ///
        feelsLikeTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("This is Apparent temperature in Celsius,\n"+
                    "this means the temperature perceived by human body,\n"+
                    "it differs from actual temperature due to affection of air temperature,relative humidity and wind speed.")
                    .setPositiveButton("OK",null)
                    .setNeutralButton("Learn more", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url ="https://www.wikiwand.com/en/Apparent_temperature";
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        ///
        minTempTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("This is daily minimum temperature")
                    .setPositiveButton("OK",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        ///
        descriptionTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("This is weather description such as sunny or rain.\n"+
                    "What is your favorite weather?\n"+
                    "Can you think of a weather that is rare in your country?")
                    .setPositiveButton("OK",null)
                    .setNeutralButton("Learn more", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url ="https://www.metoffice.gov.uk/weather/learn-about/weather/types-of-weather";
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        ///
        pressureTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("hPa stands for hectoPascals,it is a unit for measuring the pressure exerted from the Earth's atmosphere.")
                    .setPositiveButton("OK",null)
                    .setNeutralButton("Learn more", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url ="https://www.metoffice.gov.uk/weather/learn-about/weather/how-weather-works/high-and-low-pressure";
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        ///
        humidityTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("Humidity is a statistics for measuring the amount of water vapour in the air.")
                    .setPositiveButton("OK",null)
                    .setNeutralButton("Learn more", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url ="https://www.metoffice.gov.uk/weather/learn-about/weather/types-of-weather/humidity";
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        ///
        windSpeedTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("Wind speed indicates the speed of wind,here it shows how many meters the wind can travel per second.\n "+
                    "Do you know other units for measuring wind speed?\n"+
                    "What tools can help us measuring it?")
                    .setPositiveButton("OK",null)
                    .setNeutralButton("Learn more", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url ="https://www.metoffice.gov.uk/weather/guides/observations/how-we-measure-wind";
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        ///
        windDegTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(displayInfo.this);
            builder.setMessage("Wind direction is the direction that wind blows from,"+
                    "Here it is ub degrees where northwind is 0° and eastwind is 90°.\n" +
                    "Click the link below to see a cool demonstration of wind flows ")
                    .setPositiveButton("OK",null)
                    .setNeutralButton("Learn more", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url ="https://www.wikiwand.com/en/Wind_direction";
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    })
                    .setNegativeButton("Windfinder", (dialog, which) -> {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url ="https://www.windfinder.com/#3/51.2894/9.4922";
                        i.setData(Uri.parse(url));
                        startActivity(i);

                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }
}