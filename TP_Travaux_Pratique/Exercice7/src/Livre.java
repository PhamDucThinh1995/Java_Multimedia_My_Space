package util;

public class Livre {
// Variables
private String titre, auteur;
private int nbPages;
private double prix=-1;
private boolean isPrixFix=false;
private boolean isPrixFixed=false;
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
public double getPrix(){
    return prix;
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
public void setPrix(double prix1){
    if(isPrixFix==false){
    prix = prix1;
    isPrixFix = true;
    }
    else
    {
        isPrixFixed=true;
    }
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
    if (prix<0){
        return "The auteur is: "+ auteur +"," +"\n"+ "The title is: " + titre + ","+"\n"+ "The number of pages is: " + nbPages + ","+"\n"+ "The price does not input";
    }
    else{
        return "The auteur is: "+ auteur +"," +"\n"+ "The title is: " + titre + ","+"\n"+ "The number of pages is: " + nbPages + ","+"\n"+ "The price is: " + prix
                +"\n"+ "Status of price fix: " + isPrixFixed;
    }
    }
}
