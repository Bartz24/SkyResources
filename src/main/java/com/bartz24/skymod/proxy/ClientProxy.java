package com.bartz24.skymod.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.bartz24.skymod.registry.ModRenderers;



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
