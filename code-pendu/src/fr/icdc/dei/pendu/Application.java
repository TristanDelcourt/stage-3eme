package fr.icdc.dei.pendu;

import java.io.IOException;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) {
		System.out.println("Bienveue sur le jeu de Pendu codé par Tristan Delcourt");

		String nomJoueurUn;
		String nomJoueurDeux;
		String motADeviner;
		String[] motEnCoursDeResolution;
		String lettre;
		int nbErreurs = 0;

		// On demande au joueur 1 son nom
		System.out.println("Joueur 1, tapez votre nom");
		Scanner in = new Scanner(System.in);
		nomJoueurUn = in.nextLine();

		// On demande au joueur 2 son nom
		System.out.println("Joueur 2, tapez votre nom");
		nomJoueurDeux = in.nextLine();

		System.out.println(nomJoueurUn + " écrivez votre mot deviner. " + nomJoueurDeux + " ne regardez pas!");
		motADeviner = in.nextLine().toUpperCase();
		clearScreen();

		// On calcul le mot en cours de résolution
		motEnCoursDeResolution = new String[motADeviner.length()];
		for (int i = 0; i < motADeviner.length(); i++) {
			motEnCoursDeResolution[i] = "_";
		}
		System.out.println(getMotEnCoursDeResolutionToDisplay(motEnCoursDeResolution));

		do {

			// Le joueur 2 propose une lettre
			System.out.println(nomJoueurDeux + " tape une lettre!");
			lettre = in.nextLine().toUpperCase();
			clearScreen();
			EtapesPendu.afficherEtape(nbErreurs);

			if (motADeviner.contains(lettre)) {
				String tmp = motADeviner;
				int position = tmp.indexOf(lettre);
				while (position >= 0) {
					motEnCoursDeResolution[position] = lettre;
					tmp = tmp.replaceFirst(lettre, "|");
					position = tmp.indexOf(lettre);
				}

				System.out.println(getMotEnCoursDeResolutionToDisplay(motEnCoursDeResolution));

			} else {
				System.out.println("Erreur il n'y pas la lettre " + lettre + " dans le mot à deviner");
				nbErreurs = nbErreurs + 1;
				EtapesPendu.afficherEtape(nbErreurs);
				System.out.println(getMotEnCoursDeResolutionToDisplay(motEnCoursDeResolution));
			}

		} while (!motEstDevine(motADeviner, motEnCoursDeResolution) && nbErreursPasDepasse(nbErreurs));

		if (nbErreursPasDepasse(nbErreurs)) {
			System.out.println("Bravo! " + nomJoueurDeux + " Tu as gagné!");
		} else {
			System.out.println(nomJoueurDeux + " t'es nul t'as perdu. " + "Le mot était: " + motADeviner
					+ " et tu as trouvé: " + getMotEnCoursDeResolutionToDisplay(motEnCoursDeResolution));
		}

	}

	private static String getMotEnCoursDeResolutionToDisplay(String[] motEnCoursDeResolution) {
		String resultat = "";
		for (int i = 0; i < motEnCoursDeResolution.length; i++) {
			resultat += motEnCoursDeResolution[i] + " ";
		}
		return resultat;
	}

	private static String getMotEnCoursDeResolutionToString(String[] motEnCoursDeResolution) {
		String resultat = "";
		for (int i = 0; i < motEnCoursDeResolution.length; i++) {
			resultat += motEnCoursDeResolution[i];
		}
		return resultat;
	}

	/**
	 * Indique si le mot est deviné
	 * 
	 * @param motADeviner
	 * @param motEnCoursDeResolution
	 * @return
	 */
	private static boolean motEstDevine(String motADeviner, String[] motEnCoursDeResolution) {
		return getMotEnCoursDeResolutionToString(motEnCoursDeResolution).equals(motADeviner);
	}

	private static boolean nbErreursPasDepasse(int nbErreurs) {
		return nbErreurs <= 7;
	}

	private static void clearScreen() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}

}
