package br.edu.ifsp.dmo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.dmo.R;
import br.edu.ifsp.dmo.mvp.TarefaDetailsMVP;
import br.edu.ifsp.dmo.presenter.TarefaDetailsPresenter;

public class TarefaDetailsActivity extends AppCompatActivity implements TarefaDetailsMVP.View, View.OnClickListener {

    private TarefaDetailsMVP.Presenter presenter;
    private EditText descricaoEditText;
    private EditText tituloEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_details);

        presenter = new TarefaDetailsPresenter(this);
        findViews();
        setListener();
        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.verifyUpdate();
    }

    @Override
    protected void onDestroy() {
        presenter.deatach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(v == saveButton){
            presenter.saveTask(
                    tituloEditText.getText().toString(),
                    descricaoEditText.getText().toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUI(String title, String descricao) {
        tituloEditText.setText(title);
        descricaoEditText.setText(descricao);
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        presenter.deatach();
        finish();
    }

    private void findViews(){
        descricaoEditText = findViewById(R.id.edittext_descricao_details);
        tituloEditText = findViewById(R.id.edittext_title_details);
        saveButton = findViewById(R.id.button_save_task);
    }

    private void setListener(){
        saveButton.setOnClickListener(this);
    }

    private void setToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}