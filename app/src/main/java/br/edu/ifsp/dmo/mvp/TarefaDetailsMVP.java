package br.edu.ifsp.dmo.mvp;

import android.os.Bundle;

import java.time.LocalDate;

public interface TarefaDetailsMVP {
    interface View{
        void updateUI(String title, String descricao, LocalDate data);
        Bundle getBundle();
        void showToast(String message);
        void close();
    }

    interface Presenter{
        void deatach();
        void verifyUpdate();
        void saveTask(String title, String descricao, String data);
    }
}
