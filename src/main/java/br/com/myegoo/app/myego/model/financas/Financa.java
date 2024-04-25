package br.com.myegoo.app.myego.model.financas;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "financa")
public class Financa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal renda;
    private BigDecimal reserva;
    private BigDecimal saldo;
    private BigDecimal gastos;
    @OneToMany(mappedBy = "financa",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RegistroFinanca> registroFinanca;

    public Financa() {
    }

    public Financa(BigDecimal renda, BigDecimal reserva, BigDecimal saldo, BigDecimal gastos) {
        this.renda = renda;
        this.reserva = reserva;;
        this.saldo = saldo;
        this.gastos = gastos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRenda() {
        return renda;
    }

    public void setRenda(BigDecimal renda) {
        this.renda = renda;
    }

    public BigDecimal getReserva() {
        return reserva;
    }

    public void setReserva(BigDecimal reserva) {
        this.reserva = reserva;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<RegistroFinanca> getRegistroFinanca() {
        return registroFinanca;
    }

    public void setRegistroFinanca(List<RegistroFinanca> registroFinanca) {
        this.registroFinanca = registroFinanca;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }
    public void atualizaSaldo(){
        this.saldo = this.saldo.subtract(this.gastos).subtract(this.reserva);
    }
}
