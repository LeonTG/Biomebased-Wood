/*
 * Project: Biomebased-Wood
 * Class: com.leontg77.biomebasedwood.Main
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Leon Vaktskjold <leontg77@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.leontg77.biomebasedwood;

import com.leontg77.biomebasedwood.listeners.ChunkModifiableListener;
import com.leontg77.biomebasedwood.listeners.ChunkPopulateListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin class.
 *
 * @author LeonTG77
 */
public class Main extends JavaPlugin {
    private static final BlockFace[] FACES = new BlockFace[] { BlockFace.SELF, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_WEST};

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ChunkModifiableListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChunkPopulateListener(this), this);
    }

    /**
     * Get the durability of the given block.
     *
     * @param block The block checking.
     * @return The durability of the block.
     */
    public int getDurability(Block block) {
        return block.getState().getData().toItemStack().getDurability();
    }

    /**
     * Check if the given block is nearby the given location.
     *
     * @param material the type of the block
     * @param location the location.
     * @return <code>True</code> if blocks nearby has the checked type, <code>false</code> otherwise.
     *
     * @author D4mnX
     */
    public boolean hasBlockNearby(Material material, Location location) {
        Block block = location.getBlock();

        for (BlockFace face : FACES) {
            if (block.getRelative(face).getType() == material) {
                return true;
            }
        }

        return false;
    }
}
