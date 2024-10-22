package utilis;

import java.util.Comparator;

import nn.NeuralNetwork;

public class FitnessComparator implements Comparator<NeuralNetwork> {

	@Override
	public int compare(NeuralNetwork x, NeuralNetwork y) {

		if ((int) y.getFitness() >(int)  x.getFitness()) {
			return 1;
		} else if (y.getFitness() == x.getFitness()) {
			return 0;
		} else {
			return -1;
		}
	}
}