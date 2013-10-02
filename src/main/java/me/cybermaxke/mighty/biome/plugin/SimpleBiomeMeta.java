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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.bukkit.entity.LivingEntity;

import net.minecraft.server.v1_6_R3.BiomeMeta;
import net.minecraft.server.v1_6_R3.WeightedRandomChoice;

public class SimpleBiomeMeta extends me.cybermaxke.mighty.biome.api.BiomeMeta {
	private final BiomeMeta meta;

	public SimpleBiomeMeta(me.cybermaxke.mighty.biome.api.BiomeMeta meta) {
		super(null, 0, 0, 0);
		this.meta = new BiomeMeta(
				SimpleBiomePlugin.get().getEntityRegister().getMcEntity(meta.getEntity()),
				meta.getWeight(), meta.getMinGroupCount(), meta.getMaxGroupCount());
	}

	public SimpleBiomeMeta(BiomeMeta meta) {
		super(null, 0, 0, 0);
		this.meta = meta;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends LivingEntity> getEntity() {
		return (Class<? extends LivingEntity>) SimpleBiomePlugin.get().getEntityRegister()
				.getBukkitEntity(this.meta.b);
	}

	@Override
	public void setEntity(Class<? extends LivingEntity> entity) {
		this.meta.b = SimpleBiomePlugin.get().getEntityRegister().getMcEntity(entity);
	}

	@Override
	public int getWeight() {
		try {
			return this.getWeightField().getInt(this.meta);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public void setWeight(int weight) {
		try {
			this.getWeightField().set(this.meta, weight);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getMinGroupCount() {
		return this.meta.c;
	}

	@Override
	public void setMinGroupCount(int count) {
		this.meta.c = count;
	}

	@Override
	public int getMaxGroupCount() {
		return this.meta.d;
	}

	@Override
	public void setMaxGroupCount(int count) {
		this.meta.d = count;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SimpleBiomeMeta)) {
			return false;
		}

		SimpleBiomeMeta other = (SimpleBiomeMeta) o;
		return new EqualsBuilder()
				.append(this.getEntity(), other.getEntity())
				.append(this.getWeight(), other.getWeight())
				.append(this.getMinGroupCount(), other.getMinGroupCount())
				.append(this.getMaxGroupCount(), other.getMaxGroupCount())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(this.getEntity().hashCode())
				.append(this.getWeight())
				.append(this.getMinGroupCount())
				.append(this.getMaxGroupCount())
				.toHashCode();
	}

	public Field getWeightField() {
		try {
			Field field = WeightedRandomChoice.class.getDeclaredField("a");
			field.setAccessible(true);

			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public BiomeMeta getHandle() {
		return this.meta;
	}
}