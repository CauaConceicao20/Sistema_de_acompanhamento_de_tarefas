DROP TABLE IF EXISTS tarefa;
DROP TABLE IF EXISTS usuario;

CREATE TABLE IF NOT EXISTS usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    cpf TEXT NOT NULL UNIQUE,
    telefone TEXT NOT NULL,
    cargo TEXT NOT NULL CHECK (cargo IN ('FUNCIONARIO', 'SUPERVISOR', 'GERENTE')),
    supervisor_id INTEGER,
    FOREIGN KEY (supervisor_id) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS tarefa (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    descricao TEXT,
    status TEXT NOT NULL CHECK (status IN ('PENDENTE', 'CONCLUIDA')),
    supervisor_id INTEGER NOT NULL,
    funcionario_id INTEGER NOT NULL,
    FOREIGN KEY (supervisor_id) REFERENCES usuario(id),
    FOREIGN KEY (funcionario_id) REFERENCES usuario(id)
);

-- Inserção dos supervisores
INSERT INTO usuario (nome, email, senha, cpf, telefone, cargo)
VALUES
('João Supervisor', 'joao@empresa.com', 'senha123', '12345678900', '11999999999', 'SUPERVISOR'),
('Ana Supervisora', 'ana@empresa.com', 'senha222', '22233344455', '11666666666', 'SUPERVISOR');

-- Inserção dos funcionários (supervisionados por João - id = 1)
INSERT INTO usuario (nome, email, senha, cpf, telefone, cargo, supervisor_id)
VALUES
('Maria Funcionária', 'maria@empresa.com', 'senha456', '98765432100', '11888888888', 'FUNCIONARIO', 1),
('Carlos Funcionário', 'carlosf@empresa.com', 'senha457', '99988877766', '11777777777', 'FUNCIONARIO', 1),
('Juliana Funcionária', 'juliana@empresa.com', 'senha458', '55544433322', '11656565656', 'FUNCIONARIO', 1);

-- Inserção dos funcionários (supervisionados por Ana - id = 2)
INSERT INTO usuario (nome, email, senha, cpf, telefone, cargo, supervisor_id)
VALUES
('Felipe Funcionário', 'felipe@empresa.com', 'senha459', '11100099988', '11545454545', 'FUNCIONARIO', 2),
('Bianca Funcionária', 'bianca@empresa.com', 'senha460', '66677788899', '11434343434', 'FUNCIONARIO', 2);

-- Inserção de um gerente (sem supervisor)
INSERT INTO usuario (nome, email, senha, cpf, telefone, cargo)
VALUES ('Carlos Gerente', 'carlos@empresa.com', 'senha789', '11122233344', '11777777777', 'GERENTE');

-- Inserção de tarefas para os funcionários do João (id supervisor = 1)
INSERT INTO tarefa (nome, descricao, status, supervisor_id, funcionario_id)
VALUES
('Auditoria Interna', 'Preparar dados para auditoria.', 'PENDENTE', 1, 5),
('Organizar Documentos', 'Classificar e arquivar documentos antigos.', 'PENDENTE', 1, 3),
('Atualizar Planilhas', 'Revisar e atualizar planilhas de despesas.', 'CONCLUIDA', 1, 4);

-- Inserção de tarefas para os funcionários da Ana (id supervisor = 2)
INSERT INTO tarefa (nome, descricao, status, supervisor_id, funcionario_id)
VALUES('Relatório Semanal', 'Elaborar relatório de progresso semanal.', 'PENDENTE', 2, 7),
('Inventário', 'Fazer contagem de estoque e atualizar sistema.', 'PENDENTE', 2, 6);