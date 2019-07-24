package br.com.havensteinsolutions.agenda.Agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.room.Room;

import java.util.List;

import br.com.havensteinsolutions.agenda.Agenda.Converter.AlunoConverter;
import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.Agenda.Infra.database.AgendaDatabase;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;
    private final AlunoDAO dao;

    public EnviaAlunosTask(Context context) {
        this.context = context;
        this.dao = Room.databaseBuilder(context, AgendaDatabase.class, "agenda.db")
                .allowMainThreadQueries()
                .build()
                .getRoomAlunoDAO();
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos...", true, true);
    }

    @Override
    protected String doInBackground(Void... objects) {
        List<Aluno> alunos = dao.todos();
        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.Post(json);
        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
