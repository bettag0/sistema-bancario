# sistema-bancario

## Explicações:

Por ser um sistema que eu avaliaria como sendo possível de dockerizá-lo, subí-lo em um container, optei por desenvolvê-lo como um serviço backend em que uma aplicação frontend poderia chamá-lo eventualmente.
A questão de persistência de dados é feita através do JPA, ou seja, temos entidades que funcionam como tabelas de um banco de dados que armazenam a informação durante a execução da aplicação.

PATHS:

## /cadastrar-pessoa (POST)
Para uma conta poder ser criada, primeiro precisamos ter uma pessoa que será a proprietária dessa conta, por isso utilizamos esse endpoint para cadastrar pessoas em nosso sistema bancário

Request:

{
	"nome": "seu nome",
	"cpf": "seu-cpf",
	"dataNascimento": "AAAA-MM-DD"
}

## /criar-conta (POST)
Endpoint para a abertura de uma conta passando uma pessoa como proprietária. Não é necessário passar todas as informações da pessoa, o sistema consegue criar a conta utilizando apenas o ID.

Request:

{
	"pessoa"{
		"idPessoa": id-da-pessoa
	}
	"saldo": 999,
	"limiteSaqueDiário": 999,
	"flagAtivo": true,
	"tipoConta": 1(conta corrente), 2(poupança), 3(salário),
	"dataCriacao": "AAAA-MM-DD"
}

## /contas (GET)
Endpoint para listar todas as contas, não é necessário passar nenhum parâmetro.

## /pessoas (GET)
Endpoint para listar todas as pessoas cadastradas, não é necessário passar nenhum parâmetro.

## /saldo (POST)
Endpoint para mostrar o saldo da conta, nele você passa uma conta como corpo da requisição. Não é necessário passar todas as informações da conta, apenas o ID é suficiente.

Request:
{
	"idConta": 1
}

## /saque (POST)
Endpoint para realizar o saque em uma conta, nele você passa uma transação como corpo da requisição. Não é necessário passar todas as informações da conta, apenas o ID é suficiente. Não passando a data da transação, a transação será feita na hora local.

Request:
{
    "conta":{
        "idConta": 2
    },
    "valor": 200
}

/deposito (POST)
Endpoint para realizar o depósito em uma conta, nele você passa uma transação como corpo da requisição. Não é necessário passar todas as informações da conta, apenas o ID é suficiente. Não passando a data da transação, a transação será feita na hora local.

Request:
{
    "conta":{
        "idConta": 2
    },
    "valor": 200
}

## /extrato (POST)
Endpoint para verificar todas as transações de uma conta, nele você passa uma conta como requisito, somente com o ID da Conta já é possível adquirir o extrato.

Request:
{
    "conta":{
        "idConta": 2
    },
    "valor": 500
}

## /bloqueio (POST)
Endpoint para desativar uma conta, nele você passa uma conta como corpo da requisição, somente com o ID da Conta já é possível bloqueá-la. Não é possível bloquear uma conta já inativa.

Request:
{
	"idConta": 2
}

## /periodo-extrato (POST)
Endpoint para buscar o extrato de uma conta por um período de dias, nele você passa o ID da conta a ser consultada e a data inicial e final para a consulta.

Request:

{
    "idConta": 2,
    "dataInicial":"2022-03-25",
    "dataFinal":"2022-03-27"
}

## Futuras melhorias

- Dockerização
- Implementação de banco de dados para persistir os dados
- Criação de um frontend com formulários para as requisições
