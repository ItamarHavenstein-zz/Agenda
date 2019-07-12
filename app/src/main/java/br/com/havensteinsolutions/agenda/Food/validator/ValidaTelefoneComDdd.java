package br.com.havensteinsolutions.agenda.Food.validator;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.havensteinsolutions.agenda.Food.Formatter.FormataTelefoneComDdd;

public class ValidaTelefoneComDdd {

    public static final String TELEFONE_DEVE_TER_ENTRE_10_A_11_DIGITOS = "Telefone deve ter entre 10 a 11 digitos";
    private final TextInputLayout textImputTelefoneComDdd;
    private final ValidacaoPadrao validacaoPadrao;
    private final EditText campoTelefoneComDdd;
    private final FormataTelefoneComDdd formatador = new FormataTelefoneComDdd();

    public ValidaTelefoneComDdd(TextInputLayout textImputTelefoneComDdd) {
        this.textImputTelefoneComDdd = textImputTelefoneComDdd;
        this.campoTelefoneComDdd = textImputTelefoneComDdd.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textImputTelefoneComDdd);
    }

    private boolean validaEntreDezouOnzeDigitos(String telefoneCOmDdd){
        int digitos = telefoneCOmDdd.length();
        if(digitos <10 || digitos > 11){
            textImputTelefoneComDdd.setError(TELEFONE_DEVE_TER_ENTRE_10_A_11_DIGITOS);
            return false;
        }
        return true;
    }

    public boolean estaValido(){
        if(!validacaoPadrao.estaValido())return false;
        String telefoneComDdd = campoTelefoneComDdd.getText().toString();
        if(!validaEntreDezouOnzeDigitos(telefoneComDdd))return false;
        adicionaFormatacao(telefoneComDdd);
        return true;
    }

    private void adicionaFormatacao(String telefoneComDdd) {
        String telefoneComDddFormatado = formatador.Formata(telefoneComDdd);
        campoTelefoneComDdd.setText(telefoneComDddFormatado);
    }
}
