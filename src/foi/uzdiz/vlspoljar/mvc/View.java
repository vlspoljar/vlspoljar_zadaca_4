package foi.uzdiz.vlspoljar.mvc;

public class View {

    public Model model;

    public View(Model model) {
        this.model = model;
    }

    public void ispisiIzbornik() {
        System.out.println("\n=============IZBORNIK=============");
        System.out.println("1 - Unos datoteke");
        System.out.println("2 - Prikaz sadrzaja spremista");
        System.out.println("3 - Djelomicno praznjenje spremista");
        System.out.println("4 - Potpuno praznjenje spremista");
        System.out.println("Q - Kraj programa");
        System.out.println("Vas odabir: ");
    }

    public void ispisOutput(String out) {
        System.out.println(out);
    }
}
