package fr.eni.encheres.bo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Ce bean peut afficher des erreurs si besoin.
public class Erreurs implements Serializable {
    private List<String> liste;

    public Erreurs() {
        liste = new ArrayList<>();
    }

    public List<String> getListe() {
        return liste;
    }

    public void setListe(List<String> liste) {
        this.liste = liste;
    }

    public void addErreur(String contenu) {
        liste.add(contenu);
    }

    public boolean hasErrors() {
        return !liste.isEmpty();
    }
}
