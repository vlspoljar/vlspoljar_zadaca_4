package foi.uzdiz.vlspoljar.mvc;

import java.io.IOException;
import java.util.Scanner;

public class Controller {

    public Model model;
    public View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void odabirKomande(){
        view.ispisOutput(model.getOutput());
        String izbor = "";
        do {
            view.ispisiIzbornik();
            Scanner scan = new Scanner(System.in);
            izbor = scan.nextLine();
            switch (izbor) {
                case "1":
                    view.ispisOutput("\n==========UNOS DATOTEKE==========\nUnesite naziv i putanju datoteke:");
                    String datoteka = scan.nextLine();
                    view.ispisOutput("Sadrzaj datoteke: " + datoteka);
                    model.ucitajDatoteku(datoteka);
                    view.ispisOutput(model.getOutput());
                    break;
                case "2":
                    view.ispisOutput("\n========SADRZAJ SPREMISTA========\n" );
                    model.prikaziSadrzajSpremista();
                    view.ispisOutput(model.getOutput());
                    break;
                case "3":
                    view.ispisOutput("\n==DJELOMICNO PRAZNJENJE SPREMISTA==\nUnesite n KB/elemenata koje zelite isprazniti:");
                    int n = scan.nextInt();
                    model.obrisiN(n);
                    view.ispisOutput(model.getOutput());
                    break;
                case "4":
                    view.ispisOutput("\n===POTPUNO PRAZNJENJE SPREMISTA===" );
                    model.isprazniSpremiste();
                    view.ispisOutput(model.getOutput());
                    break;
                case "Q":
                    view.ispisOutput("Izlaz iz programa!");
                    break;
                default:
                    view.ispisOutput("Krivi odabir komande!");
                    
            }
        } while(!izbor.equals("Q"));
    }
}
