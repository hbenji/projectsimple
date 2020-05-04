package be.iccbxl.poo.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;

public class DaoXMLFile implements IDao {
	
	private String filename_people;
	private String filename_books;
	private ArrayList<Person> people = new ArrayList<Person>();;
	private ArrayList<Book> books = new ArrayList<Book>();;
	
	public DaoXMLFile() {
		
	}
	
	public DaoXMLFile(String filename_people, String filename_books) {
		this.filename_people = filename_people;
		this.filename_books = filename_books;
	}
	
	public String getFilename_people() {
		return this.filename_people;
	}
	
	public void setFilename_people(String filename_people) {
		this.filename_people = filename_people;
	}
	
	public String getFilename_books() {
		return this.filename_books;
	}
	
	public void setFilename_books(String filename_books) {
		this.filename_books = filename_books;
	}
	
	
	public List<Person> findAllPeople() {
		System.out.println("Lecture fichier: "+this.filename_people);
		
		File f = new File(this.filename_people);					
		
		//test si fichier existe
		if(f.exists()) {
			System.out.println("le fichier existe");
			try {
				//pas besoin de lire le fichier ni de buffer
				
				//Concertir la chaine XML en Objet
				XStream xs = new XStream();					
				xs.alias("person", Person.class);
					
				this.people = (ArrayList<Person>)xs.fromXML(f);
				
			}catch(Exception e) {
				System.out.println("Exception: "+e.getMessage());
			}
		}else {
			System.out.println("le fichier n'existe pas");
		}
		
		return this.people;
	}
	
	public Person find(Person p) {
		return findByIdPerson(p.getId());		
	}
	
	public Person findByIdPerson(UUID id) {
		findAllPeople();
		
		Person p = null;
		Iterator<Person> it = people.iterator();
		
		while(it.hasNext()) {
			p = it.next();
			
			if(p.getId().equals(id)) {
				return p;
			}
		}
		
		return null;
	}

	public List<Person> findByPerson(String property, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deletePerson(UUID id) {
		findAllPeople();
		
		Person p = null;
		Iterator<Person> it = people.iterator();
		
		while(it.hasNext()) {
			p = it.next();
			
			if(p.getId().equals(id)) {
				it.remove();
			}
		}
		
		//Sauvegarde
		this.savePeople();
	}

	public void delete(Person p) {
		deletePerson(p.getId());
	}

	public void update(Person p) {
		findAllPeople();
		
		boolean trouve = false;
		Person currentPerson = null;
		Iterator<Person> it = people.iterator();
		
		while(it.hasNext()) {
			currentPerson = it.next();
			
			if(currentPerson.getId().equals(p.getId())) {
				it.remove();
				trouve = true;
			}
		}
		
		if(trouve) {
			people.add(p);
		}
		
		//Sauvegarde
		this.savePeople();
	}

	public void save(Person p) {
		findAllPeople();
		
		boolean trouve = false;
		Person currentPerson = null;
		Iterator<Person> it = people.iterator();
		
		while(it.hasNext()) {
			currentPerson = it.next();
			
			if(currentPerson.getId().equals(p.getId())) {
				update(p);
				return;
			}
		}
		
		if(!trouve) {
			people.add(p);
		}
		
		//Sauvegarde
		this.savePeople();
	}
	
	public boolean savePeople() {
		File f = new File(this.filename_people);
		FileWriter fw = null;
		
		try {
			try {
				if(!f.exists()) {
					f.createNewFile();
				}
				
				fw = new FileWriter(f);
				
				XStream xs = new XStream(new DomDriver());
				
				//Configuration du parser XML
				xs.alias("person", Person.class);
				
				//Conversion en XML
				String xml = xs.toXML(people);
				
				//Sauvegarder dans un fichier texte
				fw.write(xml);
			} finally {
				fw.close();
			}
		} catch(IOException e) {
			
		}
		
		return false;
	}

	/*BOOKS*/
		
	public List<Book> findAllBooks(){

		System.out.println("Lecture fichier: "+this.filename_books);
		
		File f = new File(this.filename_books);					
		//List<Book> list = new ArrayList<Book>();		
				
		//
		if(f.exists()) {
			System.out.println("le fichier existe");
			try {
				//pas besoin de lire le fichier ni de buffer
				
				//Concertir la chaine XML en Objet
				XStream xs = new XStream();					
				xs.alias("book", Book.class);
					
				this.books = (ArrayList<Book>)xs.fromXML(f);
				
			}catch(Exception e) {
				System.out.println("Exception: "+e.getMessage());
			}
		}else {
			System.out.println("le fichier n'existe pas");
		}
		
		return this.books;
	}
	
	public Book find(Book b) {
		return findByIdBook(b.getId());
	}

	public Book findByIdBook(UUID id) {
		findAllBooks();
		
		Book b = null;
		Iterator<Book> it = books.iterator();
		
		while(it.hasNext()) {
			b = it.next();
			
			if(b.getId().equals(id)) {
				return b;
			}
		}
		
		return null;
	}

	public List<Book> findByBook(String property, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteBook(UUID id) {
		findAllBooks();
		
		Book b = null;
		Iterator<Book> it = books.iterator();
		
		while(it.hasNext()) {
			b = it.next();
			
			if(b.getId().equals(id)) {
				it.remove();
			}
		}
		
		//Sauvegarde
		this.saveBooks();
		
	}

	public void delete(Book b) {
		deleteBook(b.getId());
	}

	public void update(Book b) {
		findAllBooks();
		
		boolean trouve = false;
		Book currentBook = null;
		Iterator<Book> it = books.iterator();
		
		while(it.hasNext()) {
			currentBook = it.next();
			
			if(currentBook.getId().equals(b.getId())) {
				it.remove();
				trouve = true;
				System.out.println("trouvé");
			}
		}
		
		if(trouve) {
			books.add(b);
			System.out.println("ajout");
		}
		
		//Sauvegarde
		this.saveBooks();
	}

	public void save(Book b) {
		findAllBooks();
		
		boolean trouve = false;
		Book currentBook = null;
		Iterator<Book> it = books.iterator();
		
		while(it.hasNext()) {
			currentBook = it.next();
			
			if(currentBook.getId().equals(b.getId())) {
				update(b);
				return;
			}
		}
		
		if(!trouve) {
			books.add(b);
		}
		
		//Sauvegarde
		this.saveBooks();
	}
	
	public boolean saveBooks() {
		File f = new File(this.filename_books);
		FileWriter fw = null;
		
		try {
			try {
				if(!f.exists()) {
					f.createNewFile();
				}
				
				fw = new FileWriter(f);
				
				XStream xs = new XStream(new DomDriver());
				
				//Configuration du parser XML
				xs.alias("book", Book.class);
				
				//Conversion en XML
				String xml = xs.toXML(books);
				
				//Sauvegarder dans un fichier texte
				fw.write(xml);

			} finally {
				fw.close();
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}

}
