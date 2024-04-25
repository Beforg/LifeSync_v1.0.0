package br.com.myegoo.app.myego.model.meta;

public enum FiltroData {
    DIAS("Dia"),
    SEMANA("Semana"),
    MES("Mês"),
    ANO("Ano");

    private final String descricao;

    FiltroData(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
