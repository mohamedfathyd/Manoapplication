package com.Mano.Ad.view;


import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Mano.Ad.R;

public class Who_us extends AppCompatActivity {
TextView textView;
Typeface myTypeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        this.setTitle("ManoAd");

        this.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/flat.ttf");
        textView=findViewById(R.id.txt);
        textView.setTypeface(myTypeface);
        textView.setText("Mano Ad هو تطبيق يسهل لك الاستفادة بوقت فراغك عند مشاهدة اعلاناتنا ستربح هدايا ونقاط تستطيع تحويلها لفلوس أو جوائز قيمة. ومن خلال متابعتنا سيصلك عروض وتخفيضات رائعة من شركائنا يقيمون حولك في مدينتك ");
    }
}
