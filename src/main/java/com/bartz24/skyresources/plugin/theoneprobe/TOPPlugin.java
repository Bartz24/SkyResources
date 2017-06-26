package com.bartz24.skyresources.plugin.theoneprobe;

import com.bartz24.skyresources.plugin.IModPlugin;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TOPPlugin implements IModPlugin
{

	public void preInit()
	{
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe",
				"com.bartz24.skyresources.plugin.theoneprobe.GetTOP");
	}

	public void init()
	{
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
