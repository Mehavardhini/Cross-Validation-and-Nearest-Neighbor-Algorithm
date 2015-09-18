package edu.utdallas.classification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import edu.utdallas.data.SampleData;

public class DistanceOperations {
	
	public static double  getDistance(int x1, int y1, int x2, int y2) {
		double distance = 0;
		distance = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
		return distance;

	}


	public static ArrayList<NeighborSamples> extrtactNeighborDistances(int dataSample,
			double exampleDistanceMatrix[][], Set<Integer> neighborSet) {
		
		ArrayList<NeighborSamples> neighbhorList = new ArrayList<NeighborSamples>();
		double distance;
		for( Integer neighbor:neighborSet)
		{
			distance=exampleDistanceMatrix[dataSample][neighbor];
			neighbhorList.add(new NeighborSamples(neighbor, distance));
		
		}
		
		Collections.sort(neighbhorList);		
		return neighbhorList;
		

	}

	public static char signPredictor(HashMap<Integer, SampleData> exampleMap,
			ArrayList<NeighborSamples> arrNeighbors, int kNN) {
		int posCount = 0;
		int negCount = 0;
		SampleData dataSample;

		for (NeighborSamples neighbor : arrNeighbors.subList(0, kNN)) {
			dataSample = exampleMap.get(neighbor.getIndex());
			if (dataSample.getSign() == '+') {
				posCount++;
			} else {
				negCount++;
			}

		}
		if (posCount > negCount) {
			return '+';
		} else {

			return '-';
		}
	}

}
