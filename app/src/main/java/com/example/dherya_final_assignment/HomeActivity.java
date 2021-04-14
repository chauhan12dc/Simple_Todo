package com.example.dherya_final_assignment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dherya_final_assignment.ui.main.SectionsPagerAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {

    public static View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);


        getIP();
        getDate();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_todo, null);
                int width = 800;
                int height = 500;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                Button button = popupView.findViewById(R.id.CustomButton);
                button.setText("Add TODO");

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = popupView.findViewById(R.id.add_todo_popup);
                        DatabaseClass databaseClass = new DatabaseClass(HomeActivity.this);
                        if (editText.getText().toString().equals("")) {
                            Toast.makeText(HomeActivity.this, "TODO can't be blank", Toast.LENGTH_SHORT).show();
                            editText.requestFocus();
                        } else {
                            String value = editText.getText().toString();
                            if (!databaseClass.checkDublicate(value)) {
                                if (databaseClass.addTodo(value)) {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(HomeActivity.this, "Todo Not inserted.. Try Again", Toast.LENGTH_SHORT).show();
                                }
                                popupWindow.dismiss();
                            } else {
                                Toast.makeText(HomeActivity.this, "TODO already present in it.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
    }

    public void getIP() {
        String url = "http://ip.jsontest.com/";
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                TextView textView = findViewById(R.id.IP);
                textView.setText(s);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    public void getDate() {
        String url = "http://time.jsontest.com/";
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                TextView textView = findViewById(R.id.date);
                String s = new String(responseBody);
                textView.setText(s);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
}