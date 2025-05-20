package interfaces.repository;

import entidades.Professor;
import java.util.List;

/**
 * Interface que define as operações de persistência para a entidade Professor.
 * Define os métodos básicos de CRUD (Create, Read, Update, Delete) e listagem.
 */
public interface IProfessorRepository {
    /**
     * Salva um novo professor no repositório.
     * @param professor Professor a ser salvo
     * @throws IllegalArgumentException se o professor for nulo
     */
    void salvar(Professor professor);

    /**
     * Remove um professor do repositório pelo seu ID.
     * @param id ID do professor a ser removido
     * @throws IllegalArgumentException se o ID for inválido ou o professor não existir
     */
    void remover(int id);

    /**
     * Atualiza os dados de um professor existente.
     * @param professor Professor com os dados atualizados
     * @throws IllegalArgumentException se o professor for nulo ou não existir no repositório
     */
    void alterar(Professor professor);

    /**
     * Lista todos os professores cadastrados.
     * @return Lista de todos os professores
     */
    List<Professor> listar();

    /**
     * Busca um professor pelo seu ID.
     * @param id ID do professor a ser buscado
     * @return Professor encontrado ou null se não existir
     * @throws IllegalArgumentException se o ID for inválido
     */
    Professor buscarPorId(int id);
}