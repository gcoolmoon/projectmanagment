package src.ea.firstweek.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Task {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private Resource resource;
	
	private int amountNeeded;
	
	@ManyToOne
	@JoinColumn(name="projectId")
    private Project project;
	
	@ManyToMany
	@JoinTable(name="Task_Volunters",
						joinColumns = @JoinColumn(name="voulunterId"),
						inverseJoinColumns = @JoinColumn(name = "taskId"))
	private List<User> volunters = new ArrayList<>();


	private static DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
			Locale.US);
	
	
	public Task() {
		
	}
	
	public Task(String startDate, String endDate, Status status, Resource resource, int amountNeeded, Project project, List<User> volunters) {
		super();
		setStartDate(startDate);
		setEndDate(endDate);
		this.status = status;
		this.amountNeeded = amountNeeded;
		this.project = project;
		this.volunters = volunters;
		this.resource = resource;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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


	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getAmountNeeded() {
		return amountNeeded;
	}

	public void setAmountNeeded(int amountNeeded) {
		this.amountNeeded = amountNeeded;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	

}
