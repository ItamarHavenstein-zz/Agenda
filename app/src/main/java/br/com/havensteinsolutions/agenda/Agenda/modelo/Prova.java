package br.com.havensteinsolutions.agenda.Agenda.modelo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Prova implements Serializable {
    private String materia;
    private String data;
    private List<String> topico;

    public Prova(String materia, String data, List<String> topico) {
        this.materia = materia;
        this.data = data;
        this.topico = topico;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getTopico() {
        return topico;
    }

    public void setTopico(List<String> topico) {
        this.topico = topico;
    }

    @NonNull
    @Override
    public String toString() {
        return this.materia;
    }
}
