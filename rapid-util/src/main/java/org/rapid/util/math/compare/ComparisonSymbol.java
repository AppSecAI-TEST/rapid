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
	GREATER_THAN(1) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case EQUAL:
			case LESS_THAN:
			case LESS_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case BETWEEN:
			case LE_BETWEEN:
			case RE_BETWEEN:
				return Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	/**
	 * 大于等于
	 */
	GREATER_THAN_OR_EQUAL(2) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case LESS_THAN:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case EQUAL:
			case LESS_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) < Integer.valueOf(val[0]);
			case BETWEEN:
			case LE_BETWEEN:
				return Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			case RE_BETWEEN:
				return Integer.valueOf(cval[1]) < Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	LESS_THAN(3) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case EQUAL:
			case BETWEEN:
			case LE_BETWEEN:
			case RE_BETWEEN:
			case GREATER_THAN:
			case GREATER_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	LESS_THAN_OR_EQUAL(4) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case EQUAL:
			case LE_BETWEEN:
			case GREATER_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) > Integer.valueOf(val[0]);
			case BETWEEN:
			case RE_BETWEEN:
			case GREATER_THAN:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	EQUAL(5) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			if (Validator.isNumber(val[0])) {
				switch (symbol) {
				case GREATER_THAN:
					return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0]);
				case GREATER_THAN_OR_EQUAL:
					return Integer.valueOf(cval[0]) > Integer.valueOf(val[0]);
				case LESS_THAN:
					return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
				case LESS_THAN_OR_EQUAL:
					return Integer.valueOf(cval[0]) < Integer.valueOf(val[0]);
				case EQUAL:
					return Integer.valueOf(cval[0]) != Integer.valueOf(val[0]);
				case BETWEEN:
					return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0])
						|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
				case LE_BETWEEN:
					return Integer.valueOf(cval[0]) > Integer.valueOf(val[0])
						|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
				case RE_BETWEEN:
					return Integer.valueOf(cval[0]) >= Integer.valueOf(val[0])
						|| Integer.valueOf(cval[1]) < Integer.valueOf(val[0]);
				default:
					return false;
				}
			} else {
				switch (symbol) {
				case EQUAL:
					return !cval[0].equals(val[0]);
				default:
					return false;
				}
			}
		}
	},
	
	BETWEEN(7) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case GREATER_THAN:
			case GREATER_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1]);
			case LESS_THAN:
			case LESS_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case EQUAL:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0])
					|| Integer.valueOf(cval[0]) >= Integer.valueOf(val[1]);
			case BETWEEN:
			case LE_BETWEEN:
			case RE_BETWEEN:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	LE_BETWEEN(8) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case GREATER_THAN:
			case GREATER_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1]);
			case LESS_THAN:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case LESS_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) < Integer.valueOf(val[0]);
			case EQUAL:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[0]) < Integer.valueOf(val[0]);
			case BETWEEN:
			case LE_BETWEEN:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			case RE_BETWEEN:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) < Integer.valueOf(val[0]);
			default:
				return false;
			}
		}
	},
	
	RE_BETWEEN(9) {
		@Override
		protected boolean checkOverlap(ComparisonSymbol symbol, String[] cval, String[] val) {
			switch (symbol) {
			case GREATER_THAN:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1]);
			case GREATER_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) > Integer.valueOf(val[1]);
			case LESS_THAN:
			case LESS_THAN_OR_EQUAL:
				return Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case EQUAL:
				return Integer.valueOf(cval[0]) > Integer.valueOf(val[1])
					|| Integer.valueOf(cval[0]) <= Integer.valueOf(val[0]);
			case BETWEEN:
			case RE_BETWEEN:
				return Integer.valueOf(cval[0]) >= Integer.valueOf(val[1])
					|| Integer.valueOf(cval[1]) <= Integer.valueOf(val[0]);
			case LE_BETWEEN:
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
			if (symbol == ComparisonSymbol.BETWEEN || symbol == ComparisonSymbol.LE_BETWEEN || symbol == ComparisonSymbol.RE_BETWEEN) {
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
