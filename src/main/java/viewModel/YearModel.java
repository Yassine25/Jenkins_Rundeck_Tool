package viewModel;

public class YearModel {
	private int yearItem;
	private String value;

	public YearModel(int yearItem, String value) {
		this.yearItem = yearItem;
		this.value = value;
	}

	public YearModel() {
	}

	public int getYearItem() {
		return yearItem;
	}

	public void setYearItem(int yearItem) {
		this.yearItem = yearItem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getValue();
	}
}
