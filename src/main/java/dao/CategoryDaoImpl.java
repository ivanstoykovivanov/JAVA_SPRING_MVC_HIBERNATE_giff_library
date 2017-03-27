package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import model.Category;

public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	private SessionFactory sessionFactory ; 
	
	public List<Category> findAll() {
		//Open session 
		Session session = sessionFactory.openSession() ; 
		
		CriteriaBuilder builder = session.getCriteriaBuilder() ; 
		
		List<Category> categories = session.createCriteria(Category.class).list() ; 
		
		return null;
	}

	public Category findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Category category) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Category category) {
		// TODO Auto-generated method stub
		
	}

}
