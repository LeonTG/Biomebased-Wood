package com.leontg77.biomebasedwood.events;

import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.ChunkEvent;

/**
 * Event thrown when a chunk is safe to be modified.
 *
 * @author D4mnX
 */
public class ChunkModifiableEvent extends ChunkEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public ChunkModifiableEvent(Chunk chunk) {
        super(chunk);
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
