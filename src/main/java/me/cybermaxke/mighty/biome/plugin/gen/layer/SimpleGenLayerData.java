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

public class SimpleGenLayerData extends GenLayer {
	private int size;

	public SimpleGenLayerData(GenLayer parent, int size) {
		super(5L);
		this.a = parent;
		this.size = size;
	}

	@Override
	public int[] a(int ii, int jj, int kk, int ll) {
		return this.a.a(ii, jj, kk, ll);
	}

	@Override
	public void a(long long1) {
		this.a.a(long1);
	}

	@Override
	public void a(long long1, long long2) {
		this.a.a(long1, long2);
	}

	public int getSize() {
		return this.size;
	}
}