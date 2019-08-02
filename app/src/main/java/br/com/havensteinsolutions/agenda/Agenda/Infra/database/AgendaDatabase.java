package br.com.havensteinsolutions.agenda.Agenda.Infra.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

@Database(entities = {Aluno.class}, version = 3, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {

    public static final String NOME_DO_BANCO_DE_DADOS = "agenda.db";
    private static AgendaDatabase instance;

    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDatabase getInstance(Context contexto) {
        if (instance == null) {
            instance = Room.databaseBuilder(contexto, AgendaDatabase.class, NOME_DO_BANCO_DE_DADOS)
                    .allowMainThreadQueries()
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            ///fazendo a migração do banco de dados versão 1 para o 2
                            database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
                        }
                    }, new Migration(2, 3) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            ///reverter a base de dados para o que tinhamos na versão 1
                            //criar nova tabela com as informações desejadas

                            //copiar dados da tabela antiga para a nova

                            //remover tabela antiga

                            //renomear a tabela nova com o nome da tabela antiga

                        }
                    })
                    .build();
        }
        return instance;
    }
}
