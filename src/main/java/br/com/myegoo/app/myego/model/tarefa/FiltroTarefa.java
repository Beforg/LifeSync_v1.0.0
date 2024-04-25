package br.com.myegoo.app.myego.model.tarefa;

public enum FiltroTarefa {
    TODAS("Todas"),
    CONCLUIDAS("Concluídas"),
    NAO_CONCLUIDAS("Não Concluídas"),
    ATRASADAS("Atrasadas"),
    PRIORIDADE("Prioridade"),
    CATEGORIA("Categoria"),
    DATA("Data");

    private final String descricao;

    FiltroTarefa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
