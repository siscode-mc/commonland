package commonland.core;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.*;

public class WorldManager {
    // this boi maps (ChunkPos -> Claim UUID)
    // for a simple way to check if a certain chunk is already claimed
    private Map<ChunkPos, UUID> position = new HashMap<>();

    // this boi maps (Player UUID -> Set<Claim UUID>)
    // for a simple way to check if a player is part of a claim
    // and which claims this player are a part of
    private Map<UUID, Set<UUID>> membership = new HashMap<>();

    private Map<UUID, Claim> claims = new HashMap<>();

    private final ServerWorld world;

    public WorldManager(ServerWorld world) {
        this.world = world;
    }

    public boolean isPartOf(UUID player, UUID claim) {
        if(!membership.containsKey(player)) registerNewMember(player);
        return membership.get(player).contains(claim);
    }

    public boolean isClaimed(ChunkPos pos) {
        return position.containsKey(pos);
    }

    private void registerNewMember(UUID player) {
        membership.put(player, new HashSet<>());
        //TODO: some IO madness for data persistency
    }

}
