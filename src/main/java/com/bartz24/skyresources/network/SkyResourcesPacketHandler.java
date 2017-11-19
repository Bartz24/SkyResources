package com.bartz24.skyresources.network;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.network.DumpMessage.DumpMessageHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class SkyResourcesPacketHandler
{
	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(References.ModID);

	public static void preInit()
	{
		instance.registerMessage(DumpMessageHandler.class, DumpMessage.class, 0, Side.SERVER);
	}
}
