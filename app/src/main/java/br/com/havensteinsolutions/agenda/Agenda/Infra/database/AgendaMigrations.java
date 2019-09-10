package br.com.havensteinsolutions.agenda.Agenda.Infra.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.havensteinsolutions.agenda.Agenda.modelo.TipoTelefone;

import static br.com.havensteinsolutions.agenda.Agenda.modelo.TipoTelefone.FIXO;

class AgendaMigrations {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            ///fazendo a migração do banco de dados versão 1 para o 2
            database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            ///reverter a base de dados para o que tinhamos na versão 1
            //criar nova tabela com as informações desejadas
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo`" +
                    " (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " `nome` TEXT," +
                    " `endereco` TEXT, " +
                    "`telefone` TEXT, " +
                    "`site` TEXT, " +
                    "`nota` REAL NOT NULL, " +
                    "`caminhoFoto` TEXT)");

            //copiar dados da tabela antiga para a nova
            database.execSQL("INSERT INTO Aluno_novo (id, nome, endereco, telefone, site, nota, caminhoFoto) " +
                    "SELECT id, nome, endereco, telefone, site, nota, caminhoFoto FROM Aluno");

            //remover tabela antiga
            database.execSQL("DROP TABLE Aluno");

            //renomear a tabela nova com o nome da tabela antiga
            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");

        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN momentoDeCadastro INTEGER");
        }
    };
    private static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (`" +
                    "id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "`nome` TEXT, " +
                    "`endereco` TEXT, " +
                    "`telefoneFixo` TEXT, " +
                    "`TelefoneCelular` TEXT, " +
                    "`site` TEXT, " +
                    "`nota` REAL NOT NULL, " +
                    "`caminhoFoto` TEXT, " +
                    "`momentoDeCadastro` INTEGER)");

            database.execSQL("INSERT INTO Aluno_novo (id, nome, endereco, telefoneFixo, site, nota, caminhoFoto, momentoDeCadastro) " +
                    "SELECT id, nome, endereco, telefone, site, nota, caminhoFoto, momentoDeCadastro FROM Aluno");

            database.execSQL("DROP TABLE Aluno");

            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");

        }
    };
    private static final Migration MIGRATION_5_6 = new Migration(5,6){
      @Override
      public void migrate(@NonNull SupportSQLiteDatabase database){
         database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (" +
                 "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                 "`nome` TEXT, " +
                 "`endereco` TEXT, " +
                 "`site` TEXT, " +
                 "`nota` REAL NOT NULL, " +
                 "`caminhoFoto` TEXT, " +
                 "`momentoDeCadastro` INTEGER)");

          database.execSQL("INSERT INTO Aluno_novo (id, nome, endereco, site, nota, caminhoFoto, momentoDeCadastro) " +
                  "SELECT id, nome, endereco, site, nota, caminhoFoto, momentoDeCadastro FROM Aluno");


          database.execSQL("CREATE TABLE IF NOT EXISTS `Telefone` (" +
                  "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  "`numero` TEXT, " +
                  "`tipo` TEXT, " +
                  "`alunoId` INTEGER NOT NULL)");

          database.execSQL("INSERT INTO Telefone (numero,alunoId) " +
                  "SELECT telefoneFixo, id FROM Aluno");

          database.execSQL("UPDATE Telefone SET tipo = ?",new TipoTelefone[] {FIXO});

          database.execSQL("DROP TABLE Aluno");

          database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
      }
    };

    static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6};
}
