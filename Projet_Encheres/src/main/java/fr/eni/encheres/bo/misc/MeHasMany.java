package fr.eni.encheres.bo.misc;

public interface MeHasMany<T> {
	void ajouter(T generique);
	void supprimer(T generique);
}
