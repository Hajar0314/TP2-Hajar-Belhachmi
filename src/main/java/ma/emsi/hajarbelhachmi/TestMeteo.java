package ma.emsi.hajarbelhachmi;

import ma.emsi.hajarbelhachmi.outil.meteo.MeteoTool;

/**
 * Teste l'outil météo sans passer par le LLM.
 */
public class TestMeteo {

    public static void main(String[] args) {
        MeteoTool outil = new MeteoTool();

        // Coordonnées de Paris : latitude 48.85, longitude 2.35
        System.out.println("Pluie aujourd'hui + 2 jours (3 jours) à Paris ? "
                + outil.isPluiePrevue(48.85, 2.35, 0, 3));
        System.out.println("Pluie demain (1 jour) à Paris ? "
                + outil.isPluiePrevue(48.85, 2.35, 1, 1));

        // Géolocalisation
        double[] coord = outil.getCoordonnees("Paris");
        System.out.println("Coordonnées de Paris : " + coord[0] + ", " + coord[1]);
        double[] coordRabat = outil.getCoordonnees("Rabat,Morocco");
        System.out.println("Coordonnées de Rabat : " + coordRabat[0] + ", " + coordRabat[1]);
    }
}