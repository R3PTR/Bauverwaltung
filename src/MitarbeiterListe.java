import javax.naming.Name;
import java.util.ArrayList;

public class MitarbeiterListe
{
    public ArrayList<Mitarbeiter> MitarbeiterList;
    public static void main(String[] args)
    {
        ArrayList<Mitarbeiter> MitarbeiterList = new ArrayList<Mitarbeiter>();

        Mitarbeiter mitarbeiter1 = new Mitarbeiter("Janis", "Nürnberg", "Bauleiter", "13.04.2019", 1, 60000);

        MitarbeiterList.add(mitarbeiter1);



        /*.add();
        MitarbeiterList.add(Name);

        MitarbeiterList.set(0, "Tomas");

        //.get()
        System.out.println(MitarbeiterList.get(0));

        //.size()
        System.out.println(MitarbeiterList.size());

        for (int i = 0; i < MitarbeiterList.size(); i++)
        {
            System.out.println(MitarbeiterList.get(i));
        }

*/

    }
    public void hinzufuegen(String Vor, String Nac, String Ber, String Ein, int Per, int Jah)
    {
        Mitarbeiter zeit = new Mitarbeiter(Vor, Nac, Ber, Ein, Per, Jah);
        MitarbeiterList.add(zeit);

    }
}
