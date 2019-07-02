package br.com.havensteinsolutions.agenda.Food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
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
        EditText campoCpf = textInputCpf.getEditText();
        CPFFormatter cpfFormatter = new CPFFormatter();
        campoCpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String cpf = campoCpf.getText().toString();
                if (!hasFocus) {
                    if (!validaCampoObrigatorio(cpf, textInputCpf)) return;
                    if (!ValidaCampoCom11Digitos(cpf, textInputCpf)) return;
                    if (!validaCalculoCpf(cpf, textInputCpf)) return;
                    removeErro(textInputCpf);

                    String cpfFormatado = cpfFormatter.format(cpf);
                    campoCpf.setText(cpfFormatado);
                }else{
                    try {
                        String cpfSemFormato = cpfFormatter.unformat(cpf);
                        campoCpf.setText(cpfSemFormato);
                    }catch (IllegalArgumentException e){
                        Log.e("erro formatação cpf", e.getMessage());
                    }
                }
            }
        });
    }

    private boolean validaCalculoCpf(String cpf, TextInputLayout textInputCpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException ex) {
            textInputCpf.setError("CPF Inválido.");
            return false;
        }
        return true;
    }

    private void removeErro(TextInputLayout textInputCpf) {
        textInputCpf.setError(null);
        textInputCpf.setErrorEnabled(false);
    }

    private boolean ValidaCampoCom11Digitos(String cpf, TextInputLayout textInputCpf) {
        if (cpf.length() != 11) {
            textInputCpf.setError("O Cpf precisa ter 11 digitos.");
            return false;
        }
        return true;
    }

    private void configuraCampoNomeCompleto() {
        TextInputLayout textInputNomeCompleto = (TextInputLayout) findViewById(R.id.formulario_cadastro_nome_completo);
        adicionaValidacaoPadrao(textInputNomeCompleto);
    }

    private void adicionaValidacaoPadrao(TextInputLayout textInputLayout) {
        EditText campo = textInputLayout.getEditText();
        campo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String texto = campo.getText().toString();
                if (!hasFocus) {
                    if (!validaCampoObrigatorio(texto, textInputLayout)) return;
                    removeErro(textInputLayout);
                }
            }
        });
    }

    private boolean validaCampoObrigatorio(String texto, TextInputLayout textInputLayout) {
        if (texto.isEmpty()) {
            textInputLayout.setError("Campo Obrigatório");
            return false;
        }
        return true;
    }
}
