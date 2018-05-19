package com.example.mee.picframe;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ObjectActivity {

    int image;


    public ObjectActivity (int image) {
        this.image=image;
    }

    public int getImage() {
        return image;

}

    public void setImage(int image) {
        this.image = image;
    }
}
