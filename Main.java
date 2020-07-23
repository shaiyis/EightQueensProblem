import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main (String[] args){
        EightQueens eightQueens = new EightQueens();


        /*eightQueens.randomSolusion();
        while (eightQueens.getBest().getFitness() < 28){
            try {
                System.out.println("Generation " + eightQueens.getGenerationNumber());
                eightQueens.getMyWriterGeneration().write(eightQueens.getGenerationNumber() +"\n");
                System.out.println("Avg: " + eightQueens.getAvg());
                eightQueens.getMyWriterAvg().write(Float.toString(eightQueens.getAvg()) +"\n");
                System.out.println("Best: " + Arrays.toString(eightQueens.getBest().getChromosome()));
                System.out.println("Best's fitness: " + eightQueens.getBest().getFitness());
                eightQueens.getMyWriterBest().write(Float.toString(eightQueens.getBest().getFitness()) +"\n");
                System.out.println();
                eightQueens.getGenerationOne().clear();
                eightQueens.randomSolusion();
            }catch (IOException e){
                e.getMessage();
            }
        }*/


        eightQueens.initializePopulation();

        // while we don't found solution
        while (eightQueens.getBest().getFitness() < 28){

            if (eightQueens.getGenerationNumber()%500 == 0){
                eightQueens.getGenerationOne().clear();
                eightQueens.getGenerationtwo().clear();
                eightQueens.initializePopulation();
                eightQueens.setIsToGenerationTwo(true);
            }

            eightQueens.elitism();
            // selection, crossover, mutation
            eightQueens.OneGeneration();

            System.out.println("Generation " + eightQueens.getGenerationNumber());
            //eightQueens.getMyWriterGeneration().write(eightQueens.getGenerationNumber() +"\n");
            System.out.println("Avg: " + eightQueens.getAvg());
            //eightQueens.getMyWriterAvg().write(Float.toString(eightQueens.getAvg()) +"\n");
            System.out.println("Best: " + Arrays.toString(eightQueens.getBest().getChromosome()));
            System.out.println("Best's fitness: " + eightQueens.getBest().getFitness());
            //eightQueens.getMyWriterBest().write(Float.toString(eightQueens.getBest().getFitness()) +"\n");
            System.out.println();
        }
        System.out.println("Solution found:");
        System.out.println(Arrays.toString(eightQueens.getBest().getChromosome()));
        /*try {
            eightQueens.getMyWriterBest().close();
            eightQueens.getMyWriterAvg().close();
            eightQueens.getMyWriterGeneration().close();
        }catch (IOException e){
            e.getMessage();
        }*/

    }
}
