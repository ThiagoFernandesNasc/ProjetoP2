package dao;

import database.Conexao;
import entidades.Modalidade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModalidadeDAO {
    
    public void inserir(Modalidade modalidade) throws SQLException {
        String sql = "INSERT INTO modalidades (nome, descricao, idade_minima, idade_maxima) " +
                    "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, modalidade.getNome());
            stmt.setString(2, modalidade.getDescricao());
            stmt.setInt(3, modalidade.getIdadeMinima());
            stmt.setInt(4, modalidade.getIdadeMaxima());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    modalidade.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public void atualizar(Modalidade modalidade) throws SQLException {
        String sql = "UPDATE modalidades SET nome = ?, descricao = ?, " +
                    "idade_minima = ?, idade_maxima = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, modalidade.getNome());
            stmt.setString(2, modalidade.getDescricao());
            stmt.setInt(3, modalidade.getIdadeMinima());
            stmt.setInt(4, modalidade.getIdadeMaxima());
            stmt.setInt(5, modalidade.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Modalidade não encontrada com ID: " + modalidade.getId());
            }
        }
    }
    
    public void excluir(int id) throws SQLException {
        // Primeiro, remover todas as turmas associadas
        String sqlTurmas = "DELETE FROM turmas WHERE modalidade_id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlTurmas)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        
        // Depois, remover a modalidade
        String sql = "DELETE FROM modalidades WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Modalidade não encontrada com ID: " + id);
            }
        }
    }
    
    public Modalidade buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM modalidades WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Modalidade(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("idade_minima"),
                        rs.getInt("idade_maxima")
                    );
                }
            }
        }
        return null;
    }
    
    public List<Modalidade> listarTodas() throws SQLException {
        String sql = "SELECT * FROM modalidades ORDER BY nome";
        List<Modalidade> modalidades = new ArrayList<>();
        
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Modalidade modalidade = new Modalidade(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getInt("idade_minima"),
                    rs.getInt("idade_maxima")
                );
                modalidades.add(modalidade);
            }
        }
        return modalidades;
    }
} 