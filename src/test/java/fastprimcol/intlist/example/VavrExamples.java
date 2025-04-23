package fastprimcol.intlist.example;

import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Vector;

public class VavrExamples {
    public static void main(String[] args) {
        final IndexedSeq<Integer> list = Vector.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final IndexedSeq<Integer> modifiedList = list.rotateLeft(1);
    }
}
