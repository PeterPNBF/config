package com.tydic.config.util;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import com.tydic.config.util.ZooKeeperOperator;


public class ZooKeeperOperatorTest3 {
	@Test
	public void test(){
		ZooKeeperOperator zko = new ZooKeeperOperator();
		try {
			zko.connect("127.0.0.1:2181");
			zko.createOrUpdate("/Configuration", null);
			zko.watch("/Configuration");
			zko.getChild("/");

			zko.createOrUpdate("/Configuration/vds.xml", "abc".getBytes());
			zko.watch("/Configuration/vds.xml");
			System.out.println("vds.xml:" + zko.getString("/Configuration/vds.xml"));
			zko.createOrUpdate("/Configuration/vds.xml", "def".getBytes());
			zko.watch("/Configuration/vds.xml");
			System.out.println("vds.xml:" + zko.getString("/Configuration/vds.xml"));
			zko.delete("/Configuration/vds.xml", -1);
			zko.getChild("/");
			zko.createNode("/test/c1/cc1");
			zko.getAllChild("/test/c1", "");
			zko.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
