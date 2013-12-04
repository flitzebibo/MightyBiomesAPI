/**
 * 
 * This software is part of the MightyBiomesAPI
 * 
 * Copyright (c) 2013 Cybermaxke
 * 
 * MightyBiomesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * MightyBiomesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MightyBiomesAPI. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.mighty.biome.plugin.gen.layer;

import net.minecraft.server.v1_7_R1.GenLayer;
import net.minecraft.server.v1_7_R1.GenLayerIsland;
import net.minecraft.server.v1_7_R1.GenLayerMushroomShore;
import net.minecraft.server.v1_7_R1.GenLayerZoom;

public class SimpleGenLayerZoom2 extends GenLayer {
	private final SimpleGenData data;

	public SimpleGenLayerZoom2(long baseSeed, GenLayer parent, SimpleGenData data) {
		super(baseSeed);
		this.a = parent;
		this.data = data;
	}

	@Override
	public int[] a(int x, int z, int height, int width) {
		GenLayer layer = this.a;

		for (int i = 0; i < this.data.getSize(); i++) {
			layer = new GenLayerZoom(1000L + i, layer);

			if (i == 0) {
				layer = new GenLayerIsland(3L, layer);
			}

			if (i == 1) {
				layer = new GenLayerMushroomShore(1000L, layer);
			}
		}

		return layer.a(x, z, height, width);
	}
}