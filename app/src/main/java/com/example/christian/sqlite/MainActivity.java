package com.example.christian.sqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText eFirstName, eLastName, eScore;
    String fn,ln;
    int score;
    DBHelper helper;
    Cursor table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBHelper(this);
        eFirstName = findViewById(R.id.etfname);
        eLastName = findViewById(R.id.etlname);
        eScore = findViewById(R.id.etscore);
        table = helper.populateTable();
        table.moveToFirst();
        displayData();


    }
    public void addRecord(View v){
        fn =eFirstName.getText().toString();
        ln =eLastName.getText().toString();
        score = Integer.parseInt(eScore.getText().toString());
        boolean isInserted = helper.insert(fn, ln, score);
        if(isInserted == true){
              Toast.makeText(this, "Record added successfully", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Failed adding record", Toast.LENGTH_LONG).show();
        }
    }

    public void moveFirst(View v){
        table.moveToFirst();
        displayData();
    }
    public void movePrevious(View v){
        if(table.getPosition()==0){
            table.moveToFirst();
             Toast.makeText(this,"Reach the first record ",Toast.LENGTH_LONG).show();
        }else
        {
            table.moveToPrevious();
        }

       displayData();
    }
    public void moveNext(View v){
        if(table.getPosition()==(table.getCount()-1)){
            table.moveToLast();
            Toast.makeText(this,"Reach the last record ",Toast.LENGTH_LONG).show();
        }else{
            table.moveToNext();
        }

      displayData();
    }
    public void moveLast(View v){
        table.moveToLast();
       displayData();
    }
    private void displayData(){
        eFirstName.setText(table.getString(1));
        eLastName.setText(table.getString(2));
        eScore.setText(table.getString(3));
    }
    public void editRecord(View v){

        fn =eFirstName.getText().toString();
        ln =eLastName.getText().toString();
        String id = table.getString(0);
        boolean isUpdated = helper.update(id,fn,ln,score);
        if(isUpdated == true){
            table = helper.populateTable();
            Toast.makeText(this, "record was updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"record was not updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeRecord(View v){

        String id = table.getString(0);
        boolean isRemoved = helper.delete(id);
        if(isRemoved == true){
            table = helper.populateTable();
            eFirstName.setText("");
            eLastName.setText("");
            eScore.setText("");
            Toast.makeText(this, "record was deleted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"record was not deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
