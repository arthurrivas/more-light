# More-Light API

Possivel solução de backend desenvolvida para facilitar a comunicação entre cidadãos e a administração pública. A plataforma permite o registro e a gestão de ocorrências urbanas, como buracos em vias, falhas na iluminação pública, problemas de sinalização e alertas da defesa civil.

---

## 🚀 Tecnologias e Ferramentas

O projeto foi construído utilizando tecnologias modernas para garantir escalabilidade e fácil manutenção:

- **Linguagem:** Java 17  
- **Framework:** Spring Boot 3  
- **Persistência:** Spring Data JPA e Hibernate  
- **Banco de Dados:** PostgreSQL para armazenamento robusto de dados  
- **Containerização:** Docker e Docker Compose para isolamento de ambiente  
- **Documentação:** Swagger (OpenAPI) para testes de endpoints  
- **Gerenciamento de Dependências:** Maven  

---

## 🛠️ Princípios de Engenharia

Para garantir que o sistema seja sustentável a longo prazo, o desenvolvimento segue:

- **SOLID & Clean Code:** Foco em código legível, testável e de fácil refatoração  
- **Padrão REST:** Comunicação clara entre cliente e servidor através de recursos bem definidos  
- **Cultura DevOps:** Deploy facilitado através de ambientes containerizados  

---

## 📋 Funcionalidades

- **Registro de Chamados:** Cadastro de ocorrências com categoria, descrição e coordenadas geográficas  
- **Categorização Automática:** Classificação entre tipo de chamado
- **Fluxo de Status:** Gerenciamento do ciclo de vida do chamado (`Pendente -> Em Análise -> Resolvido`)  
- **Dashboard Técnico:** Endpoints para listagem e filtragem de demandas para gestão municipal  
