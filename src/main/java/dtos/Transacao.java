package dtos;

import com.sun.istack.NotNull;
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
public class Transacao {
    private @Id @GeneratedValue Long idTransacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    private @NonNull Conta conta;
    private @NonNull double valor;
    private @NonNull Date dataTransacao;

    public Transacao() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Double.compare(transacao.valor, valor) == 0 && idTransacao.equals(transacao.idTransacao) && conta.equals(transacao.conta) && dataTransacao.equals(transacao.dataTransacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransacao, conta, valor, dataTransacao);
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "idTransacao=" + idTransacao +
                ", conta=" + conta +
                ", valor=" + valor +
                ", dataTransacao=" + dataTransacao +
                '}';
    }
}
