import util.Livre;
// import Livre.java;

public class TestLivre {
	public static void main(String[] args) {
    Livre book1 = new Livre("LePetitPrince","SaintExupery");
    Livre book2 = new Livre("L'Etranger","AlbertCalmus");
    book1.setNbPages(93);
    book2.setNbPages(159);
    System.out.println("The Auteur of book 1 is" + book1.getAuteur());
    // System.out.println("Auteur of book 1: "+ book1.auteur);
    // System.out.println("Auteur of book 2: "+ book2.auteur);
    // System.out.println("Page of book 1: "+ book1.nbPages);
    // System.out.println("Page of book 2: "+ book2.nbPages);
    // int sumOfPages = book1.nbPages + book2.nbPages;
    // System.out.println("Page of 2 books: "+ sumOfPages);
  }
}
