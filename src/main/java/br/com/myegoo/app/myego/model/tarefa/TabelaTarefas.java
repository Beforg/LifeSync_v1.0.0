package br.com.myegoo.app.myego.model.tarefa;

public class TabelaTarefas {
    private String nome;
    private String data;
    private String prioridade;
    private String categoria;
    private String concluido;

    public TabelaTarefas(String nome, String data, String prioridade, String categoria, String concluido) {
        this.nome = nome;
        this.data = data;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.concluido = concluido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String isConcluido() {
        return concluido;
    }

    public void setConcluido(String concluido) {
        this.concluido = concluido;
    }

    public String getConcluido() {
        return concluido;
    }

}
