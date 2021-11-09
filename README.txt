1. O que é um padrão de projeto e por que nós os utilizamos?
Design Patterns ou Padrão de projeto, são técnicas, soluções para problemas comuns em projetos POO. São padrões eficientes que facilitam no desenvolvimento e manutenção do sistema.

2.  Cite e explique com suas palavras:
Gof(Gang of Four) dividiu 3 categorias de padrões pela natureza do problema que eles solucionaram, teoricamente.
   1. Um dos padrões de criação
Builder. Tem a intenção de separar a construção de um objeto complexo de sua representação.
   2. Um dos padrões estruturais;
Proxy. Objetiva encapsular um objeto através de um outro objeto que possua a mesma interface
   3. Um dos padrões comportamentais;
Iterator. O padrão utiliza de um iterator para percorrer os containers e acessar os elementos
      3. Explique o conceito de arquitetura de software e seu propósito;
A arquitetura de software tem como objetivo, organizar e projetar, como suas partes serão organizadas, quais padrões serão utilizadas, objetivando deixar o código de facil implementação para futuras features e compreensível, além de, que deixe o sistema o mais otimizado possível.

      4. Qual arquitetura estamos seguindo até o momento? Justifique sua resposta.
Arquitetura em camadas. O código pode ser lido através de suas camadas, e identificando que cada uma delas tem sua responsabilidade atribuída.
Controller: fazer o intermediador da url ao serviço;
Service: tratar de regras negociais e intermedia o controller ao repositório;
Repository: responsável pela conexão ao banco de dados, camada de persistência de dados

         5. O que significa a sigla SOLID?
S: Single Responsibility Principle: a classe deve ter uma única responsabilidade, para facilitar na manutenção e compreensão do código;

O: Open-Closed Principle: O projeto esta aberto para implementações, porém, fechado para alterações, para evitar que haja bugs em features que estavam funcionando anteriormente;

L: Liskov-Substitution Principle: o Subtipo deve ser capaz de substituir a classe Base sem alterar as propriedades, evitar implementações de métodos que não fazem nada e retornar valores diferentes da classe base;

I: Interface Segregation Principle: Criar interfaces mais especificas, pois as genéricas podem não atender a um tipo mais específico de objeto;

D: Dependency Inversion Principle: Módulos devem depender de abstrações.
         6. Quais princípios foram utilizados no projeto até o momento? Explique. 
Single Responsibility Principle, no projeto foi atribuído a cada classe 1 responsabilidade específica.
Interface Segregation Principle, não há no código métodos ou interface soltos sem utilização, e as interfaces não são genéricas
  
Open-Closed Principle, o código está aberto para implementações sem termos que alterar funcionalidades que já está funcionando.