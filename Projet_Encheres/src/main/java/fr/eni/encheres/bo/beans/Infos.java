package fr.eni.encheres.bo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Ce bean sers a afficher des infos en tête du site.
public class Infos implements Serializable {

	private List<String> liste;

	public Infos() {
		liste = new ArrayList<>();
	}

	public List<String> getListe() {
		return liste;
	}

	public void setListe(List<String> liste) {
		this.liste = liste;
	}

	public void addInfo(String contenu) {
		liste.add(contenu);
	}

	public boolean hasInfos() {
		return !liste.isEmpty();
	}

}
