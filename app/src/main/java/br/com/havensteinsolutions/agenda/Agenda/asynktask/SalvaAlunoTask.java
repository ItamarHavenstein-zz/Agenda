package br.com.havensteinsolutions.agenda.Agenda.asynktask;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.TelefoneDAO;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Telefone;

public class SalvaAlunoTask extends BaseAlunoComTelefoneTask {

    private final AlunoDAO alunoDAO;
    private final Aluno aluno;
    private final Telefone telefoneFixo;
    private final Telefone telefoneCelular;
    private final TelefoneDAO telefoneDAO;

    public SalvaAlunoTask(AlunoDAO alunoDAO, Aluno aluno, Telefone telefoneFixo, Telefone telefoneCelular, TelefoneDAO telefoneDAO, FinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.aluno = aluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.telefoneDAO = telefoneDAO;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int alunoId = alunoDAO.salva(aluno).intValue();
        vinculaAlunoComTelefone(alunoId, telefoneFixo, telefoneCelular);
        telefoneDAO.salva(telefoneFixo, telefoneCelular);
        return null;
    }
}
