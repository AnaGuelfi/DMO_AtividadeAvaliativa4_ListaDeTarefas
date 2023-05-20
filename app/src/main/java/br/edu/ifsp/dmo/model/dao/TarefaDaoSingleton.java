package br.edu.ifsp.dmo.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dmo.model.entities.Tarefa;

public class TarefaDaoSingleton implements ITarefaDao{
    private static TarefaDaoSingleton instance = null;
    private List<Tarefa> dataset;
    private TarefaDaoSingleton() {
        dataset = new ArrayList<>();
    }
    public static TarefaDaoSingleton getInstance(){
        if(instance == null)
            instance = new TarefaDaoSingleton();
        return instance;
    }

    @Override
    public void create(Tarefa tarefa) {
        if(tarefa != null){
            dataset.add(tarefa);
        }
    }

    @Override
    public boolean update(String oldTitle, Tarefa tarefa) {
        Tarefa inDataset;
        inDataset = dataset.stream()
                .filter(tarefa1 -> tarefa1.getTitulo().equals(oldTitle))
                .findAny()
                .orElse(null);
        if(inDataset != null){
            inDataset.setTitulo(tarefa.getTitulo());
            inDataset.setDescricao(tarefa.getDescricao());
            inDataset.setFavorite(tarefa.isFavorite());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Tarefa tarefa) {
        return dataset.remove(tarefa);
    }

    @Override
    public Tarefa findByTitle(String title) {
        return dataset.stream()
                .filter(tarefa -> tarefa.getTitulo().equals(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Tarefa> findAll() {
        return dataset;
    }
}
