package com.example.crawling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class detail extends Activity {
    private Button review;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        TextView tvName = (TextView)findViewById(R.id.textView1);
        TextView tvAddr = (TextView)findViewById(R.id.textView2);
        TextView tvPhone = (TextView)findViewById(R.id.textView3);
        ImageView iv = (ImageView)findViewById(R.id.imageView1);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        tvName.setText(intent.getStringExtra("name"));
        tvAddr.setText(intent.getStringExtra("pay"));
        tvPhone.setText(intent.getStringExtra("grade"));
        iv.setImageResource(intent.getIntExtra("img", 0));

        review = findViewById(R.id.Button1);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAct1 = new Intent(detail.this, rating_bar.class);
                startActivity(newAct1);

            }
        });
    } // end of onCreate

}
