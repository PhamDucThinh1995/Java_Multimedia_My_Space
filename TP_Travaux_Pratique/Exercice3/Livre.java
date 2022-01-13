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
// Modificateur
public void setNbPages(int nb) {
nbPages = nb;
}
public static void main(String[] args) {
    Livre book1 = new Livre("LePetitPrince","SaintExupery");
    Livre book2 = new Livre("L'Etranger","AlbertCalmus");
    System.out.println("Auteur of book 1: "+ book1.auteur);
    System.out.println("Auteur of book 2: "+ book2.auteur);
  }
}