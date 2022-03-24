package controller;

import dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ContaRepository;
import repository.PessoaRepository;
import repository.TransacaoRepository;

import java.util.*;

@RestController
public class SistemaBancarioController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    private HttpHeaders responseHeaders = new HttpHeaders();


    @PostMapping("/extrato")
    public ResponseEntity<?> extrato(@RequestBody Conta conta){
        List<Transacao> response = transacaoRepository.findTransactions(conta.getIdConta());
        return new ResponseEntity<List<Transacao>>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/periodo-extrato")
    public ResponseEntity<?> periodoExtrato(@RequestBody PeriodoExtrato periodoExtrato){
        List<Transacao> response = transacaoRepository.findPeriodTransactions(periodoExtrato.getIdConta(), periodoExtrato.getDataInicial(), periodoExtrato.getDataFinal());
        return new ResponseEntity<List<Transacao>>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/bloqueio")
    public ResponseEntity<?> bloqueio(@RequestBody Conta conta){
        Conta contaBloqueio = contaRepository.findById(conta.getIdConta())
                .orElseThrow(() -> {
                    return new RuntimeException("Conta não encontrada com id " + conta.getIdConta());
                });
        if(!contaBloqueio.getFlagAtivo()){
            responseHeaders.add("Content-Type", "application/json");
            return new ResponseEntity<String>("A conta já se encontra inativa", responseHeaders, HttpStatus.BAD_REQUEST);
        }
        contaBloqueio.setFlagAtivo(false);
        contaRepository.save(contaBloqueio);
        contaBloqueio = contaRepository.findById(conta.getIdConta()).get();
        responseHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<Conta>(contaBloqueio, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody Transacao transacao){
        Conta contaTransacao = contaRepository.findById(transacao.getConta().getIdConta())
                .orElseThrow(() -> {
                    return new RuntimeException("Conta não encontrada com id " + transacao.getConta().getIdConta());
                });
        double saldoConta = contaTransacao.getSaldo();
        double limiteSaque = contaTransacao.getLimiteSaqueDiario();
        if(saldoConta < transacao.getValor()){
            responseHeaders.add("Content-Type", "application/json");
            return new ResponseEntity<String>("Saldo da conta insuficiente para a operação de saque.", responseHeaders, HttpStatus.BAD_REQUEST);
        }
        if(limiteSaque < transacao.getValor()){
            responseHeaders.add("Content-Type", "application/json");
            return new ResponseEntity<String>("Limite de saque diário da conta atingido.", responseHeaders, HttpStatus.BAD_REQUEST);
        }
        double novoSaldo = saldoConta - transacao.getValor();
        contaTransacao.setSaldo(novoSaldo);
        contaRepository.save(contaTransacao);
        if(transacao.getDataTransacao() == null) {
            transacao.setDataTransacao(new Date());
        }
        Transacao response = transacaoRepository.save(transacao);

        contaTransacao = contaRepository.findById(transacao.getConta().getIdConta()).get();
        response.setConta(contaTransacao);

        responseHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<Transacao>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> deposito(@RequestBody Transacao transacao){
            Conta contaTransacao = contaRepository.findById(transacao.getConta().getIdConta())
                    .orElseThrow(() -> {
                        return new RuntimeException("Conta não encontrada com id " + transacao.getConta().getIdConta());
                    });
        double saldoConta = contaTransacao.getSaldo();
        saldoConta += transacao.getValor();
        contaTransacao.setSaldo(saldoConta);
        contaRepository.save(contaTransacao);
        if(transacao.getDataTransacao() == null) {
            transacao.setDataTransacao(new Date());
        }
        Transacao response = transacaoRepository.save(transacao);

        contaTransacao = contaRepository.findById(transacao.getConta().getIdConta()).get();
        response.setConta(contaTransacao);

        responseHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<Transacao>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/criar-conta")
    public ResponseEntity<?> criarConta(@RequestBody Conta conta){
       Pessoa pessoa = pessoaRepository.findById(conta.getPessoa().getIdPessoa())
               .orElseThrow(() -> {
                   return new RuntimeException("Pessoa não encontrada com id " + conta.getPessoa().getIdPessoa());
               });
        if((conta.getPessoa().getNome() == null || conta.getPessoa().getNome().isEmpty()) ||
            (conta.getPessoa().getCpf() == null || conta.getPessoa().getCpf().isEmpty()) ||
            (conta.getPessoa().getDataNascimento() == null)) conta.setPessoa(pessoa);
        if(conta.getDataCriacao() == null) {
            conta.setDataCriacao(new Date());
        }
        conta.setFlagAtivo(true);
        Conta response = contaRepository.save(conta);
        responseHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<Conta>(response, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public String bemVindo(){
        return "Bem vindo ao Sistema Bancário do Gabriel Bettanin";
    }

    @PostMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@RequestBody Conta conta){
        Optional<Conta> contaRetornada = contaRepository.findById(conta.getIdConta());
        responseHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<Double>(contaRetornada.get().getSaldo(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/contas")
    public ResponseEntity<?> listarContas(){
        List<Conta> response = contaRepository.findAll();
        responseHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<List<Conta>>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/cadastrar-pessoa")
    public ResponseEntity<?> cadastrarPessoa(@RequestBody Pessoa pessoa){
        Pessoa response = pessoaRepository.save(pessoa);
        responseHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<Pessoa>(response, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<?> listarPessoas(){
        List<Pessoa> response = pessoaRepository.findAll();
        responseHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<List<Pessoa>>(response, responseHeaders, HttpStatus.OK);
    }
}