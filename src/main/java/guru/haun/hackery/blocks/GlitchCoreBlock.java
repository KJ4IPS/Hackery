package guru.haun.hackery.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GlitchCoreBlock extends Block {

	public GlitchCoreBlock(Material mat) {
		super(mat);
		setBlockUnbreakable();
		setResistance(9000000);
		setUnlocalizedName("blockGlitchCore");
		setLightLevel(0.6F);
	}

}
