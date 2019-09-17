package br.com.havensteinsolutions.agenda.Agenda.asynktask;

import android.os.AsyncTask;
import android.widget.TextView;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.TelefoneDAO;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Telefone;
import br.com.havensteinsolutions.agenda.R;

public class BuscaPrimeiroTelefoneDoAluno extends AsyncTask<Void,Void, Telefone> {
    private final TelefoneDAO dao;
    private final TextView telefone;
    private final int alunoId;

    public BuscaPrimeiroTelefoneDoAluno(TelefoneDAO dao, TextView telefone, int alunoId) {
        this.dao = dao;
        this.telefone = telefone;
        this.alunoId = alunoId;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.buscaPrimeiroTelefoneDoAluno(alunoId);
    }

    @Override
    protected void onPostExecute(Telefone primeirotelefone) {
        super.onPostExecute(primeirotelefone);
        telefone.setText(primeirotelefone.getNumero());
    }
}
