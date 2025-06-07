document.addEventListener('DOMContentLoaded', () => {
    fetch(`/listaFuncionarios`)
        .then(async response => {
            if (!response.ok) {
                let msg = 'Erro ao buscar funcionários';
                try {
                    const data = await response.json();
                    msg = data.mensagem || data.message || msg;
                } catch {}
                throw new Error(msg);
            }
            return response.json();
        })
        .then(funcionarios => {
            const selects = [
                document.getElementById('select-funcionario-cadastro'),
                document.getElementById('select-funcionario-filtro')
            ];

            selects.forEach(select => {
                select.innerHTML = '<option value="">Selecione um funcionário</option>';
                funcionarios.forEach(func => {
                    const option = document.createElement('option');
                    option.value = func.id;
                    option.textContent = func.nome;
                    select.appendChild(option);
                });
            });
        })
        .catch(error => {
            alert(error.message);
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

        fetch(`/cadastraTarefa`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(tarefa)
        })
            .then(async response => {
                let message = 'Erro desconhecido';
                try {
                    const data = await response.json();
                    message = data.mensagem || data.message || message;
                } catch (e) {}

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

    function renderizarTarefas(tarefas) {
        const lista = document.getElementById('lista-tarefas');
        lista.innerHTML = '';

        if (!tarefas || tarefas.length === 0) {
            return;
        }

        tarefas.forEach(tarefa => {
            const div = document.createElement('div');
            div.classList.add('tarefa');

            div.innerHTML = `
                <h3>${tarefa.nome}</h3>
                <p><strong>Descrição:</strong> ${tarefa.descricao}</p>
                <p><strong>Funcionário:</strong> ${tarefa.nomeFuncionario || 'Desconhecido'}</p>
            `;

            lista.appendChild(div);
        });
    }

    function listarTarefas(endpoint) {
        const select = document.getElementById('select-funcionario-filtro');
        const funcionarioId = select.value;
        const lista = document.getElementById('lista-tarefas');

        if (!funcionarioId) {
            alert('Selecione um funcionário para filtrar.');
            return;
        }

        fetch(`${endpoint}/${funcionarioId}`)
            .then(async response => {
                let msg = 'Erro ao buscar tarefas';
                try {
                    const data = await response.json();
                    msg = data.mensagem || data.message || msg;
                    if (!response.ok) {
                        lista.innerHTML = '';
                        alert(msg);
                        return null;
                    }
                    return data;
                } catch {
                    if (!response.ok) {
                        lista.innerHTML = '';
                        alert(msg);
                        return null;
                    }
                    return [];
                }
            })
            .then(tarefas => {
                if (tarefas) renderizarTarefas(tarefas);
            })
            .catch(error => {
                lista.innerHTML = '';
                alert(error.message);
                console.error('Erro ao carregar as tarefas:', error);
            });
    }

    window.definirEndpoint = function(endpoint) {
        listarTarefas(endpoint);
    };
});