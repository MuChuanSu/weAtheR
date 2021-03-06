package com.example.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity  {
    private Button searchButton;
    public ImageButton abtBtn,chatBtn;
    private AutoCompleteTextView searchBar;
    private String result;//
    private TextView chatBotTxt;
    private final String notFoundToast = "Check your spelling";
    private final String blankToast = "City name can't be blank";
    public CityDao cDao;
    public CityDatabase CDB;
    public VideoView vv;
    public Uri videoUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();

        MainViewModel vM = new ViewModelProvider(this).get(MainViewModel.class);
        GoToAbout();
        setSuggestions();
        goToChatBot();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                Boolean found =false;
                String cityName = searchBar.getText().toString();
                if(!cityName.equals("")){
                    String[] array = getCityArray();
                    for (String s : array) {
                        if (s.equalsIgnoreCase((cityName))) {
                            found = true;
                            vM.setFinalUrl(cityName);
                            Intent i = new Intent(MainActivity.this, displayInfo.class);
                            startActivity(i);
                        }
                    }
                    if (!found){
                        Toast.makeText(getApplicationContext(), notFoundToast, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), blankToast, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setSuggestions(){
        String [] suggestionArray = {};
        suggestionArray = getCityArray();
        //create an array to store the suggestions
        //populate it with my method in DAO which retrieve all distinct names in the database
        //may feel like overkill but does the job
        //define the auto text view
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.select_dialog_item,suggestionArray);
        //create an adapter
        searchBar = findViewById(R.id.search_Bar);
        searchBar.setThreshold(3);
        //this means the suggestions will only pop-up after the user typed 3 characters
        searchBar.setAdapter(adapter);
        //and then set an adapter for the auto textview
        /////
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
    private String[] getCityArray(){
        String[] cityArray ;
        CDB = CityDatabase.getDatabase(this);
        cDao = CDB.cityDao();
        cityArray= cDao.getAllCityNames();
        return cityArray;
    }
    private void goToChatBot(){
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                String url ="https://muchuansu.github.io/weArtheRWebsite/chatBot.html";
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
    private void setUp(){
        vv = findViewById(R.id.videoBackId);
        videoUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.movie);
        vv.setVideoURI(videoUri);
        vv.start();
        searchButton = findViewById(R.id.search_button);
        abtBtn=findViewById(R.id.aboutBTNID);
        chatBtn = findViewById(R.id.chatbotbtn);
        chatBotTxt = findViewById(R.id.chatbottext);
    }
}