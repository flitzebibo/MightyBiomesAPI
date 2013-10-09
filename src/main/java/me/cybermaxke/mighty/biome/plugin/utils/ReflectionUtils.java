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
package me.cybermaxke.mighty.biome.plugin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionUtils {

	private ReflectionUtils() {

	}

	public static void copyFieldObjects(Class<?> clazz, Object from, Object to, boolean deep) {
		try {
			Field field1 = Field.class.getDeclaredField("modifiers");
			field1.setAccessible(true);

			if (deep) {
				while (clazz != null) {
					ReflectionUtils.copyFieldObjects(clazz, from, to, false);
					clazz = clazz.getSuperclass();
				}
			} else {
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);

					int modifiers = field.getModifiers();
					if (!Modifier.isStatic(modifiers)) {
						if (Modifier.isFinal(modifiers)) {
							field1.set(field, modifiers & ~Modifier.FINAL);
						}

						field.set(to, field.get(from));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}