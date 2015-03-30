package foi.uzdiz.vlspoljar.mvc;

import foi.uzdiz.vlspoljar.caching.CacheImplementation;
import foi.uzdiz.vlspoljar.caching.Resource;
import foi.uzdiz.vlspoljar.evictor.EvictionImplementation;
import foi.uzdiz.vlspoljar.settings.ProgramSettings;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    public ProgramSettings programSettings;
    public String output;
    public String out;
    public CacheImplementation cacheImplementation;
    public EvictionImplementation evictionImplementation;
    public String dnevnik = "";

    public Model() {
        cacheImplementation = new CacheImplementation();
        evictionImplementation = new EvictionImplementation();
    }

    public ProgramSettings getProgramSettings() {
        return programSettings;
    }

    public void setProgramSettings(ProgramSettings programSettings) {
        this.programSettings = programSettings;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void pripremiSpremiste() {
        out = "";
        File file = new File(programSettings.direktorijSpremista);
        if (file.exists() && file.isDirectory()) {
            out += "Ucitano postojece spremiste: " + programSettings.direktorijSpremista + "\n";
            if (programSettings.praznjenje) {
                for (File f : file.listFiles()) {
                    f.delete();
                }
                cacheImplementation.resourceList.clear();
                out += "Inicijalno praznjenje spremista...\nSpremiste ispraznjeno";
            }
        } else {
            file.mkdirs();
            out += "Kreirano novo spremiste na: " + programSettings.direktorijSpremista;
        }
        setOutput(out);
    }

    public void zapisiUDnevnik() {
        File file = new File(programSettings.datotekaDnevnika);
        try {
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(dnevnik);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajDatoteku(String datoteka) {
        out = "";
        dnevnik = "";
        File fileDatoteka = new File(datoteka);
        if (fileDatoteka.isFile()) {
            File fileSpremiste = new File(programSettings.direktorijSpremista);
            if (cacheImplementation.getRecource(datoteka) == null) {
                if (programSettings.tipSpremista) {//KB
                    if (programSettings.kapacitetSpremista > fileDatoteka.length() / 1024) {
                        if ((getVelicinaSpremista(fileSpremiste) + fileDatoteka.length()) / 1024 < programSettings.kapacitetSpremista) {
                            Resource resource = new Resource(fileDatoteka.getName(), programSettings.direktorijSpremista + "\\" + fileDatoteka.getName(), datoteka, System.currentTimeMillis(), 0, System.currentTimeMillis(), fileDatoteka.length());
                            cacheImplementation.addRecource(resource);
                            dnevnik += "\nDodan novi resurs u spremiste: " + resource.naziv + "\tVrijeme: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(resource.getVrijemeSpremanja()));
                            try {
                                kopirajDatoteku(fileDatoteka, new File(programSettings.direktorijSpremista + "\\" + fileDatoteka.getName()));
                            } catch (IOException ex) {
                                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            out += prikaziSadrzajDatoteke(resource.getPutanjaCaching());
                        } else {
                            if (programSettings.strategijaIzbacivanja.equals("NS")) {
                                dnevnik += evictionImplementation.izbacujKBpoNS(cacheImplementation, fileSpremiste, programSettings.kapacitetSpremista, fileDatoteka.length());
                                Resource resource = new Resource(fileDatoteka.getName(), programSettings.direktorijSpremista + "\\" + fileDatoteka.getName(), datoteka, System.currentTimeMillis(), 0, System.currentTimeMillis(), fileDatoteka.length());
                                cacheImplementation.addRecource(resource);
                                dnevnik += "\nDodan novi resurs u spremiste: " + resource.naziv + "\tVrijeme: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(resource.getVrijemeSpremanja()));
                                try {
                                    kopirajDatoteku(fileDatoteka, new File(programSettings.direktorijSpremista + "\\" + fileDatoteka.getName()));
                                } catch (IOException ex) {
                                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                out += prikaziSadrzajDatoteke(resource.getPutanjaCaching());
                            } else {
                                dnevnik += evictionImplementation.izbacujKBpoNK(cacheImplementation, fileSpremiste, programSettings.kapacitetSpremista, fileDatoteka.length());
                                Resource resource = new Resource(fileDatoteka.getName(), programSettings.direktorijSpremista + "\\" + fileDatoteka.getName(), datoteka, System.currentTimeMillis(), 0, System.currentTimeMillis(), fileDatoteka.length());
                                cacheImplementation.addRecource(resource);
                                dnevnik += "\nDodan novi resurs u spremiste: " + resource.naziv + "\tVrijeme: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(resource.getVrijemeSpremanja()));
                                try {
                                    kopirajDatoteku(fileDatoteka, new File(programSettings.direktorijSpremista + "\\" + fileDatoteka.getName()));
                                } catch (IOException ex) {
                                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                out += prikaziSadrzajDatoteke(resource.getPutanjaCaching());
                            }
                        }
                    } else {
                        out += "Datoteka je prevelika za spremiste!";
                    }
                } else {
                    if (fileSpremiste.listFiles().length < programSettings.kapacitetSpremista) {
                        Resource resource = new Resource(fileDatoteka.getName(), programSettings.direktorijSpremista + "\\" + fileDatoteka.getName(), datoteka, System.currentTimeMillis(), 0, System.currentTimeMillis(), fileDatoteka.length());
                        cacheImplementation.addRecource(resource);
                        dnevnik += "\nDodan novi resurs u spremiste: " + resource.naziv + "\tVrijeme: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(resource.getVrijemeSpremanja()));
                        try {
                            kopirajDatoteku(fileDatoteka, new File(programSettings.direktorijSpremista + "\\" + fileDatoteka.getName()));
                        } catch (IOException ex) {
                            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        out += prikaziSadrzajDatoteke(resource.getPutanjaCaching());
                    } else {
                        if (programSettings.strategijaIzbacivanja.equals("NS")) {
                            dnevnik += evictionImplementation.izbacujElementepoNS(cacheImplementation, programSettings.direktorijSpremista, programSettings.kapacitetSpremista);
                            Resource resource = new Resource(fileDatoteka.getName(), programSettings.direktorijSpremista + "\\" + fileDatoteka.getName(), datoteka, System.currentTimeMillis(), 0, System.currentTimeMillis(), fileDatoteka.length());
                            cacheImplementation.addRecource(resource);
                            dnevnik += "\nDodan novi resurs u spremiste: " + resource.naziv + "\tVrijeme: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(resource.getVrijemeSpremanja()));
                            try {
                                kopirajDatoteku(fileDatoteka, new File(programSettings.direktorijSpremista + "\\" + fileDatoteka.getName()));
                            } catch (IOException ex) {
                                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            out += prikaziSadrzajDatoteke(resource.getPutanjaCaching());
                        } else {
                            dnevnik += evictionImplementation.izbacujElementepoNK(cacheImplementation, programSettings.direktorijSpremista, programSettings.kapacitetSpremista);
                            Resource resource = new Resource(fileDatoteka.getName(), programSettings.direktorijSpremista + "\\" + fileDatoteka.getName(), datoteka, System.currentTimeMillis(), 0, System.currentTimeMillis(), fileDatoteka.length());
                            cacheImplementation.addRecource(resource);
                            dnevnik += "\nDodan novi resurs u spremiste: " + resource.naziv + "\tVrijeme: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(resource.getVrijemeSpremanja()));
                            try {
                                kopirajDatoteku(fileDatoteka, new File(programSettings.direktorijSpremista + "\\" + fileDatoteka.getName()));
                            } catch (IOException ex) {
                                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            out += prikaziSadrzajDatoteke(resource.getPutanjaCaching());
                        }
                    }
                }
            } else {
                cacheImplementation.getRecource(datoteka).brojKoristenja++;
                cacheImplementation.getRecource(datoteka).zadnjeKoristenje = System.currentTimeMillis();
                out += prikaziSadrzajDatoteke(cacheImplementation.getRecource(datoteka).getPutanjaCaching());
                dnevnik += "\nKoristenje datoteke: " + cacheImplementation.getRecource(datoteka).getNaziv() + "\tVrijeme: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
            }
        } else {
            out += "Nije ucitana datoteka!";
        }
        zapisiUDnevnik();
        setOutput(out);
    }

    public long getVelicinaSpremista(File fileSpremiste) {
        long length = 0;
        for (File f : fileSpremiste.listFiles()) {
            if (f.isFile()) {
                length += f.length();
            }
        }
        return length;
    }

    public String prikaziSadrzajDatoteke(String datoteka) {
        out = "";
        try {
            byte[] data = Files.readAllBytes(Paths.get(datoteka));
            for (int i = 0; i < programSettings.maxKolicinaPodataka; i++) {
                out += (char) data[i];
            }
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    private static void kopirajDatoteku(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public void obrisiN(int n) {
        out = "";
        File file = new File(programSettings.direktorijSpremista);
        if (programSettings.tipSpremista) {
            if (n < getVelicinaSpremista(file) / 1024) {
                if (programSettings.strategijaIzbacivanja.equals("NS")) {
                    evictionImplementation.izbaciNKBpoNS(cacheImplementation, n);
                    out += "Provedeno djelomicno praznjenje spremista...";
                } else {
                    evictionImplementation.izbaciNKBpoNK(cacheImplementation, n);
                    out += "Provedeno djelomicno praznjenje spremista...";
                }
            } else {
                out += "Broj n je veci ili jednak od trenutnog kapaciteta (" + getVelicinaSpremista(file) / 1024 + ") spremista!";
            }
        } else {
            if (n < file.listFiles().length) {
                if (programSettings.strategijaIzbacivanja.equals("NS")) {
                    evictionImplementation.izbaciNelemenataPoNS(cacheImplementation, n);
                    out += "Provedeno djelomicno praznjenje spremista...";
                } else {
                    evictionImplementation.izbaciNelemenataPoNK(cacheImplementation, n);
                    out += "Provedeno djelomicno praznjenje spremista...";
                }
            } else {
                out += "Broj n je veci ili jednak od trenutnog kapaciteta (" + file.listFiles().length + ") spremista!";
            }
        }
        setOutput(out);
    }

    public void prikaziSadrzajSpremista() {
        out = "Naziv\t\t\t\t\tBroj koristenja\t\t\tVrijeme spremanja\t\t\tZadnje koristenje\t\t\t";
        if (cacheImplementation.resourceList != null) {
            for (String key : cacheImplementation.resourceList.keySet()) {
                Resource value = cacheImplementation.resourceList.get(key);
                out += "\n" + value.getNaziv() + "\t\t\t" + value.getBrojKoristenja() + "\t\t\t" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(value.getVrijemeSpremanja())) + "\t\t\t" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(value.getZadnjeKoristenje())) + "\t\t\t" + value.getVelicina() / 1024 + "KB";
            }
        }
        setOutput(out);
    }

    public void isprazniSpremiste() {
        out = "";
        File file = new File(programSettings.direktorijSpremista);
        for (File f : file.listFiles()) {
            f.delete();
        }
        cacheImplementation.resourceList.clear();
        out += "Spremiste potpuno ispraznjeno";
        setOutput(out);
    }

}
