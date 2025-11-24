package exemplo.modelDomain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Pessoa.buscaPorEmail", query = "SELECT p FROM Pessoa p WHERE p.email = :email")
@NamedQuery(name = "Pessoa.listarTodos", query = "SELECT p FROM Pessoa p")

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String email;
    private int idade;
    private String endereco;

    public Pessoa() {

    }

    public Pessoa(int id, String nome, String email, int idade, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.endereco = endereco;
    }

    public Pessoa(int id) {
        this.id = id;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
