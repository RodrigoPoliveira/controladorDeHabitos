package br.com.fiap.ControladorHabitos.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_habito")
public class Habito {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    public Habito() {}

    public Habito(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habito habito = (Habito) o;
        return Objects.equals(id, habito.id) && Objects.equals(nome, habito.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
