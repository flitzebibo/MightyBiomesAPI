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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.cybermaxke.mighty.biome.api.treasure.TreasureItem;
import net.minecraft.server.v1_6_R3.StructurePieceTreasure;

public class SimpleTreasureList implements List<TreasureItem> {
	private final Field field;

	public SimpleTreasureList(Field field) {
		this.field = field;
	}

	@Override
	public boolean add(TreasureItem e) {
		List<StructurePieceTreasure> list = this.getValuesList();
		boolean succes = list.add(((SimpleTreasureItem) e).getHandle());
		this.setValuesList(list);
		return succes;
	}

	@Override
	public void add(int index, TreasureItem e) {
		List<StructurePieceTreasure> list = this.getValuesList();
		list.add(index, ((SimpleTreasureItem) e).getHandle());
		this.setValuesList(list);
	}

	@Override
	public boolean addAll(Collection<? extends TreasureItem> c) {
		List<StructurePieceTreasure> list = this.getValuesList();
		list.addAll(this.getTreasurePiecesList(c));
		this.setValuesList(list);
		return c.size() != 0;
	}

	@Override
	public boolean addAll(int index, Collection<? extends TreasureItem> c) {
		List<StructurePieceTreasure> list = this.getValuesList();
		list.addAll(index, this.getTreasurePiecesList(c));
		this.setValuesList(list);
		return c.size() != 0;
	}

	@Override
	public void clear() {
		this.setValuesArray(new StructurePieceTreasure[] {});
	}

	@Override
	public boolean contains(Object o) {
		return this.indexOf(o) > 0;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.getTreasuresList(this.getValuesList()).containsAll(c);
	}

	@Override
	public TreasureItem get(int index) {
		return this.getTreasuresList(this.getValuesList()).get(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.getTreasuresList(this.getValuesList()).indexOf(o);
    }

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public Iterator<TreasureItem> iterator() {
		return this.listIterator(0);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.getTreasuresList(this.getValuesList()).lastIndexOf(o);
	}

	@Override
	public ListIterator<TreasureItem> listIterator() {
		return this.listIterator(0);
	}

	@Override
	public ListIterator<TreasureItem> listIterator(final int index) {
		return new ListIterator<TreasureItem>() {
			private List<TreasureItem> list = SimpleTreasureList.this
					.getTreasuresList(SimpleTreasureList.this.getValuesList());
			private ListIterator<TreasureItem> it = this.list.listIterator(index);

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
				SimpleTreasureList.this.setValuesList(
						SimpleTreasureList.this.getTreasurePiecesList(this.list));
			}

		};
	}

	@Override
	public boolean remove(Object o) {
		List<TreasureItem> list = this.getTreasuresList(this.getValuesList());
		boolean succes = list.remove(o);
		this.setValuesList(this.getTreasurePiecesList(list));
		return succes;
	}

	@Override
	public TreasureItem remove(int index) {
		List<TreasureItem> list = this.getTreasuresList(this.getValuesList());
		TreasureItem old = list.remove(index);
		this.setValuesList(this.getTreasurePiecesList(list));
		return old;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		List<TreasureItem> list = this.getTreasuresList(this.getValuesList());
		boolean succes = list.removeAll(c);
		this.setValuesList(this.getTreasurePiecesList(list));
		return succes;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		List<TreasureItem> list = this.getTreasuresList(this.getValuesList());
		boolean succes = list.retainAll(c);
		this.setValuesList(this.getTreasurePiecesList(list));
		return succes;
	}

	@Override
	public SimpleTreasureItem set(int index, TreasureItem element) {
		List<StructurePieceTreasure> list = this.getValuesList();
		StructurePieceTreasure old = list.set(index, element == null ? null :
			((SimpleTreasureItem) element).getHandle());
		this.setValuesList(list);
		return old == null ? null : new SimpleTreasureItem(old);
	}

	@Override
	public int size() {
		return this.getValuesArray().length;
	}

	@Override
	public List<TreasureItem> subList(int fromIndex, int toIndex) {
		return this.getTreasuresList(this.getValuesList().subList(fromIndex, toIndex));
	}

	@Override
	public Object[] toArray() {
		return this.getTreasuresList(this.getValuesList()).toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.getTreasuresList(this.getValuesList()).toArray(a);
	}

	public List<TreasureItem> getTreasuresList(Collection<?> list) {
		List<TreasureItem> list1 = new ArrayList<TreasureItem>();

		for (Object item : list) {
			list1.add(item instanceof StructurePieceTreasure ?
					new SimpleTreasureItem((StructurePieceTreasure) item) : null);
		}

		return list1;
	}

	public List<StructurePieceTreasure> getTreasurePiecesList(Collection<?> list) {
		List<StructurePieceTreasure> list1 = new ArrayList<StructurePieceTreasure>();

		for (Object item : list) {
			list1.add(item instanceof SimpleTreasureItem ?
					((SimpleTreasureItem) item).getHandle() : null);
		}

		return list1;
	}

	public void setValuesList(List<StructurePieceTreasure> list) {
		this.setValuesArray(list.toArray(new StructurePieceTreasure[] {}));
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

	public List<StructurePieceTreasure> getValuesList() {
		List<StructurePieceTreasure> list = new ArrayList<StructurePieceTreasure>();
		list.addAll(Arrays.asList(this.getValuesArray()));
		return list;
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