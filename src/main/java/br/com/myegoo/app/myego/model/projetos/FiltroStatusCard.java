package br.com.myegoo.app.myego.model.projetos;

public enum FiltroStatusCard {
    NAO_INICIADO("Não iniciado"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDO("Concluído");

    private final String descricao;
    FiltroStatusCard (String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }


}
