const contextPath = window.location.pathname.split('/')[1]
    ? '/' + window.location.pathname.split('/')[1]
    : '';

document.addEventListener('DOMContentLoaded', () => {
    const listaTarefasDiv = document.getElementById('lista-tarefas');
    let currentEndpoint = '/listaTarefasDeFuncionario';

    async function carregarTarefas(endpoint) {
        try {
            const resposta = await fetch(`${contextPath}${endpoint}`);
            if (!resposta.ok) throw new Error('Erro ao buscar tarefas');
            const tarefas = await resposta.json();
            renderizarTarefas(tarefas);
        } catch (erro) {
            console.error('Erro ao carregar tarefas:', erro);
            listaTarefasDiv.innerHTML = `<p class="mensagem-vazia">Erro ao carregar tarefas.</p>`;
        }
    }

    function renderizarTarefas(tarefas) {
        listaTarefasDiv.innerHTML = '';

        if (!tarefas || tarefas.length === 0) {
            listaTarefasDiv.innerHTML = `
                <p class="mensagem-vazia">Você não tem tarefas.</p>
                <button class="botao-atualizar" id="botao-atualizar">Atualizar Lista</button>
            `;
        } else {
            tarefas.forEach(tarefa => {
                const tarefaDiv = document.createElement('div');
                tarefaDiv.className = 'tarefa';

                const statusTexto = tarefa.status === 'CONCLUIDA' ? 'Concluída' : 'Pendente';

                tarefaDiv.innerHTML = `
                    <h3>${tarefa.nome}</h3>
                    <p>${tarefa.descricao}</p>
                    <p><strong>Status:</strong> ${statusTexto}</p>
                    <div class="botoes-tarefa">
                        <button class="btn-concluir" data-id="${tarefa.id}">Concluir</button>
                        <button class="btn-pendente" data-id="${tarefa.id}">Marcar como Pendente</button>
                    </div>
                `;

                listaTarefasDiv.appendChild(tarefaDiv);
            });

            const botaoAtualizar = document.createElement('button');
            botaoAtualizar.className = 'botao-atualizar';
            botaoAtualizar.id = 'botao-atualizar';
            botaoAtualizar.innerText = 'Atualizar Lista';
            listaTarefasDiv.appendChild(botaoAtualizar);
        }

        document.getElementById('botao-atualizar')?.addEventListener('click', () => carregarTarefas(currentEndpoint));

        // Botão concluir
        document.querySelectorAll('.btn-concluir').forEach(botao => {
            botao.addEventListener('click', async () => {
                const id = botao.getAttribute('data-id');
                await alternarStatusTarefa(id, true); // true = concluir
                carregarTarefas(currentEndpoint);
            });
        });

        // Botão marcar como pendente
        document.querySelectorAll('.btn-pendente').forEach(botao => {
            botao.addEventListener('click', async () => {
                const id = botao.getAttribute('data-id');
                await alternarStatusTarefa(id, false); // false = pendente
                carregarTarefas(currentEndpoint);
            });
        });
    }

    async function alternarStatusTarefa(tarefaId, concluir) {
        try {
            const endpoint = concluir ? '/marcaComoConcluida/' : '/marcaComoPendente/';
            const response = await fetch(`${contextPath}${endpoint}${tarefaId}`, {
                method: 'PUT'
            });

            const responseBody = await response.json();

            if (!response.ok) {
                throw new Error(responseBody.message || 'Erro ao atualizar o status da tarefa.');
            }

            alert(responseBody.message);
        } catch (error) {
            console.error('Erro ao alternar status da tarefa:', error);
            alert(error.message || 'Erro ao atualizar o status da tarefa.');
        }
    }

    window.definirEndpoint = (endpoint) => {
        currentEndpoint = endpoint;
        carregarTarefas(endpoint);
    };

    carregarTarefas(currentEndpoint);
});
