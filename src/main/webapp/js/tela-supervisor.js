const contextPath = window.location.pathname.split('/')[1]
    ? '/' + window.location.pathname.split('/')[1]
    : '';

document.addEventListener('DOMContentLoaded', () => {
    fetch(`${contextPath}/listaFuncionarios`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar funcionários');
            }
            return response.json();
        })
        .then(funcionarios => {
            const selects = [
                document.getElementById('select-funcionario-cadastro'),
                document.getElementById('select-funcionario-filtro')
            ];

            selects.forEach(select => {
                funcionarios.forEach(func => {
                    const option = document.createElement('option');
                    option.value = func.id;
                    option.textContent = func.nome;
                    select.appendChild(option.cloneNode(true));
                });
            });
        })
        .catch(error => {
            console.error('Erro ao carregar os funcionários:', error);
        });

    const form = document.getElementById('form-cadastrar-tarefa');
    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const nome = form.titulo.value.trim();
        const descricao = form.descricao.value.trim();
        const funcionarioId = form.funcionario.value;

        const tarefa = {
            nome,
            descricao,
            funcionarioId
        };

        fetch(`${contextPath}/cadastraTarefa`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(tarefa)
        })
            .then(async response => {
                let message = 'Erro desconhecido';
                try {
                    const contentType = response.headers.get('Content-Type');
                    if (contentType && contentType.includes('application/json')) {
                        const data = await response.json();
                        if (data && data.message) message = data.message;
                    } else {
                        const text = await response.text();
                        if (text) message = text;
                    }
                } catch (e) {
                    console.error('Erro ao processar a resposta do servidor:', e);
                }

                if (!response.ok) {
                    throw new Error(message);
                }

                alert(message);
                form.reset();
            })
            .catch(error => {
                alert(error.message);
            });
    });
});