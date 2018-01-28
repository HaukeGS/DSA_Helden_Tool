package aventurian;

public enum Race {
	MIDDLEGUY("Mittellaender"), THORWALAN("Thorwaler"), ORK("Ork"), GOBLIN("Goblin"), ELVE("Elf"), HUMAN("Mensch"), DWARF("Zwerg");

	Race(String nameOfRace) {
		this.nameOfRace = nameOfRace;
	}

	private String nameOfRace;

	@Override
	public String toString() {
		return nameOfRace;
	}
}
