package commonland.core;

import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Claim {
    private final UUID claimId;
    private List<UUID> members;
    private final UUID owner;
    private String name;
    private List<ChunkPos> chunks = new ArrayList<>();

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

    public List<ChunkPos> getChunks() {
        return chunks;
    }

    public boolean registerChunk(WorldManager manager, ChunkPos pos) {
        if(manager.isClaimed(pos)) {
            return false;
        }
        manager.registerNewPosition(this.claimId, pos);
        this.chunks.add(pos);
        return true;
    }

    public boolean unregisterChunk(WorldManager manager, ChunkPos pos) {
        if(!manager.isClaimed(pos) || !this.chunks.contains(pos)) {
            return false;
        }
        manager.unregisterPosition(pos);
        this.chunks.remove(pos);
        return true;
    }

}
