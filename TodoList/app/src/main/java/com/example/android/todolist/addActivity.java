package com.example.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class addActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText add = (EditText) findViewById(R.id.listName);
        add.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Intent intent = new Intent();
                String newList = add.getText().toString();
                newList = newList.trim();
                intent.putExtra("listName",newList);
                if(newList.length() == 0){
                    add.setText("");
                    return false;
                }
                newList += ".txt";
                addFile(newList);
                add.setText("");
                setResult(RESULT_OK,intent);
                finish();
                return true;
            }
        });
    }

    public void addList(View view) {
        EditText et = (EditText) findViewById(R.id.listName);
        Intent intent = new Intent();
        String fileName = et.getText().toString();
        fileName = fileName.trim();
        if(fileName.length()<= 0){
            Toast.makeText(getApplicationContext(),"Invalid Name",Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("listName",et.getText());
        fileName += ".txt";
        addFile(fileName);
        et.setText("");
        setResult(RESULT_OK,intent);
        finish();
    }

    public void addFile(String fileName){
        File path = getApplicationContext().getFilesDir();
        try {
            File file = new File(path, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(View view) {
        EditText et = (EditText) findViewById(R.id.listName);
        et.setText("");
    }
}
