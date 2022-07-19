public class Auftrag {

    public String auftraggeber;
    public String adresse;
    public int[]aufpostleitzahl = new int[4];
    public String aufbeschreibung;
    char[]startdatum = new char[9];
    char[]enddatum = new char [9];

    public String Auftraggeber;
    public String ort;
    public String Adresse;
    public String Beschreibung;
    public String Startdatum;
    public String Enddatum;

    public void Auftrag (String Auftraggeber, String OrtundAdresse, String Beschreibung, String Startdatum, String Enddatum) {
        this.Auftraggeber = Auftraggeber;
        this.ort = ort;
        this.Beschreibung = Beschreibung;
        this.Startdatum = Startdatum;
        this.Enddatum = Enddatum;
    }

    public void setAuftraggeber(String auftraggeber) {

        Auftraggeber = auftraggeber;
    }
    public void setOrtundAdresse(String ortundAdresse) {

        Adresse = ort;
    }
    public void setBeschreibung(String beschreibung) {

        Beschreibung = beschreibung;
    }
    public void setStartdatum(String startdatum) {

        Startdatum = startdatum;
    }
    public void setEnddatum(String enddatum) {

        Enddatum = enddatum;
    }

    public String getAuftraggeber() {

        return Auftraggeber;
    }
    public String getOrt() {

        return ort;
    }
    public String getBeschreibung() {

        return Beschreibung;
    }
}
