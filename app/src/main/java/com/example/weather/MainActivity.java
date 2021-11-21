package com.example.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  {

    private Vibrator vib;
    private Button searchButton;
    public ImageButton setBtn,abtBtn;
    private AutoCompleteTextView searchBar;
    private String result;//
    private final String leftApiUrl ="https://api.openweathermap.org/data/2.5/weather?q=";
    private final String rightApiUrl ="&units=metric&appid=6ab7bc0539aba727a6b08fdc9803c4a1";
    // the json request url format provided by api provider is like the one below:
    // api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    // so I had to divide them into 3 parts like declared above
    public CityDao cDao;
    public CityDatabase CDB;
    public VideoView vv;
    public Uri videoUri;





    


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        vib =(Vibrator)getSystemService(VIBRATOR_SERVICE);
        //
        vv = findViewById(R.id.videoBackId);
        videoUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.movie);
        vv.setVideoURI(videoUri);
        vv.start();

        searchButton = findViewById(R.id.search_button);
        setBtn=findViewById(R.id.settingsBTNID);
        abtBtn=findViewById(R.id.aboutBTNID);

        GoToAbout();
        GoToSettings();
        GoToDisplay();
        setSuggestions();





    }
    private void setSuggestions(){

        String[] cityArray = {};
        CDB = CityDatabase.getDatabase(this);
        cDao = CDB.cityDao();
        cityArray= cDao.getAllCityNames();
        //create an array to store the suggestions
        //populate it with my method in DAO which retrieve all distinct names in the database
        //may feel like overkill but does the job
        //define the auto text view
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.select_dialog_item,cityArray);
        //create an adapter
        searchBar = findViewById(R.id.search_Bar);
        searchBar.setThreshold(3);
        //this means the suggestions will only pop-up after the user typed 3 characters

        searchBar.setAdapter(adapter);
        //and then set an adapter for the auto textview
        /////

    }

    private void GoToDisplay() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(VibrationEffect.createOneShot(3000,VibrationEffect.EFFECT_TICK));
                v.playSoundEffect(SoundEffectConstants.CLICK);

                getInfoMethod();


                //inside onClick method is getInfoMethod()
                //which gets the json response and convert info into variables
            }
        });
    }

    private void GoToSettings() {
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                startActivity(new Intent(MainActivity.this,Settings.class));
            }
        });
    }

    private void GoToAbout() {
        abtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                startActivity(new Intent(MainActivity.this,About.class));
            }
        });
    }






    public void getInfoMethod(){
        String finalUrl ="";
        String cityName = searchBar.getText().toString().trim();
        RequestQueue rQ = Volley.newRequestQueue(getApplicationContext());
        //create a requestQueue to add our request into
        finalUrl = leftApiUrl+cityName+rightApiUrl;

        StringRequest sR = new StringRequest(Request.Method.POST, finalUrl, new Response.Listener<String>() {
            @Override
                public void onResponse(String response) {
                result = "";

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
                    String mainDescription = weatherBlock.getString("main");
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
                    ///


                    result += "Current weather in "+ name+", "+country+": "
                            +"\ntime zone: "+ timeZone
                            +"\nvisibility: "+ visibility
                            +"\nTemperature: "+Math.round(temp_in_C)+"°C"
                            +"\n"+mainDescription
                            +"\n("+subDescription+")"
                            +"\nWind speed : "+ windSpeed+" meters per minute"
                            +"\ndegree: "+degree
                            +"\ntemp feel:"+Math.round(temp_feel)+"°C"
                            +"\nmin: "+Math.round(temp_min)+"°C/"+"max"+Math.round(temp_max)+"°C"
                            +"\npressure: "+pressure
                            +"\nhumidity: "+humidity;



                    //then send these values to the displayInfo activity
                    //using Intent and putExtra


                    Intent i =new Intent(MainActivity.this,displayInfo.class);
                    i.putExtra("city",name);
                    i.putExtra("mainD",mainDescription);
                    i.putExtra("subD",subDescription);
                    i.putExtra("temp",temp_in_C);
                    i.putExtra("tempMax",temp_max);
                    i.putExtra("tempMin",temp_min);
                    i.putExtra("tempFeel",temp_feel);
                    i.putExtra("pressure",pressure);
                    i.putExtra("humidity",humidity);
                    i.putExtra("visibility",visibility);
                    i.putExtra("speed",windSpeed);
                    i.putExtra("deg",degree);
                    i.putExtra("timezone",timeZone);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error,check network or spelling",Toast.LENGTH_SHORT).show();
            }//note that .show() is necessary for the message to show
        });
        rQ.add(sR);
        //add the request into the queue,Volley will handle it and send it
        //and then onResponse() or onErrorResponse() will run
        //https://developer.android.com/training/volley/simple
    }
}