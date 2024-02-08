package br.com.jgb.api.entity.fornecedor;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = FornecedorProduto.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FornecedorProduto{
	public static final String TABLE_NAME = "TB_PRODUTO_FORNECEDOR";
	
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "produto_id") 
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Produto produto;
    
    @ManyToOne 
    @JoinColumn(name = "fornecedor_id") 
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Fornecedor fornecedor;
    
}
