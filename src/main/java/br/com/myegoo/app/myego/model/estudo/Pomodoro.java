package br.com.myegoo.app.myego.model.estudo;

import jakarta.persistence.*;

@Entity
@Table(name = "pomodoro_configuracao")
public class Pomodoro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tempo_pomodoro")
    private int tempoPomodoro;
    @Column(name = "tempo_break_curto")
    private int tempoBreakCurto;
    @Column(name = "tempo_break_longo")
    private int tempoBreakLongo;
    private int quantidade;

    public Pomodoro(int tempoPomodoro, int tempoBreakCurto, int tempoBreakLongo, int quantidade) {
        this.tempoPomodoro = tempoPomodoro;
        this.tempoBreakCurto = tempoBreakCurto;
        this.tempoBreakLongo = tempoBreakLongo;
        this.quantidade = quantidade;
    }
    public Pomodoro() {

    }

    public int getTempoPomodoro() {
        return tempoPomodoro;
    }

    public void setTempoPomodoro(int tempoPomodoro) {
        this.tempoPomodoro = tempoPomodoro;
    }

    public int getTempoBreakCurto() {
        return tempoBreakCurto;
    }

    public void setTempoBreakCurto(int tempoBreakCurto) {
        this.tempoBreakCurto = tempoBreakCurto;
    }

    public int getTempoBreakLongo() {
        return tempoBreakLongo;
    }

    public void setTempoBreakLongo(int tempoBreakLongo) {
        this.tempoBreakLongo = tempoBreakLongo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
