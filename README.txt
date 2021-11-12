1. Explique o conceito de teste unitário?
O conceito do teste unitário é fazer testes isolados, visando que aquela parte em específico resulte de acordo com o que estava previsto, seja para sucesso do método ou alguma exceção ja esperada.

2. Descreva como fazer um código de teste. 
Utilizando a IDE IntelliJ é possível fazer uma classe teste com os comandos Alt + insert na classe que deseja testar, vai abrir uma opção de ações e selecione teste, feito isso, será gerado uma classe de teste derivada da classe que executou o comando.
Já o código deve ser pensado de forma isolado, deve entender a entrada do método e a saida, injetar a dependência necessária, e mockar as dependências da dependência, exemplo.: no contexto do spring boot o teste de um serviço, deve injetar com @Autowired o objeto service, e utilizar a anotação @MockBean para as dependências como o objeto repository.

3. Qual o intuito do teste unitário?
Validar se o método de forma isolada e sem depender de outras funcionalidades, funciona de acordo com o planejado

4. Quais são as ferramentas que utilizamos para realizar testes unitários(2 pelo menos).
Junit e MockMvc