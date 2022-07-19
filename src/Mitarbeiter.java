public class Mitarbeiter {

    public String vorname;
    public String nachname;
    public String berufsbezeichnung;
    public String einstellungsdatum;
    public int personalnummer;
    public int jahresgehalt;

    public Mitarbeiter(){};  //default konstruktor
    
    public Mitarbeiter (String Vo, String Na, String Be, String Ei, int Pe,  int Ja)

    {
        this.vorname = Vo;
        this.nachname = Na;
        this.berufsbezeichnung = Be;
        this.einstellungsdatum = Ei;
        this.personalnummer = Pe;
        this.jahresgehalt = Ja;
    }

    public void setVorname(String vorname) {
        vorname = vorname;
    }
    public void setNachname(String nachname) {
        nachname = nachname;
    }
    public void setPersonalnummer(int personalnummer) {
        personalnummer = personalnummer;
    }
    public void setBerufsbezeichnung(String berufsbezeichnung) {
        berufsbezeichnung = berufsbezeichnung;
    }
    public void setEinstellungsdatum(String einstellungsdatum) {
        einstellungsdatum = einstellungsdatum;
    }
    public void setJahresgehalt(int jahresgehalt) {
        jahresgehalt = jahresgehalt;
    }

    public String getVorname() {
        return vorname;
    }
    public String getNachname() {
        return nachname;
    }
    public int getPersonalnummer() {
        return personalnummer;
    }
    public String getBerufsbezeichnung() {
        return berufsbezeichnung;
    }
    public String getEinstellungsdatum() {
        return einstellungsdatum;
    }
    public int getJahresgehalt() {
        return jahresgehalt;
    }
}
