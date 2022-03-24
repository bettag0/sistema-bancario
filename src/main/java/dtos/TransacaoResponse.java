package dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
public class TransacaoResponse {
    private @Id Long idTransacao;
    private Long idConta;
    private @NotNull double valor;
    private @NotNull Date dataTransacao;

    public TransacaoResponse() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransacaoResponse that = (TransacaoResponse) o;
        return Double.compare(that.valor, valor) == 0 && idTransacao.equals(that.idTransacao) && idConta.equals(that.idConta) && dataTransacao.equals(that.dataTransacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransacao, idConta, valor, dataTransacao);
    }

    @Override
    public String toString() {
        return "TransacaoResponse{" +
                "idTransacao=" + idTransacao +
                ", idConta=" + idConta +
                ", valor=" + valor +
                ", dataTransacao=" + dataTransacao +
                '}';
    }
}
