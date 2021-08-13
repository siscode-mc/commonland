package commonland.core;

import java.util.List;
import java.util.UUID;

public abstract class Claim {
    private final UUID claimId;
    private List<UUID> members;
    private final UUID owner;
    private String name;

    protected Claim(UUID claimId, List<UUID> members, UUID owner, String name) {
        this.claimId = claimId;
        this.members = members;
        this.owner = owner;
        this.name = name;
    }

    public UUID getClaimId() {
        return claimId;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOwner() {
        return owner;
    }

}
