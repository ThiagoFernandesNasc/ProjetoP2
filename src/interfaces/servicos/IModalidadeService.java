package interfaces.servicos;

import java.util.List;

import entidades.Modalidade;

public interface IModalidadeService {
    void cadastrarModalidade(Modalidade modalidade);
    Modalidade buscarModalidadePorId(int id);
    void atualizarModalidade(Modalidade modalidade);
    void removerModalidade(int id);
    List<Modalidade> listarTodasModalidades();
}