package me.ryanmood.ryutils.discord.events;

import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.discord.RyDiscord;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class ReadyEvent implements EventListener {

    @Getter(AccessLevel.PRIVATE)
    private RyDiscord instance;

    public ReadyEvent(RyDiscord instance) {
        this.instance = instance;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent) {
            this.instance.setChannels();
            this.instance.onReady();
        }
    }

}
