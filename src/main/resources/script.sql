CREATE TABLE IF NOT EXISTS usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    cpf TEXT NOT NULL UNIQUE,
    telefone TEXT NOT NULL,
    cargo TEXT NOT NULL CHECK (cargo IN ('FUNCIONARIO', 'SUPERVISOR', 'GERENTE'))
);

CREATE TABLE IF NOT EXISTS tarefa (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    descricao TEXT,
    status TEXT NOT NULL CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA')),
    supervisor_id INTEGER NOT NULL,
    funcionario_id INTEGER NOT NULL,
    FOREIGN KEY (supervisor_id) REFERENCES usuario(id),
    FOREIGN KEY (funcionario_id) REFERENCES usuario(id)
);