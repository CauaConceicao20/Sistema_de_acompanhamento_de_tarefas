<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <title>Login</title>
</head>
<body>
<div class="area-de-login">
    <div class="sub-area-de-login">
        <form action="${pageContext.request.contextPath}/loginUsuario" method= "post">
            <h1>Login</h1>
            <div class="inputs-de-login">
                <input type="text" name="email" placeholder="E-mail">
                <input type="password" name="senha" placeholder="Password">
            </div>

            <div class="botoes-da-area-de-login">
                <button id="submit">Login</button>
                <a href="${pageContext.request.contextPath}/cadastrar.jsp">
                    <button type="button">Cadastrar-se</button>
                </a>
            </div>
        </form>
        <a href="#" class="esqueci-a-senha">Esqueci minha senha</a>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/login.js" type="module"></script>
</body>
</html>