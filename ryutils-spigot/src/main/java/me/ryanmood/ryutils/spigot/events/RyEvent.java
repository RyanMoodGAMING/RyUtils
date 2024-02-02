package me.ryanmood.ryutils.spigot.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public class RyEvent extends Event {

    /**
     * Make a custom event.
     */
    public RyEvent() {
        this(false);
    }

    /**
     * Make a custom event.
     *
     * @param isAsync Should the event be async?
     */
    public RyEvent(boolean isAsync) {
        super(isAsync);
    }

    private static final HandlerList handlers = new HandlerList();

    /**
     * Get the handlers list.
     *
     * @return HandlerList
     */
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Get the handlers list.
     *
     * @return HandlerList
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
