import java.io.File;

import processing.core.PApplet;
import processing.data.XML;

public class Main extends PApplet {
	XML figuras;
	File archivito = new File("../data/bolitas.xml");

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		if (!archivito.exists() && !archivito.isDirectory()) {
			figuras = crear();
		} else {
			figuras = cargar();
			System.out.println("Llorela");
		}
	}

	private XML crear() {
		XML temp = null;
		temp = parseXML("<figuras></figuras>");
		XML bola = parseXML("<bola></bola>");

		for (int i = 0; i < 5; i++) {
			bola.setInt("x", (int) random(10, width));
			bola.setInt("y", (int) random(10, height));
			bola.setInt("tam", (int) random(5, 20));

			temp.addChild(bola);

			saveXML(temp, "../data/bolitas.xml");
		}
		return temp;
	}

	private XML cargar() {
		XML temp = null;
		try {
			temp = new XML(archivito);
			saveXML(temp, "../data/bolitas.xml");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return temp;
	}

	@Override
	public void draw() {
		background(255);
		figuras = cargar();
		XML[] bolitas = figuras.getChildren("bola");
		for (int i = 0; i < bolitas.length; i++) {
			int x = bolitas[i].getInt("x");
			int y = bolitas[i].getInt("y");
			int tam = bolitas[i].getInt("tam");
			fill(255, 0, 0);
			noStroke();
			ellipse(x, y, tam, tam);
		}
	}

	public void mousePressed() {
		figuras = cargar();
		XML[] bolitas = figuras.getChildren("bola");
		XML bolitaEncontrada = null;
		for (int i = 0; i < bolitas.length && bolitaEncontrada == null; i++) {
			int x = bolitas[i].getInt("x");
			int y = bolitas[i].getInt("y");
			int tam = bolitas[i].getInt("tam");

			if (dist(x, y, mouseX, mouseY) < tam / 2) {
				bolitaEncontrada = bolitas[i];
			}
		}

		if (bolitaEncontrada != null) {
			figuras.removeChild(bolitaEncontrada);
		} else {
			int tam = (int) random(5, 20);
			XML bola = parseXML("<bola></bola>");
			bola.setInt("x", mouseX);
			bola.setInt("y", mouseY);
			bola.setInt("tam", tam);

			figuras.addChild(bola);
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		saveXML(figuras, "../data/bolitas.xml");
		super.stop();
	}

}
