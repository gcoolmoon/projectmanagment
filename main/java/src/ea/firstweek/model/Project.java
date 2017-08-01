package src.ea.firstweek.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	private String location;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@ManyToMany
	@JoinTable(name="Project_Beneficiaries",
						joinColumns = @JoinColumn(name="beneficiaryId"),
						inverseJoinColumns = @JoinColumn(name = "projectId"))
	private List<User> beneficiaries = new ArrayList<>();
	
	@OneToMany(mappedBy="project")
	private List<Task> tasks = new ArrayList<>();
	
	private static DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
			Locale.US);
	
	public Project() {
		
	}

	public Project(String description, String location, String startDate, String endDate, List<User> beneficiaries) {
		super();
		this.description = description;
		this.location = location;
		setStartDate(startDate);
		setEndDate(endDate);
		this.beneficiaries = beneficiaries;
		//this.tasks = tasks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStartDate() {
		return df.format(startDate) ;
	}

	public void setStartDate(String startDate) {
		try {
			this.startDate =  df.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getEndDate() {
		return df.format(endDate) ;
	}

	public void setEndDate(String endDate) {
		try {
			this.endDate = df.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> getBeneficiaries() {
		return beneficiaries;
	}

	public void setBeneficiaries(List<User> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
	

}
