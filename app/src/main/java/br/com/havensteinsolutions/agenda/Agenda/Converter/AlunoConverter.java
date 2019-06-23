package br.com.havensteinsolutions.agenda.Agenda.Converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

public class AlunoConverter {

    public String converteParaJSON(List<Aluno> alunos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();
            for(Aluno alu : alunos){
                js.object();
                js.key("nome").value(alu.getNome());
                js.key("nota").value(alu.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
