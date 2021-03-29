package br.com.forumbrabo.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import br.com.forumbrabo.model.Usuario;

@Named
@SessionScoped
@Entity(name = "usuarios")
public class Usuario implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
    private String usuario;
    private String senha;

	public Usuario() {
		
	}
	
    public Usuario(String usuario, String senha) {
		super();
		this.usuario = usuario;
		this.senha = senha;
	}
    
	public int getId() {
		return id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

/*	sobrescrevendo o método "toString()" para retornar uma string formatada a partir
	de um único Syso(objetoPessoa).*/
	
	@Override
	public String toString() {
		return id+" "+usuario;
	}
}