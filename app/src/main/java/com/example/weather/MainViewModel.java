package com.example.weather;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MainViewModel extends ViewModel {
    private final String leftApiUrl ="https://api.openweathermap.org/data/2.5/weather?q=";
    private final String rightApiUrl ="&units=metric&appid=6ab7bc0539aba727a6b08fdc9803c4a1";
    public static MutableLiveData<String> cityNmURL;
    public void setFinalUrl(String city){
        cityNmURL= new MutableLiveData<String>();
        cityNmURL.setValue(leftApiUrl+city+rightApiUrl);
    }
    public MutableLiveData<String> getCityNmURL(){
        return cityNmURL;
    }
}
