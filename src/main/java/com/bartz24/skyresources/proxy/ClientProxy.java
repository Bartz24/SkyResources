package com.bartz24.skyresources.proxy;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.ModKeyBindings;
import com.bartz24.skyresources.forestry.ForestryPlugin;
import com.bartz24.skyresources.ic2.IC2Plugin;
import com.bartz24.skyresources.registry.ModRenderers;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);

		OBJLoader.INSTANCE.addDomain(References.ModID);

		ModRenderers.preInit();

		
		if(Loader.isModLoaded("forestry"))
		{
			ForestryPlugin.initRenderers();
		}
		if(Loader.isModLoaded("IC2"))
		{
			IC2Plugin.initRenderers();
		}
	}

	@Override
	public void init(FMLInitializationEvent e)
	{
		new ModKeyBindings();
		super.init(e);
		ModRenderers.init();
	}
}
