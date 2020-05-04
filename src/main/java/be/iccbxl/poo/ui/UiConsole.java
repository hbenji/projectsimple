package be.iccbxl.poo.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;
import be.iccbxl.poo.metier.IMetier;

public class UiConsole implements IUi {

	private Scanner s = new Scanner(System.in);
	private List<Person> people = null;
	private IMetier metier;
	private List<Book> books = null;
	private String message;
	
	public UiConsole() {
		
	}
	
	public UiConsole(IMetier metier) {
		this.metier = metier;
	}
	
	public IMetier getMetier() {
		return this.metier;
	}
	
	public void setMetier(IMetier metier) {
		this.metier = metier;
	}
	
	
	public void run() {
		int cmd = 0;
		
		//var temp;
		int cpt = 0;
		int pos = 0;
		
		do {
			//Afficher un menu
				System.out.println("1 - Créer un membre et le sauver\n" 
				   +"2 - Créer un livre et le sauver\n"
				   +"3 - Emprunter un livre\n"
				   +"4 - Charger un fichier de membres (inutile vu qu'il y a un chargement des membres avant une action et l'option 'deserialiser')\n"
				   +"5 - Modifier un membre\n"
				   +"6 - Supprimer un membre\n"
				   +"7 - Charger un fichier de livres (inutile vu qu'il y a un chargement des livres avant une action et l'option 'deserialiser')\n"
				   +"8 - Modifier un livre\n"
				   +"9 - Supprimer un livre\n"
				   +"10 - Afficher les livres\n"
				   +"11 - Afficher les membres\n"
				   +"0 - Quitter");
			
			//Lire le choix de l'utilisateur
			cmd = s.nextInt();
			s.nextLine();
			
			//Traiter la commande de l'utilisateur						
			switch(cmd) {
				case 0:
					return;
				case 1 :
					//inscription membre
					System.out.print("Veuillez entrer le nom:");
					String nom = s.nextLine();
					UUID id = UUID.randomUUID();
										
					metier.register(new Person(id, nom));					
					message = "Inscription terminée.";
						
					break;
				case 2 : 
					//ajout livre
					System.out.print("Veuillez entrer le titre:");
					String titre = s.nextLine();
					
					System.out.print("Veuillez entrer l'auteur:");
					String auteur = s.nextLine();
					
					short nbPages;
					
					do {
						System.out.print("Veuillez entrer le nombre de pages:");
					
						try {
							nbPages = s.nextShort();
							s.nextLine();
							
							if(nbPages<=0) {
								System.out.println("Valeur incorrecte!");
							}
						} catch (InputMismatchException e) {
							s.nextLine();
							nbPages = 0;
							System.out.println("Valeur incorrecte!");
						}
					} while(nbPages<=0);
					
					System.out.print("Veuillez entrer la langue:");
					String langue = s.nextLine();
					
					UUID idBook = UUID.randomUUID();
					Book book = new Book(idBook,titre, auteur, nbPages, langue);
					
					metier.register(book);
					
					message = "Ajout livre OK.";

					break;
				case 3 : 
					//Emprunt livre
					
					//Récupérer la liste des livres
					books = metier.getBooks();
					//Récupérer la liste des membres
					people = metier.getMembres();
					
					//s'il y a au moins un livre et un membre
					if((books.size() !=0) && (people.size() != 0)) {
						
					
						//Afficher les livres
						this.printBooks();
						
						cpt = this.books.size();
						
						//Sélectionner un livre
											
						do {
							System.out.print("Veuillez choisir le livre à emprunter:");
							pos = s.nextInt();
						} while (pos<0 || pos>cpt);
						
						Book selectedBook = this.books.get(pos-1);
	
						System.out.println("Emprunt du livre : " + selectedBook.getTitle());					
						
						//Afficher les membres
						
						this.printMembers();				
						
						cpt = this.people.size();
						
						//Sélectionner un membre
						do {
							System.out.print("Veuillez choisir le membre qui emprunte:");
							pos = s.nextInt();
						} while (pos<0 || pos>cpt);
											
						Person borrower = this.people.get(pos-1);
						
						message = "Emprunté par : " + borrower.getName();
						
						borrower.borrows(selectedBook);
					}else {
						System.out.println("Malheureusement, l'emprunt n'est pas possible. (Pas de livre ou pas de membre)");
					}
					
					break;
				case 4 :
					//Charger un fichier de membres serialisés
					metier.getMembres();	
					message = "Chargement des membres du fichier OK.";
					break;
				case 5 : 
					//Modifier un membre + sauvegarde
					
					//Afficher les membres
					//Récupérer la liste des membres
					people = metier.getMembres();
					
					if(people.size() != 0) {
						
						
						this.printMembers();				
						
						cpt = this.people.size();
						
						//Sélectionner un membre
						do {
							System.out.print("Veuillez choisir le membre à modifier:");
							pos = s.nextInt();
						} while (pos<0 || pos>cpt);
						
						Person personUpdate = this.people.get(pos-1);	
						//proposer à l'utilisateur les champs à modifier ...
						personUpdate.setName("Testupdatenom");
						
						metier.update(personUpdate);
						message = "Modification de l'utilisateur OK.";
					}else {
						System.out.println("Il n'y a pas de membre.");
					}
					break;
				case 6:
					//Suppression d'un membre
					//Afficher les membres
					//Récupérer la liste des membres
					people = metier.getMembres();
					
					if(people.size() != 0) {
					
						this.printMembers();				
						
						cpt = this.people.size();
						
						//Sélectionner un membre
						do {
							System.out.print("Veuillez choisir le membre à modifier:");
							pos = s.nextInt();
						} while (pos<0 || pos>cpt);
						
						Person personDelete = this.people.get(pos-1);	
											
						metier.unregister(personDelete);
						
						message = "Suppression du membre OK.";
					}else {
						System.out.println("Il n'y a pas de membre.");
					}
					break;
				case 7 : 
					//Charger un fichier de livres
					metier.getBooks();
					message = "Chargement des livres du fichier OK.";
					break;
				case 8 : 
					//Modifier un livre + sauvegarde
					
					//Récupérer la liste des livres
					books = metier.getBooks();
					
					if(books.size() != 0) {
						//Afficher les livres
						this.printBooks();
						
						cpt = this.books.size();
						
						//Sélectionner un livre à modifier
											
						do {
							System.out.print("Veuillez choisir le livre à modifier:");
							pos = s.nextInt();
						} while (pos<0 || pos>cpt);
						
						Book bookUpdate = this.books.get(pos-1);
						
						//Proposer à l'utilisateur de modifier les champs du livre
						bookUpdate.setAuthor("testUpdateAuthor");
						bookUpdate.setTitle("Updatetitle");
						
						metier.update(bookUpdate);
						
						message = "Modification du livre OK.";
					}else {
						System.out.println("Il n'y a pas de livre.");
					}
					break;
				case 9 : 
					//Suppression d'un livre + sauvegarde
					
					//Récupérer la liste des livres
					books = metier.getBooks();
					
					if(books.size() != 0) {
						//Afficher les livres
						this.printBooks();
						
						cpt = this.books.size();
						
						//Sélectionner un livre à supprimer
											
						do {
							System.out.print("Veuillez choisir le livre à supprimer:");
							pos = s.nextInt();
						} while (pos<0 || pos>cpt);
						
						Book bookDelete = this.books.get(pos-1);
											
						metier.unregister(bookDelete);
						
						message = "Suppression du livre OK.";
					}else {
						System.out.println("Il n'y a pas de livre.");
					}
					break;
				case 10:
					//afficher les livres
					this.books = metier.getBooks();					
					this.printBooks();
					
					message = "Fin affichage des livres.";
					break;
				case 11:
					//afficher les membres
					this.people = metier.getMembres();
					this.printMembers();
					
					message = "Fin affichage des membres.";
					break;
				default:
					message = "Erreur !!! Veuillez recommencer et faire un choix.";
					break;
			}
			
			System.out.println(message);
			
		} while(cmd!=0);
	}
	
	private void printMembers() {		
		int i = 1;
		for(Person person:this.people) {
			System.out.println(i+") "+"Nom: "+person.getName() + "\tInscrit le: " + person.getRegistrationDate());
			i++;
		}
	}
	
	private void printBooks() {
		int i = 1;
		for(Book book:this.books) {
			System.out.println(i+") "+book.toString());
			i++;
		}
	}
}
