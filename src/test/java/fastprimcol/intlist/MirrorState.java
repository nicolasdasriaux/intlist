package fastprimcol.intlist;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.Functions;
import net.jqwik.api.Tuple;
import net.jqwik.api.state.Action;
import net.jqwik.api.state.Transformer;

import java.util.Random;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

record MirrorState(IntList.Builder intListBuilder, IntList intList) {
	boolean isNonEmpty() {
		return intList.size() > 0;
	}

	boolean isReasonablySized() {
		return intList.size() < 500;
	}

	static Transformer<MirrorState> size() {
		return Transformer.transform("size", state -> {
			assertThat(state.intListBuilder().size())
					.isEqualTo(state.intList().size());

			return state;
		});
	}

	static Transformer<MirrorState> get(int index) {
		return Transformer.transform("get", state -> {
			assertThat(state.intListBuilder().get(index))
					.isEqualTo(state.intList().get(index));

			return state;
		});
	}

	static Transformer<MirrorState> shuffle(Supplier<Random> randomSupplier) {
		return Transformer.transform("shuffle", state -> {
			return new MirrorState(
					state.intListBuilder().shuffle(randomSupplier.get()),
					state.intList().shuffle(randomSupplier.get())
			);
		});
	}

	static Transformer<MirrorState> set(int index, int value) {
		return Transformer.transform("set", state -> {
			return new MirrorState(
					state.intListBuilder().set(index, value),
					state.intList().set(index, value)
			);
		});
	}

	static Transformer<MirrorState> swap(int index1, int index2) {
		return Transformer.transform("swap", state -> {
			return new MirrorState(
					state.intListBuilder().swap(index1, index2),
					state.intList().swap(index1, index2)
			);
		});
	}

	static Transformer<MirrorState> replace(int value, int newValue) {
		return Transformer.transform("replace", state -> {
			return new MirrorState(
					state.intListBuilder().replace(value, newValue),
					state.intList().replace(value, newValue)
			);
		});
	}

	static Transformer<MirrorState> append(int value) {
		return Transformer.transform("append", state -> {
			return new MirrorState(
					state.intListBuilder().append(value),
					state.intList().append(value)
			);
		});
	}

	static Transformer<MirrorState> appendAll(IntList values) {
		return Transformer.transform("appendAll", state -> {
			return new MirrorState(
					state.intListBuilder().appendAll(values),
					state.intList().appendAll(values)
			);
		});
	}

	static Transformer<MirrorState> prepend(int value) {
		return Transformer.transform("prepend", state -> {
			return new MirrorState(
					state.intListBuilder().prepend(value),
					state.intList().prepend(value)
			);
		});
	}

	static Transformer<MirrorState> prependAll(IntList values) {
		return Transformer.transform("prependAll", state -> {
			return new MirrorState(
					state.intListBuilder().prependAll(values),
					state.intList().prependAll(values)
			);
		});
	}

	static Transformer<MirrorState> insert(int index, int value) {
		return Transformer.transform("insert", state -> {
			return new MirrorState(
					state.intListBuilder().insert(index, value),
					state.intList().insert(index, value)
			);
		});
	}

	static Transformer<MirrorState> insertAll(int index, IntList values) {
		return Transformer.transform("insertAll", state -> {
			return new MirrorState(
					state.intListBuilder().insertAll(index, values),
					state.intList().insertAll(index, values)
			);
		});
	}

	static Transformer<MirrorState> remove(int value) {
		return Transformer.transform("remove", state -> {
			return new MirrorState(
					state.intListBuilder().remove(value),
					state.intList().remove(value)
			);
		});
	}

	static Transformer<MirrorState> removeAll(IntList values) {
		return Transformer.transform("removeAll", state -> {
			return new MirrorState(
					state.intListBuilder().removeAll(values),
					state.intList().removeAll(values)
			);
		});
	}

	static Transformer<MirrorState> removeAt(int index) {
		return Transformer.transform("removeAt", state -> {
			return new MirrorState(
					state.intListBuilder().removeAt(index),
					state.intList().removeAt(index)
			);
		});
	}

	static Transformer<MirrorState> take(int count) {
		return Transformer.transform("take", state -> {
			return new MirrorState(
					state.intListBuilder().take(count),
					state.intList().take(count)
			);
		});
	}

	static Transformer<MirrorState> drop(int count) {
		return Transformer.transform("drop", state -> {
			return new MirrorState(
					state.intListBuilder().drop(count),
					state.intList().drop(count)
			);
		});
	}

	static Transformer<MirrorState> takeRight(int count) {
		return Transformer.transform("takeRight", state -> {
			return new MirrorState(
					state.intListBuilder().takeRight(count),
					state.intList().takeRight(count)
			);
		});
	}

	static Transformer<MirrorState> dropRight(int count) {
		return Transformer.transform("dropRight", state -> {
			return new MirrorState(
					state.intListBuilder().dropRight(count),
					state.intList().dropRight(count)
			);
		});
	}

	static Transformer<MirrorState> takeWhile(IntPredicate predicate) {
		return Transformer.transform("takeWhile", state -> {
			return new MirrorState(
					state.intListBuilder().takeWhile(predicate),
					state.intList().takeWhile(predicate)
			);
		});
	}

	static Transformer<MirrorState> dropWhile(IntPredicate predicate) {
		return Transformer.transform("dropWhile", state -> {
			return new MirrorState(
					state.intListBuilder().dropWhile(predicate),
					state.intList().dropWhile(predicate)
			);
		});
	}

	static Transformer<MirrorState> map(IntList.IntToIntFunction function) {
		return Transformer.transform("map", state -> {
			return new MirrorState(
					state.intListBuilder().map(function),
					state.intList().map(function)
			);
		});
	}

	static Transformer<MirrorState> flatMap(IntFunction<IntList> function) {
		return Transformer.transform("flatMap", state -> {
			return new MirrorState(
					state.intListBuilder().flatMap(function),
					state.intList().flatMap(function)
			);
		});
	}

	static Transformer<MirrorState> filter(IntPredicate predicate) {
		return Transformer.transform("filter", state -> {
			return new MirrorState(
					state.intListBuilder().filter(predicate),
					state.intList().filter(predicate)
			);
		});
	}

	Arbitrary<Integer> indexArbitrary() {
		return Arbitraries.integers()
				.between(0, intList.size() - 1);
	}

	Arbitrary<Integer> itemArbitrary() {
		return indexArbitrary().map(intList::get);
	}

	static Arbitrary<Integer> valueArbitrary() {
		return Arbitraries.integers()
				.between(-150, 150);
	}

	private static Arbitrary<IntList> intListArbitrary(Arbitrary<Integer> valueArbitrary, int maxSize) {
		return valueArbitrary
				.array(int[].class)
				.ofMinSize(0)
				.ofMaxSize(maxSize)
				.map(IntList::of);
	}

	interface IndependentAction extends Action.Independent<MirrorState> {
	}

	interface DependentAction extends Action.Dependent<MirrorState> {
	}

	static class SizeAction implements IndependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			return Arbitraries.of(MirrorState.size());
		}
	}

	static class GetAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> indexArbitrary = state.indexArbitrary();
			return indexArbitrary.map(MirrorState::get);
		}
	}

	static class ShuffleAction implements IndependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<Supplier<Random>> randomSupplierArbitrary = Arbitraries.integers()
					.map(seed -> () -> new Random(seed));

			return randomSupplierArbitrary.map(MirrorState::shuffle);
		}
	}

	static class SetAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> indexArbitrary = state.indexArbitrary();
			final Arbitrary<Integer> valueArbitrary = valueArbitrary();
			return Combinators.combine(indexArbitrary, valueArbitrary).as(MirrorState::set);
		}
	}

	static class SwapAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> indexArbitrary = state.indexArbitrary();
			return Combinators.combine(indexArbitrary, indexArbitrary).as(MirrorState::swap);
		}
	}

	static class ReplaceAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> itemArbitrary = state.itemArbitrary();
			final Arbitrary<Integer> valueArbitrary = valueArbitrary();
			return Combinators.combine(itemArbitrary, valueArbitrary).as(MirrorState::replace);
		}
	}

	static class AppendAction implements IndependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isReasonablySized();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<Integer> valueArbitrary = valueArbitrary();
			return valueArbitrary.map(MirrorState::append);
		}
	}

	static class AppendAllAction implements IndependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isReasonablySized();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<IntList> valuesArbitrary = intListArbitrary(valueArbitrary(), 5);
			return valuesArbitrary.map(MirrorState::appendAll);
		}
	}

	static class PrependAction implements IndependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isReasonablySized();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<Integer> valueArbitrary = valueArbitrary();
			return valueArbitrary.map(MirrorState::prepend);
		}
	}

	static class PrependAllAction implements IndependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isReasonablySized();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<IntList> valuesArbitrary = intListArbitrary(valueArbitrary(), 5);
			return valuesArbitrary.map(MirrorState::prependAll);
		}
	}

	static class InsertAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty() && state.isReasonablySized();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> indexArbitrary = state.indexArbitrary();
			final Arbitrary<Integer> valueArbitrary = valueArbitrary();
			return Combinators.combine(indexArbitrary, valueArbitrary).as(MirrorState::insert);
		}
	}

	static class InsertAllAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty() && state.isReasonablySized();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> indexArbitrary = state.indexArbitrary();
			final Arbitrary<IntList> valuesArbitrary = intListArbitrary(valueArbitrary(), 5);
			return Combinators.combine(indexArbitrary, valuesArbitrary).as(MirrorState::insertAll);
		}
	}

	static class RemoveAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> valueArbitrary = Arbitraries.frequencyOf(
					Tuple.of(8, state.itemArbitrary()),
					Tuple.of(1, valueArbitrary())
			);

			return valueArbitrary.map(MirrorState::remove);
		}
	}

	static class RemoveAllAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> valueArbitrary = Arbitraries.frequencyOf(
					Tuple.of(5, state.itemArbitrary()),
					Tuple.of(1, valueArbitrary())
			);

			final Arbitrary<IntList> valuesArbitrary = intListArbitrary(valueArbitrary, 5);

			return valuesArbitrary.map(MirrorState::removeAll);
		}
	}

	static class RemoveAtAction implements DependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isNonEmpty();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> indexArbitrary = state.indexArbitrary();
			return indexArbitrary.map(MirrorState::removeAt);
		}
	}

	static class TakeAction implements DependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> countArbitrary = Arbitraries.integers()
					.between(0, state.intList().size() + 1);

			return countArbitrary.map(MirrorState::take);
		}
	}

	static class DropAction implements DependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> countArbitrary = Arbitraries.integers()
					.between(0, state.intList().size() + 1);

			return countArbitrary.map(MirrorState::drop);
		}
	}

	static class TakeRightAction implements DependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> countArbitrary = Arbitraries.integers()
					.between(0, state.intList().size() + 1);

			return countArbitrary.map(MirrorState::takeRight);
		}
	}

	static class DropRightAction implements DependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer(MirrorState state) {
			final Arbitrary<Integer> countArbitrary = Arbitraries.integers()
					.between(0, state.intList().size() + 1);

			return countArbitrary.map(MirrorState::dropRight);
		}
	}

	static class TakeWhileAction implements IndependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<Boolean> booleanArbitrary = Arbitraries.integers().map(i -> i > 0);
			Arbitrary<IntPredicate> intPredicateArbitrary = Functions.function(IntPredicate.class).returning(booleanArbitrary);
			return intPredicateArbitrary.map(MirrorState::takeWhile);
		}
	}

	static class DropWhileAction implements IndependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<Boolean> booleanArbitrary = Arbitraries.integers().map(i -> i > 0);
			Arbitrary<IntPredicate> intPredicateArbitrary = Functions.function(IntPredicate.class).returning(booleanArbitrary);
			return intPredicateArbitrary.map(MirrorState::dropWhile);
		}
	}

	static class MapAction implements IndependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<IntList.IntToIntFunction> intToIntFunctionArbitrary = Functions.function(IntList.IntToIntFunction.class).returning(valueArbitrary());
			return intToIntFunctionArbitrary.map(MirrorState::map);
		}
	}

	static class FlatMapAction implements IndependentAction {
		@Override
		public boolean precondition(MirrorState state) {
			return state.isReasonablySized();
		}

		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<IntList> intListArbitrary = intListArbitrary(valueArbitrary(), 5);
			final Arbitrary<IntFunction<IntList>> intToIntListFunctionArbitrary = Functions.function(IntFunction.class).returning(intListArbitrary);
			return intToIntListFunctionArbitrary.map(MirrorState::flatMap);
		}
	}

	static class FilterAction implements IndependentAction {
		@Override
		public Arbitrary<Transformer<MirrorState>> transformer() {
			final Arbitrary<Boolean> booleanArbitrary = Arbitraries.integers().between(1, 10).map(i -> i <= 3);
			final Arbitrary<IntPredicate> predicateArbitrary = Functions.function(IntPredicate.class).returning(booleanArbitrary);
			return predicateArbitrary.map(MirrorState::filter);
		}
	}
}
