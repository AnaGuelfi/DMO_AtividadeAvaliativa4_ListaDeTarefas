package br.edu.ifsp.dmo.model.dao;

import android.content.Context;

import java.util.List;

import br.edu.ifsp.dmo.model.entities.Tarefa;

public interface ITarefaDao {
    void create(Tarefa tarefa);
    boolean update(String oldTitle, Tarefa tarefa);
    boolean delete(Tarefa tarefa);
    Tarefa findByTitle(String title);
    List<Tarefa> findAll();
    void setContext(Context context);
}
