package com.example.biomatchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PopupCard extends Activity {

    TextView tv_termOrDesc;
    ImageView iv_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_card);
        adjustLayout();

        tv_termOrDesc = findViewById(R.id.popup_txt);
        iv_img = findViewById(R.id.popup_img);


        Intent receivedIntent = getIntent();
        Bundle receivedBundle = receivedIntent.getExtras();
        if (receivedBundle != null) {
            // Retrieve data using keys
            int img = receivedBundle.getInt("img");
            String term = receivedBundle.getString("term");
            String desc = receivedBundle.getString("desc");
            boolean isExplain = receivedBundle.getBoolean("isExplain");

            if(isExplain){
                iv_img.setVisibility(View.VISIBLE);
                iv_img.setImageResource(img);
                tv_termOrDesc.setText(desc);
                if (desc.equals(".")) {
                    // If no description, adjust the layout parameters of the ImageView
                    iv_img.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    ));
                }
                tv_termOrDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24/* your desired larger text size */);

            }
            else
            {
                iv_img.setVisibility(View.GONE);
                tv_termOrDesc.setText(term);
                tv_termOrDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50/* your desired larger text size */);
                tv_termOrDesc.setGravity(Gravity.CENTER);
            }



        }
    }

    public void adjustLayout(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.9),(int)(height*.9) );

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x =0;
        params.y = -20;
        getWindow().setAttributes(params);
    }
}