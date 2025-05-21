package repositorys;

import database.Conexao;
import entidades.Modalidade;
import interfaces.repository.IModalidadeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModalidadeRepository implements IModalidadeRepository {
    
    @Override
    public void salvar(Modalidade modalidade) {
        String sql = "INSERT INTO modalidades (id, nome, descricao, idade_minima, idade_maxima) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, modalidade.getId());
            stmt.setString(2, modalidade.getNome());
            stmt.setString(3, modalidade.getDescricao());
            stmt.setInt(4, modalidade.getIdadeMinima());
            stmt.setInt(5, modalidade.getIdadeMaxima());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar modalidade: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        // Primeiro, remover todas as turmas associadas
        String sqlTurmas = "DELETE FROM turmas WHERE id_modalidade = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlTurmas)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover turmas associadas: " + e.getMessage(), e);
        }
        
        // Depois, remover a modalidade
        String sql = "DELETE FROM modalidades WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Modalidade não encontrada com ID: " + id);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover modalidade: " + e.getMessage(), e);
        }
    }

    @Override
    public void alterar(Modalidade modalidade) {
        String sql = "UPDATE modalidades SET nome = ?, descricao = ?, idade_minima = ?, idade_maxima = ? " +
                    "WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, modalidade.getNome());
            stmt.setString(2, modalidade.getDescricao());
            stmt.setInt(3, modalidade.getIdadeMinima());
            stmt.setInt(4, modalidade.getIdadeMaxima());
            stmt.setInt(5, modalidade.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Modalidade não encontrada com ID: " + modalidade.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar modalidade: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Modalidade> listarTodos() {
        String sql = "SELECT * FROM modalidades";
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
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar modalidades: " + e.getMessage(), e);
        }
        
        return modalidades;
    }

    @Override
    public Modalidade buscarPorId(int id) {
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
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar modalidade: " + e.getMessage(), e);
        }
        
        return null;
    }
}