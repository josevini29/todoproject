package br.grupointegrado.bhpachulski.todoproject.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.bhpachulski.todoproject.R;
import br.grupointegrado.bhpachulski.todoproject.model.Categoria;
import br.grupointegrado.bhpachulski.todoproject.model.ToDo;

/**
 * Created by bhpachulski on 29/03/17.
 */

public class DataBase extends SQLiteOpenHelper {

    private Context context;

    private static final String DB_NAME = "toDoDataBase";
    private static final int DB_VERSION = 4;


    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                context.getResources().getString(R.string.createTodo));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert (ToDo todo) {

        ContentValues cv = new ContentValues();
        cv.put("descricao", todo.getDescricao());
        cv.put("dataEntrega", todo.getDataEntrega());
        cv.put("prioridade", todo.getPrioridade());
        cv.put("categoria", todo.getCategoria().getId());

        getWritableDatabase().insert("ToDo", null, cv);

    }

    public void alterar (ToDo todo) {

        ContentValues cv = new ContentValues();
        cv.put("id", todo.getId());
        cv.put("descricao", todo.getDescricao());
        cv.put("dataEntrega", todo.getDataEntrega());
        cv.put("prioridade",  todo.getPrioridade());
        cv.put("categoria", todo.getCategoria().getId());

        getWritableDatabase().update("ToDo", cv,"id=?", new String[]{String.valueOf(todo.getId())});

    }

    public void deletar (ToDo todo) {
        getWritableDatabase().delete("ToDo", "id=?", new String[]{String.valueOf(todo.getId())});
    }


    public List<ToDo> getTodos () {

        Cursor c = getReadableDatabase().rawQuery(
                context.getResources().getString(R.string.getTodos), null);

        List<ToDo> todos = new ArrayList<>();

        while (c.moveToNext()) {

            ToDo todo = new ToDo();
            todo.setId(c.getInt(c.getColumnIndex("id")));
            todo.setDescricao(c.getString(c.getColumnIndex("descricao")));
            todo.setEntrega(c.getString(c.getColumnIndex("dataEntrega")));
            todo.setPrioridade(c.getFloat(c.getColumnIndex("prioridade")));
            todo.setCategoria(Categoria.getCategoria(c.getInt(c.getColumnIndex("categoria"))));

            todos.add(todo);
        }

        return todos;
    }
}
