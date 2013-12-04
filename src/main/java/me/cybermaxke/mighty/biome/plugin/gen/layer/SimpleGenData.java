package me.cybermaxke.mighty.biome.plugin.gen.layer;

import java.util.ArrayList;
import java.util.List;

import me.cybermaxke.mighty.biome.api.BiomeBase;

public class SimpleGenData {
	private List<BiomeBase> biomes = new ArrayList<BiomeBase>();
	private int size;

	public List<BiomeBase> getBiomes() {
		return this.biomes;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}