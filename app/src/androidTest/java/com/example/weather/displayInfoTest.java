package com.example.weather;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class displayInfoTest {
    private final String leftApiUrl ="https://api.openweathermap.org/data/2.5/weather?q=";
    private final String rightApiUrl ="&units=metric&appid=6ab7bc0539aba727a6b08fdc9803c4a1";
    private String cityName = "London";


    public void setName(String name) {
        this.name = name;
    }

    private  String name;

    public void setCountry(String country) {
        this.country = country;
    }

    private  String country;

    @Test
    public void update(){
        String Url = leftApiUrl+cityName+rightApiUrl;
        RequestQueue rQ = Volley.newRequestQueue(getApplicationContext());
        //create a requestQueue to add our request into

        StringRequest sR = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Throwable t = null;

                try {
                    JSONObject allJsonRes = new JSONObject(response);


                    setName(allJsonRes.getString("name"));


                    JSONObject sysBlock = allJsonRes.getJSONObject("sys");
                    country = sysBlock.getString("country");






                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }//note that .show() is necessary for the message to show
        });
        rQ.add(sR);

        //add the request into the queue,Volley will handle it and send it
        //and then onResponse() or onErrorResponse() will run
        //https://developer.android.com/training/volley/simple
    }






}