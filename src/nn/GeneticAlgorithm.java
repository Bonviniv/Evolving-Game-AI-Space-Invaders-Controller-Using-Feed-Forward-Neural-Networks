package nn;

import java.util.Arrays;
import java.util.Random;
import space.Board;
import space.Commons;
import space.SpaceInvaders;

public class GeneticAlgorithm {

	private static int inputDim = Commons.STATE_SIZE;
	private static int hiddenDim = Commons.STATE_SIZE;
	private static int outputDim = Commons.NUM_ACTIONS;
	private static final double MUTATION_RATE = 10;
	private static double RANDOM_MUTATE = new Random().nextDouble() * 100;
	private static final int POPULATION_SIZE = 200;
	private static final int NUM_GENERATIONS = 200;
	private static NeuralNetwork NN = new NeuralNetwork(inputDim, hiddenDim, outputDim);
	private static int CROM_SIZE = (NN.getChromossome().length);
	private static int TOUR_PORC = (int) (POPULATION_SIZE * 0.15);
	private static final long SEED = Commons.RANDOM.nextLong();

	
	
	
	
	public static double[] GeneticAlgorithmSpaceInvaders(int intervaloa, int intervalob) {
		

		int time=0;
		
		NeuralNetwork[] populationList = new NeuralNetwork[POPULATION_SIZE];

		for (int i = 0; i < POPULATION_SIZE; i++) {

			NeuralNetwork b = new NeuralNetwork(inputDim, hiddenDim, outputDim);
			Board b0 = new Board(b);
			b0.setSeed(SEED);
			b0.run();
			b.setFitness(b0.getFitness());
			// b0.setSeed(SEED);

			populationList[i] = b;

		}
		Arrays.sort(populationList, (a, b) -> Double.compare(a.getFitness(), b.getFitness()));
		
		System.out.println(" ");
		System.out.println("population initialized ");
		System.out.println(" ");

		Arrays.sort(populationList, (a, b) -> Double.compare(a.getFitness(), b.getFitness()));

		System.out.println("population sorted ");
		System.out.println(" ");

		
		Board best0 = new Board(populationList[0]);
		best0.setSeed(SEED);
		best0.run();
		

		// best0.setSeed(SEED);

		System.out.println("best fitness : " + populationList[POPULATION_SIZE - 1].getFitness());
		
		System.out.println("last fitness: " + populationList[0].getFitness());

		for (int i = 0; i < NUM_GENERATIONS; i++) {

			NeuralNetwork parent1;
			NeuralNetwork parent2;
			NeuralNetwork parent1mut;

			NeuralNetwork[] newPopulationSort = new NeuralNetwork[POPULATION_SIZE];

			for (int j = 0; j < 5; j++) {

				newPopulationSort[j] = populationList[POPULATION_SIZE - 1 - j];

			}

			//-----------------------------------------------------------------------------------------------------------			
			// na submisão estava j=3
			//-----------------------------------------------------------------------------------------------------------

			for (int j = 5; j < POPULATION_SIZE - 15; j = j + 2) {

				parent1 = selectParent(populationList);
				parent2 = selectParent(populationList);

				// confirmar que os parents escolhidos são diferentes
				while (parent1 == parent2) {
					parent1 = selectParent(populationList);
					parent2 = selectParent(populationList);
				}

				NeuralNetwork[] childs = crossover(parent1, parent2);

				newPopulationSort[j] = childs[0];
				newPopulationSort[j + 1] = childs[1];

			}

			for (int j = POPULATION_SIZE - 15; j < POPULATION_SIZE; j++) {

				parent1mut = selectParent(populationList);
				mutate(parent1mut);
				newPopulationSort[j] = (parent1mut);

				
				
			}

			//setar a fitness em todas as redes neuronais da nova população
			for (int l = 0; l < POPULATION_SIZE; l++) {

				Board b0 = new Board(newPopulationSort[l]);
				b0.setSeed(SEED);
				b0.run();
				
				newPopulationSort[l].setFitness(b0.getFitness());

			}

			Arrays.sort(newPopulationSort, (a, b) -> Double.compare(a.getFitness(), b.getFitness()));

			populationList = newPopulationSort;

			//NeuralNetwork[] childs = crossover(newPopulationSort[POPULATION_SIZE - 1],
			//populationList[POPULATION_SIZE - 2]);

			//populationList[0] = childs[0];
			//populationList[1] = childs[1];

			System.out.println("Geração " + i + " calculada ");

		
			
			if (i % intervaloa == 0 || i == 0) {

				Board test = new Board(populationList[POPULATION_SIZE - 1]);
				test.setSeed(SEED);
				test.run();
				time=test.getTime();
				SpaceInvaders.showControllerPlaying(populationList[POPULATION_SIZE - 1],SEED);
                 
			}

			if (i % intervalob == 0 || i == 0) {
				int sum = 0;
				for (int l = 0; l < POPULATION_SIZE; l++) {
					sum += populationList[l].getFitness();
				}
				sum = sum / POPULATION_SIZE;
				

				Board test = new Board(populationList[POPULATION_SIZE - 1]);
				test.setSeed(SEED);
				test.run();
				
				time=test.getTime();
	
				
				System.out.println("Geração " + i + ": best fitness : " + populationList[POPULATION_SIZE - 1].getFitness());
				System.out.println(" -Best crom: " + populationList[POPULATION_SIZE - 1].getChromossome());

				System.out.println(" -Best time : " +time);
				System.out.println(" -Best getYmed : " +test.getYmed());
			

				System.out.println(" -Seed : " + SEED);
				System.out.println(" -Geração " + i + ": media fitness : " + sum);
				System.out.println("");
			}

		}

		//-----------------------------------------------------------------------------------------------------------
        // na submissão o array era ordenado novamente
		//-----------------------------------------------------------------------------------------------------------

		//Arrays.sort(populationList, (a, b) -> Double.compare(a.getFitness(), b.getFitness()));

		Board best = new Board(populationList[POPULATION_SIZE - 1]);
		best.setSeed(SEED);
		best.run();

		Board last = new Board(populationList[0]);
		last.setSeed(SEED);
		last.run();

		System.out.println("Best solution found: ");
		System.out.println("Fitness " + populationList[POPULATION_SIZE - 1].getFitness());
		System.out.println("Best crom: " + populationList[POPULATION_SIZE - 1].getChromossome());
		System.out.println("Seed : " + SEED);

		SpaceInvaders.showControllerPlaying(populationList[POPULATION_SIZE - 1],SEED);
		
		
		//Parte adicionada pós submissao---------------------------------------------------------------------------
		
		double[] res=new double[CROM_SIZE];
		
		for (int i=0; i<CROM_SIZE;i++) {		
			 res[i]=populationList[POPULATION_SIZE - 1].getChromossome()[i];
		}
		
		return res;

		//-----------------------------------------------------------------------------------------------------------
		
	}

	public static NeuralNetwork selectParent(NeuralNetwork[] population2) {

		NeuralNetwork[] tournament = new NeuralNetwork[TOUR_PORC];

		for (int i = 0; i < TOUR_PORC; i++) {

			Random ram = new Random();
			double pos = ram.nextDouble();
			tournament[i] = (population2[(int) (POPULATION_SIZE * pos)]);

		}

		Arrays.sort(tournament, (a, b) -> Double.compare(a.getFitness(), b.getFitness()));

		return tournament[tournament.length - 1];

	}

	private static NeuralNetwork[] crossover(NeuralNetwork parent1, NeuralNetwork parent2) {

		int k4 = CROM_SIZE / 4;
		int k2 = CROM_SIZE / 2;
		int k3 = k4 * 3;

		double[][] crom = new double[2][CROM_SIZE];
		double[] p1 = parent1.getChromossome();
		double[] p2 = parent1.getChromossome();

		for (int a = 0; a < k4; a++) {
			crom[0][a] = p1[a];
			crom[1][a] = p2[a];
		}

		for (int a = k2; a < k3; a++) {
			crom[0][a] = p1[a];
			crom[1][a] = p2[a];
		}

		NeuralNetwork[] ret = new NeuralNetwork[2];

		NeuralNetwork x = new NeuralNetwork(inputDim, hiddenDim, outputDim);
		Board x0 = new Board(x);
		x0.setSeed(SEED);
		x0.run();
		x.setFitness(x0.getFitness());
		
		ret[0] = x;

		NeuralNetwork y = new NeuralNetwork(inputDim, hiddenDim, outputDim);
		Board y0 = new Board(y);
		y0.setSeed(SEED);
		y0.run();
		y.setFitness(y0.getFitness());
		
		ret[1] = y;

		return ret;
	}

	private static NeuralNetwork mutate(NeuralNetwork par) {

		double[] crom = par.getChromossome();

		for (int i = 0; i < CROM_SIZE; i++) {
			if (RANDOM_MUTATE < MUTATION_RATE) {
				Random random = new Random();
				crom[i] = (2 * random.nextDouble() - 1);
			}
		}

		NeuralNetwork ret = new NeuralNetwork(inputDim, hiddenDim, outputDim, crom);
		Board r = new Board(ret);
		r.setSeed(SEED);
		r.run();
		ret.setFitness(r.getFitness());

		return ret;
	}

}
