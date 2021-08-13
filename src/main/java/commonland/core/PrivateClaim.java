package commonland.core;

import java.util.List;
import java.util.UUID;

public class PrivateClaim extends Claim {
    protected PrivateClaim(UUID claimId, List<UUID> members, UUID owner, String name) {
        super(claimId, members, owner, name);
    }
}
