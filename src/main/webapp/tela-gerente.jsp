<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Relatórios</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tela-gerente.css">
</head>
<body>
<div class="area-de-tarefa">
    <div class="sub-area-de-tarefa">
        <h1>Relatórios</h1>

        <!-- Select para Supervisores -->
        <div class="selecao-supervisor">
            <label for="supervisorSelect">Supervisor:</label>
            <select id="supervisorSelect">
                <option value="">Selecione um supervisor</option>
            </select>
        </div>

        <!-- Botões para gerar relatórios -->
        <div class="botoes-da-area-de-tarefa multiplos-botoes">
            <button type="button" onclick="gerarRelatorio('tarefasPorSupervisor')">Gerar Relatório de Tarefas Cadastradas Por Supervisor</button>
            <button type="button" onclick="gerarRelatorio('tarefasPorStatus')">Gerar Relatório de Tarefas Pendentes</button>
            <button type="button" onclick="gerarRelatorio('quantidadePorFuncionario')">Gerar Relatório de Funcionários sem Tarefas</button>
        </div>

        <!-- Área onde os relatórios serão impressos -->
        <div class="lista-tarefas" id="area-relatorio">
            <p class="mensagem-vazia">Nenhum relatório gerado ainda.</p>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/tela-gerente.js" type="module"></script>
</body>
</html>
