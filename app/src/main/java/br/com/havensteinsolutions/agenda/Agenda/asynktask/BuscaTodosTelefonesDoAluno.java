package br.com.havensteinsolutions.agenda.Agenda.asynktask;

import android.os.AsyncTask;

import java.util.List;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.TelefoneDAO;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Telefone;

public class BuscaTodosTelefonesDoAluno extends AsyncTask<Void, Void, List<Telefone>> {

    private final TelefoneDAO telefoneDAO;
    private final Aluno aluno;
    private final TelefonesDoAlunoEncontradosListener listener;

    public BuscaTodosTelefonesDoAluno(TelefoneDAO telefoneDAO, Aluno aluno, TelefonesDoAlunoEncontradosListener listener) {
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.listener = listener;
    }

    @Override
    protected List<Telefone> doInBackground(Void... voids) {
        return telefoneDAO.buscaTodosTelefonesDoAluno(aluno.getId());
    }

    @Override
    protected void onPostExecute(List<Telefone> telefones) {
        super.onPostExecute(telefones);
        listener.quandoEncontrados(telefones);
    }

    public interface TelefonesDoAlunoEncontradosListener{
        void quandoEncontrados(List<Telefone> telefones);
    }
}
