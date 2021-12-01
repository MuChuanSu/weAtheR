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
        switch(Main){
            case "Clear":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case "Rain":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case"Clouds":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case"Snow":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case"Drizzle":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case"Thunderstorm":
            case"Squall":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
                break;
            case"Fog":
            case"Mist":
                Url = "https://github.com/MuChuanSu/Model/raw/main/Test.glb";
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
                        .setScale(1.0f)
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