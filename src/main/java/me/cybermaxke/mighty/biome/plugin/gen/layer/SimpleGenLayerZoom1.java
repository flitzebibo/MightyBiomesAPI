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
import net.minecraft.server.v1_6_R3.GenLayerZoom;

public class SimpleGenLayerZoom1 extends GenLayer {
	private int amount;

	public SimpleGenLayerZoom1(long baseSeed, GenLayer parent, int amount) {
		super(baseSeed);
		this.a = parent;
		this.amount = amount;
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

		for (int i = 0; i < (this.amount + 2); i++) {
			layer = new GenLayerZoom(1000L + i, layer);
		}

		return layer.a(x, z, height, width);
	}
}