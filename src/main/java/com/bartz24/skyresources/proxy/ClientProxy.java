package com.bartz24.skyresources.proxy;

import com.bartz24.skyresources.References;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		OBJLoader.INSTANCE.addDomain(References.ModID);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}
}