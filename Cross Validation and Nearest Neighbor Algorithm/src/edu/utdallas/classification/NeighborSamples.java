package edu.utdallas.classification;

public class NeighborSamples implements Comparable<NeighborSamples> {
	int index;
	double distance;
	public NeighborSamples(int index, double distance) {
		super();
		this.index = index;
		this.distance = distance;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String toString() {
		String str="Index:"+index +"  Distance:" + distance;
		return str;
	}
	@Override
	public int compareTo(NeighborSamples o) {
		
		if(this.distance > o.distance){
			return 1;			
		}
		else if(this.distance < o.distance){
			return -1;
		}
		else
		{
			return 0;
		}
		
	}
	
	
}
