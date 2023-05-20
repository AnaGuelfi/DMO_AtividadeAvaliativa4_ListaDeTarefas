package br.edu.ifsp.dmo.mvp;

import android.os.Bundle;

public interface TarefaDetailsMVP {
    interface View{
        void updateUI(String title, String descricao);

        Bundle getBundle();

        void showToast(String message);

        void close();
    }

    interface Presenter{
        void deatach();

        void verifyUpdate();

        void saveTask(String title, String descricao);
    }
}
