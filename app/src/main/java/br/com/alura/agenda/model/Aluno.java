package br.com.alura.agenda.model;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class Aluno implements Serializable{
    private long Id;
    private  String nome;
    private  String telefone;
    private  String email;
    private  String caminhoFoto;

    public Aluno(Long Id, String nome, String telefone, String email, String caminhoFoto) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.caminhoFoto = caminhoFoto;
        this.Id = Id;
   }

    public Aluno(String nome, String telefone, String email, String caminhoFoto) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.caminhoFoto = caminhoFoto;
    }
    public Aluno(){}


    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String setNome(String nome){
        return this.nome = nome;
    }

    public String setTelefone(String telefone){
        return this.telefone = telefone;
    }

    public String setEmail(String email){
        return this.email = email;
    }

    public  Long setId(Long id){
        return this.Id = id;
    }

    public Long getId(){
        return Id;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }
}
