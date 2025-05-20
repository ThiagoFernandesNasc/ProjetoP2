-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS escola;
USE escola;

-- Tabela de Professores
CREATE TABLE IF NOT EXISTS professores (
    id INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    disciplina VARCHAR(50) NOT NULL
);

-- Tabela de Alunos
CREATE TABLE IF NOT EXISTS alunos (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    data_nascimento DATE,
    responsavel VARCHAR(100),
    observacoes_saude TEXT
);

-- Tabela de Modalidades
CREATE TABLE IF NOT EXISTS modalidades (
    id INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao TEXT,
    idade_minima INT,
    idade_maxima INT
);

-- Tabela de Turmas
CREATE TABLE IF NOT EXISTS turmas (
    id INT PRIMARY KEY,
    id_professor INT,
    id_modalidade INT,
    dia_semana INT,
    horario TIME,
    capacidade_maxima INT,
    FOREIGN KEY (id_professor) REFERENCES professores(id) ON DELETE RESTRICT,
    FOREIGN KEY (id_modalidade) REFERENCES modalidades(id) ON DELETE RESTRICT
);

-- Tabela de Matrículas (relacionamento entre Turmas e Alunos)
CREATE TABLE IF NOT EXISTS matriculas (
    id_turma INT,
    cpf_aluno VARCHAR(14),
    PRIMARY KEY (id_turma, cpf_aluno),
    FOREIGN KEY (id_turma) REFERENCES turmas(id) ON DELETE CASCADE,
    FOREIGN KEY (cpf_aluno) REFERENCES alunos(cpf) ON DELETE CASCADE,
    CONSTRAINT fk_turma_matricula FOREIGN KEY (id_turma) REFERENCES turmas(id),
    CONSTRAINT fk_aluno_matricula FOREIGN KEY (cpf_aluno) REFERENCES alunos(cpf)
); 