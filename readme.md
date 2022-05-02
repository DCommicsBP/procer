## Ficha Técnica do projeto

### Tecnologias utilizadas no processo de desenvolvimento:
    1. Docker: para a criação de imagens e containers

    2. PostgresSQL: banco de dados relacional

    3. Intellij IDEA: ferramenta de desenvolvimento

    4. Gradle: sistema de automação de compilação
    
#### Tecnologias utilizadas na codificação da aplicação
    1. OpenJDK 11

    2. Spring Webflux

    3. R2DBC: para viabilizar o uso de um banco de dados relacional através de uma API reativa.

## Breve descrição do que o usuário vai encontrar ao abrir o código fonte.


1. Os endpoints principais que fazem parte das operações de CRUD, foram todos expostos na Controler utilizada para gerenciar o fluxo de dados da entidade ‘People’. Porém, outros serviços foram criados, mas não expostos.


2. A aplicação está dividida em nichos de demanda. Isso significa que cada pacote presente possui classes que têm funcionalidades muito bem definidas e que devem obedecer a sua finalidade específica, como se fosse uma camada de proteção para que os dados não fiquem expostos de qualquer forma.


3. A aplicação foi projetada com o objetivo de ser fácil a  sua utilização . O que mais vai ser complicado será o monitoramento das informações em um banco de dados.


4. Tenha o Docker instalado em sua máquina. O Docker Desktop viabiliza de forma rápida e tranquila a subida de containers que fornecem serviços e aplicações que auxiliam no processo de desenvolvimento de uma aplicação. Para acessar o Site e baixar a versão mais atualizada, acesse:      https://docs.docker.com/desktop/windows/install/


5. Após a instalação do Docker e a sua inicialização, você deve acessar a pasta raiz da aplicação, onde estão contidos os arquivos Dockerfile e docker-compose.yml. Se você estiver utilizando a IDE IntelliJ, você perceberá que existem alguns botões na lateral superior esquerda, que lembra o botão “play” dos aparelhos eletrônicos. Tanto no Dockerfile, quanto no docker-compose, basta pressioná-los que a aplicação já estará de pé. Mas como você deve ser apaixonado por linhas de código e adora um Prompt de Comando, um Shell, um Terminal ou até mesmo o Git Bash da vida, basta abrir a pasta raiz do projeto e digitar as seguintes linhas de código:


                         docker-compose up 


7. Após subir o container do banco de dados, você poderá conferir como a aplicação gerou o banco de forma automática, fazendo uso do PgAdmin.
Isso só é possível pois foi colocado nas configurações da aplicação um script de banco de dados que exclui e cria toda a estrutura de dados que será utilizada para realização de consultas e operações de CRUD.

8. Para acessar a API da aplicação basta acessar o seguinte link
http://localhost:8080/webjars/swagger-ui/index.html

Uma vez dentro da aplicação você poderá utilizá-la sem grandes problemas.
 
