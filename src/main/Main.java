package main;

import nn.GeneticAlgorithm;
import nn.NeuralNetwork;
import space.Board;
import space.Commons;
import space.SpaceInvaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private static int inputDim = Commons.STATE_SIZE;
	private static int hiddenDim = Commons.STATE_SIZE;
	private static int outputDim = Commons.NUM_ACTIONS;
	private static final long SEED = Commons.RANDOM.nextLong();
	private static NeuralNetwork NN = new NeuralNetwork(inputDim, hiddenDim, outputDim);

	private static int CROM_SIZE = (NN.getChromossome().length);
	public static void main(String[] args) throws IOException {

		
		
		//----------------------------------------------------------------------------------------------------
		
//		
		// GeneticAlgorithm de quantas em quantas gerações se joga e se imprime a
		// fitness

		// na submissão a função GeneticAlgorithm era void
		double[] BestCrom = GeneticAlgorithm.GeneticAlgorithmSpaceInvaders(15, 5);

		// Parte adicionada pós
		// submissao---------------------------------------------------------------------------

		// Envia para um txt os cromossomas com o melhor resultado
		
		
		
		//PrintCrom no txt-----------------------------------------------------------------


		PrintWriter out = new PrintWriter("C:\\Users\\vitor\\OneDrive\\Desktop\\proj IA\\Oral\\BestResult.txt");

		for (int i = 0; i < BestCrom.length; i++) {
			out.println(BestCrom[i]);
		}

		out.close();
		
		
		
		//--------------------------------------------------------------------------------------------------------
		
		
		
		//GetCrom do txt-----------------------------------------------------------------


		List<String> listOfStrings = new ArrayList<String>();

		try (BufferedReader bf = new BufferedReader(
				new FileReader("C:\\Users\\vitor\\OneDrive\\Desktop\\proj IA\\Oral\\BestResult.txt"))) {
			String Res = bf.readLine();

			while (Res != null) {
				listOfStrings.add(Res);
				Res = bf.readLine();
			}
		}

		double[] defCROM = new double[CROM_SIZE];

		for (int i = 0; i < listOfStrings.size(); i++) {
			defCROM[i] = Double.parseDouble(listOfStrings.get(i));
		}
		
		
		//GetSeed do txt-----------------------------------------------------------------
		
		
		String ResSeed;
		
		try (BufferedReader bf = new BufferedReader(
				new FileReader("C:\\Users\\vitor\\OneDrive\\Desktop\\proj IA\\Oral\\Seed.txt"))) {
			 ResSeed = bf.readLine();
		}
			
		Long defSeed=Long.parseLong(ResSeed);

		
		//PlayBestNN do txt-----------------------------------------------------------------


		NeuralNetwork defNN = new NeuralNetwork(inputDim, hiddenDim, outputDim, defCROM);
		Board test = new Board(defNN);
		test.setSeed(defSeed);
	
		SpaceInvaders.showControllerPlaying(defNN, defSeed);
		System.out.println(" ");
		System.out.println("//----------------------------------------------------------------- ");
		System.out.println(" ");
		System.out.println(" Playing best NN ");
		System.out.println(" ");
		System.out.println("//----------------------------------------------------------------- ");
		// -----------------------------------------------------------------------------------------------------------

	}

}