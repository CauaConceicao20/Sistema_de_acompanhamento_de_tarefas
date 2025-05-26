const contextPath = window.location.pathname.split('/')[1]
    ? '/' + window.location.pathname.split('/')[1]
    : '';

document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    const email = document.getElementById('email').value.trim();
    const senha = document.getElementById('senha').value.trim();

    const mensagemDiv = document.getElementById('mensagem');
    mensagemDiv.textContent = '';

    if (!email || !senha) {
        mensagemDiv.textContent = 'Email e senha são obrigatórios.';
        mensagemDiv.style.color = 'red';
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

        const data = await response.json();

        if (response.ok) {
            mensagemDiv.textContent = data.mensagem || 'Login realizado com sucesso.';
            mensagemDiv.style.color = 'green';


            const cargo = data.cargo ? data.cargo : null;

            switch (cargo) {
                case 'SUPERVISOR':
                    alert("Funcionou");
                    window.location.href = `${contextPath}/tela-supervisor.jsp`;
                    break;
                case 'GERENTE':
                    window.location.href = `${contextPath}/tela-gerente.jsp`;
                    break;
                case 'FUNCIONARIO':
                    window.location.href = `${contextPath}/tela-funcionario.jsp`;
                    break;
                default:
                    alert('Cargo desconhecido: ' + cargo);
            }

        } else {
            mensagemDiv.textContent = data.mensagem || 'Erro no login.';
            mensagemDiv.style.color = 'red';
        }
    } catch (error) {
        mensagemDiv.textContent = 'Erro ao conectar com o servidor.';
        mensagemDiv.style.color = 'red';
        console.log(error);
    }
});
