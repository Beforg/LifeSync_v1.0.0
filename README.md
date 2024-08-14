# LifeSync

Aplicativo para organização pessoal

![](https://github.com/Beforg/assets/blob/main/lifesync/home.png)

## Sobre o aplicativo:

Projeto simples desenvolvido para a organização pessoal de diversas áreas, como estudos, finanças, hábitos e treinos. as tecnologias usadas foram **Java, JavaFX, Spring Boot, HSQLDB, com o gerenciador de dependências Maven**, para esse projeto foi usado o banco HSQLDB que é embutido
na aplicação, o projeto apresenta 17 tabelas, entre elas algumas tabelas de configurações.

## Estrutura do projeto:

`configuration`: designado a controles das configurações do projeto, as partes do menu;<br>
`controller`: controlador principal da aplicação;<br>
`interfaces`: interfaces usadas na aplicação;<br>
`model`: modelos, entidades, tabelas e afins;<br>
`repository`: repositórios que extendem JpaRepository, onde temos os métodos para a manipulação dos dados.<br>
`service`: classes que persistem ou consultam o banco de dados.<br>
`utils`: classes de utilidades do projeto, como Listeners e lógicas do aplicativo;

## Tela de login:

![](https://github.com/Beforg/assets/blob/main/lifesync/login.png)

Após entrar e cadastrar no aplicativo, aparecerá a tela de Login, a senha é criptografada pelo Bcrypt, o aplicativo tem um usuário apenas e apresenta senha para a segurança do usuário.

## Home:

![](https://github.com/Beforg/assets/blob/main/lifesync/home.png)

Tela inicial da aplicação, traz dados gerais das outras áreas e mostra a semana atual do ano.

## Tarefas:

![](https://github.com/Beforg/assets/blob/main/lifesync/tarefas.png)

A tela de tarefas conta com uma visão das tarefas e outra de um Calendario semanal e mensal (responsabilidade da classe `Calendario`).
Temos as alternativas de adicionar, editar e remover tarefas.

![](https://github.com/Beforg/assets/blob/main/lifesync/tarefas_calendario.png)

O calendário mostra uma visão do mês ou da semana, depende de qual for selecionada, tendo interação com os dias do calendário, mostrando as informações atuais dos dias selecionados.
Mostra também o progresso do dia e das tarefas.

![](https://github.com/Beforg/assets/blob/main/lifesync/tarefas_semana.png)

## Estudos:

![](https://github.com/Beforg/assets/blob/main/lifesync/estudos.png)

Tela que apresenta dois cronômetros: um para o pomodoro e outro para contagem geral do tempo de estudo, os cronometros são responsabilidade da classe `Cronometro`, e tem opções de play, pause, reset e pular.
Nos estudos pode adicionar registros de estudos e associar com as matérias que podem ser criadas, editadas ou excluidas.

## Projetos:

![](https://github.com/Beforg/assets/blob/main/lifesync/projetos.png)

A tela de projetos apresenta um mural com um sistema de Cards, que vão sendo adicionados e vinculados a projetos previamente criados. 

## Metas:

![](https://github.com/Beforg/assets/blob/main/lifesync/metas.png)

Na tela de metas é possível criar metas, submetas e atrelar essas submetas a tarefas, há uma indicação do tempo restante dos prazos das metas.

## Finanças:

![](https://github.com/Beforg/assets/blob/main/lifesync/financas.png)

Tela para registro e controle de gastos, sistema básico que vai somando o valor dos gastos e descontando no valor da renda, apresentando também o saldo restante.

## Estatísticas:

![](https://github.com/Beforg/assets/blob/main/lifesync/estatisticas.png)

Tela que mostra as estatísticas do ano ou de sempre do usuário, pode também selecionar o ano que que ver as estatísticas.


