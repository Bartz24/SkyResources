package com.bartz24.skyresources.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import com.bartz24.skyresources.References;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGuiFactory implements IModGuiFactory
{

	@Override
	public void initialize(Minecraft minecraftInstance)
	{
		// NO-OP
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return ConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return null;
	}

	public static class ConfigGui extends GuiConfig
	{

		public ConfigGui(GuiScreen parentScreen)
		{

			super(parentScreen, ConfigOptions.getConfigElements(), References.ModID, false, false,
					GuiConfig.getAbridgedConfigPath(ConfigOptions.config.toString()));
		}

		@Override
		public void onGuiClosed()
		{
			super.onGuiClosed();
			ConfigOptions.config.save();
		}
	}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new ConfigGui(parentScreen);
	}

}
