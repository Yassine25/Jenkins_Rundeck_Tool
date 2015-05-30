package viewModel;

public class MonthModel {
	private int key;
	private String month;

	public MonthModel(int key, String month) {
		this.key = key;
		this.month = month;
	}

	public MonthModel() {
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
