package src.ea.firstweek;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import src.ea.firstweek.model.Project;
import src.ea.firstweek.model.Resource;
import src.ea.firstweek.model.Status;
import src.ea.firstweek.model.Task;
import src.ea.firstweek.model.User;
import src.ea.firstweek.model.UserType;

public class App {
	private static Logger logger = Logger.getLogger(App.class);;

	private static final EntityManagerFactory emf;

	static {
		try {
			//pvm is for project volunter management
			emf = Persistence.createEntityManagerFactory("pvm");
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		EntityManager em = null;
		EntityTransaction tx = null;

		// fill the database
		fillDataBase();

		// Flights leaving USA capacity > 500
		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			
			//3. List information about projects and their beneficiaries
			System.out.println("List all the projects with their beneficiaries:");
			List<Project> projects = em.createQuery("from Project").getResultList();
			for (Project project : projects) {
				System.out.println(String.format(
						"%-7s  %-12s %7s %8s", project.getDescription(), project.getStartDate(), 
						project.getEndDate(), project.getLocation()
						));
				for (User beneficiary : project.getBeneficiaries()) {
					System.out.println(String.format("%12s", beneficiary.getFirstname()+" "+ beneficiary.getLastname()));
					
				}
			}
			
			//4. List tasks for a project with id 1
		    List<Task> tasks = em.createQuery("select t from Task t where t.project.id = 1").getResultList();
    	        System.out.println("00"+tasks.size());
			 for(Task task : tasks)
			 {
		    	    System.out.println(" Project id : " + task.getProject().getId() + " Task ID : "+task.getId() + " SD : " 
		     +task.getStartDate() + " ED : "
		    	    + task.getEndDate());
		     }
			
			 //5. list projects by status
			 System.out.println("List all the tasks with needed resources of volunter :");
			 String query = "select t from Task t where t.resource = '" + Resource.Voulunter +"'";
				tasks = em.createQuery(query).getResultList();
				 System.out.println("00"+tasks.size());
				 for(Task task : tasks)
				     {
				    	    System.out.println(" Project id : " + task.getProject().getId() + " Task ID : "+task.getId() + " SD : " 
				     +task.getStartDate() + " ED : "
				    	    + task.getEndDate());
				     }
				 
				//6
				 
				 System.out.println("List all the projects with their description :");
					projects = em.createQuery("select p from Project p where p.description like '%main%'").getResultList();
					for (Project project : projects) {
						System.out.println(String.format(
								"%-7s  %-12s %7s %8s", project.getDescription(), project.getStartDate(), 
								project.getEndDate(), project.getLocation()
								));
						for (User beneficiary : project.getBeneficiaries()) {
							System.out.println(String.format("%12s", beneficiary.getFirstname()+" "+ beneficiary.getLastname()));
							
						}
					}
					
				//6
					 System.out.println("List all the projects with their location:");
						projects = em.createQuery("select p from Project p where p.location like '%fairfield%'").getResultList();
						for (Project project : projects) {
							System.out.println(String.format(
									"%-7s  %-12s %7s %8s", project.getDescription(), project.getStartDate(), 
									project.getEndDate(), project.getLocation()
									));
							for (User beneficiary : project.getBeneficiaries()) {
								System.out.println(String.format("%12s", beneficiary.getFirstname()+" "+ beneficiary.getLastname()));
								
							}
						}
						
			 //7 List projects and tasks where a volunteer have offered services, ordered by date of the task.
						
						 System.out.println("List all the tasks with needed resources of volunter and order them by date :");
						 query = "select t from Task t where t.resource =" + Resource.Voulunter + " order by t.startDate";
							tasks = em.createQuery(query).getResultList();
							 System.out.println("00"+tasks.size());
							 for(Task task : tasks)
							     {
							    	    System.out.println(" Project id : " + task.getProject().getId() + " Task ID : "+task.getId() + " SD : " 
							     +task.getStartDate() + " ED : "
							    	    + task.getEndDate());
							     }	
				 
			
		} catch (PersistenceException e) {
			if (tx != null) {
				logger.error("Rolling back:", e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	public static void fillDataBase() {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			
			// add users 
			
			User user1 = new User("admin1", "user1first", UserType.Adminstrator);
			User user2 = new User("beneficiery1", "beneficiery1first", UserType.Beneficiery);
			User user3 = new User("beneficiery2", "beneficiery2first", UserType.Beneficiery);
			User user4 = new User("volunter1", "volunter1first", UserType.Volunter);
			User user5 = new User("volunter2", "volunter2first", UserType.Volunter);
			
			//add project
			
			List<User> beneficiaries = new ArrayList<>();
			beneficiaries.add(user2);
			beneficiaries.add(user3);
			
			
			Project project1 = new Project("this projects main aim is project 1", "Fairfield", "08/01/2017", "08/27/2017", beneficiaries);

			Project project2 = new Project("this projects main aim is project 2", "Addis Ababa", "07/01/2017", "07/27/2017", beneficiaries);
			
			
			// add tasks
			List<User> volunters = new ArrayList<>();
			volunters.add(user5);
			volunters.add(user4);
			
			
			Task task1 = new Task("08/06/2017", "08/16/2017", Status.Active, Resource.Voulunter, 2, project1, volunters);

			Task task2 = new Task("08/05/2017", "08/26/2017", Status.Active, Resource.Asset , 3, project1, volunters);
			
			

			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			em.persist(user1);
			em.persist(user2);
			em.persist(user3);
			em.persist(user4);
			em.persist(user5);
			em.persist(task1);
			em.persist(project1);
			em.persist(project2);
			em.persist(task1);
			em.persist(task2);

			tx.commit();
			
		} catch (PersistenceException e) {
			if (tx != null) {
				logger.error("Rolling back", e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

}
