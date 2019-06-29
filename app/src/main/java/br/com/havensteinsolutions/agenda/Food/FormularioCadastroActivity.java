package br.com.havensteinsolutions.agenda.Food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.havensteinsolutions.agenda.R;

public class FormularioCadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cadastro);

        inicializaCampos();
    }

    private void inicializaCampos() {
        configuraCampoNomeCompleto();
        configuraCampoCpf();
        configuraCampoTelefone();
        configuraCampoEmail();
        configuraCampoSenha();
    }

    private void configuraCampoSenha() {
        TextInputLayout textInputSenha = (TextInputLayout) findViewById(R.id.formulario_cadastro_campo_senha);
        adicionaValidacaoPadrao(textInputSenha);
    }

    private void configuraCampoEmail() {
        TextInputLayout textInputEmail = (TextInputLayout) findViewById(R.id.formulario_cadastro_campo_email);
        adicionaValidacaoPadrao(textInputEmail);
    }

    private void configuraCampoTelefone() {
        TextInputLayout textInputTelefone = (TextInputLayout) findViewById(R.id.formulario_cadastro_campo_telefone);
        adicionaValidacaoPadrao(textInputTelefone);
    }

    private void configuraCampoCpf() {
        TextInputLayout textInputCpf = (TextInputLayout) findViewById(R.id.formulario_cadastro_campo_cpf);
        adicionaValidacaoPadrao(textInputCpf);
    }

    private void configuraCampoNomeCompleto() {
        TextInputLayout textInputNomeCompleto = (TextInputLayout) findViewById(R.id.formulario_cadastro_nome_completo);
        adicionaValidacaoPadrao(textInputNomeCompleto);
    }

    private void adicionaValidacaoPadrao( TextInputLayout textInputLayout){
        EditText campo = textInputLayout.getEditText();
        campo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String texto = campo.getText().toString();
                if(!hasFocus){
                    validaCampoObrigatorio(texto, textInputLayout);
                }
            }
        });
    }

    private void validaCampoObrigatorio(String texto, TextInputLayout textInputLayout) {
        if (texto.isEmpty()) {
            textInputLayout.setError("Campo Obrigatório");
        }else{
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        }
    }
}
