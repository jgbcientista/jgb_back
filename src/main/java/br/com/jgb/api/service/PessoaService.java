package br.com.jgb.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.dto.PessoaDTO;
import br.com.jgb.api.entity.cliente.Cliente;
import br.com.jgb.api.entity.enums.TipoCadastroEnum;
import br.com.jgb.api.entity.fornecedor.Fornecedor;
import br.com.jgb.api.entity.rh.funcionario.Funcionario;
import br.com.jgb.api.entity.rh.pessoa.Pessoa;
import br.com.jgb.api.entity.rh.pessoa.usuario.Perfil;
import br.com.jgb.api.entity.rh.pessoa.usuario.Usuario;
import br.com.jgb.api.entity.rh.pessoa.usuario.UsuarioPerfil;
import br.com.jgb.api.exceptions.BussinessException;
import br.com.jgb.api.exceptions.DataBindingViolationException;
import br.com.jgb.api.exceptions.ObjectNotFoundException;
import br.com.jgb.api.filter.PessoaFilter;
import br.com.jgb.api.repository.ClienteRepository;
import br.com.jgb.api.repository.FornecedorRepository;
import br.com.jgb.api.repository.FuncaoRepository;
import br.com.jgb.api.repository.FuncionarioRepository;
import br.com.jgb.api.repository.PerfilRepository;
import br.com.jgb.api.repository.PessoaRepository;
import br.com.jgb.api.repository.UsuarioPerfilRepository;
import br.com.jgb.api.repository.UsuarioRepository;
import br.com.jgb.api.request.PessoaRequest;
import br.com.jgb.api.util.Util;

@Service
public class PessoaService {

	private static final String CAMPO_PERFIL_NAO_FOI_LOCALIZADO_NA_BASE_DE_DADOS = "O campo perfil não foi localizado na base de dados.";

	private static final String CAMPO_FUNCAO_NAO_FOI_INFORMADO_OU_FOI_INFORMADO_COM_ERRO = "O campo função não foi informado ou foi informado com erro.";

	private static final String CAMPO_PESSOA_NAO_FOI_INFORMADO_OU_FOI_INFORMADO_COM_ERRO = "O campo pessoa não foi informado ou foi informado com erro.";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public PessoaDTO findById(Long id) {
		PessoaDTO dto = new PessoaDTO();

		Pessoa pessoa = this.pessoaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Pesssoa não encontrada"));
		BeanUtils.copyProperties(pessoa, dto);
		dto.setLogin(pessoa.getUsuario().getLogin());
		dto.setSenha(pessoa.getUsuario().getSenha());
		dto.setDataNascimento(pessoa.getDataNascimento().toString());

		this.bCryptPasswordEncoder.encode(pessoa.getUsuario().getSenha());

		return dto;
	}

	@Transactional
	public PessoaDTO associar(PessoaRequest request) {
		
		var pessoa = pessoaRepository.findById(request.getIdPessoa());
		var funcao = funcaoRepository.findById(request.getIdPerfil());
		Perfil perfil = null;

		if (request.getTipo().equals(TipoCadastroEnum.FORNECEDOR.getSigla())) {
			perfil = perfilRepository.findByNome(TipoCadastroEnum.FORNECEDOR.getDescricao());
		} else if (request.getTipo().equals(TipoCadastroEnum.FUNCIONARIO.getSigla())) {
			perfil = perfilRepository.findByNome(TipoCadastroEnum.FUNCIONARIO.getDescricao());
		} else if (request.getTipo().equals(TipoCadastroEnum.CLIENTE.getSigla())) {
			perfil = perfilRepository.findByNome(TipoCadastroEnum.CLIENTE.getDescricao());
		}

		if (request.getIdPessoa() == null || pessoa.isEmpty()) {
			throw new BussinessException(CAMPO_PESSOA_NAO_FOI_INFORMADO_OU_FOI_INFORMADO_COM_ERRO);
		}
		if (request.getIdPerfil() == null || funcao.isEmpty()) {
			throw new BussinessException(CAMPO_FUNCAO_NAO_FOI_INFORMADO_OU_FOI_INFORMADO_COM_ERRO);
		}
		if (perfil == null) {
			throw new BussinessException(CAMPO_PERFIL_NAO_FOI_LOCALIZADO_NA_BASE_DE_DADOS);
		}

		if (request.getTipo().equals(TipoCadastroEnum.FUNCIONARIO.getSigla())) {

			var jaExiste = funcionarioRepository.findByPessoaId(pessoa.get().getId());
			if (jaExiste != null) {
				throw new BussinessException("Já existe um perfil de funcionário associado para essa pessoa.");
			} else {
				// atualiza tabela de TB_FUNCIONARIO
				Funcionario entity = new Funcionario();
				entity.setDataCriacao(LocalDateTime.now());
				entity.setPessoa(pessoa.get());
				entity.setFuncao(funcao.get());
				entity.setDataCriacao(LocalDateTime.now());
				entity.setSituacao(Boolean.TRUE);
				entity = this.funcionarioRepository.save(entity);
				BeanUtils.copyProperties(entity, request);
				request.setId(entity.getId());
			}
		} else if (request.getTipo().equals(TipoCadastroEnum.FORNECEDOR.getSigla())) {

			var jaExiste = fornecedorRepository.findFornecedorByPessoaId(pessoa.get().getId());

			if (jaExiste != null) {
				throw new BussinessException("Já existe um perfil de fornecedor associado para essa pessoa.");
			} else {
				// atualiza tabela de TB_FUNCIONARIO
				Fornecedor entity = new Fornecedor();
				entity.setDataCriacao(LocalDateTime.now());
				entity.setIdPessoa(pessoa.get().getId());
				entity.setDataCriacao(LocalDateTime.now());
				entity.setSituacao(Boolean.TRUE);
				entity = this.fornecedorRepository.save(entity);
				BeanUtils.copyProperties(entity, request);
				request.setId(entity.getId());
			}
		} else if (request.getTipo().equals(TipoCadastroEnum.CLIENTE.getSigla())) {

			var jaExiste = pessoaRepository.findPessoaId(pessoa.get().getId());

			if (jaExiste != null) {
				throw new BussinessException("Já existe um perfil de cliente associado para essa pessoa.");

			} else {
				// atualiza tabela de TB_FUNCIONARIO
				Cliente entity = new Cliente();
				entity.setDataCriacao(LocalDateTime.now());
				entity.setIdPessoa(pessoa.get().getId());
				entity.setDataCriacao(LocalDateTime.now());
				entity.setSituacao(Boolean.TRUE);
				entity = this.clienteRepository.save(entity);
				BeanUtils.copyProperties(entity, request);
				request.setId(entity.getId());
			}
		}

		UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
		usuarioPerfil.setDataCriacao(LocalDateTime.now());
		usuarioPerfil.setSituacao(Boolean.TRUE);
		usuarioPerfil.setPerfil(perfil);
		usuarioPerfil.setUsuario(pessoa.get().getUsuario());
		usuarioPerfilRepository.save(usuarioPerfil);

		PessoaDTO response = new PessoaDTO();
		BeanUtils.copyProperties(pessoa, response);
		
		return response;
	}

	public List<PessoaDTO> findAllCliente() {
		List<PessoaDTO> lista = new ArrayList<>();

		var lis = this.pessoaRepository.findAllByCliente();

		lis.forEach(item -> {
			PessoaDTO pessoa = new PessoaDTO();
			BeanUtils.copyProperties(item, pessoa);
			pessoa.setLogin(item.getUsuario().getLogin());
			pessoa.setSenha(item.getUsuario().getSenha());
			pessoa.setDataNascimento(item.getDataNascimento().toString());
			lista.add(pessoa);
		});

		return lista;
	}

	public List<PessoaDTO> findAllFornecedores() {
		List<PessoaDTO> lista = new ArrayList<>();

		var lis = this.pessoaRepository.findAllByFornecedor();

		lis.forEach(item -> {
			PessoaDTO pessoa = new PessoaDTO();
			BeanUtils.copyProperties(item, pessoa);
			pessoa.setLogin(item.getUsuario().getLogin());
			pessoa.setSenha(item.getUsuario().getSenha());
			pessoa.setDataNascimento(item.getDataNascimento().toString());
			lista.add(pessoa);
		});

		return lista;
	}

	public List<PessoaDTO> findAll() {
		List<PessoaDTO> lista = new ArrayList<>();

		var lis = this.pessoaRepository.findAll();

		lis.forEach(item -> {
			PessoaDTO pessoa = new PessoaDTO();
			BeanUtils.copyProperties(item, pessoa);
			pessoa.setLogin(item.getUsuario().getLogin());
			pessoa.setSenha(item.getUsuario().getSenha());
			pessoa.setDataNascimento(item.getDataNascimento().toString());
			lista.add(pessoa);
		});

		return lista;
	}

	public List<PessoaDTO> findFilter(String tipo, String codigo, String nome, String cpf) {
		
		if (cpf != null && !Util.isNull(cpf) && !Util.isCPF(cpf)) {
			throw new BussinessException("Por favor, informe um CPF válido.");
		}
		
		var filter = new PessoaFilter();

		if (!Util.isNull(codigo)) {
			filter.setCodigo(Integer.valueOf(codigo));
		}
		filter.setNome(nome);
		filter.setCpf(cpf);

		List<PessoaDTO> listaDTO = new ArrayList<>();
		List<Pessoa> listaEntidades = new ArrayList<>();

		if (!Util.isNull(filter.getCpf()) && Util.isNull(codigo) && Util.isNull(filter.getNome())) {
			listaEntidades = this.pessoaRepository.findFilterCpf(filter.getCpf());
		} else if (Util.isNull(filter.getCpf()) && !Util.isNull(codigo) && Util.isNull(filter.getNome())) {
			listaEntidades = this.pessoaRepository.findFilterCodigo(filter.getCodigo());
		} else if (Util.isNull(filter.getCpf()) && Util.isNull(codigo) && !Util.isNull(filter.getNome())) {
			listaEntidades = this.pessoaRepository.findFilterByNome(filter.getNome());
		} else if (!Util.isNull(filter.getCpf()) && !Util.isNull(codigo) && !Util.isNull(filter.getNome())) {
			listaEntidades = this.pessoaRepository.findFilterByNomeAndCpfAndCodigo(filter.getNome(), filter.getCpf(),
					filter.getCodigo());
		} else if (!Util.isNull(filter.getCpf()) && Util.isNull(codigo) && !Util.isNull(filter.getNome())) {
			listaEntidades = this.pessoaRepository.findFilterByNomeAndCpf(filter.getNome(), filter.getCpf());
		} else if (!Util.isNull(filter.getCpf()) && !Util.isNull(codigo) && Util.isNull(filter.getNome())) {
			listaEntidades = this.pessoaRepository.findFilterByCpfAndCodigo(filter.getCpf(), filter.getCodigo());
		} else if (Util.isNull(filter.getCpf()) && !Util.isNull(filter.getNome()) && !Util.isNull(codigo)) {
			listaEntidades = this.pessoaRepository.findFilterByNomeAndCodigo(filter.getNome(), filter.getCodigo());
		} else {
			if (tipo.equals("F")) {
				listaEntidades = this.pessoaRepository.findAllByFornecedor();
			} else if (tipo.equals("C")) {
				listaEntidades = this.pessoaRepository.findAllByCliente();
			} else {
				return findAll();
			}
		}
		
		if(listaEntidades.isEmpty()) {
			throw new BussinessException("Nenhum registro encontrado na base de dados com os filtros informado.");
		}
		
		
		listaEntidades.forEach(item -> {
			PessoaDTO pessoa = new PessoaDTO();
			BeanUtils.copyProperties(item, pessoa);
			pessoa.setLogin(item.getUsuario().getLogin());
			pessoa.setSenha(item.getUsuario().getSenha());
			pessoa.setDataNascimento(item.getDataNascimento().toString());
			listaDTO.add(pessoa);
		});

		return listaDTO;
	}

	@Transactional
	public PessoaDTO findPessoaByCPF(String cpf) {
		var pessoaDTO = new PessoaDTO();

		if (cpf == null) {
			throw new BussinessException("O campo CPF não foi informado.");
		} 
		
		if (!Util.isCPF(cpf)) {
			throw new BussinessException("Por favor, informe um CPF válido.");
		}
		
		var entity = pessoaRepository.findFilterCpf(cpf);

		if (!entity.isEmpty()) {
			BeanUtils.copyProperties(entity.get(0), pessoaDTO);
		} else {
			throw new BussinessException(
					"O campo CPF de número " + cpf + " informado não está cadastrado em nossa base de dados.");
		}

		return pessoaDTO;
	}

	@Transactional
	public PessoaDTO criar(PessoaDTO obj) {
		Pessoa entity = new Pessoa();
		BeanUtils.copyProperties(obj, entity);
		entity.setId(null);
		entity.setDataCriacao(LocalDateTime.now());

		entity.setDataNascimento(LocalDate.parse(obj.getDataNascimento()));
		entity.setSituacao(obj.getSituacao());
		entity.setTipoPessoa(obj.getTipoPessoa());
		entity.setCpf(obj.getCpf());
		entity = this.pessoaRepository.save(entity);

		if (obj.getLogin() != null) {
			Usuario usuario = new Usuario();
			usuario.setSituacao(obj.getSituacao());
			usuario.setDataCriacao(entity.getDataCriacao());
			usuario.setLogin(obj.getLogin());
			usuario.setSenha(this.bCryptPasswordEncoder.encode(obj.getSenha()));
			usuario.setCpf(obj.getCpf());
			usuario = usuarioRepository.save(usuario);

			entity.setUsuario(usuario);
			this.pessoaRepository.save(entity);
		}

		BeanUtils.copyProperties(entity, obj);
		return obj;
	}

	public Boolean validarSenha(String cpf, String senha) {
		Usuario usuario = this.usuarioRepository.findByCpf(cpf);
		if (usuario.getSenha().equals(senha)) {
			return true;
		}

		return false;
	}

	@Transactional
	public PessoaDTO update(PessoaDTO obj) {
		var entity = pessoaRepository.findById(obj.getId());

		if (obj.getCpf() != null) {

			Usuario entityUsuario = usuarioRepository.findByCpf(obj.getCpf());

			if (entityUsuario == null) {
				throw new BussinessException("Usuário não foi encontrado na base de dados.");
			}

			if (!bCryptPasswordEncoder.matches(obj.getSenha(), entityUsuario.getSenha())) {
				throw new BussinessException("Por favor, informe a senha cadastrada na base de dados para realizar a alteração.");
			}

			if (entityUsuario.getLogin().equals(obj.getLogin())) {
				entityUsuario.setLogin(obj.getLogin());
				entityUsuario.setSenha(this.bCryptPasswordEncoder.encode(obj.getSenha()));
				entityUsuario.setDataAtualizacao(LocalDateTime.now());
				usuarioRepository.save(entityUsuario);
			} else {
				// alteracao do login
				if (usuarioRepository.findByLogin(obj.getLogin()) != null) {
					throw new BussinessException(
							"Já existe um usuário cadastrado na base de dados com esse login, por favor, informe um outro.");
				} else {
					entityUsuario.setLogin(obj.getLogin());
					entityUsuario.setSenha(this.bCryptPasswordEncoder.encode(obj.getSenha()));
					entityUsuario.setDataAtualizacao(LocalDateTime.now());
					usuarioRepository.save(entityUsuario);
				}

			}
		}
		if (entity.isPresent()) {
			entity.get().setNome(obj.getNome());
			entity.get().setTipoPessoa(obj.getTipoPessoa());
			entity.get().setSituacao(obj.getSituacao());
			entity.get().setDataNascimento(Util.parseStringTODate(obj.getDataNascimento()));
			entity.get().setCpf(obj.getCpf());
			entity.get().setRg(obj.getRg());
			entity.get().setCtps(obj.getCtps());
			entity.get().setTelefone(obj.getTelefone());
			entity.get().setEmail(obj.getEmail());

			var retorno = this.pessoaRepository.save(entity.get());
			BeanUtils.copyProperties(retorno, obj);
		}
		return obj;
	}

	public void delete(Long id) {
		Optional<Pessoa> entity = this.pessoaRepository.findById(id);
		try {
			if (entity.isPresent()) {
				entity.get().setSituacao(Boolean.FALSE);
				this.pessoaRepository.save(entity.get());
			}
			this.pessoaRepository.delete(entity.get());
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}
}
