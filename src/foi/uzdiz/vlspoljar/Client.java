package foi.uzdiz.vlspoljar;

import foi.uzdiz.vlspoljar.mvc.Controller;
import foi.uzdiz.vlspoljar.mvc.Model;
import foi.uzdiz.vlspoljar.mvc.View;
import foi.uzdiz.vlspoljar.settings.ProgramSettings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    public static void main(String[] args) {
        ProgramSettings programSettings = new ProgramSettings();
        String argumenti = "";
        for (int i = 0; i < args.length; i++) {
            argumenti += args[i] + " ";
        }
        String regex = "^([0-9]+)\\s([0-9]+)\\s([A-Za-zčćšđž0-9\\s\"':_.\\\\/]+)\\s([0-9]+)\\s(((KB)\\s(NS|NK))|((NS|NK)))\\s([A-Za-zčćšđž0-9\\s\"':_.\\\\/]+)(\\s(clean))?$";
        Matcher m = Pattern.compile(regex).matcher(argumenti);
        if (m.matches()) {

            programSettings.setMaxKolicinaPodataka(Integer.parseInt(m.group(1)));
            programSettings.setInterval(Integer.parseInt(m.group(2)));
            programSettings.setDirektorijSpremista(m.group(3));
            programSettings.setKapacitetSpremista(Integer.parseInt(m.group(4)));

            if (m.group(7)!=null) {
                programSettings.setTipSpremista(true);
                programSettings.setStrategijaIzbacivanja(m.group(8));
            } else {
                programSettings.setTipSpremista(false);
                programSettings.setStrategijaIzbacivanja(m.group(10));
            }

            String[] groups = m.group(11).split("\\s+");
            String dnevnik = "";
            if (groups.length > 1) {
                for (int i = 0; i < groups.length - 1; i++) {
                    dnevnik += groups[i] + " ";
                }
                programSettings.setDatotekaDnevnika(dnevnik);
            } else {
                programSettings.setDatotekaDnevnika(m.group(11));
            }

            if (groups[groups.length - 1].equals("clean")) {
                programSettings.setPraznjenje(true);
            } else {
                programSettings.setPraznjenje(false);
            }
            
            Model model = new Model();
            model.setProgramSettings(programSettings);
            model.pripremiSpremiste();
            View view = new View(model);
            Controller controller = new Controller(model, view);
            controller.odabirKomande();

        } else {
            System.out.println("Krivi format!");
        }
    }

}
