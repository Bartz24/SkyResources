package com.bartz24.skyresources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.ITextComponent;

public class ChatHelper
{
	private static final int DELETION_ID = 1254632;
    private static int lastAdded;

    public static void sendNoSpamMessages(ITextComponent[] messages)
    {
        GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        for (int i = DELETION_ID + messages.length - 1; i <= lastAdded; i++)
        {
            chat.deleteChatLine(i);
        }
        for (int i = 0; i < messages.length; i++)
        {
            chat.printChatMessageWithOptionalDeletion(messages[i], DELETION_ID + i);
        }
        lastAdded = DELETION_ID + messages.length - 1;
    }
}
