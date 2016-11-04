package com.tydic.config.test;

import java.util.Vector;

import com.tydic.config.Observable;

public class ObserverImpl implements Observable{

	@Override
	public void callService(String ConfigName, String version,
			Vector<String> args) {
		// TODO Auto-generated method stub
		Vector<String> tempVec = args;
		System.out.println("-----------------------i am Observer test begin-----------------------");
		for(int i=0; i<tempVec.size(); ++i){
			System.out.println(tempVec.get(i));
		}
		System.out.println("------------------------i am Observer test end------------------------");
	}
}
