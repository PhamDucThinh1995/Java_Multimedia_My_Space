package util;

public class Livre {
// Variables
private String titre, auteur;
private int nbPages;
// Constructeur
public Livre(String unTitre,String unAuteur) {
auteur = unAuteur;
titre = unTitre;
}
// Accesseur
public String getAuteur() {
return auteur;
}
public String getTitre() {
return titre;
}
public int getPages() {
return nbPages;
}
// Modificateur
public void setNbPages(int nb) {
if(nb>0)
{nbPages = nb;}
else
{System.out.println("The number of page of book is invalid");}
}
public void setAuteur(String aut) {
auteur = aut;
}
public void setTitre(String tit) {
titre = tit;
}
public static void main(String[] args) {
    Livre book1 = new Livre("LePetitPrince","SaintExupery");
    Livre book2 = new Livre("L'Etranger","AlbertCalmus");
    book1.setNbPages(93);
    book2.setNbPages(159);
    System.out.println("Auteur of book 1: "+ book1.auteur);
    System.out.println("Auteur of book 2: "+ book2.auteur);
    System.out.println("Page of book 1: "+ book1.nbPages);
    System.out.println("Page of book 2: "+ book2.nbPages);
    int sumOfPages = book1.nbPages + book2.nbPages;
    System.out.println("Page of 2 books: "+ sumOfPages);
  }
}
