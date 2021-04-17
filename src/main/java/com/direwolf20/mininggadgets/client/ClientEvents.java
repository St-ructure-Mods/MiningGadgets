package com.direwolf20.mininggadgets.client;

import com.direwolf20.mininggadgets.client.renderer.BlockOverlayRender;
import com.direwolf20.mininggadgets.client.renderer.ModificationShiftOverlay;
import com.direwolf20.mininggadgets.client.renderer.RenderMiningLaser;
import com.direwolf20.mininggadgets.client.screens.ModScreens;
import com.direwolf20.mininggadgets.common.items.MiningGadget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ClientEvents {
    @SubscribeEvent
    static void drawBlockHighlightEvent(DrawHighlightEvent evt) {
        if( Minecraft.getInstance().player == null )
            return;

        if(MiningGadget.isHolding(Minecraft.getInstance().player))
            evt.setCanceled(true);
    }

    @SubscribeEvent
    static void renderWorldLastEvent(RenderWorldLastEvent evt) {
        List<AbstractClientPlayerEntity> players = Minecraft.getInstance().level.players();
        PlayerEntity myplayer = Minecraft.getInstance().player;

        ItemStack myItem = MiningGadget.getGadget(myplayer);
        if (myItem.getItem() instanceof MiningGadget)
            BlockOverlayRender.render(evt, myItem);

        if (myplayer.isShiftKeyDown()) {
            ModificationShiftOverlay.render(evt, myplayer);
        }

        for (PlayerEntity player : players) {
            if (player.distanceToSqr(myplayer) > 500)
                continue;

            ItemStack heldItem = MiningGadget.getGadget(player);
            if (player.isUsingItem() && heldItem.getItem() instanceof MiningGadget) {
                if (MiningGadget.canMine(heldItem)) {
                    RenderMiningLaser.renderLaser(evt, player, Minecraft.getInstance().getFrameTime());
                }
            }
        }
    }

    @SubscribeEvent
    static void keyPressed(InputEvent.KeyInputEvent event) {
        if (OurKeys.shiftClickGuiBinding.consumeClick() && Minecraft.getInstance().screen == null) {
            if (Minecraft.getInstance().player == null) {
                return;
            }

            ItemStack gadget = MiningGadget.getGadget(Minecraft.getInstance().player);
            if (gadget.isEmpty()) {
                return;
            }

            ModScreens.openGadgetSettingsScreen(gadget);
        }
    }
}
