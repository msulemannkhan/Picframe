package com.example.mee.picframe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class AfterImageActivity extends AppCompatActivity {
    Bitmap bmp;
    ListView listView;
    ArrayList<ObjectActivity> list;
    Button buttonSave;
    ImageView image;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_image);
//        buttonSave = (Button) findViewById(R.id.save_image);
        bmp = new ImageSaver(this).setFileName("myImage.png").setDirectoryName("images").load();
        listView = (ListView) findViewById(R.id.list);
        list = new ArrayList<>();
        list.add(new ObjectActivity(R.drawable.backsplash));
        list.add(new ObjectActivity(R.drawable.backsplash));
        list.add(new ObjectActivity(R.drawable.backsplash));

        AdapterActivity arrayAdapter = new AdapterActivity(this, list);
        listView.setAdapter(arrayAdapter);

        image = (ImageView) findViewById(R.id.image_set);
        int nh = (int) (bmp.getHeight() * (512.0 / bmp.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(bmp, 1080, nh, true);
        image.setBackground(new BitmapDrawable(getResources(), scaled));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.backsplash));
                } else if (position == 1) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.backsplash));
                } else if (position == 2) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.backsplash));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            File root = Environment.getExternalStorageDirectory();
            File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/ " + System.currentTimeMillis() + ".jpg");
            try {
                cachePath.createNewFile();
                FileOutputStream ostream = new FileOutputStream(cachePath);
                image.buildDrawingCache();
                Bitmap bitmap = image.getDrawingCache();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                ostream.close();
                Toast.makeText(AfterImageActivity.this, "Picture Saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}