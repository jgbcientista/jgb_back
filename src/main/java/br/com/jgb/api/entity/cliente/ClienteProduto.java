package br.com.jgb.api.entity.cliente;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.jgb.api.entity.fornecedor.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ClienteProduto.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteProduto{
	public static final String TABLE_NAME = "TB_PRODUTO_CLIENTE";
	
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "produto_id") 
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Produto produto;
    
    @ManyToOne 
    @JoinColumn(name = "cliente_id") 
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cliente cliente;
    
}
