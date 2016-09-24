package com.bartz24.skyresources.api.event;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.bartz24.skyresources.IslandPos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * This event is fired after a player leaves an island
 * This event is not cancelable, and has no result
 * This event is fired on MinecraftForge#EVENT_BUS
 * The EntityPlayer is the player leaving, the IslandPos is the island being left
 */
public class IslandLeaveEvent extends Event
{
	private final IslandPos islandPosition;
	private final UUID playerUUID;
	
	public IslandLeaveEvent(@Nonnull EntityPlayer entityPlayer, IslandPos isPosition)
    {
    	playerUUID = entityPlayer.getUniqueID();
    	islandPosition = isPosition;
    }
	
	@Nonnull
    public UUID getPlayerUUID()
    {
        return playerUUID;
    }
	
	@Nonnull
    public IslandPos getIslandPosition()
    {
        return islandPosition;
    }
}
