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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.cybermaxke.mighty.biome.api.treasure.TreasureItem;

import net.minecraft.server.v1_7_R1.StructurePieceTreasure;

public class SimpleTreasureList implements List<TreasureItem> {
	private final Field field;
	private final List<TreasureItem> treasures;

	public SimpleTreasureList(Field field) {
		this.field = field;
		this.treasures = new ArrayList<TreasureItem>(this.getTreasuresList());
	}

	@Override
	public boolean add(TreasureItem e) {
		boolean succes = this.treasures.add(e);
		this.update();
		return succes;
	}

	@Override
	public void add(int index, TreasureItem e) {
		this.treasures.add(index, e);
		this.update();
	}

	@Override
	public boolean addAll(Collection<? extends TreasureItem> c) {
		this.treasures.addAll(c);
		this.update();
		return c.size() != 0;
	}

	@Override
	public boolean addAll(int index, Collection<? extends TreasureItem> c) {
		this.treasures.addAll(index, c);
		this.update();
		return c.size() != 0;
	}

	@Override
	public void clear() {
		this.treasures.clear();
		this.update();
	}

	@Override
	public boolean contains(Object o) {
		return this.treasures.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.treasures.containsAll(c);
	}

	@Override
	public TreasureItem get(int index) {
		return this.treasures.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.treasures.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return this.treasures.isEmpty();
	}

	@Override
	public Iterator<TreasureItem> iterator() {
		return this.listIterator(0);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.treasures.lastIndexOf(o);
	}

	@Override
	public ListIterator<TreasureItem> listIterator() {
		return this.listIterator(0);
	}

	@Override
	public ListIterator<TreasureItem> listIterator(final int index) {
		return new ListIterator<TreasureItem>() {
			private final List<TreasureItem> list = SimpleTreasureList.this.treasures;
			private final ListIterator<TreasureItem> it = this.list.listIterator(index);

			@Override
			public void add(TreasureItem item) {
				this.it.add(item);
				this.update();
			}

			@Override
			public boolean hasNext() {
				return this.it.hasNext();
			}

			@Override
			public boolean hasPrevious() {
				return this.it.hasPrevious();
			}

			@Override
			public TreasureItem next() {
				return this.it.next();
			}

			@Override
			public int nextIndex() {
				return this.it.nextIndex();
			}

			@Override
			public TreasureItem previous() {
				return this.it.previous();
			}

			@Override
			public int previousIndex() {
				return this.it.previousIndex();
			}

			@Override
			public void remove() {
				this.it.remove();
				this.update();
			}

			@Override
			public void set(TreasureItem item) {
				this.it.set(item);
				this.update();
			}

			public void update() {
				SimpleTreasureList.this.update();
			}

		};
	}

	@Override
	public boolean remove(Object o) {
		boolean succes = this.treasures.remove(o);
		this.update();
		return succes;
	}

	@Override
	public TreasureItem remove(int index) {
		TreasureItem old = this.treasures.remove(index);
		this.update();
		return old;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean succes = this.treasures.removeAll(c);
		this.update();
		return succes;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean succes = this.treasures.retainAll(c);
		this.update();
		return succes;
	}

	@Override
	public TreasureItem set(int index, TreasureItem element) {
		TreasureItem old = this.treasures.set(index, element);
		this.update();
		return old;
	}

	@Override
	public int size() {
		return this.treasures.size();
	}

	@Override
	public List<TreasureItem> subList(int fromIndex, int toIndex) {
		return this.treasures.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return this.treasures.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.treasures.toArray(a);
	}

	public void update() {
		List<StructurePieceTreasure> list = new ArrayList<StructurePieceTreasure>();

		for (TreasureItem item : this.treasures) {
			list.add(item instanceof SimpleTreasureItem ?
					((SimpleTreasureItem) item).getHandle() : null);
		}

		this.setValuesArray(list.toArray(new StructurePieceTreasure[] {}));
	}

	public List<TreasureItem> getTreasuresList() {
		List<TreasureItem> list = new ArrayList<TreasureItem>();

		for (StructurePieceTreasure item : this.getValuesArray()) {
			list.add(item instanceof StructurePieceTreasure ? new SimpleTreasureItem(item) : null);
		}

		return list;
	}

	public void setValuesArray(StructurePieceTreasure[] values) {
		try {
			this.field.setAccessible(true);

			Field mfield = Field.class.getDeclaredField("modifiers");
			mfield.setAccessible(true);
			mfield.set(this.field, this.field.getModifiers() & ~Modifier.FINAL);

			this.field.set(null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StructurePieceTreasure[] getValuesArray() {
		try {
			this.field.setAccessible(true);
			return (StructurePieceTreasure[]) this.field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}