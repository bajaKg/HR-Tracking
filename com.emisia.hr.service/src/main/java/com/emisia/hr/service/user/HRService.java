package com.emisia.hr.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.emisia.hr.model.Employee;
import com.emisia.hr.model.EmployeeDto;
import com.emisia.hr.model.Employee_Exit;
import com.emisia.hr.model.JobCandidate;
import com.emisia.hr.model.JobPost;
import com.emisia.hr.model.Project;

public interface HRService {


    public HashSet<Project> showAllProjects();

    public HashSet<Project> showSearchedProjects(String term) ;

    public void addNewProjects(String projectName, String clientName,
	    Date projectStartDate, Date projectEndDate,
	    String projectDescription);

    public HashSet<EmployeeDto> showAllEmployees();

    public HashSet<EmployeeDto> showSearchedEmployees(String term);

    public HashSet<Employee_Exit> showAllDisabledEmployees();
    public HashSet<JobCandidate> showSearchedJobCandidate(String term);
    public boolean createEmployeeTable();

    public HashSet<EmployeeDto> getAllEmployees();

    public void enableEmployee(int employeeId);

    public void disableEmployee(int employeeId);

    public boolean addNewEmployee(String firstName, String lastName,
	    Date startDate, String emailAddress, String phone,
	    String education, String workExpiriance, String interests,
	    String position, Date timeOfDisable, String cvAddress,
	    boolean enabled);
    
    public HashSet<EmployeeDto> showAllProjectEmployees(int id);

	public HashSet<EmployeeDto> showPossibleEmployees();

	public void editPossibleEmployees(ArrayList<Integer> selectedEmployees, String projectName);

	public void addToTeam(String projectName, int employeeID);
	
	public HashSet<JobPost> showAllJobPosts();
	
	public HashSet<JobPost> showSearchedJobPosts(String term);
	
	public boolean createJobCandidatesTable();
	
	public void addNewJobCandidate(String firstName, String lastName,
		    String gender, String address, String email, String phone,
		    String mobile, String education, String workExpiriance,
		    String hobbies, String position, String cvAddress);
}
