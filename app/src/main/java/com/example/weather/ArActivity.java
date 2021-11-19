package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
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
    private String MODEL_URL="https://github.com/MuChuanSu/Model/raw/main/testscene.glb";
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

        Uri uriModel = Uri.parse(MODEL_URL);




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
                        .setScale(0.02f)
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