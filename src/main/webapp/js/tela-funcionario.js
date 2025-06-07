document.addEventListener('DOMContentLoaded', () => {
    const listaTarefasDiv = document.getElementById('lista-tarefas');
    let currentEndpoint = '/listaTarefasDeFuncionario';

    async function carregarTarefas(endpoint) {
        try {
            const resposta = await fetch(`${endpoint}`);
            let data;
            try {
                data = await resposta.json();
            } catch {
                data = {};
            }

            if (!resposta.ok) {
                const msg = data.mensagem || data.message || 'Erro ao buscar tarefas';
                listaTarefasDiv.innerHTML = `<p class="mensagem-vazia">${msg}</p>`;
                return;
            }

            renderizarTarefas(data);
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


        document.querySelectorAll('.btn-concluir').forEach(botao => {
            botao.addEventListener('click', async () => {
                const id = botao.getAttribute('data-id');
                await alternarStatusTarefa(id, true);
                carregarTarefas(currentEndpoint);
            });
        });


        document.querySelectorAll('.btn-pendente').forEach(botao => {
            botao.addEventListener('click', async () => {
                const id = botao.getAttribute('data-id');
                await alternarStatusTarefa(id, false);
                carregarTarefas(currentEndpoint);
            });
        });
    }

    async function alternarStatusTarefa(tarefaId, concluir) {
         try {
             const endpoint = concluir ? '/marcaComoConcluida/' : '/marcaComoPendente/';
             const response = await fetch(`${endpoint}${tarefaId}`, {
                 method: 'PUT'
             });

             let responseBody;
             try {
                 responseBody = await response.json();
             } catch {
                 responseBody = {};
             }

             const msg = responseBody.mensagem || responseBody.message || 'Erro ao atualizar o status da tarefa.';

             if (!response.ok) {
                 throw new Error(msg);
             }

             alert(msg);
         } catch (error) {
             alert(error.message || 'Erro ao atualizar o status da tarefa.');
         }
     }

    window.definirEndpoint = (endpoint) => {
        currentEndpoint = endpoint;
        carregarTarefas(endpoint);
    };

    carregarTarefas(currentEndpoint);
});