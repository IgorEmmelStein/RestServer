package exemplo.dao;

import exemplo.modelDomain.Comida; // Usamos a nova entidade Comida
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    public Comida getComida(String nome) { // Busca por Nome, não por email
        // Usa a NamedQuery criada em Comida.java
        Comida comida = em.createNamedQuery("Comida.buscaPorNome", Comida.class)
                .setParameter("nome", nome)
                .getSingleResult();

        return comida;
    }

    public boolean excluir(String nome) {
        Comida comida = em.createNamedQuery("Comida.buscaPorNome", Comida.class)
                .setParameter("nome", nome)
                .getSingleResult();

        if (comida != null) {
            this.em.getTransaction().begin();
            this.em.remove(comida);
            this.em.getTransaction().commit();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Comida> listarComidas() { // Retorna a lista de Comidas
        List<Comida> lista = em.createNamedQuery("Comida.listarTodas", Comida.class)
                .getResultList();

        return new ArrayList<>(lista);
    }
}
