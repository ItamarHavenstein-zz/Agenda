package br.com.havensteinsolutions.agenda.Food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.havensteinsolutions.agenda.Food.Formatter.FormataTelefoneComDdd;
import br.com.havensteinsolutions.agenda.Food.validator.ValidaCpf;
import br.com.havensteinsolutions.agenda.Food.validator.ValidaEmail;
import br.com.havensteinsolutions.agenda.Food.validator.ValidaTelefoneComDdd;
import br.com.havensteinsolutions.agenda.Food.validator.ValidacaoPadrao;
import br.com.havensteinsolutions.agenda.Food.validator.Validador;
import br.com.havensteinsolutions.agenda.R;

public class FormularioCadastroActivity extends AppCompatActivity {

    private static final String ERRO_FORMATAO_CPF = "erro formatação cpf";
    private final List<Validador> validadores = new ArrayList<>();

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
        configuraBotaoCadastrar();
    }

    private void configuraBotaoCadastrar() {
        Button botaoCadastrar = findViewById(R.id.formulario_cadastro_botao_cadastrar);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean formularioEstaValido = validaTodosCampos();
                if(formularioEstaValido) {
                    cadastroRealizado();
                }
            }
        });
    }

    private void cadastroRealizado() {
        Toast.makeText(FormularioCadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
    }

    private boolean validaTodosCampos() {
        boolean formularioEstaValido = true;
        for(Validador validador: validadores){
            if(!validador.estaValido()){
                formularioEstaValido = false;
            }
        }
        return formularioEstaValido;
    }

    private void configuraCampoSenha() {
        TextInputLayout textInputSenha = (TextInputLayout) findViewById(R.id.formulario_cadastro_campo_senha);
        adicionaValidacaoPadrao(textInputSenha);
    }

    private void configuraCampoEmail() {
        TextInputLayout textInputEmail = (TextInputLayout) findViewById(R.id.formulario_cadastro_campo_email);
        EditText campoEmail = textInputEmail.getEditText();
        ValidaEmail validador = new ValidaEmail(textInputEmail);
        validadores.add(validador);
        campoEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validador.estaValido();
                }
            }
        });
    }

    private void configuraCampoTelefone() {
        TextInputLayout textInputTelefone = (TextInputLayout) findViewById(R.id.formulario_cadastro_campo_telefone);
        EditText campoTelefoneComDdd = textInputTelefone.getEditText();
        ValidaTelefoneComDdd validador = new ValidaTelefoneComDdd(textInputTelefone);
        FormataTelefoneComDdd formatador = new FormataTelefoneComDdd();
        validadores.add(validador);
        campoTelefoneComDdd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String telefoneComDdd = campoTelefoneComDdd.getText().toString();
                if (hasFocus) {
                    String telefoneComDddSemFormatacao = formatador.remove(telefoneComDdd);
                    campoTelefoneComDdd.setText(telefoneComDddSemFormatacao);
                } else {
                    validador.estaValido();
                }
            }
        });
    }

    private void configuraCampoCpf() {
        TextInputLayout textInputCpf = (TextInputLayout) findViewById(R.id.formulario_cadastro_campo_cpf);
        EditText campoCpf = textInputCpf.getEditText();
        CPFFormatter formatador = new CPFFormatter();
        ValidaCpf validador = new ValidaCpf(textInputCpf);
        validadores.add(validador);
        campoCpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    removeFormatacao(formatador, campoCpf);
                } else {
                    validador.estaValido();
                }
            }
        });
    }

    private void removeFormatacao(CPFFormatter formatador, EditText campoCpf) {
        String cpf = campoCpf.getText().toString();
        try {
            String cpfSemFormato = formatador.unformat(cpf);
            campoCpf.setText(cpfSemFormato);
        } catch (IllegalArgumentException e) {
            Log.e(ERRO_FORMATAO_CPF, e.getMessage());
        }
    }

    private void configuraCampoNomeCompleto() {
        TextInputLayout textInputNomeCompleto = (TextInputLayout) findViewById(R.id.formulario_cadastro_nome_completo);
        adicionaValidacaoPadrao(textInputNomeCompleto);
    }

    private void adicionaValidacaoPadrao(TextInputLayout textInputLayout) {
        EditText campo = textInputLayout.getEditText();
        ValidacaoPadrao validacaoPadrao = new ValidacaoPadrao(textInputLayout);
        validadores.add(validacaoPadrao);
        campo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validacaoPadrao.estaValido();
                }
            }
        });
    }
}
