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
package me.cybermaxke.mighty.biome.plugin.treasure;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.bukkit.craftbukkit.v1_7_R1.inventory.CraftItemStack;

import net.minecraft.server.v1_7_R1.ItemStack;
import net.minecraft.server.v1_7_R1.StructurePieceTreasure;
import net.minecraft.server.v1_7_R1.WeightedRandomChoice;

import me.cybermaxke.mighty.biome.api.treasure.TreasureItem;
import me.cybermaxke.mighty.biome.plugin.utils.ReflectionUtils;

public class SimpleTreasureItem implements TreasureItem {
	private final StructurePieceTreasure handle;

	public SimpleTreasureItem(org.bukkit.inventory.ItemStack item, int weight,
			int minCount, int maxCount) {
		this.handle = new StructurePieceTreasure(null, weight, minCount, maxCount);
		this.setItem(item);
	}

	public SimpleTreasureItem(StructurePieceTreasure handle) {
		this.handle = handle;
	}

	@Override
	public CraftItemStack getItem() {
		ItemStack item = this.getItemHandle();
		return item == null ? null : CraftItemStack.asCraftMirror(item);
	}

	@Override
	public void setItem(org.bukkit.inventory.ItemStack item) {
		ReflectionUtils.setFieldObject(StructurePieceTreasure.class, this.handle, "b",
				item == null ? null : CraftItemStack.asNMSCopy(item));
	}

	@Override
	public int getWeight() {
		return ReflectionUtils.getFieldObject(WeightedRandomChoice.class,
				Integer.class, this.handle, "a");
	}

	@Override
	public void setWeight(int weight) {
		ReflectionUtils.setFieldObject(WeightedRandomChoice.class, this.handle, "a", weight);
	}

	@Override
	public int getMinCount() {
		return ReflectionUtils.getFieldObject(StructurePieceTreasure.class,
				Integer.class, this.handle, "c");
	}

	@Override
	public void setMinCount(int minCount) {
		ReflectionUtils.setFieldObject(StructurePieceTreasure.class, this.handle, "c", minCount);
	}

	@Override
	public int getMaxCount() {
		return ReflectionUtils.getFieldObject(StructurePieceTreasure.class,
				Integer.class, this.handle, "d");
	}

	@Override
	public void setMaxCount(int maxCount) {
		ReflectionUtils.setFieldObject(StructurePieceTreasure.class, this.handle, "d", maxCount);
	}

	@Override
	public SimpleTreasureItem clone() {
		return new SimpleTreasureItem(new StructurePieceTreasure(this.getItemHandle(),
				this.getWeight(), this.getMinCount(), this.getMaxCount()));
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TreasureItem)) {
			return false;
		}

		TreasureItem other = (TreasureItem) o;
		return new EqualsBuilder()
				.append(this.getItem(), other.getItem())
				.append(this.getWeight(), other.getWeight())
				.append(this.getMinCount(), other.getMinCount())
				.append(this.getMaxCount(), other.getMaxCount())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.getItem())
				.append(this.getWeight())
				.append(this.getMinCount())
				.append(this.getMaxCount())
				.toHashCode();
	}

	public StructurePieceTreasure getHandle() {
		return this.handle;
	}

	public ItemStack getItemHandle() {
		return ReflectionUtils.getFieldObject(StructurePieceTreasure.class,
				ItemStack.class, this.handle, "b");
	}
}