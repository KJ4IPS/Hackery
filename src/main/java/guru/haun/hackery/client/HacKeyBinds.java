package guru.haun.hackery.client;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class HacKeyBinds {
	public static KeyBinding debugkey;
	
	public HacKeyBinds (){
		debugkey = new KeyBinding( "key.debug", Keyboard.KEY_APOSTROPHE, "key.categories.hackery");
		ClientRegistry.registerKeyBinding(debugkey);
	}
}
