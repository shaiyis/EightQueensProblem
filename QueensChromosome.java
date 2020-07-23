import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class QueensChromosome {
    private int[] chromosome = {0,0,0,0,0,0,0,0};
    private float fitness;
    private Random rand;

    public QueensChromosome() {
        this.rand = new Random();
        setChromosome();
        setFitness();
    }
    public QueensChromosome(float fitness) {
        this.fitness = fitness;
    }

        public int[] getChromosome() {
        return chromosome;
    }

    private void setChromosome() {
        for (int i = 0; i < 8; i++) {
            int rand = this.rand.nextInt(8);
            this.chromosome[i] = rand;
        }
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness() {
        this.fitness = 28;
        for (int i = 0; i < 8; i++) {
            for (int j = i+1; j < 8; j++) {
                // same diagonal
                if ((chromosome[i] - i) == (chromosome[j] - j)){
                    this.fitness--;
                }
                else if ((chromosome[i] + i) == (chromosome[j] + j)){
                    this.fitness--;
                }
                // same row
                else if  (chromosome[i] == (chromosome[j])){
                    this.fitness--;
                }
            }
        }
    }
}


class SortByFitness implements Comparator<QueensChromosome>
{
    public int compare(QueensChromosome a, QueensChromosome b)
    {
        return Float.compare(a.getFitness(),b.getFitness());
    }
}


