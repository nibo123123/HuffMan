package chencj.cc.huffman;

import java.util.Map;

public class HuffManNode implements Comparable<HuffManNode>{
	public HuffManNode(int value,Byte byte1) {
		this.value = value;
		this.byte1 = byte1;
	}

	public HuffManNode(int value) {
		this.value = value;
	}

	Integer value;
	Byte byte1;
	HuffManNode leftNode;
	HuffManNode rightNode;
	@Override
	public int compareTo(HuffManNode o) {
		// TODO Auto-generated method stub
		return o.value-this.value;
	}
	@Override
	public String toString() {
		return "HuffManNode [value=" + value + "]";
	}
	

}
