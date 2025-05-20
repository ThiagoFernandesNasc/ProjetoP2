package dao;

import database.Conexao;
import entidades.Turma;
import entidades.Professor;
import entidades.Modalidade;
import entidades.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;

public class TurmaDAO {
    
    public void inserir(Turma turma) throws SQLException {
        String sql = "INSERT INTO turmas (modalidade_id, professor_id, dia_semana, horario, capacidade_maxima) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, turma.getModalidade().getId());
            stmt.setInt(2, turma.getProfessor().getId());
            stmt.setString(3, turma.getDiaSemana().toString());
            stmt.setTime(4, Time.valueOf(turma.getHorario()));
            stmt.setInt(5, turma.getCapacidadeMaxima());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    turma.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public void atualizar(Turma turma) throws SQLException {
        String sql = "UPDATE turmas SET modalidade_id = ?, professor_id = ?, " +
                    "dia_semana = ?, horario = ?, capacidade_maxima = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, turma.getModalidade().getId());
            stmt.setInt(2, turma.getProfessor().getId());
            stmt.setString(3, turma.getDiaSemana().toString());
            stmt.setTime(4, Time.valueOf(turma.getHorario()));
            stmt.setInt(5, turma.getCapacidadeMaxima());
            stmt.setInt(6, turma.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Turma não encontrada com ID: " + turma.getId());
            }
        }
    }
    
    public void excluir(int id) throws SQLException {
        // Primeiro, remover todas as matrículas da turma
        String sqlMatriculas = "DELETE FROM matriculas WHERE turma_id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlMatriculas)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        
        // Depois, remover a turma
        String sql = "DELETE FROM turmas WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Turma não encontrada com ID: " + id);
            }
        }
    }
    
    public Turma buscarPorId(int id) throws SQLException {
        String sql = "SELECT t.*, m.*, p.* FROM turmas t " +
                    "JOIN modalidades m ON t.modalidade_id = m.id " +
                    "JOIN professores p ON t.professor_id = p.id " +
                    "WHERE t.id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Modalidade modalidade = new Modalidade(
                        rs.getInt("m.id"),
                        rs.getString("m.nome"),
                        rs.getString("m.descricao"),
                        rs.getInt("m.idade_minima"),
                        rs.getInt("m.idade_maxima")
                    );
                    
                    Professor professor = new Professor(
                        rs.getInt("p.id"),
                        rs.getString("p.nome"),
                        rs.getString("p.disciplina")
                    );
                    
                    Turma turma = new Turma(
                        rs.getInt("t.id"),
                        modalidade,
                        professor,
                        DayOfWeek.valueOf(rs.getString("t.dia_semana")),
                        rs.getTime("t.horario").toLocalTime(),
                        rs.getInt("t.capacidade_maxima")
                    );
                    
                    // Buscar alunos matriculados
                    buscarAlunosMatriculados(turma);
                    
                    return turma;
                }
            }
        }
        return null;
    }
    
    public List<Turma> listarTodas() throws SQLException {
        String sql = "SELECT t.*, m.*, p.* FROM turmas t " +
                    "JOIN modalidades m ON t.modalidade_id = m.id " +
                    "JOIN professores p ON t.professor_id = p.id " +
                    "ORDER BY m.nome, t.dia_semana, t.horario";
        List<Turma> turmas = new ArrayList<>();
        
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Modalidade modalidade = new Modalidade(
                    rs.getInt("m.id"),
                    rs.getString("m.nome"),
                    rs.getString("m.descricao"),
                    rs.getInt("m.idade_minima"),
                    rs.getInt("m.idade_maxima")
                );
                
                Professor professor = new Professor(
                    rs.getInt("p.id"),
                    rs.getString("p.nome"),
                    rs.getString("p.disciplina")
                );
                
                Turma turma = new Turma(
                    rs.getInt("t.id"),
                    modalidade,
                    professor,
                    DayOfWeek.valueOf(rs.getString("t.dia_semana")),
                    rs.getTime("t.horario").toLocalTime(),
                    rs.getInt("t.capacidade_maxima")
                );
                
                turmas.add(turma);
            }
            
            // Buscar alunos matriculados para cada turma
            for (Turma turma : turmas) {
                buscarAlunosMatriculados(turma);
            }
        }
        return turmas;
    }
    
    private void buscarAlunosMatriculados(Turma turma) throws SQLException {
        String sql = "SELECT a.* FROM matriculas m " +
                    "JOIN alunos a ON m.aluno_cpf = a.cpf " +
                    "WHERE m.turma_id = ? " +
                    "ORDER BY a.nome";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, turma.getId());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getString("responsavel"),
                        rs.getString("observacoes_saude")
                    );
                    turma.adicionarAluno(aluno);
                }
            }
        }
    }
    
    public void matricularAluno(int turmaId, String alunoCpf) throws SQLException {
        // Verificar se a turma existe
        Turma turma = buscarPorId(turmaId);
        if (turma == null) {
            throw new SQLException("Turma não encontrada com ID: " + turmaId);
        }
        
        // Verificar se o aluno existe
        AlunoDAO alunoDAO = new AlunoDAO();
        Aluno aluno = alunoDAO.buscarPorCpf(alunoCpf);
        if (aluno == null) {
            throw new SQLException("Aluno não encontrado com CPF: " + alunoCpf);
        }
        
        // Verificar se a turma já está cheia
        if (turma.getAlunos().size() >= turma.getCapacidadeMaxima()) {
            throw new SQLException("Turma já está com capacidade máxima");
        }
        
        // Verificar se o aluno já está matriculado
        for (Aluno a : turma.getAlunos()) {
            if (a.getCpf().equals(alunoCpf)) {
                throw new SQLException("Aluno já está matriculado nesta turma");
            }
        }
        
        String sql = "INSERT INTO matriculas (turma_id, aluno_cpf) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, turmaId);
            stmt.setString(2, alunoCpf);
            
            stmt.executeUpdate();
        }
    }
    
    public void desmatricularAluno(int turmaId, String alunoCpf) throws SQLException {
        String sql = "DELETE FROM matriculas WHERE turma_id = ? AND aluno_cpf = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, turmaId);
            stmt.setString(2, alunoCpf);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Matrícula não encontrada para turma " + turmaId + " e aluno " + alunoCpf);
            }
        }
    }
} 