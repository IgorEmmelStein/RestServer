package exemplo.dao;

import exemplo.modelDomain.Comida; // Usamos a nova entidade Comida
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class ComidaDao { // Nome da classe adaptado

    EntityManagerFactory emf;
    EntityManager em;

    public ComidaDao() {
        this.emf = Persistence.createEntityManagerFactory("exemplo_RestWebServer_jar_v1PU");
        this.em = emf.createEntityManager(); // Corrigindo a atribuição, como discutido
    }

    public boolean inserir(Comida comida) { // Método para inserir Comida
        this.em.getTransaction().begin();
        this.em.persist(comida);
        this.em.getTransaction().commit();

        return true;
    }

    public boolean atualizar(Comida c) { // Implementação do PUT (Atualizar)
        try {
            this.em.getTransaction().begin();
            this.em.merge(c);
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
            return false;
        }
    }

    public Comida getComida(String nome) {
        try {
            Comida comida = em.createNamedQuery("Comida.buscaPorNome", Comida.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
            return comida;
        } catch (NoResultException e) {
            // Retorna null se não encontrar
            return null;
        } catch (Exception e) {
            // Trata outros erros de persistência
            e.printStackTrace();
            return null;
        }
    }

    public boolean excluir(String nome) {
        List<Comida> listaComidas;

        try {
            // 1. Usa getResultList para buscar TODAS as entidades com o mesmo nome
            listaComidas = em.createNamedQuery("Comida.buscaPorNome", Comida.class)
                    .setParameter("nome", nome)
                    .getResultList();

            if (listaComidas.isEmpty()) {
                return false; // Não encontrada
            }

            this.em.getTransaction().begin();

            // 2. Deleta todas as ocorrências
            for (Comida c : listaComidas) {
                // Garante que a entidade esteja gerenciada antes de remover
                Comida managedComida = em.contains(c) ? c : em.merge(c);
                this.em.remove(managedComida);
            }

            this.em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            // Captura qualquer erro (incluindo falha de transação)
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
            // Para debug em produção, descomente abaixo:
            // e.printStackTrace(); 
            return false;
        }
    }

    public ArrayList<Comida> listarComidas() { // Retorna a lista de Comidas
        List<Comida> lista = em.createNamedQuery("Comida.listarTodas", Comida.class)
                .getResultList();

        return new ArrayList<>(lista);
    }
}
