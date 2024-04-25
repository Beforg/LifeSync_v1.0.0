package br.com.myegoo.app.myego.model.estudo;

public enum FiltroEstudos {
    TODOS("Todos"),
    MATERIA("Matéria"),
    TIPO("Tipo"),

    CONTEUDO("Conteúdo"),
    DATA("Data");

    private final String descricao;

    FiltroEstudos(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
