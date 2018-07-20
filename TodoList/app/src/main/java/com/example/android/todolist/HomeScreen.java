package com.example.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {
    final int idListActivity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void listActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addActivity(View view) {
        Intent intent = new Intent(this, addActivity.class);
        startActivityForResult(intent,idListActivity);

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if(resultCode == idListActivity){
            String listName = data.getStringExtra("listName");
            Toast.makeText(getApplicationContext(),listName,Toast.LENGTH_SHORT).show();
        }
    }

}
