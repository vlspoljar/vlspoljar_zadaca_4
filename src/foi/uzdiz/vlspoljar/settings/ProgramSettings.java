package foi.uzdiz.vlspoljar.settings;

public class ProgramSettings {
    public int maxKolicinaPodataka;
    public int interval;
    public String direktorijSpremista;
    public int kapacitetSpremista;
    public boolean tipSpremista; //true - KB, false - broj elemenata
    public String strategijaIzbacivanja;
    public String datotekaDnevnika;
    public boolean praznjenje; //true - clean, false - bez clean

    public ProgramSettings() {
    }

    public ProgramSettings(int maxKolicinaPodataka, int interval, String direktorijSpremista, int kapacitetSpremista, boolean tipSpremista, String strategijaIzbacivanja, String datotekaDnevnika, boolean praznjenje) {
        this.maxKolicinaPodataka = maxKolicinaPodataka;
        this.interval = interval;
        this.direktorijSpremista = direktorijSpremista;
        this.kapacitetSpremista = kapacitetSpremista;
        this.tipSpremista = tipSpremista;
        this.strategijaIzbacivanja = strategijaIzbacivanja;
        this.datotekaDnevnika = datotekaDnevnika;
        this.praznjenje = praznjenje;
    }
    
    public int getMaxKolicinaPodataka() {
        return maxKolicinaPodataka;
    }

    public void setMaxKolicinaPodataka(int maxKolicinaPodataka) {
        this.maxKolicinaPodataka = maxKolicinaPodataka;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getDirektorijSpremista() {
        return direktorijSpremista;
    }

    public void setDirektorijSpremista(String direktorijSpremista) {
        this.direktorijSpremista = direktorijSpremista;
    }

    public int getKapacitetSpremista() {
        return kapacitetSpremista;
    }

    public void setKapacitetSpremista(int kapacitetSpremista) {
        this.kapacitetSpremista = kapacitetSpremista;
    }

    public boolean isTipSpremista() {
        return tipSpremista;
    }

    public void setTipSpremista(boolean tipSpremista) {
        this.tipSpremista = tipSpremista;
    }

    public String getStrategijaIzbacivanja() {
        return strategijaIzbacivanja;
    }

    public void setStrategijaIzbacivanja(String strategijaIzbacivanja) {
        this.strategijaIzbacivanja = strategijaIzbacivanja;
    }

    public String getDatotekaDnevnika() {
        return datotekaDnevnika;
    }

    public void setDatotekaDnevnika(String datotekaDnevnika) {
        this.datotekaDnevnika = datotekaDnevnika;
    }

    public boolean isPraznjenje() {
        return praznjenje;
    }

    public void setPraznjenje(boolean praznjenje) {
        this.praznjenje = praznjenje;
    }
    
}
