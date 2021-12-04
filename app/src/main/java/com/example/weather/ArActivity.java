package com.example.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class ArActivity extends AppCompatActivity implements BackToLast{
    private ArFragment arFragment;
    private ModelRenderable modelRenderable;

    private ImageButton backBtn;
    private ModelRenderable.Builder renBuilder;
    private CompletableFuture<ModelRenderable> future;
    private View view;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        
        backBtn = findViewById(R.id.backToDisplayId);

        goBack();

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        //

        Intent ii = getIntent();
        String mainDes = ii.getStringExtra("MainDescription");

        Uri uriModel = Uri.parse(pickArModel(mainDes));




        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                //create anchor using hit result
                {
                    Anchor anchor = hitResult.createAnchor();
                    //call placeObject() to actually render object in the fragment
                    placeObject(arFragment,anchor,uriModel);
                }
            }
        });
    }

    private String pickArModel(String mainDes) {
        String Url = "";
        String Main = mainDes.split("\\:")[0];
        String rain1 = "https://github.com/MuChuanSu/Model/raw/main/rainQ1.glb";
        String rain2 = "https://github.com/MuChuanSu/Model/raw/main/rainQ2.glb";
        String rain3 = "https://github.com/MuChuanSu/Model/raw/main/rainQ3.glb";
        String rain4 = "https://github.com/MuChuanSu/Model/raw/main/rainQ4.glb";
        String rain5 = "https://github.com/MuChuanSu/Model/raw/main/rainQ5.glb";
        String rain6 = "https://github.com/MuChuanSu/Model/raw/main/rainQ6.glb";
        String rain7 = "https://github.com/MuChuanSu/Model/raw/main/rainQ7.glb";
        String rain8 = "https://github.com/MuChuanSu/Model/raw/main/rainQ8.glb";
        String rain9 = "https://github.com/MuChuanSu/Model/raw/main/rainQ9.glb";
        String clear1 = "https://github.com/MuChuanSu/Model/raw/main/sunnyQ1.glb";
        String clear2 = "https://github.com/MuChuanSu/Model/raw/main/sunnyQ2.glb";
        String clear3 = "https://github.com/MuChuanSu/Model/raw/main/sunnyQ3.glb";
        String clear4 = "https://github.com/MuChuanSu/Model/raw/main/sunnyQ4.glb";
        String clear5 = "https://github.com/MuChuanSu/Model/raw/main/sunnyQ5.glb";
        String clear6 = "https://github.com/MuChuanSu/Model/raw/main/sunnyQ6.glb";
        String clear7 = "https://github.com/MuChuanSu/Model/raw/main/sunnyQ7.glb";
        String cloud1 = "https://github.com/MuChuanSu/Model/raw/main/cloud1.glb";
        String cloud2 = "https://github.com/MuChuanSu/Model/raw/main/cloud2.glb";
        String cloud3 = "https://github.com/MuChuanSu/Model/raw/main/cloud3.glb";
        String snow1 = "https://github.com/MuChuanSu/Model/raw/main/snowQ1.glb";
        String snow2 = "https://github.com/MuChuanSu/Model/raw/main/snowQ2.glb";
        String snow3 = "https://github.com/MuChuanSu/Model/raw/main/snowQ3.glb";
        String snow4 = "https://github.com/MuChuanSu/Model/raw/main/snowQ4.glb";
        String snow5 = "https://github.com/MuChuanSu/Model/raw/main/snowQ5.glb";
        String snow6 = "https://github.com/MuChuanSu/Model/raw/main/snowQ6.glb";
        String snow7 = "https://github.com/MuChuanSu/Model/raw/main/snowQ7.glb";
        String thunder1 = "https://github.com/MuChuanSu/Model/raw/main/thunderQ1.glb";
        String thunder2 = "https://github.com/MuChuanSu/Model/raw/main/thunderQ1.glb";
        String fog1 = "https://github.com/MuChuanSu/Model/raw/main/fogQ1.glb";
        String fog2 = "https://github.com/MuChuanSu/Model/raw/main/fogQ2.glb";


        switch(Main){
            case "Clear":
                ArrayList<String> clearArray = new ArrayList<String>();
                clearArray.add(clear1);
                clearArray.add(clear2);
                clearArray.add(clear3);
                clearArray.add(clear4);
                clearArray.add(clear5);
                clearArray.add(clear6);
                clearArray.add(clear7);

                Collections.shuffle(clearArray);


                Url = clearArray.get(0);

                break;
            case "Rain":
            case"Drizzle":
                ArrayList<String> rainArray = new ArrayList<String>();
                rainArray.add(rain1);
                rainArray.add(rain2);
                rainArray.add(rain3);
                rainArray.add(rain4);
                rainArray.add(rain5);
                rainArray.add(rain6);
                rainArray.add(rain7);
                rainArray.add(rain8);
                rainArray.add(rain9);

                Collections.shuffle(rainArray);
                Url =rainArray.get(0);
                //get a random model in the rain array,the order changes everytime we call shuffle()
                break;
            case"Clouds":
                ArrayList<String> cloudArray = new ArrayList<String>();
                cloudArray.add(cloud1);
                cloudArray.add(cloud2);
                cloudArray.add(cloud3);
                Collections.shuffle(cloudArray);
                Url = cloudArray.get(0);
                break;
            case"Snow":
                ArrayList<String> snowArray = new ArrayList<String>();
                snowArray.add(snow1);
                snowArray.add(snow2);
                snowArray.add(snow3);
                snowArray.add(snow4);
                snowArray.add(snow5);
                snowArray.add(snow6);
                snowArray.add(snow7);

                Collections.shuffle(snowArray);
                Url = snowArray.get(0);
                break;
            case"Thunderstorm":
                ArrayList<String> thunderArray = new ArrayList<String>();
                thunderArray.add(thunder1);
                thunderArray.add(thunder2);
                Collections.shuffle(thunderArray);
                Url = thunderArray.get(0);
                break;


            case"Squall":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case"Fog":
            case"Mist":
                ArrayList<String> fogMistArray = new ArrayList<String>();
                fogMistArray.add(fog1);
                fogMistArray.add(fog2);
                Collections.shuffle(fogMistArray);
                Url = fogMistArray.get(0);
                break;
            case"Tornado":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case"Smoke":
            case"Haze":
            case"Dust":
            case"Sand":
            case"Ash":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
        }
        return Url;
    }

    public void goBack() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                startActivity(new Intent(ArActivity.this,displayInfo.class));
            }
        });
    }

    private void placeObject(ArFragment arFragment,Anchor anchor,Uri modelUri){
        renBuilder = ModelRenderable.builder();
        renBuilder.setSource
                (this, RenderableSource.builder().setSource(this,modelUri, RenderableSource.SourceType.GLB)
                        .setScale(2.0f)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build());

                //All build() methods in Sceneform return a CompletableFuture.
                //The object is built on a separate thread and the callback function is executed on the main thread.

                future = renBuilder.build();
                future.thenAccept((renderable) -> addNodeToScene(arFragment,anchor,renderable))

                .exceptionally(throwable -> {
                    Toast.makeText(ArActivity.this,"Error,check network connection",Toast.LENGTH_SHORT);
                    return null;
                });
    }


    private void addNodeToScene(ArFragment arFragment, Anchor anchor, Renderable renderable){
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }



}