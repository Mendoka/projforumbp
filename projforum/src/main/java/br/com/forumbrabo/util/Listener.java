package br.com.forumbrabo.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.forumbrabo.model.Usuario;

public class Listener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String viewId = facesContext.getViewRoot().getViewId();

		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
		boolean paginaLogin = viewId.lastIndexOf("login") > -1;
		boolean paginaHome = viewId.lastIndexOf("home") > -1;
		boolean paginaCadastro = viewId.lastIndexOf("cadastro") > -1;
		
		if (existeUsuarioLogado() && (paginaLogin || paginaCadastro)) {
			nh.handleNavigation(facesContext, null, "home?faces-redirect=true");
		} else if (!existeUsuarioLogado() && paginaHome) {
			nh.handleNavigation(facesContext, null, "login?faces-redirect=true");
		}
	}

	public boolean existeUsuarioLogado() {
		return (((Usuario) getAtributoSessao("usuario")) != null);
	}

	public Object getAtributoSessao(String attributeName) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			return session.getAttribute(attributeName);
		}
		return null;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}

}