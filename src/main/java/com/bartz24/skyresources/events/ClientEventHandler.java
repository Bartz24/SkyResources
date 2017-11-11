package com.bartz24.skyresources.events;

import com.bartz24.skyresources.InfoToast;
import com.bartz24.skyresources.alchemy.FusionCatalysts;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ClientEventHandler
{
	@SubscribeEvent
	public void onPlayerTickEvent(PlayerTickEvent event)
	{
		if (event.player.world.isRemote)
		{
			EntityPlayer player = event.player;
			if (ConfigOptions.guideSettings.displayGuideMessage && ConfigOptions.guideSettings.allowGuide
					&& Minecraft.getMinecraft().player != null
					&& Minecraft.getMinecraft().player.getGameProfile().getId().equals(player.getGameProfile().getId())
					&& player.ticksExisted > 100 && player.ticksExisted < 150
					&& Minecraft.getMinecraft().getToastGui().getToast(InfoToast.class, InfoToast.Type.Info) == null)
			{
				Minecraft.getMinecraft().getToastGui().add(new InfoToast(new TextComponentString("Sky Resources Guide"),
						new TextComponentString("Press " + TextFormatting.AQUA + "Open Guide Key (G)"), 5000));
			}
		}
	}

	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();
		if (FusionCatalysts.isCatalyst(stack))
		{
			event.getToolTip().add(TextFormatting.AQUA + "Catalyst Yield: "
					+ (int) (FusionCatalysts.getCatalystValue(stack) * 100f) + "%");
		}
	}
}
