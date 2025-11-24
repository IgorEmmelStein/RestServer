/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.dao;

import exemplo.modelDomain.Pessoa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PessoaDao {

    EntityManagerFactory emf;
    EntityManager em;

    public PessoaDao() {
        this.emf = Persistence.createEntityManagerFactory("exemplo_RestWebServer_jar_v1PU");
        this.em = emf.createEntityManager();
    }

    public boolean inserir(Pessoa pessoa) {
        this.em.getTransaction().begin();
        this.em.persist(pessoa);
        this.em.getTransaction().commit();

        return true;
    }

    public boolean atualizar(Pessoa p) {
        //Implementar
        return true;
    }

    public Pessoa getPessoa(String email) {
        Pessoa pessoa = em.createNamedQuery("Pessoa.buscaPorEmail", Pessoa.class)
                .setParameter("email", email)
                .getSingleResult();

        return pessoa;
    }

    public boolean excluir(String email) {
        Pessoa pessoa = em.createNamedQuery("Pessoa.buscaPorEmail", Pessoa.class)
                .setParameter("email", email)
                .getSingleResult();

        if (pessoa != null) {
            this.em.getTransaction().begin();
            this.em.remove(pessoa);
            this.em.getTransaction().commit();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Pessoa> listarPessoas() {
        List<Pessoa> lista = em.createNamedQuery("Pessoa.listarTodos", Pessoa.class)
                .getResultList();

        return new ArrayList<>(lista);
    }

}
