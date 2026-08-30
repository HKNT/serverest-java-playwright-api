# ServeRest API Automation 🚀

Projeto de automação de testes de API focado em alta performance e arquitetura limpa, utilizando o ecossistema moderno do Playwright com Java para validar o e-commerce [ServeRest](https://serverest.dev/).

## 🛠 Tecnologias
- **Java 21**
- **Playwright API** (Motor de requisições super rápido)
- **JUnit 5** (Runner e asserções)
- **DataFaker** (Geração de massa de dados dinâmica)
- **Allure Report** (Geração de relatórios com evidências anexadas)
- **GitHub Actions** (CI/CD com GitHub Pages)

## 🏗 Arquitetura Aplicada
O projeto foi desenhado utilizando o **API Object Pattern**.
A complexidade das requisições HTTP (Rotas, Headers, Tokens, Anexação de Evidências) fica encapsulada em classes do tipo *Client* (ex: `CartClient`, `UserClient`). Os testes apenas orquestram as chamadas de negócio, resultando em scripts limpos, legíveis e com manutenção facilitada.

## 🚀 Como Executar Localmente

**1. Clone o repositório:**
```bash
git clone https://github.com/SEU_USUARIO/serverest-playwright-api.git
```

**2. Execute a suíte de testes:**
```bash
mvn clean test
```

**3. Gere e visualize o relatório Allure:**
```bash
mvn allure:serve
```

## 🚥 Pipeline e Ambientes (Shift-Left)
Os testes estão integrados no fluxo de CI/CD. A cada Pull Request, uma esteira sobe a aplicação isoladamente garantindo a integridade da entrega antes do merge (Shift-Left Testing). O relatório estático final é publicado no GitHub Pages.