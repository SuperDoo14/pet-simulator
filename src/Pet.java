public class Pet {
    private String name;
    private int hunger;
    private int happiness;
    private int energy;

    public Pet(String name) {
        this.name = name;
        hunger = 5;
        happiness = 5;
        energy = 5;
    }

    public void feed() {
        if (hunger < 10) {
            hunger++;
            happiness++;
        }
    }

    public void play() {
        if (happiness < 9) {
            happiness += 2;
        }
        if (energy > 0) {
            energy--;
        }
        if (hunger > 0) {
            hunger--;
        }
    }
}
