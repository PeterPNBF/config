package com.tydic.config.spi;
/**
 * 配置文件类，用于文件类型判断
 * @author yuhaiming
 * @date 2014-9-2
 */
public class ConfigFile {
	private String name = null;
	private Type type = null;
	public ConfigFile() {
		// TODO Auto-generated constructor stub
	}
	public ConfigFile(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public enum Type{
		XML(".xml", 4), PROPERTIES(".properties", 11);
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		private String name;
		private int index;
		private Type(String name, int index) {
			// TODO Auto-generated constructor stub
			this.name = name;
			this.index = index;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.index + "_" + this.name;
		}
	}
	public static String getTypes(){
		StringBuilder properType = new StringBuilder("[");
		Type [] types = Type.values();
		for(int i=0; i<types.length; ++i){
			if(i == 0){
				properType.append("*" + types[i].getName());
			} else {
				properType.append(" , *" + types[i].getName());
			}
		}
		properType.append("]");
		return properType.toString();
	}
	public Type judgeType(){
		if(name!=null){
			Type [] types = Type.values();
			for(Type type : types){
				if(name.endsWith(type.getName())){
					this.type = type;
					return type;
				}
			}
		}
		return null;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Type getType() {
		return type;
	}
}
