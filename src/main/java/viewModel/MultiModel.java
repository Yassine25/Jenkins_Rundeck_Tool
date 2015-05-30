package viewModel;

import java.util.Collections;
import java.util.List;

public class MultiModel {
	private List<String> options = Collections.emptyList();
	private List<String> deployments = Collections.emptyList();
	private List<Integer> years = Collections.emptyList();
	private List<String> customers = Collections.emptyList();

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}
	
	public List<String> getDeployments() {
		return deployments;
	}

	public void setDeployments(List<String> deployments) {
		this.deployments = deployments;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}
	
	public List<String> getCustomers() {
		return customers;
	}

	public void setCustomers(List<String> customers) {
		this.customers = customers;
	}

}
