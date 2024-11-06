package br.com.fiap.ControladorHabitos.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_mentorado")
public class Mentorado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    public Mentorado(){}

    public Mentorado(Integer id, String nome) {
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
        Mentorado mentorado = (Mentorado) o;
        return Objects.equals(id, mentorado.id) && Objects.equals(nome, mentorado.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
