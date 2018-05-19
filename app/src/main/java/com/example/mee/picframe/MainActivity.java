package com.example.mee.picframe;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView imageset;
    ImageView camera, gellay;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = (ImageView) findViewById(R.id.image_camra);
        gellay = (ImageView) findViewById(R.id.image_gellary);

        imageset = (ImageView) findViewById(R.id.image_check);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
        }
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, 0);

            }
        });


        gellay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //imageset.setScaleType(ImageView.ScaleType.CENTER_CROP);         //set the image type in imageview

                imageset.setImageBitmap(thumbnail);
                if (camera.callOnClick()) {
                   // imageset.buildDrawingCache();
                   // Bitmap bitmap = imageset.getDrawingCache();
                    Intent intent = new Intent(this, AfterImageActivity.class);
                    ImageSaver s = new ImageSaver(this).setFileName("myImage.png").setDirectoryName("images").save(thumbnail);//save image to local drive
                    intent.putExtra("imageName", "myImage.png");
                    intent.putExtra("imageDirectory", "images");
                    Toast.makeText(this, "starting new intent", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        } else if (requestCode == 1) {
            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
              //  Uri pickedImage = data.getData();
             //   imageset.setImageURI(pickedImage);//set the selected image to ImageView
                imageset.setImageBitmap(thumbnail);
                if (camera.callOnClick()) {
                  //  imageset.buildDrawingCache();
                    //Bitmap bitmap = imageset.getDrawingCache();
                    Intent intent = new Intent(this, AfterImageActivity.class);
                    intent.putExtra("imageName", "myImage.png");
                    intent.putExtra("imageDirectory", "images");
                    ImageSaver s = new ImageSaver(this).setFileName("myImage.png").setDirectoryName("images").save(thumbnail);//save image to local drive
                    startActivity(intent);
                }
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    //method used to resize image to less size
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void nextImageSet(View v){
//        imageset.buildDrawingCache();
//        Bitmap bitmap = imageset.getDrawingCache();
//        Intent intent = new Intent(this, AfterImageActivity.class);
//        intent.putExtra("bmp",bitmap);
//        startActivity(intent);
    }
}

