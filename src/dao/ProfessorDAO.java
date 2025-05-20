package dao;

import database.Conexao;
import entidades.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    
    public void inserir(Professor professor) throws SQLException {
        String sql = "INSERT INTO professores (id, nome, disciplina) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, professor.getId());
            stmt.setString(2, professor.getNome());
            stmt.setString(3, professor.getDisciplina());
            
            stmt.executeUpdate();
        }
    }
    
    public void atualizar(Professor professor) throws SQLException {
        String sql = "UPDATE professores SET nome = ?, disciplina = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getDisciplina());
            stmt.setInt(3, professor.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Professor não encontrado com ID: " + professor.getId());
            }
        }
    }
    
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM professores WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Professor não encontrado com ID: " + id);
            }
        }
    }
    
    public Professor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM professores WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("disciplina")
                    );
                }
            }
        }
        return null;
    }
    
    public List<Professor> listarTodos() throws SQLException {
        String sql = "SELECT * FROM professores";
        List<Professor> professores = new ArrayList<>();
        
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Professor professor = new Professor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("disciplina")
                );
                professores.add(professor);
            }
        }
        return professores;
    }
} 