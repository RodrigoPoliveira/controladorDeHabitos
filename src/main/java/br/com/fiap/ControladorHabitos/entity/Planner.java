package br.com.fiap.ControladorHabitos.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_planner")
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "planner_habito",
            joinColumns = @JoinColumn(name = "planner_id"),
            inverseJoinColumns = @JoinColumn(name = "habito_id")
    )
    private List<Habito> habitos;

    public Planner() {}

    public Planner(Integer id, String nome, List<Habito> habitos) {
        this.id = id;
        this.nome = nome;
        this.habitos = habitos;
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

    public List<Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(List<Habito> habitos) {
        this.habitos = habitos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planner planner = (Planner) o;
        return Objects.equals(id, planner.id) && Objects.equals(nome, planner.nome) && Objects.equals(habitos, planner.habitos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, habitos);
    }
}
