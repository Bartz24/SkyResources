package com.bartz24.skyresources.base.guide.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.base.guide.GuideImage;
import com.bartz24.skyresources.base.guide.GuideImageButton;
import com.bartz24.skyresources.base.guide.GuideLinkPageButton;
import com.bartz24.skyresources.base.guide.GuidePage;
import com.bartz24.skyresources.base.guide.GuidePageButton;
import com.bartz24.skyresources.base.guide.GuideRecipeButton;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;

import joptsimple.internal.Strings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GuideGUI extends GuiScreen
{

	GuiButton closeButton, cycleCatLeftButton, cycleCatRightButton;

	List<GuiButton> currentLinkButtons;

	String currentCategory;

	GuideImage currentImage;

	GuidePage currentPage;

	List<List<Object>> currentPageInfo;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		if (currentImage == null)
		{
			super.drawScreen(mouseX, mouseY, partialTicks);

			ItemStack stack = currentPage.pageItemDisplay;

			int x = Math.max(this.width / 2 - 100, 150);

			if (stack != null)
				drawItem(stack, x, 20);

			this.fontRendererObj.drawString(currentPage.pageDisplay, x + 20, 24,
					16777215);

			String catDisplay = Strings.isNullOrEmpty(currentCategory) ? "All"
					: I18n.format(currentCategory);
			this.fontRendererObj.drawString(catDisplay,
					60 - fontRendererObj.getStringWidth(catDisplay) / 2, 24,
					16777215);

			renderRichText(x, 80);
		}

		if (currentImage != null)
		{

			this.mc.getTextureManager().bindTexture(currentImage.imgLocation);
			this.drawTexturedModalRect(this.width / 2 - 128,
					this.height / 2 - 128, 0, 0, 256, 256);
			this.closeButton.drawButton(mc, mouseX, mouseY);
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	public void openPage(GuidePage page)
	{
		currentPage = page;
		initGui();
	}

	public void closeImage()
	{
		currentImage = null;
		for (GuiButton b : buttonList)
		{
			b.enabled = true;
		}
	}

	public void openImage(GuideImage image)
	{
		currentImage = image;

		for (GuiButton b : buttonList)
		{
			if (b != this.closeButton)
				b.enabled = false;
		}
	}

	@Override
	public void initGui()
	{
		buttonList.clear();
		new GuideRecipeButton(new ItemStack(Blocks.DIRT));
		new GuideLinkPageButton(null, null, null);
		new GuideImageButton("", null, null);
		if (currentPage == null)
			currentPage = SkyResourcesGuide.getPage("basics");
		if (currentLinkButtons == null)
			currentLinkButtons = new ArrayList<GuiButton>();
		currentLinkButtons.clear();
		this.buttonList.add(this.cycleCatLeftButton = new GuiButton(8, 5, 4, 55,
				20, "Cycle <"));
		this.buttonList.add(this.cycleCatRightButton = new GuiButton(9, 60, 4,
				55, 20, "> Cycle"));
		this.buttonList.add(this.closeButton = new GuiButton(0, 0,
				this.height - 20, 40, 20, "Close"));
		this.addLinkButtons();
		buttonList.addAll(buttonList.size(), currentLinkButtons);

		currentPageInfo = setupPage(currentPage.pageInfo,
				this.width - Math.max(this.width / 2 - 100, 150) - 50, 600);

		if (currentImage != null)
		{
			for (GuiButton b : buttonList)
			{
				if (b != this.closeButton)
					b.enabled = false;
			}
		}
	}

	public void addLinkButton(String pageLink, String display, ItemStack stack)
	{
		currentLinkButtons
				.add(new GuiPageButton(currentLinkButtons.size() + 2000, 10,
						40 + currentLinkButtons.size() * 20,
						new GuideLinkPageButton(pageLink, display, stack)));
	}

	public void addLinkButtons()
	{
		for (GuidePage p : SkyResourcesGuide.getPages(currentCategory))
		{
			addLinkButton(p.pageId, p.pageDisplay, p.pageItemDisplay);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button == this.closeButton)
		{
			if (currentImage == null)
			{
				// Main.packetHandler.sendToServer(...);
				this.mc.displayGuiScreen(null);
				if (this.mc.currentScreen == null)
					this.mc.setIngameFocus();
			} else
				closeImage();
		}
		if (currentImage == null)
		{
			if (button == this.cycleCatLeftButton)
			{
				List<String> categories = SkyResourcesGuide.getCategories();
				int curIndex = categories.indexOf(currentCategory);
				curIndex--;
				if (curIndex < -1)
					curIndex = categories.size() - 1;
				currentCategory = (curIndex == -1) ? ""
						: categories.get(curIndex);
				initGui();
			}
			if (button == this.cycleCatRightButton)
			{
				List<String> categories = SkyResourcesGuide.getCategories();
				int curIndex = categories.indexOf(currentCategory);
				curIndex++;
				if (curIndex >= categories.size())
					curIndex = -1;
				currentCategory = (curIndex == -1) ? ""
						: categories.get(curIndex);
				initGui();
			}

			for (GuiButton b : buttonList)
			{
				if (b instanceof GuiPageButton && b == button)
					if (((GuiPageButton) b).onPressed())
						break;
			}
		}
	}

	void drawItem(ItemStack stack, int x, int y)
	{
		RenderHelper.enableGUIStandardItemLighting();
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		RenderHelper.disableStandardItemLighting();

	}

	List<List<Object>> setupPage(String string, int width, int height)
	{
		int buttonIndex = 5000;
		List<List<Object>> richTextLines = new ArrayList<List<Object>>();

		String[] words = string.split(" ");
		List<Object> line = new ArrayList<Object>();
		int lineWidth = 0;
		String currentString = "";
		for (String word : words)
		{
			if (word.equals("\n"))
			{
				line.add(currentString);
				currentString = "";
				richTextLines.add(line);
				line = new ArrayList<Object>();
				lineWidth = 0;
			} else if (word.startsWith("<") && word.endsWith(">"))
			{
				String argWord = word.replace("<", "").replace(">", "");

				String[] args = argWord.split(",");

				GuidePageButton button = SkyResourcesGuide
						.getBlankButton(args[0]);
				if (button != null)
				{
					line.add(currentString);
					button.setDisplay(args[1]);
					button.setItemDisplay(args[2]);
					button.setArguments(args);
					GuiPageButton guiButton = new GuiPageButton(buttonIndex,
							-100, -100, button);
					guiButton.resetWidth();
					if (lineWidth + guiButton.width > width)
					{
						richTextLines.add(line);
						line = new ArrayList<Object>();
						lineWidth = 0;

					}
					buttonList.add(guiButton);

					lineWidth += guiButton.width;
					line.add(guiButton);

					currentString = " ";
					buttonIndex++;
				}
			} else
			{
				int wordWidth = fontRendererObj.getStringWidth(word + " ");

				if (lineWidth + wordWidth > width)
				{
					line.add(currentString);
					currentString = "";
					richTextLines.add(line);
					line = new ArrayList<Object>();
					lineWidth = 0;
				}

				currentString += word + " ";
				lineWidth += wordWidth;
			}

		}
		line.add(currentString);
		richTextLines.add(line);

		return richTextLines;
	}

	void renderRichText(int x, int y)
	{
		int curX = x;
		int curY = y;
		for (List<Object> objList : currentPageInfo)
		{
			for (Object obj : objList)
			{
				if (obj instanceof String)
				{
					fontRendererObj.drawString(obj.toString(), curX, curY,
							16777215);
					curX += fontRendererObj.getStringWidth(obj.toString());
				} else if (obj instanceof GuiPageButton)
				{
					GuiPageButton button = (GuiPageButton) obj;
					button.buttonInfo.setDisplay(
							button.buttonInfo.getDisplay().replace("_", " "));
					button.xPosition = curX;
					button.yPosition = curY - 4;
					curX += button.width;
				}
			}
			curX = x;
			curY += 20;
		}
	}
}
