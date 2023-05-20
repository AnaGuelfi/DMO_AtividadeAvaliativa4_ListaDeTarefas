package br.edu.ifsp.dmo.presenter;

import android.os.Bundle;

import br.edu.ifsp.dmo.model.dao.ITarefaDao;
import br.edu.ifsp.dmo.model.dao.TarefaDaoSingleton;
import br.edu.ifsp.dmo.model.entities.Tarefa;
import br.edu.ifsp.dmo.mvp.TarefaDetailsMVP;
import br.edu.ifsp.dmo.utils.Constant;

public class TarefaDetailsPresenter implements TarefaDetailsMVP.Presenter{

    private TarefaDetailsMVP.View view;
    private Tarefa tarefa;
    private ITarefaDao dao;

    public TarefaDetailsPresenter(TarefaDetailsMVP.View view){
        this.view = view;
        tarefa = null;
        dao = TarefaDaoSingleton.getInstance();
    }

    @Override
    public void deatach() {
        this.view = null;
    }

    @Override
    public void verifyUpdate() {
        String title;
        Bundle bundle = view.getBundle();
        if(bundle != null){
            title = bundle.getString(Constant.ATTR_TITLE);
            tarefa = dao.findByTitle(title);
            view.updateUI(tarefa.getTitulo(), tarefa.getDescricao());
        }
    }

    @Override
    public void saveTask(String title, String descricao) {
        if(tarefa == null){
            tarefa = new Tarefa(descricao, title);
            dao.create(tarefa);
            view.showToast("Novo artigo adicionado com sucesso.");
            view.close();
        }else{
            //Update a existent article
            String oldTitle = tarefa.getTitulo();
            Tarefa newTarefa = new Tarefa(descricao, title, tarefa.isFavorite());
            if(dao.update(oldTitle, newTarefa)){
                view.showToast("Tarefa atualizada com sucesso.");
                view.close();
            }else{
                view.showToast("Erro ao atualizar a tarefa.");
            }
        }
    }
}
