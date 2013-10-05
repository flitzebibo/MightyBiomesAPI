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

import net.minecraft.server.v1_6_R3.GenLayer;
import net.minecraft.server.v1_6_R3.GenLayerIsland;
import net.minecraft.server.v1_6_R3.GenLayerMushroomShore;
import net.minecraft.server.v1_6_R3.GenLayerSwampRivers;
import net.minecraft.server.v1_6_R3.GenLayerZoom;

public class SimpleGenLayerZoom2 extends GenLayer {
	private int amount;

	public SimpleGenLayerZoom2(long baseSeed, GenLayer parent, int amount) {
		super(baseSeed);
		this.a = parent;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return this.amount;
	}

	@Override
	public int[] a(int x, int z, int height, int width) {
		GenLayer layer = this.a;

		for (int i = 0; i < this.amount; i++) {
			layer = new GenLayerZoom(1000L + i, layer);

			if (i == 0) {
				layer = new GenLayerIsland(3L, layer);
			}

			if (i == 1) {
				layer = new GenLayerMushroomShore(1000L, layer);
			}

			if (i == 1) {
				layer = new GenLayerSwampRivers(1000L, layer);
			}
		}

		return layer.a(x, z, height, height);
	}
}