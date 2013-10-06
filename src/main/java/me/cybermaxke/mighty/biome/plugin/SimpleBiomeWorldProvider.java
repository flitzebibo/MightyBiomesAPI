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
package me.cybermaxke.mighty.biome.plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.server.v1_6_R3.WorldProvider;
import net.minecraft.server.v1_6_R3.WorldProviderNormal;

public class SimpleBiomeWorldProvider extends WorldProviderNormal {
	private int seaLevel;

	public SimpleBiomeWorldProvider(WorldProvider old) {
		this.seaLevel = old.getSeaLevel();

		try {
			for (Field field : WorldProvider.class.getDeclaredFields()) {
				field.setAccessible(true);

				if (!Modifier.isStatic(field.getModifiers())) {
					field.set(this, field.get(old));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getSeaLevel() {
		return this.seaLevel;
	}

	public void setSeaLevel(int seaLevel) {
		this.seaLevel = seaLevel;
	}
}