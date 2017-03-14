import java.io.File;

import processing.core.PApplet;
import processing.data.XML;

public class Main extends PApplet{
	XML figuras;
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
		File archivito = new File("../data/bolitas.xml");
		
		
		
		System.out.println("Llorela");
	}
	
	private void crear(){
		figuras = parseXML("<figuras></figuras>");
		XML bola = parseXML("<bola></bola>");

		for (int i = 0; i < 5; i++) {
			bola.setInt("x", (int) random(10, width));
			bola.setInt("y", (int) random(10, height));
			bola.setInt("tam", (int) random(5, 20));
			
			figuras.addChild(bola);
		}
	}

	@Override
	public void draw() {
		background(255);
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
