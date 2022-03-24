package dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Pessoa {

    private @Id @GeneratedValue Long idPessoa;
    private @NonNull String nome;
    private @NonNull String cpf;
    private @NonNull Date dataNascimento;

    public Pessoa() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return idPessoa.equals(pessoa.idPessoa) && nome.equals(pessoa.nome) && cpf.equals(pessoa.cpf) && dataNascimento.equals(pessoa.dataNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPessoa, nome, cpf, dataNascimento);
    }

}
