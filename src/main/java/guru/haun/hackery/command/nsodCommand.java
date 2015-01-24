package guru.haun.hackery.command;

import guru.haun.hackery.HackeryMod;
import guru.haun.hackery.network.setBSODPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by KJ4IPS on 5/30/2014.
 */
public class nsodCommand extends CommandBase {

    @Override
    public String getCommandName() {
        //noinspection SpellCheckingInspection
        return "nsod";
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return "commands.nsod.usage";
    }

    @Override
    public int getRequiredPermissionLevel(){
        return 3;
    }

    @Override
    public void processCommand(ICommandSender plr, String[] args) {
        if (!plr.getEntityWorld().isRemote) {
            if(args.length != 2){
                plr.addChatMessage(new ChatComponentText("commands.nsod.usage"));
                return;
            }
            EntityPlayerMP vicitm = null;
            try {
                vicitm = getPlayer(plr,args[0]);
            } catch (PlayerNotFoundException e) {
                e.printStackTrace();
                return;
            }
            if(vicitm == null){
                plr.addChatMessage(new ChatComponentText("commands.nsod.noexist").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }
            Boolean value = Boolean.parseBoolean(args[1]);
            if(value == null){
                plr.addChatMessage(new ChatComponentText("commands.nsod.usage"));
            }
            HackeryMod.channel.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
            HackeryMod.channel.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(vicitm);
            HackeryMod.channel.get(Side.SERVER).writeAndFlush(new setBSODPacket(value));
        }
    }
}
