public class Pet {

    // constant values for pet stats
    private static final int MAX_STAT_VALUE = 10;
    private static final int MIN_STAT_VALUE = 0;
    private static final int STARTING_STAT_VALUE = 5;

    private String name;
    private int hunger;
    private int happiness;
    private int energy;

    public Pet(String name) {
        this.name = name;
        hunger = STARTING_STAT_VALUE;
        happiness = STARTING_STAT_VALUE;
        energy = STARTING_STAT_VALUE;
    }

    // function to prevent stats exceeding min and max values
    private int clampStat(int value) {
        if (value < MIN_STAT_VALUE) {
            return MIN_STAT_VALUE;
        }

        if (value > MAX_STAT_VALUE) {
            return MAX_STAT_VALUE;
        }

        return value;
    }

    // function to feed pet, increases hunger + happiness
    public void feed() {
        hunger = clampStat(hunger + 1);
        happiness = clampStat(happiness + 1);
    }

    // function to play with pet, increases happiness, but decreases hunger and energy
    public void play() {
        happiness = clampStat(happiness + 2);
        energy = clampStat(energy - 1);
        hunger = clampStat(hunger - 1);
    }

    // function to make pet sleep
    public void sleep() {
        energy = clampStat(energy + 3);
    }

    public int getHunger() {
        return hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getEnergy() {
        return energy;
    }

    public String getStatus() {
        return name + " | Hunger: " + hunger + " | Happiness: " + happiness + " | Energy: " + energy;
    }

}
