1.  O que são libs quando se trata de código?
libs é uma coleção de códigos voltados a resolver um determinado problema

2. Cite 2 libs populares no Java e seu objetivo
Junit: biblioteca com códigos que auxiliam no teste unitário do projeto
Tomcat: biblioteca responsável por providenciar e configurar um servidor tomcat

3. Qual é o propósito do Maven, e qual é o seu relacionamento com o arquivo pom.xml?
O propósito do maven é ser um gerenciador de bibliotecas, através dele é possível acessar diversos repositórios e inserir no projeto libs externas. O seu relacionamento com o pom.xml é que o pom vai definir quais dependências o maven vai fazer o download para o projeto

4. Qual é a diferença do Maven para o Gradle?
O maven é uma ferramenta de gerenciamento de projeto, enquanto o Gradle é uma ferramenta de automação de desenvolvimento.

5. Com os conhecimentos adquiridos até o agora, realize uma conexão com o desafio 3 onde falamos sobre JDBC, os drivers de comunicação com os bancos de dados.
   1. Qual a relação entre os termos lib, driver e JDBC?
o driver jdbc é uma lib responsável por fazer a conexão com o banco de dados especificado.
   2. Como é adicionado uma lib no projeto?
Pode ser adicionado através do arquivo pom.xml.
Basta adicionar a linha de código com o corpo exemplo.:
  
   3. Escolha um banco de dados (menos o H2) e explique como utilizar o driver de comunicação. 
Utilizando o mysql como exemplo, basta inserir no arquivo pom.xml a linha de configuração citada no tópico acima, e dar um reload no maven, feito isso
fazer a configuração no arquivo application.properties da conexão com o banco:
  
obs.: No meu exemplo o ddl-auto esta como update, sendo assim quando a aplicação for iniciada ele tem a tarefa apenas de atualizar o banco de dados, se não estiver um schema previamente criado com o nome providenciado, dará erro.