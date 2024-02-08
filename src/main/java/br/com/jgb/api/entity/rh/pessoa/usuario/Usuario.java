package br.com.jgb.api.entity.rh.pessoa.usuario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.jgb.api.entity.enums.PerfilEnum;
import br.com.jgb.api.entity.rh.funcionario.Tarefa;
import br.com.jgb.api.entity.rh.pessoa.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Usuario.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends BaseEntity{

    public static final String TABLE_NAME = "TB_USUARIO";

    @Column(name = "LOGIN", length = 100, nullable = false, unique = true)
    @Size(min = 2, max = 100)
    @NotBlank
    private String login;

    @Column(name = "SENHA", length = 60, nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    @Size(min = 4, max = 256)
    @NotBlank
    private String senha;
    
    @Column(name = "CPF", length = 20, nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank
    private String cpf;

    @OneToMany(mappedBy = "usuario")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Tarefa> tarefas = new ArrayList<Tarefa>();

    @Column(name = "PERFIL_ID", nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TB_USUARIO_PERFIL")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Long> perfis = new HashSet<>();

    public Set<PerfilEnum> getProfiles() {
        return this.perfis.stream().map(x -> PerfilEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(PerfilEnum profileEnum) {
        this.perfis.add(profileEnum.getCode());
    }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public Set<Long> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Long> perfis) {
		this.perfis = perfis;
	}
	
	
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return that.getId() != null && Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
    

}
