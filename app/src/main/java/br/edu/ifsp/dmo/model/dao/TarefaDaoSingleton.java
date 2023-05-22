package br.edu.ifsp.dmo.model.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifsp.dmo.model.entities.Tarefa;
import br.edu.ifsp.dmo.utils.Constant;

public class TarefaDaoSingleton implements ITarefaDao{
    private static TarefaDaoSingleton instance = null;
    private Context context;
    private List<Tarefa> dataset;
    private TarefaDaoSingleton() {
        dataset = new ArrayList<>();
    }
    public static TarefaDaoSingleton getInstance(){
        if(instance == null)
            instance = new TarefaDaoSingleton();
        return instance;
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public void create(Tarefa tarefa) {
        if(tarefa != null){
            dataset.add(tarefa);
            dataset.stream().sorted(Comparator.comparing(Tarefa::isFavorite,Comparator.reverseOrder())).collect(Collectors.toList());
            writeDataset();
            readDatabase();
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
            inDataset.setDataCriacao(tarefa.getDataCriacao());
            inDataset.setDescricao(tarefa.getDescricao());
            inDataset.setFavorite(tarefa.isFavorite());
            dataset.stream().sorted(Comparator.comparing(Tarefa::isFavorite,Comparator.reverseOrder())).collect(Collectors.toList());
            writeDataset();
            readDatabase();
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
        return dataset.stream().sorted(Comparator.comparing(Tarefa::isFavorite,Comparator.reverseOrder())).collect(Collectors.toList());
    }

    private void writeDataset(){
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        JSONObject jsonObject;
        JSONArray jsonArray;

        jsonArray = new JSONArray();
        for(Tarefa t : dataset){
            jsonObject = new JSONObject();
            try{
                jsonObject.put(Constant.ATTR_TITLE, t.getTitulo());
                jsonObject.put(Constant.ATTR_DESC, t.getDescricao());
                jsonObject.put(Constant.ATTR_DATE, t.getDataCriacao());
                jsonObject.put(Constant.ATTR_FAVORITE, t.isFavorite());
                jsonArray.put(jsonObject);
            }catch (JSONException e){
                Log.e("erro", e.getMessage());
            }
        }
        preferences = context.getSharedPreferences(Constant.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(Constant.TABLE_NAME, jsonArray.toString());
        editor.commit();
    }

    private void readDatabase(){
        SharedPreferences preferences;
        String json;
        Tarefa tarefa;
        JSONObject jsonObject;
        JSONArray jsonArray;

        preferences = context.getSharedPreferences(Constant.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
        json = preferences.getString(Constant.TABLE_NAME, "");

        if(!json.isEmpty()){
            dataset.clear();
            try{
                jsonArray = new JSONArray(json);
                for(int i=0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    tarefa = new Tarefa(jsonObject.getString(Constant.ATTR_TITLE), jsonObject.getString(Constant.ATTR_DESC), jsonObject.getBoolean(Constant.ATTR_FAVORITE));
                    dataset.add(tarefa);
                }
            }catch (JSONException e){
                Log.e("TaskDAOJson", e.getMessage());
            }
        }else{
            Log.v("TaskDAOJson", "Sem dados para recuperar do JSON");
        }
    }
}
