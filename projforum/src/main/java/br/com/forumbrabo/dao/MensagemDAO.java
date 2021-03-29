package br.com.forumbrabo.dao;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.com.forumbrabo.model.Mensagem;

@Named
@RequestScoped
public class MensagemDAO implements Serializable{
	
	public MensagemDAO() {
		
	}
	
	private static final long serialVersionUID = 1L;
	
	public String gravarMesagemDAO (Mensagem msg) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projforum");
		EntityManager em = emf.createEntityManager();
		try{
			
			em.getTransaction().begin();
			em.persist(msg);
			em.getTransaction().commit();
			em.clear();
			em.close();
			emf.close();
			
			return "home?faces-redirect=true";				
		}catch(IllegalArgumentException iae) {
			return null;
		}
	}
	
	public List<Mensagem>  retornaMensagensDAO() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projforum");
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("unchecked")
		List<Mensagem> msgs = em.createQuery("select m from mensagens m").getResultList();
			return msgs;
	}
	
}