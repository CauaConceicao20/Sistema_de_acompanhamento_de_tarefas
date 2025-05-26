<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro.css">
    <title>Cadastro</title>
</head>
<body>
    <div class="area-de-cadastro">
        <div class="sub-area-de-cadastro">
            <h1>Cadastro</h1>
            <form id="cadastroForm" method="post">
                <div class="inputs-de-cadastro">
                    <input type="text" name="nome" placeholder="Nome" required>
                    <input type="email" name="email" placeholder="Email" required>
                    <input type="password" name="senha" placeholder="Senha" required>
                    <input type="text" name="cpf" placeholder="CPF" required>
                    <input type="text" name="telefone" placeholder="Telefone" required>
                    <select name="cargo" required>
                        <option value="" disabled selected hidden>Cargo</option>
                        <option value="FUNCIONARIO">FUNCIONÁRIO</option>
                        <option value="GERENTE">GERENTE</option>
                        <option value="SUPERVISOR">SUPERVISOR</option>
                    </select>
                </div>

                <div class="botoes-da-area-de-cadastro">
                    <button type="submit" id="cadastrar">Cadastrar</button>
                </div>
            </form>

            <a href="${pageContext.request.contextPath}/login.jsp" class="link-voltar-login">Já tem uma conta? Fazer login</a>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/cadastro.js" type="module"></script>
</body>
</html>
