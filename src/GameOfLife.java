import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameOfLife {

	/**
	 * Implementation of the Game of Life
	 *
	 *
	 * @author Christian Meyenburg
	 */

	private boolean[][] calculationArea;
	private int rows;
	private int columns;
	private static int iter = 0;


	// method to launch execution of gol
	public static void execute(int rows, int columns, int iterationCount) {
		GameOfLife s = new GameOfLife(rows, columns);
		s.fillCalculationArea();
		s.drawImagesOfCalculationArea(false);

		for (int i = 0; i < iterationCount; i++) {
			s.setDeadOrAlive();
			s.drawImagesOfCalculationArea(false);
		}		
	}
	
	public BufferedImage drawImagesOfCalculationArea(boolean Textausgabe) {
		BufferedImage img = new BufferedImage(rows, columns, BufferedImage.TYPE_INT_ARGB);
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				if (Textausgabe == true)
					System.out.print("[" + calculationArea[row][column] + "] ");
				if (calculationArea[row][column] == true)
					img.setRGB(row, column, Color.BLACK.getRGB());
				else
					img.setRGB(row, column, Color.WHITE.getRGB());
			}

		}
		ImageUtil.writeSingleImageToFile(img, iter);
		System.out.println("Iteration No.:" + iter);
		iter++;
		return img;
	}



	public GameOfLife(int rows, int columns) {
		this.calculationArea = new boolean[rows][columns];
		this.rows = calculationArea.length;
		this.columns = calculationArea[0].length;
	}

	public void fillCalculationArea() {
		/**
		 * Definiert das Rechengebiet und f�llt es zuf�llig mit lebendigen Zellen
		 * rel.H�ufigkeit f�r lebende Zellen = 0,3
		 */
		Random dice = new Random();
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				int Zufallszahl;
				Zufallszahl = 1 + dice.nextInt(10);
				if (Zufallszahl < 4) {
					calculationArea[row][column] = true;
				}
			}
		}
	}

	public GameOfLife setDeadOrAlive() {
		/**
		 * Wendet die Regeln auf alle Gitterzellen eines existierenden Rechengebietes an
		 * gibt ein neues Rechengebiet mit derselben Breite und H�he zur�ck, welches die
		 * ver�nderte Situation enth�lt
		 */
		int result = 0;
		GameOfLife s2 = new GameOfLife(rows, columns);
		// Es wirt das UrsprungRechengebiet ausgelesen, die Regeln angewendet und das
		// Rechengebiet des neu erzeugten Objektes bearbeitet
		// Durch die arbeit auf dem neuen Objekt s2 beeinflussen die �nderungen auf dem
		// neuen Objekt nicht die alte Zellgeneration
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				result = pruefeNachbarn(row, column);
				// Regel 1: Eine tote Zelle mit genau drei lebenden Nachbarn wird in der
				// Folgegeneration neu geboren.
				if ((calculationArea[row][column] == false) && (result == 3)) {
					s2.calculationArea[row][column] = true;
				}
				// Regel 2: Lebende Zellen mit weniger als zwei lebenden Nachbarn sterben in der
				// Folgegeneration an Einsamkeit.
				if ((calculationArea[row][column] == true) && (result < 2)) {
					s2.calculationArea[row][column] = false;
				}
				// Regel 3: Eine lebende Zelle mit zwei oder drei lebenden Nachbarn bleibt in
				// der Folgegeneration am Leben.
				if ((calculationArea[row][column] == true) && (result == 2 || result == 3)) {
					s2.calculationArea[row][column] = true;
				}
				// Regel 4:Lebende Zellen mit mehr als drei lebenden Nachbarn sterben in der
				// Folgegeneration an �berbev�lkerung.
				if ((calculationArea[row][column] == true) && (result > 3)) {
					s2.calculationArea[row][column] = false;
				}
			}
		}
		// Das urspr�ngliche Rechengebiet wird durch das neu bearbeitete RG ersetzt
		calculationArea = s2.calculationArea;
		return s2;
	}

	public int pruefeNachbarn(int row, int column) {
		/**
		 * pr�fe zustand von Nachbarn des Index Zeile; Spalte; liefert anzahl der
		 * Nachbarn mit True
		 */
		int quantityTrue = 0;
		for (int Z = row - 1; Z <= row + 1; Z++) {
			for (int S = column - 1; S <= column + 1; S++) {
				if (Z != row || S != column) {
					if (Z >= rows || S >= columns || Z < 0 || S < 0) {
						// Tue nichts um nicht den definitionsbereich des 2D-Arrays zu verlassen
					} else {
						if (calculationArea[Z][S] == true)
							quantityTrue++;
					}
				} else {

				}
			}

		}
//		System.out.println("[" + Zeile +"; "+ Spalte + "] "   +	"Anzahl True: " + AnzahlTrue);
		return quantityTrue;
	}

}
