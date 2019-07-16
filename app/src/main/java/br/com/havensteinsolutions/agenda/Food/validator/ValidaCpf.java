package br.com.havensteinsolutions.agenda.Food.validator;

import android.util.Log;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidaCpf implements Validador {

    private static final String CPF_INVALIDO = "CPF Inválido.";
    private static final String ERRO_FORMATAO_CPF = "erro formatação cpf";
    private static final String DEVE_TER_11_DIGITOS = "O Cpf precisa ter 11 digitos.";
    private final TextInputLayout textInputCpf;
    private final EditText campoCpf;
    private final ValidacaoPadrao validacaoPadrao;
    private final CPFFormatter cpfFormatter = new CPFFormatter();

    public ValidaCpf(TextInputLayout textInputCpf) {
        this.textInputCpf = textInputCpf;
        this.campoCpf = textInputCpf.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textInputCpf);
    }

    @Override
    public boolean estaValido() {
        if (!validacaoPadrao.estaValido()) return false;
        String cpf = getCpf();
        String cpfSemFormato = cpf;
        try {
            cpfSemFormato = cpfFormatter.unformat(cpf);
        } catch (IllegalArgumentException e) {
            Log.e(ERRO_FORMATAO_CPF, e.getMessage());
        }
        if (!ValidaCampoCom11Digitos(cpfSemFormato)) return false;
        if (!validaCalculoCpf(cpfSemFormato)) return false;
        adicionaFormatacao(cpfSemFormato);
        return true;
    }

    private void adicionaFormatacao(String cpf) {
        String cpfFormatado = cpfFormatter.format(cpf);
        campoCpf.setText(cpfFormatado);
    }

    private boolean validaCalculoCpf(String cpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException ex) {
            textInputCpf.setError(CPF_INVALIDO);
            return false;
        }
        return true;
    }

    private String getCpf() {
        return campoCpf.getText().toString();
    }

    private boolean ValidaCampoCom11Digitos(String cpf) {
        if (cpf.length() != 11) {
            textInputCpf.setError(DEVE_TER_11_DIGITOS);
            return false;
        }
        return true;
    }
}
