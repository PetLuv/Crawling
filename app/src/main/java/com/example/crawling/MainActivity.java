package com.example.crawling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<itemobject> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //AsyncTask (파싱)
        new MainActivity.Description().execute();
    }


    private class Description extends AsyncTask<Void, Void, Void> {


        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다이어로그 시작
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Wait please...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect("https://www.gsshop.com/shop/search/main.gs?tq=%EC%95%A0%EA%B2%AC%EC%8B%9D%ED%92%88&lseq=399079").get();
                Elements doc = document.select("section[class=prd-list]").select("ul").select("li"); //필요한 녀석만 꼬집어서 지정


                String region = null;
                String region_cases = null;
                String region_cases_p = null;
                String region_deaths = null;
                String region_recovered = null;

                //목록의 수 만큼 가져오기
                for(int i=1; i<doc.size(); i++) {

                    region = doc.get(i).select("dt").text();
                    region_cases = doc.get(i).select("strong").text()+"원";
                    region_cases_p = doc.get(i).select("a").attr("href");
                    region_deaths ="http:" + doc.get(i).select("img").attr("src");
                    region_recovered = doc.get(i).select("button[class=user-comment]").text();



                    list.add(new itemobject(region, region_cases, region_cases_p, region_deaths, region_recovered));
                    Log.v("태그1","12" + region);
                    Log.v("태그2","12" + region_cases);
                    Log.v("태그3","12" + region_cases_p);
                    Log.v("태그4","12" + region_deaths);
                    Log.v("태그5","12" + region_recovered);

                }




            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어댑터와 연결한다.
            MyAdapter myAdapter = new MyAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }
}