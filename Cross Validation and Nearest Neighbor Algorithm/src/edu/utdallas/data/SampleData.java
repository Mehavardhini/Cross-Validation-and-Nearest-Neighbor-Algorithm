package edu.utdallas.data;

public class SampleData {

	int row;
	int column;
	char sign;

	public SampleData(int row, int column, char sign) {
		super();
		this.row = row;
		this.column = column;
		this.sign = sign;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "Row:" + row + " Column:" + column + " sign:" + sign ;
		return str;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		SampleData o = (SampleData)obj;
		if(this.row==o.row && this.column==o.column)
		{
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (row+1)*(column+1);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public char getSign() {
		return sign;
	}

	public void setSign(char sign) {
		this.sign = sign;
	}

}
