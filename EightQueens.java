import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EightQueens {

    private ArrayList<QueensChromosome> generationOne;
    private ArrayList<QueensChromosome> generationTwo;


    // we transfer each generation's chromosomes from  generationOne arraylist to generationTwo arraylist.
    // toGenerationTwo tells us if we have to locate the next generation in generationTwo arraylist.
    private boolean toGenerationTwo;
    private Random rand;
    private float fitnessSum;
    private QueensChromosome parent1;
    private QueensChromosome parent2;
    private QueensChromosome child1;
    private QueensChromosome child2;
    private float avg;
    private QueensChromosome best;
    private int generationNumber;
    private final int population = 200;
    private FileWriter myWriterAvg;
    private FileWriter myWriterBest;
    private FileWriter myWriterGeneration;

    public FileWriter getMyWriterAvg() {
        return myWriterAvg;
    }

    public FileWriter getMyWriterBest() {
        return myWriterBest;
    }

    public FileWriter getMyWriterGeneration() {
        return myWriterGeneration;
    }

    public void setIsToGenerationTwo(boolean b) {
        this.toGenerationTwo = b;
    }

    public EightQueens() {
        this.generationOne = new ArrayList<QueensChromosome>();
        this.generationTwo = new ArrayList<QueensChromosome>();
        this.toGenerationTwo = true;
        this.rand = new Random();
        this.fitnessSum = 0;
        this.generationNumber = 1;
        this.best = new QueensChromosome(0);
        setAvg();
        /*try {
            this.myWriterAvg = new FileWriter("avg.txt");
            this.myWriterBest = new FileWriter("best.txt");
            this.myWriterGeneration = new FileWriter("generation.txt");
        }catch (IOException e){
            e.getMessage();
        }*/
    }

    public QueensChromosome getBest() {
        return best;
    }

    public float getAvg() {
        return avg;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public ArrayList<QueensChromosome> getGenerationOne() {
        return this.generationOne;
    }

    public ArrayList<QueensChromosome> getGenerationtwo() {
        return this.generationTwo;
    }


    public void randomSolusion() {
        toGenerationTwo = false;
        for (int i = 0; i < population; i++) {
            QueensChromosome queensChromosome = new QueensChromosome();
            this.generationOne.add(queensChromosome);
        }
        Collections.sort(generationOne, new SortByFitness());
        setBest();
        setAvg();
        this.generationNumber++;
    }

    public void initializePopulation() {
        for (int i = 0; i < population; i++) {
            QueensChromosome queensChromosome = new QueensChromosome();
            this.generationOne.add(queensChromosome);
        }
        Collections.sort(generationOne, new SortByFitness());
    }

    public void elitism() {
        if (toGenerationTwo) {
            generationTwo.add(generationOne.get(population-2));
            generationTwo.add(generationOne.get(population-1));
            generationOne.remove(0);
            generationOne.remove(0); // after removing 0, 1 becomes 0
        } else {
            generationOne.add(generationTwo.get(population-2));
            generationOne.add(generationTwo.get(population-1));
            generationTwo.remove(0);
            generationTwo.remove(0);
        }
    }

    public void selection() {

        // p is random in range of 0-fitnessSum. cumulative is the cumulative fitness,
        // this way we choose parents with priority to high fitness ones
        if (toGenerationTwo) {
            fitnessSum = 0;
            for (QueensChromosome queensChromosome : generationOne) {
                fitnessSum += queensChromosome.getFitness();
            }
            float p = rand.nextInt((int) fitnessSum);
            float cumulative = 0;
            for (QueensChromosome queensChromosome : generationOne) {
                cumulative += queensChromosome.getFitness();
                if (p <= cumulative) {
                    parent1 = queensChromosome;
                    break;
                }
            }
            p = rand.nextInt((int) fitnessSum);
            cumulative = 0;
            for (QueensChromosome queensChromosome : generationOne) {
                cumulative += queensChromosome.getFitness();
                if (p <= cumulative) {
                    parent2 = queensChromosome;
                    break;
                }
            }
        } else {
            fitnessSum = 0;
            for (QueensChromosome queensChromosome : generationTwo) {
                fitnessSum += queensChromosome.getFitness();
            }
            float p = rand.nextInt((int) fitnessSum);
            float cumulative = 0;
            for (QueensChromosome queensChromosome : generationTwo) {
                cumulative += queensChromosome.getFitness();
                if (p <= cumulative) {
                    parent1 = queensChromosome;
                    break;
                }
            }
            p = rand.nextInt((int) fitnessSum);
            cumulative = 0;
            for (QueensChromosome queensChromosome : generationTwo) {
                cumulative += queensChromosome.getFitness();
                if (p <= cumulative) {
                    parent2 = queensChromosome;
                    break;
                }
            }
        }
    }

    public void OneGeneration() {

        if (toGenerationTwo) {
            while (generationTwo.size() < population) {
                // select two parents
                selection();
                crossOver();
                mutation();
                generationTwo.add(child1);
                generationTwo.add(child2);
            }
            // clear previous generation
            generationOne.clear();
            Collections.sort(generationTwo, new SortByFitness());
            fitnessSum = 0;
            setAvg();
            setBest();
            toGenerationTwo = false;
            generationNumber++;
        } else {
            while (generationOne.size() < population) {
                selection();
                crossOver();
                mutation();
                generationOne.add(child1);
                generationOne.add(child2);
            }
            generationTwo.clear();
            Collections.sort(generationOne, new SortByFitness());
            fitnessSum = 0;
            setAvg();
            setBest();
            toGenerationTwo = true;
            generationNumber++;
        }
    }

    public void setAvg() {
        avg = 0;
        if (toGenerationTwo) {
            for (QueensChromosome queensChromosome : generationTwo) {
                avg += queensChromosome.getFitness();
            }
            this.avg = avg / population;
        } else {
            for (QueensChromosome queensChromosome : generationOne) {
                avg += queensChromosome.getFitness();
            }
            this.avg = avg / population;
        }
    }

    public void setBest() {
        if (toGenerationTwo) {
            this.best = generationTwo.get(population-1);
        }else{
            this.best = generationOne.get(population-1);
        }
    }

    private void crossOver(){
        int p = rand.nextInt(100);
        if (p < 80){
            child1 = new QueensChromosome();
            child2 = new QueensChromosome();
            // do cross over by probability of 0.75
            int loaction = 1 + rand.nextInt(7);
            for (int i = 0; i < loaction ; i++){
                child1.getChromosome()[i] = parent1.getChromosome()[i];
                child2.getChromosome()[i] = parent2.getChromosome()[i];
            }
            for (int i = loaction ; i < 8 ; i++){
                child1.getChromosome()[i] = parent2.getChromosome()[i];
                child2.getChromosome()[i] = parent1.getChromosome()[i];
            }
            child1.setFitness();
            child2.setFitness();
        }else{
            // no crossover
            child1 = parent1;
            child2 = parent2;
        }
    }
    private void mutation(){
        int p, replace;
        // replace one place with random between 0-7 by probability of 0.001 to every loaction
        // in the chromosome
        for (int i = 0 ; i < 8 ; i++){
            p = rand.nextInt(1000);
            if (p == 1){
                replace = rand.nextInt(8);
                child1.getChromosome()[i] = replace;
                child1.setFitness();
            }
        }
        for (int i = 0 ; i < 8 ; i++){
            p = rand.nextInt(1000);
            if (p == 1){
                replace = rand.nextInt(8);
                child2.getChromosome()[i] = replace;
                child2.setFitness();
            }
        }
    }
}


