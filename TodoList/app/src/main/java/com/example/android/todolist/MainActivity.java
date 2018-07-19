package com.example.android.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText add = (EditText) findViewById(R.id.add);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ListView list = (ListView) findViewById(R.id.todo);

        //Default files must be written to for initial state
        writeToFile("To-Do.txt","");
        writeToFile("Completed.txt","");

        //Getting initial state
        list.setAdapter(makeListAdapter(getEntries("To-Do.txt")));
        spinner.setAdapter(makeSpinnerAdapter(getSpinnerEntires()));

        add.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String newTodo = add.getText().toString();
                newTodo = newTodo.trim();
                if(newTodo.length() == 0){
                    add.setText("");
                    return false;
                }
                String filename = spinner.getSelectedItem().toString() + ".txt";
                writeToFile(filename,newTodo);
                list.setAdapter(makeListAdapter(getEntries(filename)));
                add.setText("");
                return true;
            }
        });

        //Switching list
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String filename = spinner.getSelectedItem().toString() + ".txt";
                if(filename.equals("Completed.txt")){
                    add.setVisibility(View.GONE);
                }else{
                    add.setVisibility(1);
                }
                list.setAdapter(makeListAdapter(getEntries(filename)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Removing an item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filename = spinner.getSelectedItem().toString() + ".txt";
                if(filename.equals("Completed.txt")) {
                    deleteFromFile(filename, i);
                }
                else{
                    moveToCompleted(filename,i);
                    spinner.setAdapter(makeSpinnerAdapter(getSpinnerEntires()));
                }
                list.setAdapter(makeListAdapter(getEntries(filename)));
            }
        });
    }

    private List<String> getSpinnerEntires(){
        List<String> spinnerEntries = new ArrayList<>();
        File path = getApplicationContext().getFilesDir();
        for(String fileName : path.list()){
            spinnerEntries.add(fileName.substring(0, fileName.lastIndexOf('.')));
        }
        return spinnerEntries;
    }

    private List<String> getEntries(String fileName){
        File path = getApplicationContext().getFilesDir();
        List<String> entries = new ArrayList<>();
        try{
            File file = new File(path,fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            InputStream inputStream = getApplicationContext().openFileInput(fileName);
            Scanner scan = new Scanner(inputStream);
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(line.length() >0) {
                    entries.add(line);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return entries;
    }

    private void writeToFile(String fileName, String data){
        File path = getApplicationContext().getFilesDir();
        try{
            File file = new File(path, fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    getApplicationContext().openFileOutput(
                            fileName,
                            Context.MODE_APPEND));
                    if(data.length() > 0) {
                        outputStreamWriter.write(data + "\n");
                    }
                    outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void moveToCompleted(String fileName,int i){
        String s = deleteFromFile(fileName,i);
        writeToFile("Completed.txt",s);
    }

    private String deleteFromFile(String fileName,int i) {
        List<String> entries = new ArrayList<String>();
        String result="";
        try {
            InputStream inputStream = getApplicationContext().openFileInput(fileName);
            Scanner scanner = new Scanner(inputStream);
            while(scanner.hasNextLine()){
                    entries.add(scanner.nextLine());
            }
            result += entries.remove(i);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    getApplicationContext().openFileOutput(
                            fileName,
                            Context.MODE_PRIVATE));
            for(String s : entries) {
                outputStreamWriter.write(s + "\n");
            }
            outputStreamWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ArrayAdapter<String> makeListAdapter(List<String> entries){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                entries
        );
        return adapter;
    }

    private ArrayAdapter<String> makeSpinnerAdapter(List<String> entries){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                entries
        );
        return adapter;
    }
}
