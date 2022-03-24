package repository;

import dtos.Conta;
import dtos.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    @Query("SELECT t FROM Transacao t WHERE t.conta.idConta = ?1")
    List<Transacao> findTransactions(long id);

    @Query("SELECT t FROM Transacao t WHERE t.conta.idConta = ?1 and t.conta.dataCriacao >= ?2 and t.conta.dataCriacao <= ?3")
    List<Transacao> findPeriodTransactions(long id, Date periodoInicial, Date periodoFinal);
}