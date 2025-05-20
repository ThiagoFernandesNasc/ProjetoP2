package repositorys;

import database.Conexao;
import entidades.Professor;
import interfaces.repository.IProfessorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de professores usando uma lista encadeada.
 * Mantém os professores em memória usando uma estrutura de dados eficiente.
 */
public class ProfessorRepository implements IProfessorRepository {
    /**
     * Classe interna que representa um nó da lista encadeada.
     */
    private class Node {
        Professor professor;
        Node next;

        Node(Professor professor) {
            this.professor = professor;
        }
    }

    /**
     * Salva um novo professor no repositório.
     * @param professor Professor a ser salvo
     * @throws IllegalArgumentException se o professor for nulo
     */
    @Override
    public void salvar(Professor professor) {
        String sql = "INSERT INTO professores (id, nome, disciplina) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, professor.getId());
            stmt.setString(2, professor.getNome());
            stmt.setString(3, professor.getDisciplina());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar professor: " + e.getMessage(), e);
        }
    }

    /**
     * Remove um professor do repositório pelo seu ID.
     * @param id ID do professor a ser removido
     * @throws IllegalArgumentException se o ID for inválido ou o professor não existir
     */
    @Override
    public void remover(int id) {
        String sql = "DELETE FROM professores WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Professor não encontrado com ID: " + id);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover professor: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de um professor existente.
     * @param professor Professor com os dados atualizados
     * @throws IllegalArgumentException se o professor for nulo ou não existir no repositório
     */
    @Override
    public void alterar(Professor professor) {
        String sql = "UPDATE professores SET nome = ?, disciplina = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getDisciplina());
            stmt.setInt(3, professor.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Professor não encontrado com ID: " + professor.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar professor: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos os professores cadastrados.
     * @return Lista de todos os professores
     */
    @Override
    public List<Professor> listar() {
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
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores: " + e.getMessage(), e);
        }
        
        return professores;
    }

    /**
     * Busca um professor pelo seu ID.
     * @param id ID do professor a ser buscado
     * @return Professor encontrado ou null se não existir
     * @throws IllegalArgumentException se o ID for inválido
     */
    @Override
    public Professor buscarPorId(int id) {
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
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor: " + e.getMessage(), e);
        }
        
        return null;
    }
}
