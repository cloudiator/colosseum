package cloud;

import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 06.07.15.
 */
public class DecoratedId {

    private final static String SEPARATOR = "/";

    private final String credential;
    private final String cloud;
    @Nullable private final String location;
    private final String id;


    private DecoratedId(String credential, String cloud, @Nullable String location, String id) {
        this.credential = credential;
        this.cloud = cloud;
        this.location = location;
        this.id = id;
    }

    static public DecoratedId of(String credential, String cloud, String id) {
        return new DecoratedId(credential, cloud, null, id);
    }

    static public DecoratedId of(String credential, String cloud, String location, String id) {
        return new DecoratedId(credential, cloud, location, id);
    }

    static public DecoratedId of(String id) {
        checkNotNull(id);
        final String[] split = id.split(SEPARATOR);
        switch (split.length) {
            case 3:
                return new DecoratedId(split[0], split[1], null, split[2]);
            case 4:
                return new DecoratedId(split[0], split[1], split[2], split[3]);
            default:
                throw new IllegalArgumentException();
        }
    }

    public String colosseumLocation() {
        if (location != null) {
            return credential + SEPARATOR + cloud + SEPARATOR + location;
        } else {
            return credential + SEPARATOR + cloud;
        }
    }

    public String colosseumId() {
        return colosseumLocation() + SEPARATOR + id;
    }

    public String swordId() {
        return IdScopeByLocations.from(location, id).getIdWithLocation();
    }



}
