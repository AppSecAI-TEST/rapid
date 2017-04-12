package org.rapid.util.common.region;

public enum RegionType {

	COUNTRY(1){
		// 最后两位是 "00 的必是县级行政区划"
		@Override
		protected boolean _match(int code) {
			String str = String.valueOf(code).substring(1);
			return str.equals("00000");
		}
		@Override
		public int parent0(int code) {
			throw new UnsupportedOperationException("Country has no parent");
		}
	},
	
	PROVINCE(2){
		// 最后两位是 "00 的必是县级行政区划"
		@Override
		protected boolean _match(int code) {
			String str = String.valueOf(code).substring(2);
			return str.equals("0000");
		}
		@Override
		public int parent0(int code) {
			String str = String.valueOf(code).substring(0, 1);
			return Integer.valueOf(str += "00000");
		}
	},
	
	CITY(3) {
		// 最后两位是 "00 的必是县级行政区划"
		@Override
		protected boolean _match(int code) {
			String str = String.valueOf(code).substring(4);
			return str.equals("00");
		}
		@Override
		public int parent0(int code) {
			String str = String.valueOf(code).substring(0, 2);
			return Integer.valueOf(str += "0000");
		}
	},
	
	
	DISTRICT(4) {
		// 最后两位不是 "00 的必是县级行政区划"
		@Override
		protected boolean _match(int code) {
			String str = String.valueOf(code).substring(4);
			return !str.equals("00");
		}
		@Override
		public int parent0(int code) {
			String str = String.valueOf(code).substring(0, 4);
			return Integer.valueOf(str += "00");
		}
	};
	
	protected int level;
	
	/**
	 * 尾部编码
	 */
	protected String tailCode;
	
	/**
	 * 固定部分和尾部的分割位置
	 */
	protected int separator;
	
	private RegionType(int level) {
		this.level = level;
	}
	
	public int level() {
		return level;
	}
	
	public int separator() {
		return separator;
	}
	
	public String tailCode() {
		return tailCode;
	}
	
	protected abstract int parent0(int code);
	
	protected boolean _match(int code) {
		String str = String.valueOf(code).substring(separator);
		return str.equals(tailCode);
	}
	
	public static final RegionType match(int code) {
		for (RegionType type : RegionType.values()) {
			if (type._match(code))
				return type;
		}
		throw new IllegalArgumentException("Unsupported region code");
	}
	
	public static int parent(int code) {
		RegionType type = match(code);
		return type.parent0(code);
	}
}
