package interfaces.servicos;

import java.util.List;

import entidades.Aluno;

public interface IAlunoService {
    void cadastrarAluno(Aluno aluno);
    Aluno buscarAlunoPorCpf(String cpf);
    void atualizarAluno(Aluno aluno);
    void removerAluno(String cpf);
    List<Aluno> listarTodosAlunos();
}