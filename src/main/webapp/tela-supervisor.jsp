<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Tarefas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tela-supervisores.css">
</head>
<body>
<div class="area-de-tarefa">
    <div class="sub-area-de-tarefa">
        <h1>Gerenciar Tarefas</h1>

        <!-- Cadastro de nova tarefa -->
        <h2>Nova Tarefa</h2>
        <form id="form-cadastrar-tarefa" method="post">
            <div class="inputs-de-tarefa">
                <input type="text" name="titulo" placeholder="Título da tarefa" required>
                <textarea name="descricao" placeholder="Descrição da tarefa" required></textarea>
                <select name="funcionario" id="select-funcionario-cadastro" required>
                    <option value="" disabled selected hidden>Selecionar funcionário</option>
                </select>
            </div>
            <div class="botoes-da-area-de-tarefa">
                <button type="submit">Cadastrar Tarefa</button>
            </div>
        </form>

        <h2 class="titulo-tarefas">Tarefas</h2>

        <div class="botoes-da-area-de-tarefa multiplos-botoes">
            <form id="form-filtrar" method="get">
                <div class="filtros-e-botoes">
                    <select name="funcionarioId" id="select-funcionario-filtro" required>
                        <option value="" disabled selected hidden>Selecionar funcionário</option>
                    </select>
                    <input type="hidden" name="tipo" id="tipo-tarefa">
                    <button type="button" onclick="definirEndpoint('/listaTarefasPendentes')">Listar Pendentes</button>
                    <button type="button" onclick="definirEndpoint('/listaTarefasConcluidas')">Listar Concluídas</button>
                </div>
            </form>
        </div>
        <div class="lista-tarefas" id="lista-tarefas"></div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/tela-supervisor.js" type="module"></script>
</body>
</html>