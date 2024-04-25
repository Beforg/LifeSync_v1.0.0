package br.com.myegoo.app.myego.model.tarefa;

public enum SubFiltroTarefa {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
    URGENTE("Urgente"),
    DIA("Dia"),
    SEMANA("Semana"),
    MES("Mês");

    private final String descricao;

    SubFiltroTarefa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
