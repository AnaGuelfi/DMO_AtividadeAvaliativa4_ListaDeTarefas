package br.edu.ifsp.dmo.model.entities;

import java.time.LocalDate;

public class Tarefa {
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private boolean favorite;

    public Tarefa(String descricao, String titulo){
        this.descricao = descricao;
        this.titulo = titulo;
    }

    public Tarefa(String descricao, String titulo, boolean favorite){
        this.descricao = descricao;
        this.titulo = titulo;
        this.favorite = favorite;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
