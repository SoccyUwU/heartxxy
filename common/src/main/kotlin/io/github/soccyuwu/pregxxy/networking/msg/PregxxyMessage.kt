package io.github.soccyuwu.pregxxy.networking.msg

import dev.architectury.networking.NetworkChannel
import dev.architectury.networking.NetworkManager.PacketContext
import io.github.soccyuwu.pregxxy.Pregxxy
import io.github.soccyuwu.pregxxy.networking.PregxxyNetworking
import io.github.soccyuwu.pregxxy.networking.handler.applyOnClient
import io.github.soccyuwu.pregxxy.networking.handler.applyOnServer
import net.fabricmc.api.EnvType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import java.util.function.Supplier

sealed interface PregxxyMessage

sealed interface PregxxyMessageC2S : PregxxyMessage {
    fun sendToServer() {
        PregxxyNetworking.CHANNEL.sendToServer(this)
    }
}

sealed interface PregxxyMessageS2C : PregxxyMessage {
    fun sendToPlayer(player: ServerPlayer) {
        PregxxyNetworking.CHANNEL.sendToPlayer(player, this)
    }

    fun sendToPlayers(players: Iterable<ServerPlayer>) {
        PregxxyNetworking.CHANNEL.sendToPlayers(players, this)
    }
}

sealed interface PregxxyMessageCompanion<T : PregxxyMessage> {
    val type: Class<T>

    fun decode(buf: FriendlyByteBuf): T

    fun T.encode(buf: FriendlyByteBuf)

    fun apply(msg: T, supplier: Supplier<PacketContext>) {
        val ctx = supplier.get()
        when (ctx.env) {
            EnvType.SERVER, null -> {
                Pregxxy.LOGGER.debug("Server received packet from {}: {}", ctx.player.name.string, this)
                when (msg) {
                    is PregxxyMessageC2S -> msg.applyOnServer(ctx)
                    else -> Pregxxy.LOGGER.warn("Message not handled on server: {}", msg::class)
                }
            }
            EnvType.CLIENT -> {
                Pregxxy.LOGGER.debug("Client received packet: {}", this)
                when (msg) {
                    is PregxxyMessageS2C -> msg.applyOnClient(ctx)
                    else -> Pregxxy.LOGGER.warn("Message not handled on client: {}", msg::class)
                }
            }
        }
    }

    fun register(channel: NetworkChannel) {
        channel.register(type, { msg, buf -> msg.encode(buf) }, ::decode, ::apply)
    }
}
