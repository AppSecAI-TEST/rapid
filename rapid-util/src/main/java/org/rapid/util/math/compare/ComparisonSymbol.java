package org.rapid.util.math.compare;

import org.rapid.util.validator.Validator;

/**
 * 比较符
 * 
 * @author ahab
 */
public enum ComparisonSymbol {

	/**
	 * 大于
	 */
	gt(1) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case eq:
			case lt:
			case lte:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case bteween:
			case lbteween:
			case rbteween:
				return Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	/**
	 * 大于等于
	 */
	gte(2) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case lt:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case eq:
			case lte:
				return Integer.valueOf(cval[0]) < Integer.valueOf(val[0]);
			case bteween:
			case lbteween:
				return Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			case rbteween:
				return Integer.valueOf(cval[1]) < Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	lt(3) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case eq:
			case bteween:
			case lbteween:
			case rbteween:
			case gt:
			case gte:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	lte(4) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case eq:
			case lbteween:
			case gte:
				return Integer.valueOf(cval[0]) > Integer.valueOf(val[0]);
			case bteween:
			case rbteween:
			case gt:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	eq(5) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			if (Validator.isNumber(val[0])) {
				switch (symbol) {
				case gt:
					return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0]);
				case gte:
					return Integer.valueOf(cval[0]) > Integer.valueOf(val[0]);
				case lt:
					return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
				case lte:
					return Integer.valueOf(cval[0]) < Integer.valueOf(val[0]);
				case eq:
					return Integer.valueOf(cval[0]) != Integer.valueOf(val[0]);
				case bteween:
					return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0])
						|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
				case lbteween:
					return Integer.valueOf(cval[0]) > Integer.valueOf(val[0])
						|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
				case rbteween:
					return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0])
						|| Integer.valueOf(cval[1]) < Integer.valueOf(val[0]);
				default:
					return false;
				}
			} else {
				switch (symbol) {
				case eq:
					return !cval[0].equals(val[0]);
				default:
					return false;
				}
			}
		}
	},
	
	bteween(7) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case gt:
			case gte:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1]);
			case lt:
			case lte:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case eq:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0])
					|| Integer.valueOf(cval[0]) >= Integer.valueOf(val[1]);
			case bteween:
			case lbteween:
			case rbteween:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	lbteween(8) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case gt:
			case gte:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1]);
			case lt:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case lte:
				return Integer.valueOf(cval[0]) < Integer.valueOf(val[0]);
			case eq:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[0]) < Integer.valueOf(val[0]);
			case bteween:
			case lbteween:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			case rbteween:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) < Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	rbteween(9) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case gt:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1]);
			case gte:
				return Integer.valueOf(cval[0]) > Integer.valueOf(val[1]);
			case lt:
			case lte:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case eq:
				return Integer.valueOf(cval[0]) > Integer.valueOf(val[1])
					|| Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case bteween:
			case rbteween:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			case lbteween:
				return Integer.valueOf(cval[0]) > Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	};
	
	private int mark;
	
	private ComparisonSymbol(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	private boolean _checlRange(String[] value) {
		if (value.length != 2)
			return false;
		return Integer.valueOf(value[0]) < Integer.valueOf(value[1]);
	}
	
	public boolean isOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
		try {
			if (symbol == ComparisonSymbol.bteween || symbol == ComparisonSymbol.lbteween || symbol == ComparisonSymbol.rbteween) {
				if (cval.length != 2 || (Integer.valueOf(cval[0]) >= Integer.valueOf(cval[1])))
					return true;
			} else if (cval.length != 1)
				return true;
			return !checkOverlap(symbol, cval, val);
		} catch (Exception e) {
			return true;
		}
	}
	
	protected abstract boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val);
	
	public static final ComparisonSymbol match(int mark) {
		for (ComparisonSymbol symbol : ComparisonSymbol.values()) {
			if (symbol.mark != mark)
				continue;
			return symbol;
		}
		return null;
	}
}
