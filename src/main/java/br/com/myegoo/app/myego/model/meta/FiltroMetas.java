package br.com.myegoo.app.myego.model.meta;

public enum FiltroMetas {
    ESTUDOS("Estudos"),
    TRABALHO("Trabalho"),
    LAZER("Lazer"),
    SAUDE("Saúde"),
    FINANCAS("Finanças");

    private final String descricao;

    FiltroMetas(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
