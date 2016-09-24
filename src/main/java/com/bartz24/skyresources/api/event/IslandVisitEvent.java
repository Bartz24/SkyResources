package com.bartz24.skyresources.api.event;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.bartz24.skyresources.IslandPos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * This event is fired after a player visits an island
 * This event is not cancelable, and has no result
 * This event is fired on MinecraftForge#EVENT_BUS
 * The EntityPlayer is the player visiting, the IslandPos is the island being visited
 */
public class IslandVisitEvent extends Event
{
	private final IslandPos islandPosition;
	private final UUID playerUUID;
	
	public IslandVisitEvent(@Nonnull EntityPlayer entityPlayer, IslandPos isPosition)
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
