package org.example;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Assume;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Group;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.state.ActionChain;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

class IntListTest {
    @Group
    class IntListFeatures {
        @Group
        class Permutations {
            @Example
            void permutations() {
                final List<IntList> actual = IntList.of(5, 6, 7, 8).permutations();

                assertThat(actual).containsExactlyInAnyOrder(
                        IntList.of(5, 6, 7, 8),
                        IntList.of(5, 6, 8, 7),
                        IntList.of(5, 7, 6, 8),
                        IntList.of(5, 7, 8, 6),
                        IntList.of(5, 8, 6, 7),
                        IntList.of(5, 8, 7, 6),
                        IntList.of(6, 5, 7, 8),
                        IntList.of(6, 5, 8, 7),
                        IntList.of(6, 7, 5, 8),
                        IntList.of(6, 7, 8, 5),
                        IntList.of(6, 8, 5, 7),
                        IntList.of(6, 8, 7, 5),
                        IntList.of(7, 5, 6, 8),
                        IntList.of(7, 5, 8, 6),
                        IntList.of(7, 6, 5, 8),
                        IntList.of(7, 6, 8, 5),
                        IntList.of(7, 8, 5, 6),
                        IntList.of(7, 8, 6, 5),
                        IntList.of(8, 5, 6, 7),
                        IntList.of(8, 5, 7, 6),
                        IntList.of(8, 6, 5, 7),
                        IntList.of(8, 6, 7, 5),
                        IntList.of(8, 7, 5, 6),
                        IntList.of(8, 7, 6, 5)
                );
            }

            @Example
            void permutations_n_1() {
                final IntList list1 = IntList.of(5);
                assertThat(list1.permutations()).containsExactly(list1);
            }

            @Example
            void permutations_n_0() {
                final IntList list0 = IntList.of();
                assertThat(list0.permutations()).containsExactly(list0);
            }
        }

        @Group
        class Combinations {
            @Example
            void combinations() {
                final List<IntList> actual = IntList.of(5, 6, 7, 8, 9).combinations(3);

                assertThat(actual).containsExactlyInAnyOrder(
                        IntList.of(5, 6, 7),
                        IntList.of(5, 6, 8),
                        IntList.of(5, 6, 9),
                        IntList.of(5, 7, 8),
                        IntList.of(5, 7, 9),
                        IntList.of(5, 8, 9),
                        IntList.of(6, 7, 8),
                        IntList.of(6, 7, 9),
                        IntList.of(6, 8, 9),
                        IntList.of(7, 8, 9)
                );
            }

            @Example
            void combinations_same_n_and_k() {
                final IntList list = IntList.of(5, 6, 7, 8, 9);
                assertThat(list.combinations(list.size())).containsExactly(list);
            }

            @Example
            void combinations_k_0() {
                final IntList list = IntList.of(5, 6, 7, 8, 9);
                assertThat(list.combinations(0)).containsExactly(IntList.of());
            }

            @Example
            void combinations_n_0() {
                final IntList list0 = IntList.of();
                assertThat(list0.combinations(0)).containsExactlyInAnyOrder(list0);
            }
        }

        @Group
        class Arrangements {
            @Example
            void arrangements() {
                final List<IntList> actual = IntList.of(5, 6, 7, 8, 9).arrangements(3);

                assertThat(actual).containsExactlyInAnyOrder(
                        IntList.of(5, 6, 7),
                        IntList.of(5, 7, 6),
                        IntList.of(6, 5, 7),
                        IntList.of(6, 7, 5),
                        IntList.of(7, 5, 6),
                        IntList.of(7, 6, 5),

                        IntList.of(5, 6, 8),
                        IntList.of(5, 8, 6),
                        IntList.of(6, 5, 8),
                        IntList.of(6, 8, 5),
                        IntList.of(8, 5, 6),
                        IntList.of(8, 6, 5),

                        IntList.of(5, 6, 9),
                        IntList.of(5, 9, 6),
                        IntList.of(6, 5, 9),
                        IntList.of(6, 9, 5),
                        IntList.of(9, 5, 6),
                        IntList.of(9, 6, 5),

                        IntList.of(5, 7, 8),
                        IntList.of(5, 8, 7),
                        IntList.of(7, 5, 8),
                        IntList.of(7, 8, 5),
                        IntList.of(8, 5, 7),
                        IntList.of(8, 7, 5),

                        IntList.of(5, 7, 9),
                        IntList.of(5, 9, 7),
                        IntList.of(7, 5, 9),
                        IntList.of(7, 9, 5),
                        IntList.of(9, 5, 7),
                        IntList.of(9, 7, 5),

                        IntList.of(5, 8, 9),
                        IntList.of(5, 9, 8),
                        IntList.of(8, 5, 9),
                        IntList.of(8, 9, 5),
                        IntList.of(9, 5, 8),
                        IntList.of(9, 8, 5),

                        IntList.of(6, 7, 8),
                        IntList.of(6, 8, 7),
                        IntList.of(7, 6, 8),
                        IntList.of(7, 8, 6),
                        IntList.of(8, 6, 7),
                        IntList.of(8, 7, 6),

                        IntList.of(6, 7, 9),
                        IntList.of(6, 9, 7),
                        IntList.of(7, 6, 9),
                        IntList.of(7, 9, 6),
                        IntList.of(9, 6, 7),
                        IntList.of(9, 7, 6),

                        IntList.of(6, 8, 9),
                        IntList.of(6, 9, 8),
                        IntList.of(8, 6, 9),
                        IntList.of(8, 9, 6),
                        IntList.of(9, 6, 8),
                        IntList.of(9, 8, 6),

                        IntList.of(7, 8, 9),
                        IntList.of(7, 9, 8),
                        IntList.of(8, 7, 9),
                        IntList.of(8, 9, 7),
                        IntList.of(9, 7, 8),
                        IntList.of(9, 8, 7)
                );
            }

            @Example
            void arrangements_same_n_and_k() {
                final IntList list = IntList.of(1, 2, 3);
                assertThat(list.arrangements(list.size())).isEqualTo(list.permutations());
            }
        }

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
            final IntList list = IntList.of(10, 20, 30);
            assertThat(list.contains(20)).isTrue();
            assertThat(list.contains(90)).isFalse();
        }

        @Example
        void containsAll() {
            final IntList list = IntList.of(10, 20, 30);
            assertThat(list.containsAll(IntList.of(10, 20, 30))).isTrue();
            assertThat(list.containsAll(IntList.of(10, 20))).isTrue();
            assertThat(list.containsAll(IntList.of(10))).isTrue();
            assertThat(list.containsAll(IntList.of(10, 20, 90))).isFalse();
            assertThat(list.containsAll(IntList.of())).isTrue();
        }

        @Example
        void containsAny() {
            final IntList list = IntList.of(10, 20, 30);
            assertThat(list.containsAny(IntList.of(10, 20, 30))).isTrue();
            assertThat(list.containsAny(IntList.of(10, 20, 35))).isTrue();
            assertThat(list.containsAny(IntList.of(10, 25, 35))).isTrue();
            assertThat(list.containsAny(IntList.of(15, 25, 35))).isFalse();
            assertThat(list.containsAny(IntList.of())).isFalse();
        }

        @Example
        void set() {
            final IntList list = IntList.of(10, 20, 30);
            assertThat(list.set(0, 15)).isEqualTo(IntList.of(15, 20, 30));
            assertThat(list.set(1, 25)).isEqualTo(IntList.of(10, 25, 30));
            assertThat(list.set(2, 35)).isEqualTo(IntList.of(10, 20, 35));
        }

        @Example
        void subList() {
            final IntList list = IntList.of(10, 20, 30, 40, 50);
            assertThat(list.subList(1, 4)).isEqualTo(IntList.of(20, 30, 40));
            assertThat(list.subList(0, 4)).isEqualTo(IntList.of(10, 20, 30, 40));
            assertThat(list.subList(1, 5)).isEqualTo(IntList.of(20, 30, 40, 50));
            assertThat(list.subList(0, 5)).isEqualTo(IntList.of(10, 20, 30, 40, 50));
        }

        @Property
        void shuffle(
                @ForAll("intList") IntList list,
                @ForAll Random random) {

            final IntList actual = list.shuffle(random);
            Assume.that(!actual.equals(list));

            assertThat(actual.sort()).isEqualTo(list.sort());
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
            assertThat(IntList.of(20, 10, 30).sort())
                    .isEqualTo(IntList.of(10, 20, 30));
        }

        @Example
        void sortDesc() {
            assertThat(IntList.of(20, 10, 30).sortDesc())
                    .isEqualTo(IntList.of(30, 20, 10));
        }

        @Example
        void append() {
            assertThat(IntList.of(10, 20, 30).append(35))
                    .isEqualTo(IntList.of(10, 20, 30, 35));
        }

        @Example
        void prepend() {
            assertThat(IntList.of(10, 20, 30).prepend(5))
                    .isEqualTo(IntList.of(5, 10, 20, 30));
        }

        @Example
        void appendAll() {
            assertThat(IntList.of(10, 20, 30).appendAll(IntList.of(31, 32, 33)))
                    .isEqualTo(IntList.of(10, 20, 30, 31, 32, 33));
        }

        @Example
        void prependAll() {
            assertThat(IntList.of(10, 20, 30).prependAll(IntList.of(1, 2, 3)))
                    .isEqualTo(IntList.of(1, 2, 3, 10, 20, 30));
        }

        @Example
        void retainAll() {
            assertThat(IntList.of(5, 10, 15, 20, 25, 30, 35).retainAll(IntList.of(10, 20, 30, 40)))
                    .isEqualTo(IntList.of(10, 20, 30));
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
        void take() {
            final IntList list = IntList.of(10, 20, 30, 40, 50);
            assertThat(list.take(0)).isEqualTo(IntList.of());
            assertThat(list.take(3)).isEqualTo(IntList.of(10, 20, 30));
            assertThat(list.take(5)).isEqualTo(IntList.of(10, 20, 30, 40, 50));
            assertThat(list.take(6)).isEqualTo(IntList.of(10, 20, 30, 40, 50));
        }

        @Example
        void drop() {
            final IntList list = IntList.of(10, 20, 30, 40, 50);
            assertThat(list.drop(0)).isEqualTo(IntList.of(10, 20, 30, 40, 50));
            assertThat(list.drop(3)).isEqualTo(IntList.of(40, 50));
            assertThat(list.drop(5)).isEqualTo(IntList.of());
            assertThat(list.drop(6)).isEqualTo(IntList.of());
        }

        @Example
        void takeRight() {
            final IntList list = IntList.of(10, 20, 30, 40, 50);
            assertThat(list.takeRight(0)).isEqualTo(IntList.of());
            assertThat(list.takeRight(3)).isEqualTo(IntList.of(30, 40, 50));
            assertThat(list.takeRight(5)).isEqualTo(IntList.of(10, 20, 30, 40, 50));
            assertThat(list.takeRight(6)).isEqualTo(IntList.of(10, 20, 30, 40, 50));
        }

        @Example
        void dropRight() {
            final IntList list = IntList.of(10, 20, 30, 40, 50);
            assertThat(list.dropRight(0)).isEqualTo(IntList.of(10, 20, 30, 40, 50));
            assertThat(list.dropRight(3)).isEqualTo(IntList.of(10, 20));
            assertThat(list.dropRight(5)).isEqualTo(IntList.of());
            assertThat(list.dropRight(6)).isEqualTo(IntList.of());
        }

        @Example
        void remove() {
            assertThat(IntList.of(10, 20, 30, 40).remove(30))
                    .isEqualTo(IntList.of(10, 20, 40));
        }

        @Example
        void removeAll() {
            assertThat(IntList.of(10, 20, 30, 40, 50).removeAll(IntList.of(20, 40)))
                    .isEqualTo(IntList.of(10, 30, 50));
        }

        @Example
        void removeAt() {
            final IntList list = IntList.of(10, 20, 30);
            assertThat(list.removeAt(0)).isEqualTo(IntList.of(20, 30));
            assertThat(list.removeAt(1)).isEqualTo(IntList.of(10, 30));
            assertThat(list.removeAt(2)).isEqualTo(IntList.of(10, 20));
        }

        @Example
        void map() {
            final IntList actual = IntList.of(10, 20, 30)
                    .map(i -> i + 5);

            assertThat(actual).isEqualTo(IntList.of(15, 25, 35));
        }

        @Example
        void flatMap() {
            final IntList actual = IntList.of(10, 20, 30)
                    .flatMap(i -> IntList.of(i + 1, i + 2, i + 3));

            assertThat(actual).isEqualTo(IntList.of(11, 12, 13, 21, 22, 23, 31, 32, 33));
        }

        @Example
        void filter() {
            final IntList actual = IntList.of(-10, 10, -20, 20, -30, 30, -40)
                    .filter(i -> i > 0);

            assertThat(actual).isEqualTo(IntList.of(10, 20, 30));
        }

        @Example
        void equals() {
            assertThat(IntList.of(10, 20, 30)).isEqualTo(IntList.of(10, 20, 30));
            assertThat(IntList.of(10, 20, -30)).isNotEqualTo(IntList.of(10, 20, 30));
        }

        @Property
        void _hashCode(
                @ForAll("intList") IntList a,
                @ForAll("intList") IntList b) {

            assertThat(!a.equals(b) || a.hashCode() == b.hashCode()).isTrue();
        }

        @Example
        void _toString() {
            assertThat(IntList.of(10, 20, 30)).hasToString("[10, 20, 30]");
        }

        @Example
        void _compareTo() {
            assertThat(IntList.of(10, 20, 30)).isEqualByComparingTo(IntList.of(10, 20, 30));
            assertThat(IntList.of(10, 20)).isLessThan(IntList.of(10, 20, 30));
            assertThat(IntList.of(10, 20, 30)).isLessThan(IntList.of(10, 20, 35));
            assertThat(IntList.of(10, 20, 30)).isGreaterThan(IntList.of(10, 20));
            assertThat(IntList.of(10, 20, 35)).isGreaterThan(IntList.of(10, 20, 30));
        }

        @Example
        void range() {
            assertThat(IntList.range(1, 5)).isEqualTo(IntList.of(1, 2, 3, 4));
            assertThat(IntList.range(1, 1)).isEqualTo(IntList.of());
        }

        @Example
        void rangeClosed() {
            assertThat(IntList.rangeClosed(1, 5)).isEqualTo(IntList.of(1, 2, 3, 4, 5));
            assertThat(IntList.rangeClosed(1, 1)).isEqualTo(IntList.of(1));
            assertThat(IntList.rangeClosed(1, 0)).isEqualTo(IntList.of());
        }
    }

    @Group
    class IntListProperties {
        @Property
        void appendAll_containsAll(
                @ForAll("intList") IntList as,
                @ForAll("intList") IntList bs) {

            final IntList l = as.appendAll(bs);

            assertThat(l.containsAll(as)).isTrue();
            assertThat(l.containsAll(bs)).isTrue();
        }

        @Property
        void reverse_reverse(@ForAll("intList") IntList a) {
            assertThat(a.reverse().reverse()).isEqualTo(a);
        }

        @Property
        void append_prepend(
                @ForAll("integer") int a,
                @ForAll("intList") IntList bs,
                @ForAll("integer") int c) {

            final IntList l1 = bs.prepend(a).append(c);
            final IntList l2 = bs.append(c).prepend(a);
            assertThat(l1).isEqualTo(l2);
        }

        @Property
        void appendAll_prependAll(
                @ForAll("intList") IntList as,
                @ForAll("intList") IntList bs) {

            final IntList l1 = as.appendAll(bs);
            final IntList l2 = bs.prependAll(as);
            assertThat(l1).isEqualTo(l2);
        }

        @Property
        void appendAll_removeAll_containsAny(
                @ForAll("intList") IntList as,
                @ForAll("intList") IntList bs) {

            assertThat(as.appendAll(bs).removeAll(bs).containsAny(bs)).isFalse();
        }
    }

    @Group
    class Consistency {
        @Label("IntList.Builder should be consistent with IntList")
        @Property(tries = 5000)
        void consistency(@ForAll("actionChain") ActionChain<MirrorState> actionChain) {
            actionChain
                    .withInvariant("mirror consistency", state -> {
                        assertThat(state.intListBuilder().build()).isEqualTo(state.intList());
                    })
                    .run();
        }

        @Provide
        Arbitrary<ActionChain<MirrorState>> actionChain(
                @ForAll("intList") IntList intList,
                @ForAll @IntRange(max = 10) int lead,
                @ForAll @IntRange(max = 10) int trail) {

            return ActionChain
                    .startWith(() -> {
                        final IntList.Builder intListBuilder = intList.toBuilder(lead, trail);
                        return new MirrorState(intListBuilder, intList);
                    })
                    .withAction(new MirrorState.SetAction())
                    .withAction(new MirrorState.SwapAction())
                    .withAction(new MirrorState.ReplaceAction())
                    .withAction(5, new MirrorState.AppendAction())
                    .withAction(3, new MirrorState.AppendAllAction())
                    .withAction(5, new MirrorState.PrependAction())
                    .withAction(3, new MirrorState.PrependAllAction())
                    .withAction(5, new MirrorState.InsertAction())
                    .withAction(3, new MirrorState.InsertAllAction())
                    .withAction(5, new MirrorState.RemoveAction())
                    .withAction(3, new MirrorState.RemoveAllAction())
                    .withAction(new MirrorState.RemoveAtAction())
                    .withAction(new MirrorState.TakeAction())
                    .withAction(new MirrorState.DropAction())
                    .withAction(new MirrorState.TakeRightAction())
                    .withAction(new MirrorState.DropRightAction())
                    .withAction(5, new MirrorState.MapAction())
                    .withAction(3, new MirrorState.FlatMapAction())
                    .withAction(new MirrorState.FilterAction())
                    .withMaxTransformations(150);
        }
    }

    @Provide
    Arbitrary<Integer> integer() {
        return Arbitraries.integers()
                .between(1, 50);
    }

    @Provide
    Arbitrary<IntList> intList() {
        return integer()
                .array(int[].class)
                .ofMinSize(0)
                .ofMaxSize(100)
                .map(IntList::of);
    }
}
