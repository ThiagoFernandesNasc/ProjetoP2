package repositorys;

import database.Conexao;
import entidades.Professor;
import entidades.Modalidade;
import entidades.Turma;
import interfaces.repository.ITurmaRepository;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class TurmaRepository implements ITurmaRepository {
    
    @Override
    public void salvar(Turma turma) {
        String sql = "INSERT INTO turmas (id, id_professor, id_modalidade, dia_semana, horario, capacidade_maxima) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, turma.getId());
            stmt.setInt(2, turma.getProfessor().getId());
            stmt.setInt(3, turma.getModalidade().getId());
            stmt.setInt(4, turma.getDiaSemana().getValue());
            stmt.setTime(5, Time.valueOf(turma.getHorario()));
            stmt.setInt(6, turma.getCapacidadeMaxima());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar turma: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM turmas WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Turma não encontrada com ID: " + id);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover turma: " + e.getMessage(), e);
        }
    }

    @Override
    public void alterar(Turma turma) {
        String sql = "UPDATE turmas SET id_professor = ?, id_modalidade = ?, dia_semana = ?, " +
                    "horario = ?, capacidade_maxima = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, turma.getProfessor().getId());
            stmt.setInt(2, turma.getModalidade().getId());
            stmt.setInt(3, turma.getDiaSemana().getValue());
            stmt.setTime(4, Time.valueOf(turma.getHorario()));
            stmt.setInt(5, turma.getCapacidadeMaxima());
            stmt.setInt(6, turma.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Turma não encontrada com ID: " + turma.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar turma: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Turma> listarTodos() {
        String sql = "SELECT t.*, p.nome as nome_professor, m.nome as nome_modalidade " +
                    "FROM turmas t " +
                    "JOIN professores p ON t.id_professor = p.id " +
                    "JOIN modalidades m ON t.id_modalidade = m.id";
        List<Turma> turmas = new ArrayList<>();
        
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                // Aqui você precisará buscar o professor e a modalidade completos
                // usando seus respectivos repositórios
                // Por enquanto, vamos criar objetos temporários
                Professor professor = new Professor(
                    rs.getInt("id_professor"),
                    rs.getString("nome_professor"),
                    "" // disciplina não disponível na consulta
                );
                
                Modalidade modalidade = new Modalidade(
                    rs.getInt("id_modalidade"),
                    rs.getString("nome_modalidade"),
                    "", // descrição não disponível na consulta
                    0,  // idade mínima não disponível na consulta
                    0   // idade máxima não disponível na consulta
                );
                
                Turma turma = new Turma(
                    rs.getInt("id"),
                    modalidade,
                    professor,
                    DayOfWeek.of(rs.getInt("dia_semana")),
                    rs.getTime("horario").toLocalTime(),
                    rs.getInt("capacidade_maxima")
                );
                turmas.add(turma);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar turmas: " + e.getMessage(), e);
        }
        
        return turmas;
    }

    @Override
    public Turma buscarPorId(int id) {
        String sql = "SELECT t.*, p.nome as nome_professor, m.nome as nome_modalidade " +
                    "FROM turmas t " +
                    "JOIN professores p ON t.id_professor = p.id " +
                    "JOIN modalidades m ON t.id_modalidade = m.id " +
                    "WHERE t.id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Aqui você precisará buscar o professor e a modalidade completos
                    // usando seus respectivos repositórios
                    // Por enquanto, vamos criar objetos temporários
                    Professor professor = new Professor(
                        rs.getInt("id_professor"),
                        rs.getString("nome_professor"),
                        "" // disciplina não disponível na consulta
                    );
                    
                    Modalidade modalidade = new Modalidade(
                        rs.getInt("id_modalidade"),
                        rs.getString("nome_modalidade"),
                        "", // descrição não disponível na consulta
                        0,  // idade mínima não disponível na consulta
                        0   // idade máxima não disponível na consulta
                    );
                    
                    return new Turma(
                        rs.getInt("id"),
                        modalidade,
                        professor,
                        DayOfWeek.of(rs.getInt("dia_semana")),
                        rs.getTime("horario").toLocalTime(),
                        rs.getInt("capacidade_maxima")
                    );
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar turma: " + e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public boolean matricularAluno(int idTurma, String cpfAluno) {
        String sql = "INSERT INTO matriculas (id_turma, cpf_aluno) VALUES (?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idTurma);
            stmt.setString(2, cpfAluno);
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao matricular aluno: " + e.getMessage(), e);
        }
    }
}