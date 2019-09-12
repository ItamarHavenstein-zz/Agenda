package br.com.havensteinsolutions.agenda.Agenda.Infra.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

@Dao
public interface AlunoDAO {
    @Insert
    Long salva(Aluno aluno);

    @Query("SELECT * FROM aluno")
    List<Aluno> todos();

    @Delete
    void remove(Aluno aluno);

    @Update
    void altera(Aluno aluno);

//    @Query("SELECT * FROM aluno WHERE telefoneFixo = :telefone")
//    boolean ehAluno(String telefone);
}
