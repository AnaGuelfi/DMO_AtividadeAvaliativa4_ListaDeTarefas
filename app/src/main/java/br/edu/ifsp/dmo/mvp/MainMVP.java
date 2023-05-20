package br.edu.ifsp.dmo.mvp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.dmo.model.entities.Tarefa;

public interface MainMVP {
    interface View{
        Context getContext();
    }

    interface Presenter{
        void deatach();

        void openDetails();

        void openDetails(Tarefa tarefa);

        void populateList(RecyclerView recyclerView);

        void favoriteTask(Tarefa tarefa);
        void removeTask(Tarefa tarefa);
    }
}
