package dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Conta {

    private @Id @GeneratedValue Long idConta;
    @ManyToOne(cascade = CascadeType.MERGE)
    private @NonNull Pessoa pessoa;
    private @NonNull double saldo;
    private @NonNull double limiteSaqueDiario;
    private @NonNull boolean flagAtivo;
    private @NonNull TipoConta tipoConta;
    private @NonNull Date dataCriacao;

    public Conta() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return Double.compare(conta.saldo, saldo) == 0 && Double.compare(conta.limiteSaqueDiario, limiteSaqueDiario) == 0 && flagAtivo == conta.flagAtivo && idConta.equals(conta.idConta) && pessoa.equals(conta.pessoa) && tipoConta == conta.tipoConta && dataCriacao.equals(conta.dataCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConta, pessoa, saldo, limiteSaqueDiario, flagAtivo, tipoConta, dataCriacao);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "idConta=" + idConta +
                ", idPessoa=" + pessoa +
                ", saldo=" + saldo +
                ", limiteSaqueDiario=" + limiteSaqueDiario +
                ", flagAtivo=" + flagAtivo +
                ", tipoConta=" + tipoConta +
                ", dataCriacao=" + dataCriacao +
                '}';
    }

    public boolean getFlagAtivo() {
        return this.flagAtivo;
    }
}