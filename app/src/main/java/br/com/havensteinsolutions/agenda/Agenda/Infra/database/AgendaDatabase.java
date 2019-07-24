package br.com.havensteinsolutions.agenda.Agenda.Infra.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

@Database(entities = {Aluno.class}, version = 1, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {
    public abstract AlunoDAO getRoomAlunoDAO();
}
