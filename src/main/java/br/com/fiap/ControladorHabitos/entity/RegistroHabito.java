package br.com.fiap.ControladorHabitos.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_registro_habito")
public class RegistroHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "mentorado_id")
    private Mentorado mentorado;

    @ManyToOne
    @JoinColumn(name = "planner_id")
    private Planner planner;

    @ManyToOne
    @JoinColumn(name = "habito_id")
    private Habito habito;

    public RegistroHabito() {}

    public RegistroHabito(Integer id, LocalDate data, Mentorado mentorado, Planner planner, Habito habito) {
        this.id = id;
        this.data = data;
        this.mentorado = mentorado;
        this.planner = planner;
        this.habito = habito;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Mentorado getMentorado() {
        return mentorado;
    }

    public void setMentorado(Mentorado mentorado) {
        this.mentorado = mentorado;
    }

    public Planner getPlanner() {
        return planner;
    }

    public void setPlanner(Planner planner) {
        this.planner = planner;
    }

    public Habito getHabito() {
        return habito;
    }

    public void setHabito(Habito habito) {
        this.habito = habito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroHabito that = (RegistroHabito) o;
        return Objects.equals(id, that.id) && Objects.equals(data, that.data) && Objects.equals(mentorado, that.mentorado) && Objects.equals(planner, that.planner) && Objects.equals(habito, that.habito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, mentorado, planner, habito);
    }
}
