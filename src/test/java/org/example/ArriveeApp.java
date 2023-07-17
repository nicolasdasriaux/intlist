package org.example;

import java.util.ArrayList;
import java.util.List;

public class ArriveeApp {
	public record Arrivee(List<IntList> groupes) {
		public List<IntList> arriveesElementaires(int nbrPositions) {
			final ArrayList<IntList> arriveesElementaires = new ArrayList<>();
			final IntList.Builder arriveeElementaire = IntList.Builder.empty(0, nbrPositions);
			arriveesElementairesLoop(0, nbrPositions, arriveeElementaire, arriveesElementaires);
			return arriveesElementaires;
		}

		private void arriveesElementairesLoop(int i, int nbrPositions, IntList.Builder arriveeElementaire, List<IntList> arriveesElementaires) {
			if (nbrPositions == 0) {
				arriveesElementaires.add(arriveeElementaire.build());
			} else {
				final IntList groupe = groupes.get(i);
				final int nbrPositionsGroupe = Math.min(nbrPositions, groupe.size());

				for (IntList arrangementGroupe : groupe.arrangements(nbrPositionsGroupe)) {
					arriveeElementaire.appendAll(arrangementGroupe);
					arriveesElementairesLoop(i + 1, nbrPositions - nbrPositionsGroupe, arriveeElementaire, arriveesElementaires);
					arriveeElementaire.dropRight(nbrPositionsGroupe);
				}
			}
		}
	}

	public static void main(String[] args) {
		final Arrivee arrivee = new Arrivee(List.of(IntList.of(1, 2, 3), IntList.of(10), IntList.of(5, 6, 7), IntList.of(8), IntList.of(9)));
		arrivee.arriveesElementaires(6).forEach(System.out::println);
	}
}
