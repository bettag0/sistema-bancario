package dtos;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class PeriodoExtrato {
    private @Id Long idConta;
    private @NotNull Date dataInicial;
    private @NotNull Date dataFinal;

    public PeriodoExtrato() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodoExtrato that = (PeriodoExtrato) o;
        return idConta.equals(that.idConta) && dataInicial.equals(that.dataInicial) && dataFinal.equals(that.dataFinal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConta, dataInicial, dataFinal);
    }

    @Override
    public String toString() {
        return "PeriodoExtrato{" +
                "idConta=" + idConta +
                ", dataInicial=" + dataInicial +
                ", dataFinal=" + dataFinal +
                '}';
    }
}

