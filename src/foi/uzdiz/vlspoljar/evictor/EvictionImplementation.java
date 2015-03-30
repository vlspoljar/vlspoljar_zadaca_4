package foi.uzdiz.vlspoljar.evictor;

import foi.uzdiz.vlspoljar.caching.CacheImplementation;
import foi.uzdiz.vlspoljar.caching.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EvictionImplementation implements EvictionInterface {
    public String out = "";

    public EvictionImplementation() {
    }

    public String izbacujKBpoNS(CacheImplementation cacheImplementation, File spremiste, long velicinaSpremista, long velicinaDatoteke) {
        out = "";
        while (velicinaSpremista < ((getVelicinaSpremista(spremiste) + velicinaDatoteke) / 1024)) {
            String najstarijiPutanjaOriginalna = "";
            String najstarijiPutanjaCaching = "";
            long vrijemeNajstarijeg = System.currentTimeMillis();
            String najstarijiNaziv = "";
            int najstarijiBrojKoristenja = 0;
            for (String key : cacheImplementation.resourceList.keySet()) {
                if (cacheImplementation.resourceList.get(key).getVrijemeSpremanja() < vrijemeNajstarijeg) {
                    vrijemeNajstarijeg = cacheImplementation.resourceList.get(key).getVrijemeSpremanja();
                    najstarijiPutanjaOriginalna = cacheImplementation.resourceList.get(key).getPutanjaOriginalna();
                    najstarijiPutanjaCaching = cacheImplementation.resourceList.get(key).getPutanjaCaching();
                    najstarijiNaziv = cacheImplementation.resourceList.get(key).getNaziv();
                    najstarijiBrojKoristenja = cacheImplementation.resourceList.get(key).getBrojKoristenja();
                }
            }
            cacheImplementation.removeResource(najstarijiPutanjaOriginalna);
            File obrisan = new File(najstarijiPutanjaCaching);
            obrisan.delete();
            out += "\nBrisanje datoteke: " + najstarijiNaziv + "\tBroj koristenja: " + najstarijiBrojKoristenja + "\tUkupno vrijeme: " + (System.currentTimeMillis()-vrijemeNajstarijeg)/1000 + "s";
        }
        return out;
    }

    public String izbacujKBpoNK(CacheImplementation cacheImplementation, File spremiste, long velicinaSpremista, long velicinaDatoteke) {
        out = "";
        while (velicinaSpremista <= ((getVelicinaSpremista(spremiste) + velicinaDatoteke) / 1024)) {
            String najkoristenijiPutanjaOriginalna = "";
            String najkoristenijiPutanjaCaching = "";
            int koristenje = 0;
            String najkoristenijiNaziv = "";
            long najkoristenijiVrijeme = 0;
            for (String key : cacheImplementation.resourceList.keySet()) {
                if (cacheImplementation.resourceList.get(key).getBrojKoristenja() >= koristenje) {
                    koristenje = cacheImplementation.resourceList.get(key).getBrojKoristenja();
                    najkoristenijiPutanjaOriginalna = cacheImplementation.resourceList.get(key).getPutanjaOriginalna();
                    najkoristenijiPutanjaCaching = cacheImplementation.resourceList.get(key).getPutanjaCaching();
                    najkoristenijiNaziv = cacheImplementation.resourceList.get(key).getNaziv();
                    najkoristenijiVrijeme = cacheImplementation.resourceList.get(key).getVrijemeSpremanja();
                }
            }
            cacheImplementation.removeResource(najkoristenijiPutanjaOriginalna);
            File obrisan = new File(najkoristenijiPutanjaCaching);
            obrisan.delete();
            out += "\nBrisanje datoteke: " + najkoristenijiNaziv + "\tBroj koristenja: " + koristenje + "\tUkupno vrijeme: " + (System.currentTimeMillis()-najkoristenijiVrijeme)/1000 + "s";
        }
        return out;
    }

    public String izbacujElementepoNS(CacheImplementation cacheImplementation, String spremiste, int velicinaSpremista) {
        out = "";
        int brojElemenata = getBrojElemenata(spremiste);
        while (velicinaSpremista <= brojElemenata) {
            String najstarijiPutanjaOriginalna = "";
            String najstarijiPutanjaCaching = "";
            long vrijemeNajstarijeg = System.currentTimeMillis();
            String najstarijiNaziv = "";
            int najstarijiKoristenje = 0;
            for (String key : cacheImplementation.resourceList.keySet()) {
                if (cacheImplementation.resourceList.get(key).getVrijemeSpremanja() < vrijemeNajstarijeg) {
                    vrijemeNajstarijeg = cacheImplementation.resourceList.get(key).getVrijemeSpremanja();
                    najstarijiPutanjaOriginalna = cacheImplementation.resourceList.get(key).getPutanjaOriginalna();
                    najstarijiPutanjaCaching = cacheImplementation.resourceList.get(key).getPutanjaCaching();
                    najstarijiNaziv = cacheImplementation.resourceList.get(key).getNaziv();
                    najstarijiKoristenje = cacheImplementation.resourceList.get(key).getBrojKoristenja();
                }
            }
            cacheImplementation.removeResource(najstarijiPutanjaOriginalna);
            File obrisan = new File(najstarijiPutanjaCaching);
            obrisan.delete();
            brojElemenata = getBrojElemenata(spremiste);
            out += "\nBrisanje datoteke: " + najstarijiNaziv + "\tBroj koristenja: " + najstarijiKoristenje + "\tUkupno vrijeme: " + (System.currentTimeMillis()-vrijemeNajstarijeg)/1000 + "s";
        }
        return out;
    }

    public String izbacujElementepoNK(CacheImplementation cacheImplementation, String spremiste, int velicinaSpremista) {
        out = "";
        int brojElemenata = getBrojElemenata(spremiste);
        while (velicinaSpremista <= brojElemenata) {
            String najkoristenijiPutanjaOriginalna = "";
            String najkoristenijiPutanjaCaching = "";
            int koristenje = 0;
            String najkoristenijiNaziv = "";
            long najkoristenijiVrijeme = 0;
            for (String key : cacheImplementation.resourceList.keySet()) {
                if (cacheImplementation.resourceList.get(key).getBrojKoristenja() >= koristenje) {
                    koristenje = cacheImplementation.resourceList.get(key).getBrojKoristenja();
                    najkoristenijiPutanjaOriginalna = cacheImplementation.resourceList.get(key).getPutanjaOriginalna();
                    najkoristenijiPutanjaCaching = cacheImplementation.resourceList.get(key).getPutanjaCaching();
                    najkoristenijiNaziv = cacheImplementation.resourceList.get(key).getNaziv();
                    najkoristenijiVrijeme = cacheImplementation.resourceList.get(key).getBrojKoristenja();
                }
            }
            cacheImplementation.removeResource(najkoristenijiPutanjaOriginalna);
            File obrisan = new File(najkoristenijiPutanjaCaching);
            obrisan.delete();
            brojElemenata = getBrojElemenata(spremiste);
            out += "\nBrisanje datoteke: " + najkoristenijiNaziv + "\tBroj koristenja: " + koristenje + "\tUkupno vrijeme: " + (System.currentTimeMillis()-najkoristenijiVrijeme)/1000 + "s";
        }
        return out;
    }

    public void izbaciNKBpoNS(CacheImplementation cacheImplementation, int n) {
        int br = 0;
        while (n > br) {
            String najstarijiPutanjaOriginalna = "";
            String najstarijiPutanjaCaching = "";
            long vrijemeNajstarijeg = System.currentTimeMillis();
            int najstarijiVelicina = 0;
            for (String key : cacheImplementation.resourceList.keySet()) {
                if (cacheImplementation.resourceList.get(key).getVrijemeSpremanja() < vrijemeNajstarijeg) {
                    vrijemeNajstarijeg = cacheImplementation.resourceList.get(key).getVrijemeSpremanja();
                    najstarijiPutanjaOriginalna = cacheImplementation.resourceList.get(key).getPutanjaOriginalna();
                    najstarijiPutanjaCaching = cacheImplementation.resourceList.get(key).getPutanjaCaching();
                    najstarijiVelicina = (int) (cacheImplementation.resourceList.get(key).getVelicina() / 1024);
                }
            }
            cacheImplementation.removeResource(najstarijiPutanjaOriginalna);
            File obrisan = new File(najstarijiPutanjaCaching);
            obrisan.delete();
            br += najstarijiVelicina;
        }
    }

    public void izbaciNKBpoNK(CacheImplementation cacheImplementation, int n) {
        int br = 0;
        while (n > br) {
            String najkoristenijiPutanjaOriginalna = "";
            String najkoristenijiPutanjaCaching = "";
            int koristenje = 0;
            int najkoristenijiVelicina = 0;
            for (String key : cacheImplementation.resourceList.keySet()) {
                if (cacheImplementation.resourceList.get(key).getBrojKoristenja() >= koristenje) {
                    koristenje = cacheImplementation.resourceList.get(key).getBrojKoristenja();
                    najkoristenijiPutanjaOriginalna = cacheImplementation.resourceList.get(key).getPutanjaOriginalna();
                    najkoristenijiPutanjaCaching = cacheImplementation.resourceList.get(key).getPutanjaCaching();
                    najkoristenijiVelicina = (int) (cacheImplementation.resourceList.get(key).getVelicina()/1024);
                }
            }
            cacheImplementation.removeResource(najkoristenijiPutanjaOriginalna);
            File obrisan = new File(najkoristenijiPutanjaCaching);
            obrisan.delete();
            br += najkoristenijiVelicina;
        }
    }

    public void izbaciNelemenataPoNS(CacheImplementation cacheImplementation, int n) {
        for (int i = 0; i < n; i++) {
            String najstarijiPutanjaOriginalna = "";
            String najstarijiPutanjaCaching = "";
            long vrijemeNajstarijeg = System.currentTimeMillis();
            for (String key : cacheImplementation.resourceList.keySet()) {
                if (cacheImplementation.resourceList.get(key).getVrijemeSpremanja() < vrijemeNajstarijeg) {
                    vrijemeNajstarijeg = cacheImplementation.resourceList.get(key).getVrijemeSpremanja();
                    najstarijiPutanjaOriginalna = cacheImplementation.resourceList.get(key).getPutanjaOriginalna();
                    najstarijiPutanjaCaching = cacheImplementation.resourceList.get(key).getPutanjaCaching();
                }
            }
            cacheImplementation.removeResource(najstarijiPutanjaOriginalna);
            File obrisan = new File(najstarijiPutanjaCaching);
            obrisan.delete();
        }
    }

    public void izbaciNelemenataPoNK(CacheImplementation cacheImplementation, int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(i);
            String najkoristenijiPutanjaOriginalna = "";
            String najkoristenijiPutanjaCaching = "";
            int koristenje = 0;
            for (String key : cacheImplementation.resourceList.keySet()) {
                if (cacheImplementation.resourceList.get(key).getBrojKoristenja() >= koristenje) {
                    koristenje = cacheImplementation.resourceList.get(key).getBrojKoristenja();
                    najkoristenijiPutanjaOriginalna = cacheImplementation.resourceList.get(key).getPutanjaOriginalna();
                    najkoristenijiPutanjaCaching = cacheImplementation.resourceList.get(key).getPutanjaCaching();
                }
            }
            cacheImplementation.removeResource(najkoristenijiPutanjaOriginalna);
            File obrisan = new File(najkoristenijiPutanjaCaching);
            obrisan.delete();
        }
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

    public int getBrojElemenata(String spremiste) {
        File file = new File(spremiste);
        return file.listFiles().length;
    }

    @Override
    public Resource info() {
        return null;
    }

}
