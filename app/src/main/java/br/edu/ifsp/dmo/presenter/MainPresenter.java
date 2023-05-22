package br.edu.ifsp.dmo.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.stream.Collectors;

import br.edu.ifsp.dmo.model.dao.ITarefaDao;
import br.edu.ifsp.dmo.model.dao.TarefaDaoSingleton;
import br.edu.ifsp.dmo.model.entities.Tarefa;
import br.edu.ifsp.dmo.mvp.MainMVP;
import br.edu.ifsp.dmo.utils.Constant;
import br.edu.ifsp.dmo.view.RecyclerViewItemClickListener;
import br.edu.ifsp.dmo.view.TarefaDetailsActivity;
import br.edu.ifsp.dmo.view.adapter.ItemPocketRecyclerAdapter;

public class MainPresenter implements MainMVP.Presenter {
    private MainMVP.View view;
    private ITarefaDao dao;
    private Context context;
    Tarefa tarefa;

    public MainPresenter(MainMVP.View view) {
        this.view = view;
        dao = TarefaDaoSingleton.getInstance();
        context = view.getContext();
        dao.setContext(context);
    }

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
                ItemPocketRecyclerAdapter(view.getContext(), dao.findAll().stream().sorted(Comparator.comparing(Tarefa::isFavorite,Comparator.reverseOrder())).collect(Collectors.toList()), this);
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

    @Override
    public void removeTask(Tarefa tarefa) {
        dao.delete(tarefa);
    }

    @Override
    public void editTask(Tarefa tarefa) {
        openDetails(tarefa);
    }
}
