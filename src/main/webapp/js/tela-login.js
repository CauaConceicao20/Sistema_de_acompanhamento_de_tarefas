const contextPath = window.location.pathname.split('/')[1]
    ? '/' + window.location.pathname.split('/')[1]
    : '';

document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    const email = document.getElementById('email').value.trim();
    const senha = document.getElementById('senha').value.trim();

    if (!email || !senha) {
        alert("Email e Senha são obrigatórios");
        return;
    }

    try {
        const response = await fetch(`${contextPath}/loginUsuario`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, senha })
        });

        let data = {};
        try {
            data = await response.json();
        } catch {}

        if (response.ok) {
            const cargo = data.cargo ? data.cargo : null;

            switch (cargo) {
                case 'SUPERVISOR':
                    window.location.href = `${contextPath}/tela-supervisor.jsp`;
                    break;
                case 'GERENTE':
                    window.location.href = `${contextPath}/tela-gerente.jsp`;
                    break;
                case 'FUNCIONARIO':
                    window.location.href = `${contextPath}/tela-funcionario.jsp`;
                    break;
                default:
                    alert(data.mensagem || 'Cargo desconhecido');
            }
        } else {
            alert(data.mensagem || data.message || 'Erro ao realizar login');
        }
    } catch (error) {
        mensagemDiv.textContent = 'Erro de conexão com o servidor';
        console.log(error);
    }
});