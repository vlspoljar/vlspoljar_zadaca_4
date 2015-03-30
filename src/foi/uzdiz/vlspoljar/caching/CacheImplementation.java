package foi.uzdiz.vlspoljar.caching;

import java.util.HashMap;

public class CacheImplementation implements Cache {

    public HashMap<String, Resource> resourceList = new HashMap<>();

    public CacheImplementation() {
    }

    @Override
    public void addRecource(Resource resource) {
        String key = resource.getPutanjaOriginalna();
        resourceList.put(key, resource);
    }

    @Override
    public Resource getRecource(String originalnaPutanja) {
        Resource resource = (Resource) resourceList.get(originalnaPutanja);
        return resource;
    }

    public void removeResource(String originalnaPutanja) {
        resourceList.remove(originalnaPutanja);
    }
}
