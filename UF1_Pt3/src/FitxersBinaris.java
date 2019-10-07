
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FitxersBinaris {

	// main
	public static void main(String[] args) {
		menu();
	}

	// metode amb el menu
	public static void menu() {
		int i = 0;
		while (i != 4) {
			Scanner lector = new Scanner(System.in);
			System.out.println("\n-----MENU FITXERS BINARIS-----");
			System.out.println("1 - Introduir dades");
			System.out.println("2 - Obtenir llistat complet");
			System.out.println("3 - Backup del fitxer a un altre fitxer");
			System.out.println("4 - Finalitzar");
			System.out.println("\nTria una opcio:");
			if (lector.hasNextInt()) {
				i = lector.nextInt();
				if (i > 0 && i < 5) {
					switch (i) {
					case 1:
						introduirDades();
						break;
					case 2:
						obtenirLlistat();
						break;
					case 3:
						backupFitxer();
						break;
					default:
						System.out.println("\nAdeu!");
						break;
					}
				} else {
					System.out.println("Error! Valor incorrecte.");
				}
			} else {
				System.out.println("Error! Ha de ser numeros.");
			}
		}
	}

	// metode per introduir les dades al fitxer
	public static void introduirDades() {
		Scanner lector = new Scanner(System.in);
		FileOutputStream fichero = null;
		DataOutputStream salida = null;
		String nom, cognom, residencia, valor;
		char sexe;
		int edat, suspensos;
		float ingressos;
		try {
			// creem el fitxer (ruta) i escriurem amb el DataOutputStream
			fichero = new FileOutputStream(new File("..\\UF1_Pt3\\src\\becadades.dat"), true);
			salida = new DataOutputStream(fichero);
			// nom
			do {
				System.out.println("\nNom:");
				nom = lector.next();
			} while (lletres(nom) == false);
			// cognom
			do {
				System.out.println("Cognom:");
				cognom = lector.next();
			} while (lletres(cognom) == false);
			// sexe
			do {
				System.out.println("Sexe (H | M):");
				sexe = lector.next().charAt(0);
			} while (sexe != 'H' && sexe != 'M');
			// edat
			do {
				System.out.println("Edat (20 - 60):");
				edat = lector.nextInt();
			} while (edat < 20 || edat > 60);
			// suspensos
			do {
				System.out.println("Suspensos curs anterior (0 - 4):");
				suspensos = lector.nextInt();
			} while (suspensos < 0 || suspensos > 4);
			// residencia familiar
			do {
				System.out.println("Residencia familiar (SI | NO):");
				residencia = lector.next();
			} while (!residencia.equalsIgnoreCase("SI") && !residencia.equalsIgnoreCase("NO"));
			// ingressos
			do {
				System.out.println("Ingressos anuals de la familia:");
				valor = lector.next();
			} while (numeros(valor) == false);
			ingressos = Float.valueOf(valor);
			// escribim cada dada al fitxer segons el tipus
			salida.writeUTF(nom);
			salida.writeUTF(cognom);
			salida.writeChar(sexe);
			salida.writeInt(edat);
			salida.writeInt(suspensos);
			salida.writeUTF(residencia);
			salida.writeFloat(ingressos);
			System.out.println("\nDades guardades al fitxer 'becadades.dat'");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (fichero != null)
					fichero.close();
				if (salida != null)
					salida.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// metode per llistar les dades del fitxer
	public static void obtenirLlistat() {
		FileInputStream fichero = null;
		DataInputStream entrada = null;
		String nom, cognom, residencia;
		char sexe;
		int edat, suspensos;
		float ingressos;
		try {
			// agafem el fitxer (ruta) i llegirem amb el DataInputStream
			fichero = new FileInputStream("..\\UF1_Pt3\\src\\becadades.dat");
			entrada = new DataInputStream(fichero);
			// guardem les dades en variables i les mostrem
			System.out.println("\n---Llistat complet---");
			while (true) {
				nom = entrada.readUTF();
				cognom = entrada.readUTF();
				sexe = entrada.readChar();
				edat = entrada.readInt();
				suspensos = entrada.readInt();
				residencia = entrada.readUTF();
				ingressos = entrada.readFloat();
				System.out.println("\n" + nom + "\n" + cognom + "\n" + sexe + "\n" + edat + "\n" + suspensos + "\n"
						+ residencia + "\n" + ingressos);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (EOFException e) {
			// System.out.println("Fin de fichero");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (fichero != null) {
					fichero.close();
				}
				if (entrada != null) {
					entrada.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// metode per fer una copia de seguretat (backup)
	public static void backupFitxer() {
		FileInputStream ficheroA = null;
		DataInputStream entrada = null;
		FileOutputStream ficheroB = null;
		DataOutputStream salida = null;
		String nom, cognom, residencia;
		char sexe;
		int edat, suspensos;
		float ingressos;
		// tractament del fitxer que volem copiar
		try {
			ficheroA = new FileInputStream("..\\UF1_Pt3\\src\\becadades.dat");
			entrada = new DataInputStream(ficheroA);
			// llegim el fitxer i guardem les dades en variables
			while (true) {
				nom = entrada.readUTF();
				cognom = entrada.readUTF();
				sexe = entrada.readChar();
				edat = entrada.readInt();
				suspensos = entrada.readInt();
				residencia = entrada.readUTF();
				ingressos = entrada.readFloat();
				// tractament del fitxer copiat
				try {
					ficheroB = new FileOutputStream(new File("..\\UF1_Pt3\\src\\becadadesBK.dat"), true);
					salida = new DataOutputStream(ficheroB);
					// escribim cada dada al fitxer nou amb les variables
					salida.writeUTF(nom);
					salida.writeUTF(cognom);
					salida.writeChar(sexe);
					salida.writeInt(edat);
					salida.writeInt(suspensos);
					salida.writeUTF(residencia);
					salida.writeFloat(ingressos);
					System.out.println("\nDades copiades al fitxer 'becadadesBK.dat'");
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					System.out.println(e.getMessage());
				} finally {
					try {
						if (ficheroB != null)
							ficheroB.close();
						if (salida != null)
							salida.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (EOFException e) {
			// System.out.println("Fin de fichero");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (ficheroA != null) {
					ficheroA.close();
				}
				if (entrada != null) {
					entrada.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// metodo boolean para comprobar que solo escribe letras
	public static boolean lletres(String text) {
		if (!text.matches("[a-zA-Z]*") || text == null || text.isEmpty()) {
			System.out.println("Error! Ha de ser lletres.\n");
			return false;
		} else
			return true;
	}

	// metodo boolean para comprobar que solo escribe numeros
	public static boolean numeros(String valor) {
		if (valor.matches("[-+]?[0-9]*\\.?[0-9]+"))
			return true;
		else {
			System.out.println("Error! Ha de ser numeros.\n");
			return false;
		}
	}
}
