package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import model.Category;

public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	private SessionFactory sessionFactory ; 
	
	public List<Category> findAll() {
		Session session = sessionFactory.openSession() ; 
		
		CriteriaBuilder builder = session.getCriteriaBuilder() ; 
		CriteriaQuery<Category> criteria = builder.createQuery(Category.class) ; 
		criteria.from(Category.class) ; 
		List<Category> categories = session.createQuery(criteria).getResultList() ;  
		session.close() ; 
		
		return categories ;
	}

	public Category findById(Long id) {
		Session session = sessionFactory.openSession() ; 
		Category category = session.get(Category.class, id);
        Hibernate.initialize(category.getGifs());
        session.close();
		
		return null;
	}

	public void save(Category category) {
		Session session = sessionFactory.openSession() ; 
		session.beginTransaction() ; 
		session.saveOrUpdate(category);
		session.getTransaction().commit();
		session.close(); 
	}

	public void delete(Category category) {
		Session session = sessionFactory.openSession() ; 
		session.beginTransaction() ; 
		session.delete(category);
		session.getTransaction().commit() ; 
		session.close();
	}
}
