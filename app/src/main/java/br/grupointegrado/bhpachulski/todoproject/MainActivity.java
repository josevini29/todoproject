package br.grupointegrado.bhpachulski.todoproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.grupointegrado.bhpachulski.todoproject.bd.DataBase;
import br.grupointegrado.bhpachulski.todoproject.model.ToDo;

public class MainActivity extends AppCompatActivity {

    private ListView lvToDo;
    private DataBase db;
    private List<ToDo> listToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvToDo = (ListView) findViewById(R.id.lvTodo);

         db = new DataBase(this);

        lvToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                alterar(listToDo.get(position));

            }

        });

    }

    public void alterar (ToDo e) {
        Intent i = new Intent(this, RegisterToDoActivity.class);
        i.putExtra("todo", e);

        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listToDo = db.getTodos();

        ArrayAdapter<ToDo> la =
                new ArrayAdapter<ToDo>(this, android.R.layout.simple_list_item_1, listToDo);

        lvToDo.setAdapter(la);
    }

    public void gotoAddToDo(View v) {

        Intent i = new Intent(this, RegisterToDoActivity.class);
        startActivity(i);

    }

}
