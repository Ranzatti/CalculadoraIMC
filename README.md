# 📊 Calculadora de Saúde & IMC - Jetpack Compose

Este é um aplicativo Android moderno desenvolvido em **Kotlin** com **Jetpack Compose**, projetado para fornecer uma análise abrangente da saúde do usuário através de métricas antropométricas e armazenamento persistente de dados.

## 🎯 Objetivo do Projeto

O aplicativo foi desenvolvido para cumprir requisitos acadêmicos de desenvolvimento mobile, focando em:
* **Validação de Dados**: Garantir que as entradas de altura, peso e idade sejam realistas e consistentes.
* **Persistência Local**: Uso do banco de dados **Room** para manter um histórico completo de medições.
* **Documentação Científica**: Fórmulas matemáticas documentadas diretamente na interface de ajuda para transparência com o usuário.

## 🚀 Funcionalidades Adicionais de Saúde

Além do IMC tradicional, o app calcula e exibe três funcionalidades extras exigidas:

1.  **Taxa Metabólica Basal (TMB)**: Utiliza a fórmula de **Mifflin-St Jeor** para determinar o gasto energético em repouso.
2.  **Cálculo de Peso Ideal**: Baseado na fórmula clássica de **Devine**, oferecendo uma meta saudável baseada na altura.
3.  **Necessidade Calórica Diária**: Estimativa de calorias para manutenção de peso considerando um fator de atividade física moderada.

## 🛠️ Tecnologias e Arquitetura

O código segue padrões rigorosos de organização e separação de responsabilidades:

* **Jetpack Compose**: Interface integralmente construída com estados e recomposição eficiente.
* **Room Database**: Implementação de entidades, DAO e migração de dados eficiente.
* **NavHost & NavController**: Navegação estruturada entre as telas de Home, Histórico e Ajuda.
* **Material Design 3**: Uso de componentes modernos como Cards, Badges de status e TopAppBars.

## 📝 Fórmulas Utilizadas

* **IMC**: $Peso / (Altura \times Altura)$
* **TMB (Mifflin-St Jeor)**: $(10 \times peso) + (6.25 \times altura) - (5 \times idade) + 5$
* **Peso Ideal (Devine)**: $50kg + 2.3kg$ por cada polegada acima de $152.4cm$

## 📂 Organização do Código

* **`com.example.calculadoraimc.datasource`**: Contém a lógica de banco de dados e as funções de cálculo matemático puro.
* **`com.example.calculadoraimc.view`**: Contém as telas (Screens) e componentes de UI desenvolvidos em Compose.
* **`com.example.calculadoraimc.ui.theme`**: Configurações globais de cores, tipografia e tema visual do Material 3.
