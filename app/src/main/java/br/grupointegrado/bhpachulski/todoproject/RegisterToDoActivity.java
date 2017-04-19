package br.grupointegrado.bhpachulski.todoproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.grupointegrado.bhpachulski.todoproject.bd.DataBase;
import br.grupointegrado.bhpachulski.todoproject.model.Categoria;
import br.grupointegrado.bhpachulski.todoproject.model.ToDo;

public class RegisterToDoActivity extends AppCompatActivity {

    private EditText edtId;
    private EditText edtDescricao;
    private EditText edtDataEntrega;
    private RatingBar rbPrioridade;
    private Spinner spnCategoria;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_to_do);

        edtId = (EditText) findViewById(R.id.edtId);
        edtDescricao = (EditText) findViewById(R.id.edtDescricao);
        edtDataEntrega = (EditText) findViewById(R.id.edtDataEntrega);
        rbPrioridade = (RatingBar) findViewById(R.id.rbPrioridade);

        List<Categoria> categoria = Arrays.asList(Categoria.values());

        spnCategoria = (Spinner) findViewById(R.id.spnCategoria);

        ArrayAdapter<Categoria> aaCategoria = new ArrayAdapter<Categoria>(this,
                android.R.layout.simple_list_item_1, categoria);

        spnCategoria.setAdapter(aaCategoria);
        try {
            ToDo todo = (ToDo) getIntent().getSerializableExtra("todo");
            edtId.setText(String.valueOf(todo.getId()));
            edtDescricao.setText(String.valueOf(todo.getDescricao()));
            edtDataEntrega.setText(String.valueOf(todo.getDataEntrega().toString()));
            rbPrioridade.setRating(todo.getPrioridade());
            spnCategoria.setSelection(todo.getCategoria().getId() - 1);
        } catch (NullPointerException e) {
            Log.v("ToDo", "Não veio todo para alteração");
        }

    }

    public ToDo getToDo() {
        ToDo todo = new ToDo();
        if (!edtId.getText().toString().equals("")) {
            todo.setId(Integer.parseInt(edtId.getText().toString()));
        }
        todo.setDescricao(edtDescricao.getText().toString());
        todo.setEntrega(edtDataEntrega.getText().toString());
        todo.setPrioridade(rbPrioridade.getRating());

        todo.setCategoria((Categoria) spnCategoria.getSelectedItem());

        return todo;
    }

    public void limpaActivity() {
        edtDescricao.setText("");
        edtDataEntrega.setText("");
        rbPrioridade.setRating(0);

        spnCategoria.setSelection(0);

        edtDescricao.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_padrao, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_salvar:

                //Usando o método
                ToDo todo = this.getToDo();

                DataBase db = new DataBase(this);
                if (edtId.getText().toString().equals("")) {
                    db.insert(todo);
                    Toast.makeText(this, "Registro salvo!", Toast.LENGTH_SHORT).show();
                    this.limpaActivity();
                    this.onBackPressed();
                } else {
                    db.alterar(todo);
                    Toast.makeText(this, "Registro alterado!", Toast.LENGTH_SHORT).show();
                }



                break;

            case R.id.action_cancelar:

                this.onBackPressed();

                Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT).show();

                break;
            case R.id.action_apagar:

                todo = this.getToDo();
                db = new DataBase(this);
                if (!edtId.getText().toString().equals("")) {
                    db.deletar(todo);
                }

                this.onBackPressed();

                Toast.makeText(this, "Registro apagado!", Toast.LENGTH_SHORT).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
