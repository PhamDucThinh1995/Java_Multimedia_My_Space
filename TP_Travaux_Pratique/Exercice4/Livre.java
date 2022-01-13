// package util;

public class Livre {
// Variables
public String titre, auteur;
public int nbPages;
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
}
