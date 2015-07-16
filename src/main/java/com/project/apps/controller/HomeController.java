package com.project.apps.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import viewModel.DateMonth;
import viewModel.DateYears;
import viewModel.MonthModel;
import viewModel.MultiModel;
import viewModel.YearModel;

import com.project.apps.dao.CustomerDao;
import com.project.apps.dao.DeploymentDao;
import com.project.apps.dao.DeploymentJobDao;
import com.project.apps.dao.DeploymentProjectDao;
import com.project.apps.dao.JobDao;
import com.project.apps.dao.ReleasesDao;
import com.project.apps.dao.UserDao;
import com.project.apps.model.Customer;
import com.project.apps.model.Deployment;
import com.project.apps.model.DeploymentProject;
import com.project.apps.model.Job;
import com.project.apps.model.Release;
import com.project.apps.model.User;
import com.project.apps.model.deploymentJob;

@Controller
@RequestMapping(value = "/")
public class HomeController {
	@Autowired
	private ReleasesDao releaseDao;
	@Autowired
	private JobDao jobDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private DeploymentProjectDao deploymentProjectDao;
	@Autowired
	private DeploymentJobDao deploymentJobDao;
	@Autowired
	private DeploymentDao deploymentDao;
	@Autowired 
	private UserDao userDao;

	private ArrayList<Deployment> ListDeployments = new ArrayList<Deployment>();
	private ArrayList<deploymentJob> ListDeploymentJobs = new ArrayList<deploymentJob>();

	private static String customer = "";
	private static int year = 0;
	private static int month = 0;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid credentials!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		
		model.setViewName("login");
		return model;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView model = new ModelAndView();
		//importData();
		model.addObject("releaseContinuous", releaseDao.getAverageDurationContinuous());
		model.addObject("releaseReleases", releaseDao.getAverageDurationReleases());
		model.addObject("deploymentReleases", deploymentDao.getAverageDurationReleases());
		model.addObject("deploymentContinuous", deploymentDao.getAverageDurationContinuous());
		model.setViewName("home");
		
		return model;
	}

	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public ModelAndView ShowCustomerType(ModelAndView model) {
		model.addObject("multimodel", new MultiModel());
		model.addObject("options",jobDao.getNameJobs());
		model.addObject("deployments",deploymentJobDao.getNameDeploymentJobs());
		model.addObject("customers",customerDao.getListCustomerType());
		model.setViewName("jobs");

		return model;
	}
	
	@RequestMapping(value = "/AddJobsToCustomer", method = RequestMethod.POST)
	public String ShowCustomerType(@ModelAttribute("list")MultiModel multiModel,BindingResult result, ModelMap modelMap) {
		String customer  = multiModel.getCustomers().get(0);
		Customer c = customerDao.getCustomer(customer);
		 
		 if(multiModel.getOptions() != null) {
			 for(String nameJob:multiModel.getOptions()) {
				 jobDao.addCustomerID(nameJob,c.getId());
				 }
			}
		 
		 if(multiModel.getDeployments() != null) {
			 for(String deploymentJob:multiModel.getDeployments()) {
				 deploymentJobDao.addCustomerID(deploymentJob, c.getId());
			 }
		 }
		 return "redirect:/jobs";
	}
	
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public ModelAndView showCustomers(ModelAndView model) {
		model.addObject("customers", customerDao.getListCustomerType());
		model.setViewName("customers");
		
		return model;
	}
	
	@RequestMapping(value = "/ShowCustomerStatistics", method = RequestMethod.GET)
	public ModelAndView ShowCustomerStatistics( HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		customer = request.getParameter("id");
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		
		int totalReleasesAndDeployments = releaseDao.getCountReleases(customer, year, month) + deploymentDao.getCountDeployments(customer, year, month);
		

		model.addObject("releaseContinuous",releaseDao.getStatisticsContinous(customer));
		model.addObject("releaseReleases",releaseDao.getStatisticsReleases(customer));
		model.addObject("deploymentContinuous",deploymentDao.getStatisticsContinous(customer));
		model.addObject("deploymentRelease",deploymentDao.getStatisticsReleases(customer));
		model.addObject("deploymentsByMonth",deploymentDao.getListDeploymentsByMonth(customer, year, month));
		model.addObject("releasesByMonth",releaseDao.getListReleasesByMonth(customer, year, month));
		model.addObject("countReleases",releaseDao.getCountReleases(customer, year, month));
		model.addObject("countDeployments",deploymentDao.getCountDeployments(customer, year, month));
		model.addObject("total",totalReleasesAndDeployments);
		model.addObject("customer",customer);
		model.addObject("dateyears", new DateYears());
		model.addObject("years", getYears());
		model.addObject("monthmodel", new DateMonth());
		model.addObject("months", getMonths());
		
		
		model.setViewName("customerStatistics");

		return model;
	}
	
	@RequestMapping(value = "AddCustomer", method = RequestMethod.POST)
	public String AddCustomer(ModelAndView model,@RequestParam("nameCustomer") String nameCustomer) {
		customerDao.addCustomer(nameCustomer);
		model.setViewName("customerType");
		
		return "redirect:/jobs";
	}

	@RequestMapping(value = "/customerStatistics", method = RequestMethod.POST)
	public String ShowCustomerTypeStatistics(@ModelAttribute("dateyears") DateYears dateYears,BindingResult result, ModelMap model) {
		year = dateYears.getYearModel().getYearItem();
		month = dateYears.getMonthModel().getKey();
		
		int totalReleasesAndDeployments = releaseDao.getCountReleases(customer, year, month) + deploymentDao.getCountDeployments(customer, year, month);

		model.addAttribute("releaseContinuous",releaseDao.getStatisticsContinous(customer));
		model.addAttribute("releaseReleases",releaseDao.getStatisticsReleases(customer));
		model.addAttribute("deploymentContinuous",deploymentDao.getStatisticsContinous(customer));
		model.addAttribute("deploymentRelease",deploymentDao.getStatisticsReleases(customer));
		model.addAttribute("deploymentsByMonth",deploymentDao.getListDeploymentsByMonth(customer, year, month));
		model.addAttribute("releasesByMonth",releaseDao.getListReleasesByMonth(customer, year, month));
		model.addAttribute("countReleases",releaseDao.getCountReleases(customer, year, month));
		model.addAttribute("countDeployments",deploymentDao.getCountDeployments(customer, year, month));
		model.addAttribute("total", totalReleasesAndDeployments);
		model.addAttribute("dateyears", new DateYears());
		model.addAttribute("years", getYears());
		model.addAttribute("monthmodel", new DateMonth());
		model.addAttribute("months", getMonths());
		

		return "customerStatistics";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView showUsersPage() {
		ModelAndView model = new ModelAndView();
		User users = new User();
		model.addObject("users", users);
		model.setViewName("users");
		
		return model;
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String AddUser(@ModelAttribute User user) {
		userDao.addUser(user);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("users");
		model.addObject("error","password incorrect");
		
		return "redirect:/users";
	}

	@Scheduled(fixedDelay = 3600000)
	public void importData() {
		Calendar start = Calendar.getInstance();
		System.out.println("start import" + start.getTime());
		importReleaseData();
		importDeploymentData();
		Calendar end = Calendar.getInstance();
		System.out.println("end import" + end.getTime());
	}


	public void importReleaseData() {
		HandleImportReleaseJobs();
		HandleImportReleases();
	}

	public void importDeploymentData() {
		HandleImportDeploymentProjects();
		HandleImportDeploymentProjectJobs();
		HandleImportDeployments();
	}

	public void HandleImportReleases() {
		for (Job job : jobDao.getListJobs()) {
			String JsonString = "";
			URL url = null;

			try {
				url = new URL("http://jenkins-cd.finalist.nl/job/"+ job.getNameJob() + "/api/json?/pretty=true&depth=2&tree=builds[builtOn,duration,timestamp,id,building,result]");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					JsonString = line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				JSONObject obj = new JSONObject(JsonString);
				JSONArray a;
				a = obj.getJSONArray("builds");
				
				for (int i = 0; i < a.length(); i++) {
					boolean releaseSuccedeed = a.getJSONObject(i).getString("result").toUpperCase().equals("SUCCESS");
					
					if(releaseSuccedeed) {
						Timestamp date = setTimeStamp(a.getJSONObject(i).getString("id"));
						long releaseDuration = Long.parseLong(a.getJSONObject(i).getString("duration"));
						
						Release release = new Release();
						release.setDuration(releaseDuration);
						release.setDate(date);
						release.setJobID(job.getId());
						
						if (releaseDao.getRelease(release) == null) {
							releaseDao.addReleases(release);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void HandleImportReleaseJobs() {
		String JsonString = "";
		URL url = null;

		try {
			url = new URL("http://jenkins-cd.finalist.nl/api/json?job[name]");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				JsonString = line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			JSONObject obj = new JSONObject(JsonString);
			JSONArray a = obj.getJSONArray("jobs");

			for (int i = 0; i < a.length(); i++) {
				String nameJob = a.getJSONObject(i).getString("name");
				if (nameJob.toUpperCase().endsWith("CONTINOUS")|| nameJob.toUpperCase().endsWith("CONTINUOUS") || nameJob.toUpperCase().endsWith("RELEASE")) {
					if (jobDao.getJob(nameJob) == null) {
						jobDao.addJob(nameJob);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void HandleImportDeploymentProjects() {
		String xmlData = "";
		URL url = null;
		DocumentBuilder builder = null;
		Document document = null;

		try {
			url = new URL("http://rundeck-cd.finalist.nl/api/11/projects?authtoken=3ztHBgZNUZB8FXwGc1F4IvWmCxtviQIK HTTP/1.1");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				xmlData += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		InputSource source = new InputSource(new StringReader(xmlData));

		try {
			document = builder.parse(source);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		NodeList items = document.getElementsByTagName("project");

		for (int i = 0; i < items.getLength(); i++) {
			Element element = (Element) items.item(i);
			NodeList name = element.getElementsByTagName("name");
			Element elementLine = (Element) name.item(0);
			String nameProject = elementLine.getFirstChild().getTextContent();

			if (deploymentProjectDao.getProject(nameProject.toUpperCase()) == null) {
				DeploymentProject project = new DeploymentProject();
				project.setNameJob(nameProject);
				deploymentProjectDao.addDeploymentProject(project);
			}
		}
	}

	public void HandleImportDeploymentProjectJobs() {
		ListDeploymentJobs.clear();

		for (DeploymentProject project : deploymentProjectDao.getlistDeploymentProjects()) {
			URL url = null;
			DocumentBuilder builder = null;
			Document document = null;
			String xmlData = "";

			try {
				url = new URL("http://rundeck-cd.finalist.nl/api/2/project/" + project.getNameJob().toLowerCase() + "/jobs?authtoken=3ztHBgZNUZB8FXwGc1F4IvWmCxtviQIK HTTP/1.1");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					xmlData += line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			InputSource source = new InputSource(new StringReader(xmlData));

			try {
				document = builder.parse(source);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			NodeList items = document.getElementsByTagName("job");
			for (int i = 0; i < items.getLength(); i++) {
				Element element = (Element) items.item(i);

				NodeList nameNode = element.getElementsByTagName("name");
				Element nameElement = (Element) nameNode.item(0);
				String nameJob = nameElement.getFirstChild().getTextContent();
				String jobID = element.getAttribute("id");
				
				boolean containsWords = nameJob.toUpperCase().contains("-CONTINUOUS") || nameJob.toUpperCase().contains("-RELEASE");

				if (deploymentJobDao.getDeploymentJob(jobID) == null && containsWords) {
					int deploymentProjectID = project.getDeploymentProjectID();

					deploymentJob job = new deploymentJob();
					job.setDeploymentJobID(jobID);
					job.setDeploymentProjectID(deploymentProjectID);
					job.setNameJob(nameJob);

					ListDeploymentJobs.add(job);
				}
				
				else if(deploymentJobDao.getDeploymentJob(jobID) != null && !deploymentJobDao.getDeploymentJob(jobID).getNameJob().equals(nameJob)) {
					    deploymentJobDao.updateNameJob(jobID, nameJob);
				}
			}
		}

		deploymentJobDao.addDeploymentJob(ListDeploymentJobs);
	}

	public void HandleImportDeployments() {
		ListDeployments.clear();

		for (deploymentJob job : deploymentJobDao.getListDeploymentJobs()) {
			String xmlData = "";
			URL url = null;
			DocumentBuilder builder = null;
			Document document = null;

			try {
				url = new URL("http://rundeck-cd.finalist.nl/api/1/job/"+ job.getDeploymentJobID() + "/executions?authtoken=3ztHBgZNUZB8FXwGc1F4IvWmCxtviQIK HTTP/1.1");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					xmlData += line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			InputSource source = new InputSource(new StringReader(xmlData));

			try {
				document = builder.parse(source);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			NodeList items = document.getElementsByTagName("execution");

			for (int i = 0; i < items.getLength(); i++) {
				Element element = (Element) items.item(i);

				NodeList nameItem = element.getElementsByTagName("name");
				Element lines = (Element) nameItem.item(0);
				String nameJob = lines.getFirstChild().getTextContent();

				int executionID = Integer.parseInt(element.getAttribute("id"));
				String jobID = job.getDeploymentJobID();
				
				boolean deploymentSucceeded = element.getAttribute("status").toUpperCase().equals("SUCCEEDED");
				
				if(deploymentSucceeded) {
					boolean containsWords = nameJob.toUpperCase().contains("-RELEASE") || nameJob.toUpperCase().contains("-CONTINUOUS");
					
					if (deploymentDao.getDeployment(executionID, jobID) == null && containsWords) {
					NodeList nodeDateStartDeployment = element.getElementsByTagName("date-started");
					NodeList nodeDateEndDeployment = element.getElementsByTagName("date-ended");
					Element elementStartDeployment = (Element) nodeDateStartDeployment.item(0);
					Element elementEndDeployment = (Element) nodeDateEndDeployment.item(0);

					long startTimeDeployment = Long.parseLong(elementStartDeployment.getAttribute("unixtime"));
					long endTimeDeployment = Long.parseLong(elementEndDeployment.getAttribute("unixtime"));
					long durationDeployment = endTimeDeployment- startTimeDeployment;

					Element dateTimeElement = (Element) nodeDateEndDeployment.item(0);
					String dateTimeElements = dateTimeElement.getFirstChild().getTextContent();
					String[] dateItem = dateTimeElements.split("T");

					String date = dateItem[0];
					String time = dateItem[1].replace("Z", "");
					String timeStamp = date + "_" + time;

					Timestamp timeStampDeployment = setTimeStamp(timeStamp);

					Deployment deployment = new Deployment();
					deployment.setDeploymentJobID(jobID);
					deployment.setDuration(durationDeployment);
					deployment.setDateDeployment(timeStampDeployment);
					deployment.setExecutionID(executionID);
					ListDeployments.add(deployment);
					}
				}
			}
		}

		deploymentDao.addDeployment(ListDeployments);
	}

	public List<YearModel> getYears() {
		List<YearModel> listYears = new ArrayList<YearModel>();
		Calendar past = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		past.set(past.YEAR, releaseDao.getLowestYearRelease());

		int startYear = past.get(Calendar.YEAR);
		int endYear = now.get(Calendar.YEAR);
		int years = endYear - startYear;

		for (int i = years; i > 0; i--) {
			int year = startYear + i;
			listYears.add(new YearModel(year, ""));
		}

		return listYears;
	}

	public List<MonthModel> getMonths() {
		List<MonthModel> listMonths = new ArrayList<MonthModel>();
		Calendar cal = Calendar.getInstance();

		for (int i = 0; i < 12; i++) {
			cal.set(Calendar.MONTH, i);
			int key = cal.get(Calendar.MONTH) + 1;
			String month = new SimpleDateFormat("MMMM").format(cal.getTime()).toUpperCase();
			listMonths.add(new MonthModel(key, month));
		}

		return listMonths;
	}

	public Timestamp setTimeStamp(String date) {
		String[] dateTime = date.split("_");
		String time = dateTime[1].replace("-", ":");
		String timestamp = dateTime[0] + " " + time;

		return Timestamp.valueOf(timestamp);
	}
}
