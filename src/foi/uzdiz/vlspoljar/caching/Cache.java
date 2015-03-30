package foi.uzdiz.vlspoljar.caching;

public interface Cache {
    public void addRecource (Resource resource);
    public Resource getRecource (String originalnaPutanja);
}
