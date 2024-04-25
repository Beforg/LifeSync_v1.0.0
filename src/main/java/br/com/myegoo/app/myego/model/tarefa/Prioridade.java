package br.com.myegoo.app.myego.model.tarefa;

public enum Prioridade {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
    URGENTE("Urgente");

    private final String descricao;

    Prioridade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
