package dtos;

import lombok.AllArgsConstructor;

public enum TipoConta{
    CONTACORRENTE(1, "Conta Corrente"),
    CONTAPOUPANCA(2, "Conta Poupança"),
    CONTASALARIO(03, "Conta Salário");

    private final int valor;
    private final String descricao;

    TipoConta(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor(){
        return this.valor;
    }

    public String getDescricao(){
        return this.descricao;
    }
}



