package commonland.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NestedClaim extends Claim {
    private List<Claim> subclaims = new ArrayList<>();

    protected NestedClaim(UUID claimId, List<UUID> members, UUID owner, String name) {
        super(claimId, members, owner, name);
    }

    public void addSubclaim(Claim claim) {
        subclaims.add(claim);
    }

    public void removeSubclaim(Claim claim) {
        if(!subclaims.contains(claim)) return;
        subclaims.remove(claim);
    }
}
