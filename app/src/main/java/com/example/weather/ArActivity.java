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
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.Collections;

public class ArActivity extends AppCompatActivity implements BackToLast{
    private ArFragment arFragment;
    private ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        backBtn = findViewById(R.id.backToDisplayId);
        goBack();

        //retrieve the main description from displayInfo activity
        Intent ii = getIntent();
        String mainDes = ii.getStringExtra("MainDescription");
        //pick the AR model uri based on the weather main description
        Uri uriModel = Uri.parse(pickArModel(mainDes));
        //
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        //arFragment is an opponent that AR happens, it also automatically checks if the device is AR compatible.
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                {
                    //create an anchor using hit result (where the user tapped on the screen)
                    //(anchor is the reference of a fixed position in the real world,it also
                    // update the model's position per frame so when the camera moves, we can see
                    // different angles of the model)
                    // then Create an AnchorNode with the specified anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    arFragment.getArSceneView().getScene().addChild(anchorNode);
                    // create a transformable node that we can attach a 3D object to,we must do
                    // so users can drag/scale up and down/rotate the rendered 3D model.
                    TransformableNode tNode = new TransformableNode(arFragment.getTransformationSystem());
                    tNode.getScaleController().setMinScale(0.1f);
                    // the line above was added to let us pinch and scale down the model more
                    // due many participants tried to do this in user testing.
                    // then we attach the transformable node to the anchor node
                    anchorNode.addChild(tNode);
                    tNode.select();
                    //
                    ModelRenderable.builder()
                            .setSource(ArActivity.this, RenderableSource.builder().setSource(ArActivity.this,uriModel, RenderableSource.SourceType.GLB)
                                            .setScale(2.0f)//set the default size of model when rendered 2.0f means it will be 200% of the original size
                                            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                                            .build()).build().thenAccept((renderable) ->tNode.setRenderable(renderable))
                            //lines above renders a .GLB 3D model from the remote source (git hub)
                            //finally, setRenderable() will decide which 3D model will be attached on the transformable node
                            //the attached 3D model will then be rendered on the node.
                            .exceptionally(throwable -> {
                                //if there's error when trying to render the model,
                                //e.g. can't retrieve model source due no internet, the toast will show.
                                Toast.makeText(ArActivity.this,"Error,check network connection",Toast.LENGTH_SHORT).show();
                                return null;
                            });


                    /***************************************************************************************
                     * I learned to write codes in this block from the learning resources below:
                     *
                     *    Title: <Create a Renderable-Load 3D models at runtime>
                     *    Author: <Google>
                     *    Date: <17/12/2021>
                     *    Code version: <N/A>
                     *    Availability: <https://developers.google.com/sceneform/develop/create-renderables#load_3d_models_at_runtime>
                     *
                     *    Title: <Develop your HelloAR app in Android studio using ARCore and Sceneform>
                     *    Author: <Vishnu Sivan>
                     *    Date: <7/12/2021>
                     *    Code version: <N/A>
                     *    Availability: <https://medium.com/geekculture/develop-your-helloar-app-in-android-studio-using-arcore-and-sceneform-d032e5788036>
                     *
                     ***************************************************************************************/

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
        String clear1 = "https://github.com/MuChuanSu/Model/raw/main/sunQ1.glb";
        String clear2 = "https://github.com/MuChuanSu/Model/raw/main/sunQ2.glb";
        String clear3 = "https://github.com/MuChuanSu/Model/raw/main/sunQ3.glb";
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
                ArrayList<String> clearArray = new ArrayList<>();
                clearArray.add(clear1);
                clearArray.add(clear2);
                clearArray.add(clear3);
                Collections.shuffle(clearArray);
                Url = clearArray.get(0);
                break;
            case "Rain":
            case"Drizzle":
                ArrayList<String> rainArray = new ArrayList<>();
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
                ArrayList<String> cloudArray = new ArrayList<>();
                cloudArray.add(cloud1);
                cloudArray.add(cloud2);
                cloudArray.add(cloud3);
                Collections.shuffle(cloudArray);
                Url = cloudArray.get(0);
                break;
            case"Snow":
                ArrayList<String> snowArray = new ArrayList<>();
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
                ArrayList<String> thunderArray = new ArrayList<>();
                thunderArray.add(thunder1);
                thunderArray.add(thunder2);
                Collections.shuffle(thunderArray);
                Url = thunderArray.get(0);
                break;
            case"Squall":
            case"Tornado":
            case"Smoke":
            case"Haze":
            case"Dust":
            case"Sand":
            case"Ash":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case"Fog":
            case"Mist":
                ArrayList<String> fogMistArray = new ArrayList<>();
                fogMistArray.add(fog1);
                fogMistArray.add(fog2);
                Collections.shuffle(fogMistArray);
                Url = fogMistArray.get(0);
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
}