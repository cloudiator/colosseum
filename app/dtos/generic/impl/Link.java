package dtos.generic.impl;

/**
 * Created by daniel on 18.12.14.
 */
public class Link {

    private final String href;
    private final Rel rel;

    public Link(String href, Rel rel) {
        this.href = href;
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel.toString();
    }
}
