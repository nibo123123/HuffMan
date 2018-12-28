package chencj.cc.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TestHuffManTree {

	public static void main(String[] args) {
		String string = "you are right ,we are family,please take care of!!!!!!!" ;
		//String string;
		Map<Byte, Integer> huffManMap = HuffMan.huffManCode(string);
		HuffManNode node = HuffMan.createHuffManTree(huffManMap);
		Map<Byte, String> map = HuffMan.huffManCode(node);
		for(Byte bs : map.keySet()) {
			System.out.println(bs+"   "+map.get(bs));
		}
		System.out.println("---------------");
		byte[] zip = HuffMan.zip(map, string.getBytes());
		StringBuilder sBuilder = new StringBuilder();
		
		
		byte[] bytes = HuffMan.unzip(map, zip);
		System.out.println(new String(bytes));
		
		int[] arr = {3,7,8,29,5,11,23,14};
		HuffManNode huffManNode = HuffMan.createHuffManTree(arr);
	}
	
	
}
