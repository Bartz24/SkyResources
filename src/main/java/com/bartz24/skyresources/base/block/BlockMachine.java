package com.bartz24.skyresources.base.block;

import com.bartz24.skyresources.base.tile.TileBase;
import com.bartz24.skyresources.base.tile.TileItemInventory;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMachine extends Block implements ITileEntityProvider {

    public BlockMachine(Material materialIn) {
        super(materialIn);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileBase) {
            ((TileBase) tile).updateRedstone();
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (world.getTileEntity(pos) instanceof TileItemInventory) {
            TileItemInventory te = (TileItemInventory) world.getTileEntity(pos);
            te.dropInventory();
        }
        super.breakBlock(world, pos, state);
    }
}
