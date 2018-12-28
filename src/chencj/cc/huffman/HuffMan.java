package chencj.cc.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class HuffMan {

	
	static HuffManNode root;
	
	/**
	 * huffman解压
	 * @param huffManCode
	 * @param src
	 * @return
	 */
	
	public static byte[] unzip(Map<Byte, String> huffManCode,byte[] src) {
		StringBuilder sb3 = new StringBuilder();
		
		for (int i=0;i<src.length;i++) {
			byte b = src[i];
			//System.out.println(b);
			boolean flag = i==src.length-1;
			String b2 = byte2bit(!flag,b);
			//System.out.println(b2);
			sb3.append(b2);
		}
		String unZipStr = sb3.toString();
		//System.out.println(unZipStr);
		Map<String, Byte> unMap = new HashMap<>();
		for (byte b : huffManCode.keySet()) {
			unMap.put(huffManCode.get(b), b);
		}
	
		List<Byte> unZipBytes = new ArrayList<>();
		for (int i = 0; i < unZipStr.length();) {
			int count =1;
			len++;
			boolean flag=true;
			while(flag) {
				String substrUnzip = unZipStr.substring(i,i+count);
			//	System.out.println(substrUnzip);
				if(unMap.get(substrUnzip)==null) {
					count++;
					
					
					
				}
				else {
					Byte unZipByte = unMap.get(substrUnzip);
					unZipBytes.add(unZipByte);
					flag=false;
					
				}
			}
			i += count;
		}	
		
		byte[] bytes  = new byte[unZipBytes.size()];
		int index =0;
		for (byte b : unZipBytes) {
			bytes[index++]=b;
		}
		return bytes;
	}
	
	public static String  byte2bit(Boolean flag,byte b) {
		int temp = b;
		String bystr ="";
		if(flag) {
			temp = temp | 256;
			bystr = Integer.toBinaryString(temp);
		}
		if(flag)
			return bystr.substring(bystr.length()-8);
		else {
			return Integer.toBinaryString(b);
		}
		//return ;
	}
	
	
	
	
	/**
	 * huffman压缩
	 * @param huffManCode
	 * @param src
	 * @return
	 */
	
	
	
	public static int len =0;
	public static byte[] zip(Map<Byte, String> huffManCode,byte[] src) {
		//把源字符串转成字节，然后对应huffmancode转成二进制字节存在sb中
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0;i<src.length ; i++) {
			
			stringBuilder.append(huffManCode.get(src[i]));
			
		}
		String string = stringBuilder.toString();
		len = string.length();
		byte[] bytes = new byte[string.length()%8==0?string.length()/8:string.length()/8+1];
		
		
		for (int i=0,j=0; i<string.length(); i+=8) {
			String strByte;
			if(i+8<string.length()) {
				strByte = string.substring(i,i+8);
				
			}else {
				strByte = string.substring(i);
			}
			byte bt = (byte) Integer.parseInt(strByte,2);
			bytes[j++]=bt;
			
			//System.out.println(bt+ "  " +strByte);
		}
		System.out.println(stringBuilder.toString());
		return bytes;
	}
	
	
	/**
	 * 根据树得到huffmancode
	 * @param node
	 * @param code
	 * @param sb
	 */
	
	
	//处理左右的节点，加上对应的0 -  1
	static StringBuilder sb = new StringBuilder();
	static Map<Byte, String> huffManCode = new HashMap<>(); 
	public static Map<Byte, String> huffManCode(HuffManNode node){
		if(root==null) {
			root = node;
		}
			getCode(root.leftNode,"1",sb);
			getCode(root.rightNode,"0",sb);
		
		
		return huffManCode;
	} 
		
	/**
	 * 循环递归处理
	 * @param node
	 * @param code
	 * @param sb
	 */
	private static void getCode(HuffManNode node, String code, StringBuilder sb) {
		StringBuilder sb2 = new StringBuilder(sb);
		sb2.append(code);
		if(node.byte1==null) {
			getCode(node.leftNode, "1", sb2);
			getCode(node.rightNode, "0", sb2);
		}else {
			huffManCode.put(node.byte1, sb2.toString());
		}
		
	}

	
	
	
	/**
	 * 统计字符数据，为创建huffman打基础
	 * @param str
	 * @return
	 */
	public static Map<Byte, Integer> huffManCode(String str){
		//统计字符串中的分类
		Map<Byte, Integer> map = new HashMap<>();
		byte[] bs = str.getBytes();
		for (byte b : bs) {
			Integer i = map.get(b);
			if(i == null) {
				i = 1;
			}else {
				i++;
			}
			map.put(b, i);
		}
		
		
		
		return map;
	}
	
	
	/**
	 * 创建huffman树
	 * @param map
	 * @return
	 */
	
	public static HuffManNode createHuffManTree(Map<Byte, Integer> map) {
		//生成节点
		List<HuffManNode> list = new ArrayList<>();
		for(Byte bs: map.keySet()) {
			list.add(new HuffManNode(map.get(bs), bs));
		}
		while(list.size()>1) {
			Collections.sort(list);
			HuffManNode rightNode = list.get(list.size()-1);
			HuffManNode leftNode = list.get(list.size()-2);
			HuffManNode parent = new HuffManNode(rightNode.value+leftNode.value,null);
			parent.leftNode=leftNode;
			parent.rightNode=rightNode;
			//移除，加入。
			list.remove(leftNode);list.remove(rightNode);
			list.add(parent);
		}
		
		HuffManNode root = list.get(0);
		
		return root;
	}


	/**
	 * 创建huffman树
	 * @param arr
	 * @return
	 */
	public static HuffManNode createHuffManTree(int[] arr) {
		//建立二叉树
		List<HuffManNode> list = new ArrayList<>();
		for (int value : arr) {
			list.add(new HuffManNode(value));
		}
		while(list.size() > 1) {
			//先进行排序
			Collections.sort(list);
			//取出最小的两个 合并
			HuffManNode rightNode = list.get(list.size()-1);
			HuffManNode leftNode = list.get(list.size()-2);
			HuffManNode parent = new HuffManNode(rightNode.value+leftNode.value);
			parent.leftNode=leftNode;
			parent.rightNode=rightNode;
			//移除，加入。
			list.remove(leftNode);list.remove(rightNode);
			list.add(parent);
			
		}
		HuffManNode root = list.get(0);
		
		return root;
	}
}
