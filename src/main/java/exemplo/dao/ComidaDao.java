package exemplo.dao;

import exemplo.modelDomain.Comida;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class ComidaDao {

    EntityManagerFactory emf;
    EntityManager em;

    public ComidaDao() {
        this.emf = Persistence.createEntityManagerFactory("exemplo_RestWebServer_jar_v1PU");
        this.em = emf.createEntityManager();
    }

    public boolean inserir(Comida comida) {
        this.em.getTransaction().begin();
        this.em.persist(comida);
        this.em.getTransaction().commit();

        return true;
    }

    public boolean atualizar(Comida c) {
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

            return null;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public boolean excluir(String nome) {
        List<Comida> listaComidas;

        try {

            listaComidas = em.createNamedQuery("Comida.buscaPorNome", Comida.class)
                    .setParameter("nome", nome)
                    .getResultList();

            if (listaComidas.isEmpty()) {
                return false;
            }

            this.em.getTransaction().begin();

            for (Comida c : listaComidas) {

                Comida managedComida = em.contains(c) ? c : em.merge(c);
                this.em.remove(managedComida);
            }

            this.em.getTransaction().commit();
            return true;

        } catch (Exception e) {

            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }

            return false;
        }
    }

    public ArrayList<Comida> listarComidas() {
        List<Comida> lista = em.createNamedQuery("Comida.listarTodas", Comida.class)
                .getResultList();

        return new ArrayList<>(lista);
    }
}
