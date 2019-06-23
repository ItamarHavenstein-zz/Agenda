package br.com.havensteinsolutions.agenda;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import br.com.havensteinsolutions.agenda.Agenda.ListaAlunosActivity;
import br.com.havensteinsolutions.agenda.Food.MainActivity;

public class PrincipalActivity extends AppCompatActivity {

    private Button btnAgenda;
    private Button btnFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MapearInterface();
        ClickBotao();
    }

    private void ClickBotao() {
        btnAgenda.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent vaiParaAgenda = new Intent(PrincipalActivity.this, ListaAlunosActivity.class);
                startActivity(vaiParaAgenda);
            }
        });

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFood = new Intent(PrincipalActivity.this, MainActivity.class);
                startActivity(vaiParaFood);
            }
        });
    }

    private void MapearInterface() {
        btnAgenda = (Button)findViewById(R.id.btn_agenda);
        btnFood = (Button) findViewById(R.id.btn_food);
    }
}
