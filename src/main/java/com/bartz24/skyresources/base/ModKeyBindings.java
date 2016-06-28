package com.bartz24.skyresources.base;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModKeyBindings
{
	public static KeyBinding guideKey;
	
	public ModKeyBindings()
	{
		guideKey = new KeyBinding("key.skyresources.guide", Keyboard.KEY_G, "Sky Resources");		
		ClientRegistry.registerKeyBinding(guideKey);	
	}
}
