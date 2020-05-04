package be.iccbxl.poo.metier;

import java.util.ArrayList;
import java.util.List;

import be.iccbxl.poo.dao.DaoXMLFile;
import be.iccbxl.poo.dao.IDao;
import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;

public class Metier implements IMetier {
	
	private IDao dao = new DaoXMLFile();
	//private List<Person> people = new ArrayList<Person>();
	
	public Metier() {
		
	}
	
	public Metier(IDao dao) {
		this.dao = dao;
	}
	
	public IDao getDao() {
		return this.dao;
	}
	
	public void setDao(IDao dao) {
		this.dao = dao;
	}

	public int computeRemainingDays(Book b) {		
		Book book = dao.find(b);	
		return book.computeRemainingDays();
	}

	public List<Person> getMembres() {
		return dao.findAllPeople();
	}

	public void register(Person p) {
		dao.save(p);
	}

	public void remove(Person p) {
		dao.delete(p);
	}

	public void update(Person p) {
		dao.update(p);
	}

	public void unregister(Person p) {
		dao.delete(p);
	}

	public List<Book> getBooks() {
		return dao.findAllBooks();
	}

	public void register(Book b) {
		dao.save(b);
	}

	public void update(Book b) {
		dao.update(b);
	}

	public void unregister(Book b) {
		dao.delete(b);
	}
	
}
