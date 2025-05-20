package interfaces.repository;

import java.util.List;

import entidades.Modalidade;

public interface IModalidadeRepository {
    void salvar(Modalidade modalidade);
    void remover(int id);
    void alterar(Modalidade modalidade);
    List<Modalidade> listarTodos();
    Modalidade buscarPorId(int id);
}