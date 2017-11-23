package com.bartz24.skyresources.network;

import com.bartz24.skyresources.alchemy.item.ItemCondenser;
import com.bartz24.skyresources.alchemy.tile.TileAlchemyFusionTable;
import com.bartz24.skyresources.base.tile.TileCasing;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DumpMessage implements IMessage
{
	public DumpMessage()
	{
	}

	public DumpMessage(int machineType, BlockPos machinePos)
	{
		machine = machineType;
		x = machinePos.getX();
		y = machinePos.getY();
		z = machinePos.getZ();
	}

	// 0=Fusion. 1=Condenser
	public int machine;
	public int x;
	public int y;
	public int z;

	@Override
	public void fromBytes(ByteBuf buf)
	{
		machine = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(machine);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	public BlockPos getPos()
	{
		return new BlockPos(x, y, z);
	}

	public static class DumpMessageHandler implements IMessageHandler<DumpMessage, IMessage>
	{
		@Override
		public IMessage onMessage(DumpMessage message, MessageContext ctx)
		{
			WorldServer world = ctx.getServerHandler().player.getServerWorld();
			if (!world.isBlockLoaded(message.getPos()))
				return null;

			TileEntity te = world.getTileEntity(message.getPos());
			if (message.machine == 0 && te instanceof TileAlchemyFusionTable)
			{
				TileAlchemyFusionTable fusion = (TileAlchemyFusionTable) te;
				fusion.setCurItemLeft(0);
				fusion.setCurYield(0);
			} else if (message.machine == 1 && te instanceof TileCasing)
			{
				TileCasing casing = (TileCasing) te;
				if (!casing.machineStored.isEmpty())
				{
					if (casing.getMachine() instanceof ItemCondenser)
					{
						casing.machineData.setInteger("time", 0);
						casing.machineData.setFloat("left", 0);
						casing.machineData.setTag("itemUsing", ItemStack.EMPTY.writeToNBT(new NBTTagCompound()));
					}
				}
			}

			return null;
		}

	}
}
