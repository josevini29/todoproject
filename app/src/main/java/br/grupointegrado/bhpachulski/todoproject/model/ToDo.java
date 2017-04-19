package br.grupointegrado.bhpachulski.todoproject.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bhpachulski on 28/03/17.
 */

public class ToDo implements Serializable {

    private int id;
    private String descricao;
    private Date entrega;
    private Float prioridade;
    private Categoria categoria;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataEntrega () {
        return sdf.format(this.getEntrega());
    }

    public Date getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        try {
            this.entrega = sdf.parse(entrega);
        } catch (ParseException e) {}
    }

    public void setEntrega(Date entrega) {
        this.entrega = entrega;
    }

    public Float getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Float prioridade) {
        this.prioridade = prioridade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
