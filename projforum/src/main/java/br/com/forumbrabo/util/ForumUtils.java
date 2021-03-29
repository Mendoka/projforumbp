package br.com.forumbrabo.util;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.forumbrabo.dao.MensagemDAO;
import br.com.forumbrabo.dao.UsuarioDAO;
import br.com.forumbrabo.model.Mensagem;
import br.com.forumbrabo.model.Usuario;

@Named
@RequestScoped
public class ForumUtils implements Serializable{
	private static final long serialVersionUID = 1L;

	public String verficaLogin (String usuario, String senha) {
		UsuarioDAO userDao = new UsuarioDAO();
		Usuario user = userDao.buscarDadosUS(usuario, senha);

		if (user.getUsuario() == null || user.getSenha() == null) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Relação usuário e senha inválida", "Relação usuário e senha inválida");
			ctx.addMessage(null, facesMsg);
			return null; 
		}else {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			if (session != null) {
				session.setAttribute("usuario", user);
			}
			return "home?faces-redirect=true";
		}
	}

	public String logOff() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		return "login?faces-redirect=true";
	}

	public List<Mensagem>  retornaMensagens() {
		MensagemDAO msgDAO = new MensagemDAO();
		List<Mensagem> msgs = msgDAO.retornaMensagensDAO();
		if(msgs.isEmpty())
			return null;
		else
			return msgs;
	}

	public String gravarMensagem(String usuario, String senha, String mensagens) {
		String urlRecebida = "";
		UsuarioDAO usrDAO = new UsuarioDAO();
		Usuario usr = usrDAO.buscarDadosUS(usuario, senha);
		Mensagem msg = new Mensagem(mensagens, usr);
		MensagemDAO msgDAO = new MensagemDAO();
		urlRecebida = msgDAO.gravarMesagemDAO(msg);

		if(urlRecebida == null) {
			System.out.println("Deu algo errado!");
			return null;
		}
		else
			return urlRecebida;

	}

	public String chamaCadastro() {
		return "cadastro?faces-redirect=true";
	}

	public String gravarNovoUsuario(String usuario, String senha) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		FacesMessage facesMsg;

		if((usuario == null || usuario == "") || (senha == null || senha == "")) {
			if(usuario == null || usuario == "") {
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo usuário é obrigatório", "Campo usuário é obrigatório");
				ctx.addMessage(null, facesMsg);
			}
			if(senha == null || senha == "") {
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo senha é obrigatório", "Campo senha é obrigatório");
				ctx.addMessage(null, facesMsg);
			}
			return null;
		}

		UsuarioDAO usrDAO = new UsuarioDAO();
		Usuario usr = usrDAO.buscarUsuario(usuario);

		if (usr.getUsuario() != null) {
			facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este usuário já está em uso", "Relação usuário e senha inválida");
			ctx.addMessage(null, facesMsg);
			return null;
		}else {
			usr = new Usuario(usuario, senha);
			usrDAO.gravarUsuario(usr);
			return "sucesso?faces-redirect=true";
		}
	}
}