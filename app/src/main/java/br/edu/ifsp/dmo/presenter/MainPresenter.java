package br.edu.ifsp.dmo.presenter;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.dmo.model.dao.ITarefaDao;
import br.edu.ifsp.dmo.model.entities.Tarefa;
import br.edu.ifsp.dmo.mvp.MainMVP;

public class MainPresenter implements MainMVP.Presenter {
    private MainMVP.View view;
    private ITarefaDao dao;
    Tarefa tarefa;
    @Override
    public void deatach() {
        view = null;
    }

    @Override
    public void openDetails() {
        Intent intent = new Intent(view.getContext(), TarefaDetailsActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void openDetails(Tarefa tarefa) {
        Intent intent = new Intent(view.getContext(), TarefaDetailsActivity.class);
        intent.putExtra(Constant.ATTR_TITLE, tarefa.getTitulo());
        view.getContext().startActivity(intent);
    }

    @Override
    public void populateList(RecyclerView recyclerView) {
        ItemPocketRecyclerAdapter adapter = new
                ItemPocketRecyclerAdapter(view.getContext(), dao.findAll(), this);
        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                tarefa = dao.findAll().get(position);
                openDetails(tarefa);
            }
        });
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void favoriteTask(Tarefa tarefa) {
        tarefa.setFavorite(!tarefa.isFavorite());
        dao.update(tarefa.getTitulo(), tarefa);
    }
}