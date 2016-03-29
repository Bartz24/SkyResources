package com.bartz24.skyresources.proxy;

import com.bartz24.skyresources.registry.ModRenderers;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;



public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		ModRenderers.preInit();
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
		ModRenderers.init();
	}
}
