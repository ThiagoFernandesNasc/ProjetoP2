package repositorys;

import database.Conexao;
import entidades.Aluno;
import interfaces.repository.IAlunoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository implements IAlunoRepository {
    
    @Override
    public void salvar(Aluno aluno) {
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
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(String cpf) {
        String sql = "DELETE FROM alunos WHERE cpf = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Aluno não encontrado com CPF: " + cpf);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public void alterar(Aluno aluno) {
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
                throw new RuntimeException("Aluno não encontrado com CPF: " + aluno.getCpf());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Aluno> listarTodos() {
        String sql = "SELECT * FROM alunos";
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
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage(), e);
        }
        
        return alunos;
    }

    @Override
    public Aluno buscarPorCpf(String cpf) {
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
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno: " + e.getMessage(), e);
        }
        
        return null;
    }
}