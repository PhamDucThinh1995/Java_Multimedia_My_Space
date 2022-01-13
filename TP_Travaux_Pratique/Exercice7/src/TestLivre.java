// import Livre.java;
import util.Livre;
public class TestLivre {
	public static void main(String[] args) {
    util.Livre book1 = new Livre("LePetitPrince","SaintExupery");
    Livre book2 = new Livre("L'Etranger","AlbertCalmus");
    book1.setNbPages(93);
    book2.setNbPages(159);
//    System.out.println("Auteur of book 1: " + book1.getAuteur());
//    System.out.println("Auteur of book 2: "+ book2.getAuteur());
//    System.out.println("Page of book 1: "+ book1.getPages());
//    System.out.println("Page of book 2: "+ book2.getPages());
        book1.setPrix(9.5);
        book2.setPrix(12);
        book2.setPrix(15);
//        book1.afficheToi();
//        book2.afficheToi();
        System.out.println(book1);
        System.out.println(book2);
//    int sumOfPages = book1.getPages() + book2.getPages();
//    System.out.println("Total page of 2 books: "+ sumOfPages);
  }
}