package org.example;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Group;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.IntRange;

import static org.assertj.core.api.Assertions.*;

class IntListTest {
	@Group
	class Features {
		@Example
		void size() {
			assertThat(IntList.of(10, 20, 30).size()).isEqualTo(3);
		}

		@Example
		void get() {
			final IntList list = IntList.of(10, 20, 30);
			assertThat(list.get(0)).isEqualTo(10);
			assertThat(list.get(1)).isEqualTo(20);
			assertThat(list.get(2)).isEqualTo(30);
		}

		@Example
		void contains() {


		}

		@Example
		void set() {
			final IntList list = IntList.of(10, 20, 30);
			assertThat(list.set(0, 15)).isEqualTo(IntList.of(15, 20, 30));
			assertThat(list.set(1, 25)).isEqualTo(IntList.of(10, 25, 30));
			assertThat(list.set(2, 35)).isEqualTo(IntList.of(10, 20, 35));
		}

		@Example
		void swap() {
			final IntList list = IntList.of(10, 20, 30);
			assertThat(list.swap(0, 1)).isEqualTo(IntList.of(20, 10, 30));
			assertThat(list.swap(1, 0)).isEqualTo(IntList.of(20, 10, 30));
			assertThat(list.swap(0, 2)).isEqualTo(IntList.of(30, 20, 10));
			assertThat(list.swap(2, 0)).isEqualTo(IntList.of(30, 20, 10));
			assertThat(list.swap(1, 2)).isEqualTo(IntList.of(10, 30, 20));
			assertThat(list.swap(2, 1)).isEqualTo(IntList.of(10, 30, 20));
		}

		@Example
		void replace() {
			assertThat(IntList.of(10, 20, 30).replace(20, 25)).isEqualTo(IntList.of(10, 25, 30));
		}

		@Example
		void reverse() {
			assertThat(IntList.of(10, 20, 30).reverse()).isEqualTo(IntList.of(30, 20, 10));
			assertThat(IntList.of(10, 20, 30, 40).reverse()).isEqualTo(IntList.of(40, 30, 20, 10));
		}

		@Example
		void sort() {
			assertThat(IntList.of(20, 10, 30).sort()).isEqualTo(IntList.of(10, 20, 30));
		}

		@Example
		void sortDesc() {
			assertThat(IntList.of(20, 10, 30).sortDesc()).isEqualTo(IntList.of(30, 20, 10));
		}

		@Example
		void add() {
			assertThat(IntList.of(10, 20, 30).add(35)).isEqualTo(IntList.of(10, 20, 30, 35));
		}

		@Example
		void addFirst() {
			assertThat(IntList.of(10, 20, 30).addFirst(5)).isEqualTo(IntList.of(5, 10, 20, 30));
		}

		@Example
		void addAll() {
			assertThat(IntList.of(10, 20, 30).addAll(IntList.of(31, 32, 33))).isEqualTo(IntList.of(10, 20, 30, 31, 32, 33));
		}

		@Example
		void addAllFirst() {
			final IntList list = IntList.of(10, 20, 30);
			assertThat(list.addAllFirst(IntList.of(1, 2, 3))).isEqualTo(IntList.of(1, 2, 3, 10, 20, 30));
		}

		@Example
		void retainAll() {
			assertThat(IntList.of(5, 10, 15, 20, 25, 30, 35).retainAll(IntList.of(10, 20, 30, 40))).isEqualTo(IntList.of(10, 20, 30));
		}

		@Example
		void remove() {
			assertThat(IntList.of(10, 20, 30, 40).remove(30)).isEqualTo(IntList.of(10, 20, 40));
		}

		@Example
		void removeAll() {
			assertThat(IntList.of(10, 20, 30, 40, 50).removeAll(IntList.of(20, 40))).isEqualTo(IntList.of(10, 30, 50));
		}

		@Example
		void map() {
			assertThat(IntList.of(10, 20, 30).map(i -> i + 5)).isEqualTo(IntList.of(15, 25, 35));
		}

		@Example
		void filter() {
			assertThat(IntList.of(-10, 10, -20, 20, -30, 30, -40).filter(i -> i > 0)).isEqualTo(IntList.of(10, 20, 30));
		}

		@Example
		void delete() {
			final IntList list = IntList.of(10, 20, 30);
			assertThat(list.delete(0)).isEqualTo(IntList.of(20, 30));
			assertThat(list.delete(1)).isEqualTo(IntList.of(10, 30));
			assertThat(list.delete(2)).isEqualTo(IntList.of(10, 20));
		}

		@Example
		void insert() {
			final IntList list = IntList.of(10, 20, 30);
			assertThat(list.insert(0, 5)).isEqualTo(IntList.of(5, 10, 20, 30));
			assertThat(list.insert(1, 15)).isEqualTo(IntList.of(10, 15, 20, 30));
			assertThat(list.insert(2, 25)).isEqualTo(IntList.of(10, 20, 25, 30));
			assertThat(list.insert(3, 35)).isEqualTo(IntList.of(10, 20, 30, 35));
		}

		@Example
		void insertAll() {
			final IntList list = IntList.of(10, 20, 30);
			assertThat(list.insertAll(0, IntList.of(1, 2, 3))).isEqualTo(IntList.of(1, 2, 3, 10, 20, 30));
			assertThat(list.insertAll(1, IntList.of(11, 12, 13))).isEqualTo(IntList.of(10, 11, 12, 13, 20, 30));
			assertThat(list.insertAll(2, IntList.of(21, 22, 23))).isEqualTo(IntList.of(10, 20, 21, 22, 23, 30));
			assertThat(list.insertAll(3, IntList.of(31, 32, 33))).isEqualTo(IntList.of(10, 20, 30, 31, 32, 33));
		}

		@Example
		void equals() {
			assertThat(IntList.of(10, 20, 30)).isEqualTo(IntList.of(10, 20, 30));
			assertThat(IntList.of(10, 20, -30)).isNotEqualTo(IntList.of(10, 20, 30));
		}

		@Property
		void _hashCode(
				@ForAll("items") IntList a,
				@ForAll("items") IntList b) {

			assertThat(
					a.equals(b) && a.hashCode() == b.hashCode() ||
					!a.equals(b)
			).isTrue();
		}

		@Example
		void _toString() {
			assertThat(IntList.of(10, 20, 30).toString()).isEqualTo("[10, 20, 30]");
		}

		@Example
		void _compareTo() {
			assertThat(IntList.of(10, 20, 30).compareTo(IntList.of(10, 20, 30))).isZero();
			assertThat(IntList.of(10, 20).compareTo(IntList.of(10, 20, 30))).isNegative();
			assertThat(IntList.of(10, 20, 30).compareTo(IntList.of(10, 20, 35))).isNegative();
			assertThat(IntList.of(10, 20, 30).compareTo(IntList.of(10, 20))).isPositive();
			assertThat(IntList.of(10, 20, 35).compareTo(IntList.of(10, 20, 30))).isPositive();
		}
	}

	@Group
	class Robustness {
		@Property
		void add(
				@ForAll("item") int a1,
				@ForAll("item") int a2,
				@ForAll("items") IntList b,
				@ForAll("item") int c1,
				@ForAll("item") int c2,
				@ForAll @IntRange(min = 0, max = 10) int lead,
				@ForAll @IntRange(min = 0, max = 10) int trail) {

			final IntList l1 = b.toBuilder(lead, trail)
					.addFirst(a2)
					.addFirst(a1)
					.add(c1)
					.add(c2)
					.build();

			final IntList l2 = b.toBuilder(lead, trail)
					.add(c1)
					.add(c2)
					.addFirst(a2)
					.addFirst(a1)
					.build();

			assertThat(l1).isEqualTo(l2);
		}

		@Property
		void addAll(
				@ForAll("items") IntList a,
				@ForAll("items") IntList b,
				@ForAll("items") IntList c,
				@ForAll @IntRange(min = 0, max = 10) int lead,
				@ForAll @IntRange(min = 0, max = 10) int trail) {

			final IntList l1 = a.toBuilder(lead, trail)
					.addAll(b)
					.addAll(c)
					.build();

			final IntList l2 = c.toBuilder(lead, trail)
					.addAllFirst(b)
					.addAllFirst(a)
					.build();

			assertThat(l1).isEqualTo(l2);
		}
	}

	@Provide
	Arbitrary<Integer> item() {
		return Arbitraries.integers()
				.between(1, 50);
	}

	@Provide
	Arbitrary<IntList> items() {
		return item()
				.array(int[].class)
				.ofMinSize(0)
				.ofMaxSize(100)
				.map(IntList::of);
	}
}
