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
public void afficheToi()
{
    System.out.println("Method AfficheToi() in exercice 6");
    System.out.println("Auteur of book: "+ auteur);
    System.out.println("Titre of book: "+ titre);
    System.out.println("Page of book: "+ nbPages);
}
public String toString(){
    System.out.println("This is the book description using toString() method");
    return "The auteur is: "+ auteur +"," +"\n"+ "The title is: " + titre + ","+"\n"+ "The number of pages is: " + nbPages + ",";
    }
}
