package com.bartz24.skyresources.base.guide.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.bartz24.skyresources.base.guide.GuideImage;
import com.bartz24.skyresources.base.guide.GuideImageButton;
import com.bartz24.skyresources.base.guide.GuideLinkPageButton;
import com.bartz24.skyresources.base.guide.GuidePage;
import com.bartz24.skyresources.base.guide.GuidePageButton;
import com.bartz24.skyresources.base.guide.GuideRecipeButton;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.config.ConfigOptions;

import joptsimple.internal.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GuideGUI extends GuiScreen
{

	GuiButton closeButton, cycleCatLeftButton, cycleCatRightButton, backButton,
			upButton, downButton;

	List<GuiButton> currentLinkButtons;

	String currentCategory;

	GuideImage currentImage;

	GuidePage currentPage;

	List<List<Object>> currentPageInfo;

	GuiTextField searchBox;

	List<String> pageHistory;

	int prevScale = -1;

	int curIndex = 0;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		if (currentImage == null)
		{
			super.drawScreen(mouseX, mouseY, partialTicks);

			ItemStack stack = currentPage.pageItemDisplay;

			int x = Math.max(this.width / 2 - 100, 225);

			if (stack != null)
				drawItem(stack, x, 20);

			this.fontRendererObj.drawString(currentPage.pageDisplay, x + 20, 24,
					16777215);

			String catDisplay = Strings.isNullOrEmpty(currentCategory) ? "All"
					: I18n.format(currentCategory);
			this.fontRendererObj.drawString(catDisplay,
					60 - fontRendererObj.getStringWidth(catDisplay) / 2, 18,
					16777215);
			this.fontRendererObj.drawString("Category",
					60 - fontRendererObj.getStringWidth("Category") / 2, 6,
					16777215);

			this.searchBox.drawTextBox();

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
		if (page != null)
		{
			if (pageHistory.size() == 0 || !pageHistory
					.get(pageHistory.size() - 1).equals(currentPage.pageId))
				pageHistory.add(currentPage.pageId);
			currentPage = page;
			initGui();
		}
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
	public void onGuiClosed()
	{
		super.onGuiClosed();
		if (prevScale != -1)
			Minecraft.getMinecraft().gameSettings.guiScale = prevScale;
	}

	@Override
	public void initGui()
	{
		if (ConfigOptions.scaleGuideGui)
		{
			ScaledResolution curRes = new ScaledResolution(this.mc);
			if (prevScale == -1
					&& (curRes.getScaledWidth() < 700
							|| curRes.getScaledHeight() < 300)
					&& Minecraft.getMinecraft().gameSettings.guiScale != 1)
			{
				prevScale = Minecraft.getMinecraft().gameSettings.guiScale;
				while (Minecraft.getMinecraft().gameSettings.guiScale != 1)
				{
					Minecraft.getMinecraft().gameSettings.guiScale = Minecraft
							.getMinecraft().gameSettings.guiScale == 0
									? 3
									: Minecraft
											.getMinecraft().gameSettings.guiScale
											- 1;
					ScaledResolution res = new ScaledResolution(this.mc);
					this.width = res.getScaledWidth();
					this.height = res.getScaledHeight();
					if (!(width < 700 || height < 300))
						break;
				}
			}
		}

		new GuideRecipeButton(new ItemStack(Blocks.DIRT));
		new GuideLinkPageButton(null, null, null);
		new GuideImageButton("", null, null);
		if (currentPage == null)
			currentPage = SkyResourcesGuide.getPage("basics");
		if (currentLinkButtons == null)
			currentLinkButtons = new ArrayList<GuiButton>();
		if (pageHistory == null)
			pageHistory = new ArrayList<String>();
		buttonList.clear();
		currentLinkButtons.clear();

		if (this.searchBox == null)
		{
			this.searchBox = new GuiTextField(55, this.fontRendererObj, 120, 5,
					100, 20);
			searchBox.setMaxStringLength(23);
			this.searchBox.setFocused(true);
		}
		this.buttonList.add(
				this.cycleCatLeftButton = new GuiButton(8, 5, 4, 10, 20, "<"));
		this.buttonList.add(this.cycleCatRightButton = new GuiButton(9, 105, 4,
				10, 20, ">"));
		this.buttonList.add(this.closeButton = new GuiButton(0, 0,
				this.height - 20, 40, 20, "Close"));
		this.buttonList.add(this.backButton = new GuiButton(10,
				this.width - Math.max(this.width / 2 + 75, 225), 2, 40, 20,
				"Back"));
		this.buttonList
				.add(this.upButton = new GuiButton(11, 0, 40, 10, 20, "^"));
		this.buttonList
				.add(this.downButton = new GuiButton(12, 0, 60, 10, 20, "v"));
		this.addLinkButtons();

		currentPageInfo = setupPage(currentPage.pageInfo,
				this.width - Math.max(this.width / 2 - 100, 225) - 50, 600);

		if (currentImage != null)
		{
			for (GuiButton b : buttonList)
			{
				if (b != this.closeButton)
					b.enabled = false;
			}
		}
	}

	public void removeLinkButtons()
	{
		currentLinkButtons.clear();
		List<GuiButton> buttonsToRemove = new ArrayList<GuiButton>();
		for (GuiButton b : buttonList)
		{
			if (b.id >= 2000 && b.id < 3000)
				buttonsToRemove.add(b);
		}
		for (GuiButton b : buttonsToRemove)
		{
			buttonList.remove(b);
		}
	}

	public void addLinkButton(String pageLink, String display, ItemStack stack)
	{
		currentLinkButtons
				.add(new GuiPageButton(currentLinkButtons.size() + 2000, 10,
						40 + currentLinkButtons.size() * 20,
						new GuideLinkPageButton(pageLink, display, stack)));
	}

	public int getMaxLinkButtons()
	{
		int heightAllowed = this.height - 40 - 20;
		return (int) Math.floor((float) heightAllowed / 20f);
	}

	public void addLinkButtons()
	{
		int curAddIndex = 0;
		for (GuidePage p : SkyResourcesGuide.getPages(currentCategory,
				(this.searchBox == null) ? "" : this.searchBox.getText()))
		{
			curAddIndex++;
			if (curAddIndex - 1 < curIndex
					|| curAddIndex - 1 >= curIndex + getMaxLinkButtons())
				continue;
			addLinkButton(p.pageId, p.pageDisplay, p.pageItemDisplay);
		}
		buttonList.addAll(buttonList.size(), currentLinkButtons);
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
				curIndex = 0;
				removeLinkButtons();
				addLinkButtons();
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
				curIndex = 0;
				removeLinkButtons();
				addLinkButtons();
			}
			if (button == this.backButton)
			{
				if (pageHistory.size() > 0)
				{
					String prevPage = pageHistory.get(pageHistory.size() - 1);
					pageHistory.remove(pageHistory.size() - 1);
					currentPage = SkyResourcesGuide.getPage(prevPage);
					initGui();

				}
			}
			if (button == this.upButton)
			{
				if (SkyResourcesGuide.getPages(currentCategory, (this.searchBox == null) ? "" : this.searchBox.getText())
						.size() > getMaxLinkButtons())
				{
					curIndex--;
					if (curIndex < 0)
						curIndex = SkyResourcesGuide.getPages(currentCategory, (this.searchBox == null) ? "" : this.searchBox.getText())
								.size() - getMaxLinkButtons();

					removeLinkButtons();
					addLinkButtons();
				} else
					curIndex = 0;
			}
			if (button == this.downButton)
			{
				if (SkyResourcesGuide.getPages(currentCategory, (this.searchBox == null) ? "" : this.searchBox.getText())
						.size() > getMaxLinkButtons())
				{
					curIndex++;
					if (curIndex + getMaxLinkButtons() > SkyResourcesGuide
							.getPages(currentCategory, (this.searchBox == null) ? "" : this.searchBox.getText()).size())
						curIndex = 0;

					removeLinkButtons();
					addLinkButtons();
				} else
					curIndex = 0;
			}

			for (GuiButton b : buttonList)
			{
				if (b instanceof GuiPageButton && b == button)
					if (((GuiPageButton) b).onPressed())
						break;
			}
		}
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		this.searchBox.textboxKeyTyped(typedChar, keyCode);
		curIndex = 0;
		removeLinkButtons();
		addLinkButtons();
		if(keyCode ==Keyboard.KEY_ESCAPE)
		{
			if (currentImage == null)
			{
				super.keyTyped(typedChar, keyCode);
			} else
				closeImage();
		}
		
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
			throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.searchBox.mouseClicked(mouseX, mouseY, mouseButton);
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
