package com.jaipurpinkpanthers.android.util;

import android.graphics.Canvas;
import android.widget.TextView;

/**
 * Created by Mahesh on 6/20/2016.
 */
public class OutlineTextView extends TextView {

    public OutlineTextView(){
        super(null);
        System.out.print("hi");
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            super.draw(canvas);
        }
    }
}
