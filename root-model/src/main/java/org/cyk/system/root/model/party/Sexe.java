package org.cyk.system.root.model.party;

public enum Sexe {
	MASCULIN{
		@Override
		public String toString() {
			return "Masculin";
		}
	}, FEMININ{
		@Override
		public String toString() {
			return "Féminin";
		}
	};
}