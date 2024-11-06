package br.com.fiap.ControladorHabitos.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    @OneToOne
    @JoinColumn(name = "planner_id", referencedColumnName = "id")
    private Planner planner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "turma_id")
    private List<Mentorado> mentorados;

    public Turma() {}

    public Turma(Integer id, String nome, LocalDate dataInicio, LocalDate dataFim, Planner planner, List<Mentorado> mentorados) {
        this.id = id;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.planner = planner;
        this.mentorados = mentorados;
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Planner getPlanner() {
        return planner;
    }

    public void setPlanner(Planner planner) {
        this.planner = planner;
    }

    public List<Mentorado> getMentorados() {
        return mentorados;
    }

    public void setMentorados(List<Mentorado> mentorados) {
        this.mentorados = mentorados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turma turma = (Turma) o;
        return Objects.equals(id, turma.id) && Objects.equals(nome, turma.nome) && Objects.equals(dataInicio, turma.dataInicio) && Objects.equals(dataFim, turma.dataFim) && Objects.equals(planner, turma.planner) && Objects.equals(mentorados, turma.mentorados);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, dataInicio, dataFim, planner, mentorados);
    }
}
