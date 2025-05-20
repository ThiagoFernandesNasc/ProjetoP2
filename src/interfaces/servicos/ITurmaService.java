package interfaces.servicos;

import java.util.List;

import entidades.Turma;

public interface ITurmaService {
    void criarTurma(Turma turma);
    Turma buscarTurmaPorId(int id);
    void atualizarTurma(Turma turma);
    void encerrarTurma(int id);
    List<Turma> listarTodasTurmas();
    boolean matricularAluno(int idTurma, String cpfAluno);
}