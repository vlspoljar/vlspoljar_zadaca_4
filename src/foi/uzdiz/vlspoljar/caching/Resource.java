package foi.uzdiz.vlspoljar.caching;

import foi.uzdiz.vlspoljar.evictor.EvictionInterface;

public class Resource implements EvictionInterface{
    public String naziv;
    public String putanjaCaching;
    public String putanjaOriginalna;
    public long vrijemeSpremanja;
    public int brojKoristenja;
    public long zadnjeKoristenje;
    public long velicina;

    public Resource() {
    }

    public Resource(String naziv, String putanjaCaching, String putanjaOriginalna, long vrijemeSpremanja, int brojKoristenja, long zadnjeKoristenje, long velicina) {
        this.naziv = naziv;
        this.putanjaCaching = putanjaCaching;
        this.putanjaOriginalna = putanjaOriginalna;
        this.vrijemeSpremanja = vrijemeSpremanja;
        this.brojKoristenja = brojKoristenja;
        this.zadnjeKoristenje = zadnjeKoristenje;
        this.velicina = velicina;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPutanjaCaching() {
        return putanjaCaching;
    }

    public void setPutanjaCaching(String putanjaCaching) {
        this.putanjaCaching = putanjaCaching;
    }

    public String getPutanjaOriginalna() {
        return putanjaOriginalna;
    }

    public void setPutanjaOriginalna(String putanjaOriginalna) {
        this.putanjaOriginalna = putanjaOriginalna;
    }

    public long getVrijemeSpremanja() {
        return vrijemeSpremanja;
    }

    public void setVrijemeSpremanja(long vrijemeSpremanja) {
        this.vrijemeSpremanja = vrijemeSpremanja;
    }

    public int getBrojKoristenja() {
        return brojKoristenja;
    }

    public void setBrojKoristenja(int brojKoristenja) {
        this.brojKoristenja = brojKoristenja;
    }

    public long getZadnjeKoristenje() {
        return zadnjeKoristenje;
    }

    public void setZadnjeKoristenje(long zadnjeKoristenje) {
        this.zadnjeKoristenje = zadnjeKoristenje;
    }

    public long getVelicina() {
        return velicina;
    }

    public void setVelicina(long velicina) {
        this.velicina = velicina;
    }

    @Override
    public Resource info() {
        return this;
    }
    
}
