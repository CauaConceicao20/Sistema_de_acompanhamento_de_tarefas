<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Minhas Tarefas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tela-funcionario.css">
</head>
<body>
    <div class="area-de-tarefa">
        <div class="sub-area-de-tarefa">
            <h1>Minhas Tarefas</h1>

            <!-- Essa div será preenchida dinamicamente pelo JS -->
            <div class="lista-tarefas" id="lista-tarefas"></div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/tela-funcionario.js" type="module"></script>
</body>
</html>
