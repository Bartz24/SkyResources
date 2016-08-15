package com.bartz24.skyresources.base.guide.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

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
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GuideGUI extends GuiScreen
{

	GuiButton closeButton, cycleCatLeftButton, cycleCatRightButton, backButton;

	List<GuiPageButton> linkButtons;
	List<GuiButton> infoButtons;

	String currentCategory;

	GuideImage currentImage;

	GuidePage currentPage;

	List<List<Object>> currentPageInfo;

	GuiTextField searchBox;

	List<String> pageHistory;

	int fontType = 0;

	int curIndex = 0;

	PageList pageScroll;
	PageInfo pageInfo;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		if (currentImage == null)
		{
			super.drawScreen(mouseX, mouseY, partialTicks);

			ItemStack stack = currentPage.pageItemDisplay;

			int x = Math.max(this.width / 2 - 100, 275);

			if (stack != null)
				drawItem(stack, x, 22);

			this.fontRendererObj.drawString(currentPage.pageDisplay, x + 20, 26, 16777215);

			String catDisplay = Strings.isNullOrEmpty(currentCategory) ? "All"
					: I18n.format(currentCategory);
			this.fontRendererObj.drawString(catDisplay,
					60 - fontRendererObj.getStringWidth(catDisplay) / 2, 18, 16777215);
			this.fontRendererObj.drawString("Category",
					60 - fontRendererObj.getStringWidth("Category") / 2, 6, 16777215);

			this.searchBox.drawTextBox();
			this.pageScroll.drawScreen(mouseX, mouseY, partialTicks);
			this.pageInfo.drawScreen(mouseX, mouseY, partialTicks);
		}

		if (currentImage != null)
		{

			this.mc.getTextureManager().bindTexture(currentImage.imgLocation);
			this.drawTexturedModalRect(this.width / 2 - 128, this.height / 2 - 128, 0, 0, 256, 256);
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
			if (pageHistory.size() == 0
					|| !pageHistory.get(pageHistory.size() - 1).equals(currentPage.pageId))
				pageHistory.add(currentPage.pageId);
			currentPage = page;
			curIndex = getPageIndex(currentPage);
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

		if (ConfigOptions.rememberGuide)
		{
			ConfigOptions.lastGuideCat = currentCategory;
			ConfigOptions.lastGuidePage = currentPage.pageId;
			ConfigOptions.lastGuideSearch = this.searchBox.getText();
		}
	}

	@Override
	public void initGui()
	{
		new GuideRecipeButton(new ItemStack(Blocks.DIRT));
		new GuideLinkPageButton(null, null, null);
		new GuideImageButton("", null, null);
		if (currentPage == null)
		{
			if (ConfigOptions.rememberGuide)
			{
				currentCategory = ConfigOptions.lastGuideCat;
				GuidePage lastPage = SkyResourcesGuide.getPage(ConfigOptions.lastGuidePage);
				currentPage = lastPage == null ? SkyResourcesGuide.getPage("basics") : lastPage;
			}
		}
		if (linkButtons == null)
			linkButtons = new ArrayList();
		if (infoButtons == null)
			infoButtons = new ArrayList();
		if (pageHistory == null)
			pageHistory = new ArrayList();
		buttonList.clear();
		linkButtons.clear();
		infoButtons.clear();

		if (this.searchBox == null)
		{
			this.searchBox = new GuiTextField(55, this.fontRendererObj, 120, 5, 100, 20);
			searchBox.setMaxStringLength(23);
			this.searchBox.setFocused(true);

			if (ConfigOptions.rememberGuide
					&& !Strings.isNullOrEmpty(ConfigOptions.lastGuideSearch))
			{
				this.searchBox.setText(ConfigOptions.lastGuideSearch);
			}
		}

		this.searchBox.width = Math.min(width / 2, 250) - 110;

		if (pageScroll == null || pageScroll.getWidth() != width
				|| pageScroll.getHeight() != height)
		{
			this.pageScroll = new PageList(mc, Math.min(width / 2, 250), this.height - 60, 40,
					this.height - 20, 10, 20, width, height);
		}
		if (pageInfo == null || pageInfo.getWidth() != width || pageInfo.getHeight() != height)
		{
			this.pageInfo = new PageInfo(mc, this.width - Math.max(this.width / 2 - 100, 225) - 50,
					this.height - 60, 40, this.height - 20, Math.max(this.width / 2 - 100, 275), 20,
					width, height);
		}
		this.buttonList.add(this.cycleCatLeftButton = new GuiButton(8, 10, 4, 10, 20, "<"));
		this.buttonList.add(this.cycleCatRightButton = new GuiButton(9, 105, 4, 10, 20, ">"));
		this.buttonList
				.add(this.closeButton = new GuiButton(0, 0, this.height - 20, 40, 20, "Close"));
		this.buttonList.add(this.backButton = new GuiButton(10, Math.max(this.width / 2 - 100, 275),
				2, 40, 20, "Back"));
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

	public void addLinkButton(String pageLink, String display, ItemStack stack)
	{
		linkButtons.add(new GuiPageButton(linkButtons.size() + 2000, 10,
				40 + linkButtons.size() * 20, new GuideLinkPageButton(pageLink, display, stack)));
	}

	public int getMaxLinkButtons()
	{
		int heightAllowed = this.height - 40 - 20;
		return (int) Math.floor((float) heightAllowed / 20f);
	}

	public void removeLinkButtons()
	{
		linkButtons.clear();
	}

	public void addLinkButtons()
	{
		for (GuidePage p : SkyResourcesGuide.getPages(currentCategory,
				(this.searchBox == null) ? "" : this.searchBox.getText()))
		{
			addLinkButton(p.pageId, p.pageDisplay, p.pageItemDisplay);
		}
	}

	public int getPageIndex(GuidePage page)
	{
		for (int i = 0; i < ((linkButtons == null) ? 0 : linkButtons.size()); i++)
		{
			if (linkButtons.get(i).buttonInfo instanceof GuideLinkPageButton
					&& ((GuideLinkPageButton) linkButtons.get(i).buttonInfo)
							.getLinkPage().pageId == page.pageId)
				return i;
		}
		return 0;
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
				currentCategory = (curIndex == -1) ? "" : categories.get(curIndex);
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
				currentCategory = (curIndex == -1) ? "" : categories.get(curIndex);
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
					curIndex = getPageIndex(currentPage);
					initGui();

				}
			}

			for (GuiButton b : buttonList)
			{
				if (b instanceof GuiPageButton && b == button)
					if (((GuiPageButton) b).onPressed())
						break;
			}

			for (GuiButton b : infoButtons)
			{
				if (b instanceof GuiPageButton && b == button)
					if (((GuiPageButton) b).onPressed())
						break;
			}
		}
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		int mouseX = Mouse.getEventX() * width / mc.displayWidth;
		int mouseY = height - Mouse.getEventY() * height / mc.displayHeight - 1;

		super.handleMouseInput();
		pageScroll.handleMouseInput(mouseX, mouseY);
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (this.searchBox.textboxKeyTyped(typedChar, keyCode))
		{
			curIndex = 0;
			removeLinkButtons();
			addLinkButtons();
		} else if (keyCode == Keyboard.KEY_ESCAPE)
		{
			if (currentImage == null)
			{
				super.keyTyped(typedChar, keyCode);
			} else
				closeImage();
		}

	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.searchBox.mouseClicked(mouseX, mouseY, mouseButton);
		if (mouseButton == 0)
		{
			for (int i = 0; i < this.infoButtons.size(); ++i)
			{
				GuiButton guibutton = (GuiButton) this.infoButtons.get(i);

				if (guibutton.mousePressed(this.mc, mouseX, mouseY))
				{
					net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(
							this, guibutton, this.infoButtons);
					if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
						break;
					guibutton = event.getButton();
					guibutton.playPressSound(this.mc.getSoundHandler());
					this.actionPerformed(guibutton);
				}
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
			} else if (word.equals("*nl"))
			{
				line.add(currentString);
				line.add("*nl");
				currentString = "";
			} else if (word.equals("*gl"))
			{
				line.add(currentString);
				line.add("*gl");
				currentString = "";
			} else if (word.startsWith("<") && word.endsWith(">"))
			{
				String argWord = word.replace("<", "").replace(">", "");

				String[] args = argWord.split(",");

				GuidePageButton button = SkyResourcesGuide.getBlankButton(args[0]);
				if (button != null)
				{
					line.add(currentString);
					button.setDisplay(args[1]);
					button.setItemDisplay(args[2]);
					button.setArguments(args);
					GuiPageButton guiButton = new GuiPageButton(buttonIndex, -100, -100, button);
					guiButton.resetWidth();
					if (lineWidth + guiButton.width > width)
					{
						richTextLines.add(line);
						line = new ArrayList<Object>();
						lineWidth = 0;

					}
					infoButtons.add(guiButton);

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

	public static class SpecialTextEffect
	{
		int type = 0;

		public SpecialTextEffect(int type)
		{
			this.type = type;
		}
	}

	private class PageList extends GuiScrollingList
	{

		public PageList(Minecraft client, int width, int height, int top, int bottom, int left,
				int entryHeight, int screenWidth, int screenHeight)
		{
			super(client, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
		}

		@Override
		protected int getSize()
		{
			return linkButtons.size();
		}

		@Override
		protected void elementClicked(int index, boolean doubleClick)
		{
			curIndex = index;
			GuiPageButton button = linkButtons.get(index);
			button.onPressed();
		}

		@Override
		protected boolean isSelected(int index)
		{
			return curIndex == index;
		}

		@Override
		protected void drawBackground()
		{

		}

		@Override
		protected void drawSlot(int slotId, int entryRight, int slotTop, int slotBuffer,
				Tessellator tess)
		{
			GuiPageButton button = linkButtons.get(slotId);

			button.drawScrollButton(mc, left, slotTop, isSelected(slotId));
		}

		public int getHeight()
		{
			return this.screenHeight;
		}

		public int getWidth()
		{
			return this.screenWidth;
		}
	}

	private class PageInfo extends GuiScrollingList
	{

		public PageInfo(Minecraft client, int width, int height, int top, int bottom, int left,
				int entryHeight, int screenWidth, int screenHeight)
		{
			super(client, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
		}

		@Override
		protected int getSize()
		{
			return currentPageInfo.size();
		}

		@Override
		protected void elementClicked(int index, boolean doubleClick)
		{
		}

		@Override
		protected boolean isSelected(int index)
		{
			return false;
		}

		@Override
		protected void drawBackground()
		{

		}

		@Override
		protected void drawSlot(int slotId, int entryRight, int slotTop, int slotBuffer,
				Tessellator tess)
		{
			int curX = left;
			int curY = slotTop;
			if (currentPageInfo != null)
			{
				for (Object obj : currentPageInfo.get(slotId))
				{
					if (obj instanceof String)
					{
						if (obj.toString().equals("*gl"))
						{
							fontType = 1;
							continue;
						} else if (obj.toString().equals("*nl"))
						{
							fontType = 0;
							continue;
						}
						Date date = new Date();
						FontRenderer fontrenderer = (fontType == 1
								|| (date.getMonth() == 4 && date.getDate() == 1)
										? mc.standardGalacticFontRenderer : mc.fontRendererObj);
						fontrenderer.drawString(obj.toString(), curX, curY, 16777215);
						curX += fontrenderer.getStringWidth(obj.toString());
					} else if (obj instanceof GuiPageButton)
					{
						GuiPageButton button = (GuiPageButton) obj;
						button.buttonInfo
								.setDisplay(button.buttonInfo.getDisplay().replace("_", " "));
						button.xPosition = curX;
						button.yPosition = curY - 4;
						button.drawButton(mc, mouseX, mouseY);
						curX += button.width;
					}
				}
			}
		}

		public int getHeight()
		{
			return this.screenHeight;
		}

		public int getWidth()
		{
			return this.screenWidth;
		}
	}
}
