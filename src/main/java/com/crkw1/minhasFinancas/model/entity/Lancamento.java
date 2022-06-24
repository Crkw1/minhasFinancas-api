package com.crkw1.minhasFinancas.model.entity;


import com.crkw1.minhasFinancas.model.enums.StatusLancamento;
import com.crkw1.minhasFinancas.model.enums.TipoLancamento;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter

@Entity
@Table(name = "lancamento", schema = "financas")
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lancamento)) return false;
        Lancamento that = (Lancamento) o;
        return getId().equals(that.getId()) && getDescricao().equals(that.getDescricao()) && getMes().equals(that.getMes()) && getAno().equals(that.getAno()) && getUsuario().equals(that.getUsuario()) && getValor().equals(that.getValor()) && getDataCadastro().equals(that.getDataCadastro()) && getTipo() == that.getTipo() && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) getId(), (Object) getDescricao(), getMes(), getAno(), getUsuario(), getValor(), getDataCadastro(), getTipo(), getStatus());
    }

}
