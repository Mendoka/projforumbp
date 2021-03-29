package br.com.forumbrabo.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Named
@RequestScoped
@Entity(name = "mensagens" )
public class Mensagem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String mensagem;
	private Date dataMsg = new Date();
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	Usuario usuario;
	
	public Mensagem() {
		
	}
	
	public Mensagem(String mensagem, Usuario usuario) {
		this.mensagem = mensagem;
		this.usuario = usuario;
	}
	
	public int getId() {
		return id;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getDataMsg() {
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm (dd/MM/yyyy)");
		String dataFormatada = formato.format(dataMsg);
		return dataFormatada;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		if (mensagem == null)
			return "";
		else {
			SimpleDateFormat formato = new SimpleDateFormat("HH:mm (dd/MM/yyyy)");
			String dataFormatada = formato.format(dataMsg);
			return usuario.getUsuario()+" disse: \""+mensagem+"\" às "+dataFormatada;
		}
	}
	
}