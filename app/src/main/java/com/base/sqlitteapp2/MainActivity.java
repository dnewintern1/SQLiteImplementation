package com.base.sqlitteapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    EditText edt_Name, edt_Type;

    Button btnadd, btndelete;

    ListView listView;
    List<Computer> allComputer;

    ArrayList<String> computerName;

    MySQLiteHandler databaseHandler;

    ArrayAdapter  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_Name = findViewById(R.id.edt_Name);
        edt_Type = findViewById(R.id.edt_Type);
        btnadd = findViewById(R.id.btnadd);
        btndelete = findViewById(R.id.btndelete);
        listView = findViewById(R.id.listView);

       btnadd.setOnClickListener(MainActivity.this);
       btndelete.setOnClickListener(MainActivity.this);

       databaseHandler = new MySQLiteHandler(MainActivity.this);
       allComputer = databaseHandler.getAllComputers();
       computerName = new ArrayList<>();

       if(allComputer.size() > 0 ){
           for(int i = 0 ; i <allComputer.size();i++){

               Computer computer = allComputer.get(i);
               computerName.add(computer.getComputerName() + " - " + computer.getComputerType());
           }
       }

       adapter =  new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, computerName);
       listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnadd){
            if(edt_Name.getText().toString().matches("") || edt_Type.getText().toString().matches("")){
                return;
            }else{
                Computer computer = new Computer(edt_Name.getText().toString(), edt_Type.getText().toString());

                allComputer.add(computer);
                databaseHandler.addComputer(computer);
                computerName.add(computer.getComputerName() + " - " + computer.getComputerType());
                edt_Name.setText("");
                edt_Type.setText("");

            }
        }
        if (v.getId() == R.id.btndelete){

            if(allComputer.size()>0){
                computerName.remove(0);
                databaseHandler.deleteComputer(allComputer.get(0));
                allComputer.remove(0);

            }else {
                return;
            }
        }
        adapter.notifyDataSetChanged();
    }
}