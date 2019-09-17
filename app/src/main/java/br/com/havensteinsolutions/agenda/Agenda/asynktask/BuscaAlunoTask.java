package br.com.havensteinsolutions.agenda.Agenda.asynktask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.List;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.Agenda.adapter.AlunosAdapter;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

public class BuscaAlunoTask extends AsyncTask<Void,Void,AlunosAdapter> {
    private Context contexto;
    private AlunoDAO dao;
    private ListView list;

    public BuscaAlunoTask(Context c, AlunoDAO dao, ListView list) {
        contexto = c;
        this.dao = dao;
        this.list = list;
    }

    @Override
    protected AlunosAdapter doInBackground(Void... objects) {
        List<Aluno> alunos = dao.todos();
        AlunosAdapter adapter = new AlunosAdapter(contexto, alunos);

        return adapter;
    }

    @Override
    protected void onPostExecute(AlunosAdapter adapter) {
        list.setAdapter((AlunosAdapter)adapter);
    }
}
