const contextPath = window.location.pathname.split('/')[1]
    ? '/' + window.location.pathname.split('/')[1]
    : '';

document.addEventListener("DOMContentLoaded", () => {
    carregarSupervisores();
});

function carregarSupervisores() {
    fetch(`${contextPath}/listaSupervisores`, {
        method: 'GET',
        credentials: 'include'
    })
    .then(async response => {
        let data;
        try {
            data = await response.json();
        } catch {
            data = {};
        }

        if (!response.ok) {
            const msg = data.mensagem || data.message || "Erro ao buscar supervisores";
            alert(msg);
            return;
        }

        const select = document.getElementById("supervisorSelect");
        select.innerHTML = '<option value="">Selecione um supervisor</option>';

        (data || []).forEach(sup => {
            const option = document.createElement("option");
            option.value = sup.id;
            option.textContent = sup.nome;
            select.appendChild(option);
        });
    })
    .catch(error => {
        alert("Erro inesperado ao carregar supervisores");
        console.error("Erro inesperado ao carregar supervisores:", error);
    });
}

function gerarRelatorio(tipo) {
    let url = '';
    const areaRelatorio = document.getElementById("area-relatorio");
    areaRelatorio.innerHTML = '';

    if (tipo === 'tarefasPorSupervisor') {
        const supervisorId = document.getElementById("supervisorSelect").value;

        if (!supervisorId) {
            alert("Selecione um supervisor antes de gerar o relatório.");
            return;
        }

        url = `${contextPath}/relatorioDeTarefasCadastradasPorSupervisor/${supervisorId}`;

        fetch(url, {
            method: 'GET',
            credentials: 'include'
        })
        .then(async response => {
            let data;
            try {
                data = await response.json();
            } catch {
                data = {};
            }

            if (!response.ok) {
                const msg = data.mensagem || data.message || "Erro ao gerar relatório";
                areaRelatorio.innerHTML = `<p class="mensagem-vazia">${msg}</p>`;
                return;
            }

            const supervisor = data.dados && data.dados[0];
            const tarefas = supervisor?.tarefasCadastradas || [];

            if (!tarefas.length) {
                areaRelatorio.innerHTML = `<p class="mensagem-vazia">${data.mensagem || data.message || "Nenhuma tarefa encontrada para este supervisor."}</p>`;
                return;
            }

            const relatorioHTML = `
                <div class="relatorio">
                    <h2>${data.relatorio}</h2>
                    <p><strong>Supervisor:</strong> ${supervisor.supervisorNome}</p>
                    <p><strong>Total de Tarefas:</strong> ${tarefas.length}</p>
                    <table border="1" cellpadding="5" cellspacing="0">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Descrição</th>
                                <th>Status</th>
                                <th>Funcionário Responsável</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${tarefas.map(tarefa => `
                                <tr>
                                    <td>${tarefa.tarefaId}</td>
                                    <td>${tarefa.nome}</td>
                                    <td>${tarefa.descricao}</td>
                                    <td>${tarefa.status}</td>
                                    <td>${tarefa.funcionarioNome || 'Não atribuído'}</td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </div>
            `;

            areaRelatorio.innerHTML = relatorioHTML;
        })
        .catch(error => {
            areaRelatorio.innerHTML = `<p class="mensagem-vazia">Erro ao gerar relatório.</p>`;
            console.error("Erro ao gerar relatório:", error);
        });

    } else if (tipo === 'tarefasPorStatus') {
        url = `${contextPath}/relatorioDeTarefasPendentes`;

        fetch(url, {
            method: 'GET',
            credentials: 'include'
        })
        .then(async response => {
            let data;
            try {
                data = await response.json();
            } catch {
                data = {};
            }

            if (!response.ok) {
                const msg = data.mensagem || data.message || "Erro ao gerar relatório";
                areaRelatorio.innerHTML = `<p class="mensagem-vazia">${msg}</p>`;
                return;
            }

            if (!data.tarefas || data.tarefas.length === 0) {
                areaRelatorio.innerHTML = `<p class="mensagem-vazia">${data.mensagem || data.message || "Nenhuma tarefa pendente encontrada."}</p>`;
                return;
            }

            const relatorioHTML = `
                <div class="relatorio">
                    <h2>${data.relatorio}</h2>
                    <p><strong>Total de Tarefas Pendentes:</strong> ${data.totalTarefas}</p>
                    <table border="1" cellpadding="5" cellspacing="0">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Descrição</th>
                                <th>Status</th>
                                <th>Responsável</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${data.tarefas.map(tarefa => `
                                <tr>
                                    <td>${tarefa.tarefaId}</td>
                                    <td>${tarefa.nome}</td>
                                    <td>${tarefa.descricao}</td>
                                    <td>${tarefa.status}</td>
                                    <td>${tarefa.funcionarioNome || 'Não atribuído'}</td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </div>
            `;

            areaRelatorio.innerHTML = relatorioHTML;
        })
        .catch(error => {
            areaRelatorio.innerHTML = `<p class="mensagem-vazia">Erro ao gerar relatório.</p>`;
            console.error("Erro ao gerar relatório:", error);
        });

    } else if (tipo === 'quantidadePorFuncionario') {
        url = `${contextPath}/relatorioDeFuncionariosSemPendencias`;

        fetch(url, {
            method: 'GET',
            credentials: 'include'
        })
        .then(async response => {
            let data;
            try {
                data = await response.json();
            } catch {
                data = {};
            }

            if (!response.ok) {
                const msg = data.mensagem || data.message || "Erro ao gerar relatório";
                areaRelatorio.innerHTML = `<p class="mensagem-vazia">${msg}</p>`;
                return;
            }

            if (!data.funcionarios || data.funcionarios.length === 0) {
                areaRelatorio.innerHTML = `<p class="mensagem-vazia">${data.mensagem || data.message || "Todos os funcionários têm tarefas atribuídas."}</p>`;
                return;
            }

            const relatorioHTML = `
                <div class="relatorio">
                    <h2>${data.relatorio}</h2>
                    <p><strong>Total de Funcionários sem Tarefas:</strong> ${data.totalFuncionarios}</p>
                    <table border="1" cellpadding="5" cellspacing="0">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Email</th>
                                <th>Cargo</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${data.funcionarios.map(func => `
                                <tr>
                                    <td>${func.id}</td>
                                    <td>${func.nome}</td>
                                    <td>${func.email}</td>
                                    <td>${func.cargo}</td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </div>
            `;

            areaRelatorio.innerHTML = relatorioHTML;
        })
        .catch(error => {
            areaRelatorio.innerHTML = `<p class="mensagem-vazia">Erro ao gerar relatório.</p>`;
            console.error("Erro ao gerar relatório:", error);
        });
    }
}

window.gerarRelatorio = gerarRelatorio;