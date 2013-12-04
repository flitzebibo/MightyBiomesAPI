package me.cybermaxke.mighty.biome.plugin.gen.layer;

import net.minecraft.server.v1_7_R1.GenLayer;

public class SimpleGenLayerData extends GenLayer {
	private SimpleGenData data;

	public SimpleGenLayerData(long seed, GenLayer parent, SimpleGenData data) {
		super(seed);
		this.a = parent;
		this.data = data;
	}

	@Override
	public int[] a(int x, int z, int height, int width) {
		return this.a(x, z, height, width);
	}

	public SimpleGenData getData() {
		return this.data;
	}
}