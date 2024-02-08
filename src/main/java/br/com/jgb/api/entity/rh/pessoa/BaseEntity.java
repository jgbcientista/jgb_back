package br.com.jgb.api.entity.rh.pessoa;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    @Schema(description = "Data de criação da entidade")
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Schema(description = "Data de atualização da entidade")
    private LocalDateTime dataAtualizacao;
    
    @Column(name = "SITUACAO", nullable = false)
	private Boolean situacao;
   

}