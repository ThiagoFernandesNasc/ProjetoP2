package dao;

import database.Conexao;
import entidades.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    
    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (cpf, nome, telefone, email, data_nascimento, responsavel, observacoes_saude) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getEmail());
            stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(6, aluno.getResponsavel());
            stmt.setString(7, aluno.getObservacoesSaude());
            
            stmt.executeUpdate();
        }
    }
    
    public void atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE alunos SET nome = ?, telefone = ?, email = ?, " +
                    "data_nascimento = ?, responsavel = ?, observacoes_saude = ? WHERE cpf = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getTelefone());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(5, aluno.getResponsavel());
            stmt.setString(6, aluno.getObservacoesSaude());
            stmt.setString(7, aluno.getCpf());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Aluno não encontrado com CPF: " + aluno.getCpf());
            }
        }
    }
    
    public void excluir(String cpf) throws SQLException {
        // Primeiro, remover todas as matrículas do aluno
        String sqlMatriculas = "DELETE FROM matriculas WHERE aluno_cpf = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlMatriculas)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
        
        // Depois, remover o aluno
        String sql = "DELETE FROM alunos WHERE cpf = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Aluno não encontrado com CPF: " + cpf);
            }
        }
    }
    
    public Aluno buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM alunos WHERE cpf = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getString("responsavel"),
                        rs.getString("observacoes_saude")
                    );
                }
            }
        }
        return null;
    }
    
    public List<Aluno> listarTodos() throws SQLException {
        String sql = "SELECT * FROM alunos ORDER BY nome";
        List<Aluno> alunos = new ArrayList<>();
        
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
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
                alunos.add(aluno);
            }
        }
        return alunos;
    }
} 