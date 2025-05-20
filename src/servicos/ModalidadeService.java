package servicos;

import interfaces.repository.IModalidadeRepository;
import interfaces.servicos.IModalidadeService;

import java.util.List;

import entidades.Modalidade;

public class ModalidadeService implements IModalidadeService {
    private final IModalidadeRepository repository;

    public ModalidadeService(IModalidadeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void cadastrarModalidade(Modalidade modalidade) {
        repository.salvar(modalidade);
    }

    @Override
    public Modalidade buscarModalidadePorId(int id) {
        return repository.buscarPorId(id);
    }

    @Override
    public void atualizarModalidade(Modalidade modalidade) {
        repository.alterar(modalidade);
    }

    @Override
    public void removerModalidade(int id) {
        repository.remover(id);
    }

    @Override
    public List<Modalidade> listarTodasModalidades() {
        return repository.listarTodos();
    }

    public IModalidadeRepository getRepository() {
        return null;
    }
}