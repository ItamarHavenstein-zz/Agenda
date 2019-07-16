package br.com.havensteinsolutions.agenda.Food.validator;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ValidaEmail implements Validador{

    private final TextInputLayout textInputEmail;
    private final EditText campoEmail;
    private final ValidacaoPadrao validador;

    public ValidaEmail(TextInputLayout textInputEmail) {
        this.textInputEmail = textInputEmail;
        this.campoEmail = textInputEmail.getEditText();
        this.validador = new ValidacaoPadrao(this.textInputEmail);
    }

    private boolean validaPadrao(String email) {
        if (email.matches(".+@.+\\..+")) {
            return true;
        }
        textInputEmail.setError("E-mail inválido");
        return false;
    }

    @Override
    public boolean estaValido() {
        if (!validador.estaValido()) return false;
        String email = campoEmail.getText().toString();
        if (!validaPadrao(email)) return false;
        return true;
    }
}
