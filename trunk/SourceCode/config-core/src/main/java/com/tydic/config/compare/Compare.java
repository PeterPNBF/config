package com.tydic.config.compare;

import java.util.List;

/**
 * 数据比较接口
 * @author yuhaiming
 * @date 2014-9-3
 */
public interface Compare<T> {
	/**
	 * 按指定point比较data1和data2，result保存不同，若相同返回true,否则返回false
	 * @param data1 
	 * @param data2
	 * @param point
	 * @param result
	 * @return
	 */
	public boolean compare(T data1, T data2, List<String> point, List<String> result);
}
