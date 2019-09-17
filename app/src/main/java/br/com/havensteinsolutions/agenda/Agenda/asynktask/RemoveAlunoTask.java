package br.com.havensteinsolutions.agenda.Agenda.asynktask;

import android.os.AsyncTask;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

public class RemoveAlunoTask extends AsyncTask<Void,Void,Void> {
    private AlunoDAO dao;
    private Aluno aluno;

    public RemoveAlunoTask(AlunoDAO dao, Aluno aluno) {
        this.dao = dao;
        this.aluno = aluno;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.remove(aluno);
        return null;
    }
}
