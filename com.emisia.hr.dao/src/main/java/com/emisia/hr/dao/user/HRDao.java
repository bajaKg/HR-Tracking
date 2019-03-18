package com.emisia.hr.dao.user;

import java.util.Date;
import java.util.HashSet;

import com.emisia.hr.model.Employee;
import com.emisia.hr.model.EmployeeDto;
import com.emisia.hr.model.Employee_Exit;
import com.emisia.hr.model.JobCandidate;
import com.emisia.hr.model.JobPost;
import com.emisia.hr.model.Project;

public interface HRDao {

    public boolean createEmployeeTable();

    public HashSet<EmployeeDto> getAllEmployees();

    public HashSet<EmployeeDto> showSearchedEmployees(String term);
    public HashSet<JobCandidate> showSearchedJobCandidate(String term);
    public HashSet<JobPost> showSearchedJobPosts(String term);
    
    public EmployeeDto getEmployee(int id);

    public boolean addNewEmployee(String firstName, String lastName,
	    Date startDate, String emailAddress, String phone,
	    String education, String workExpiriance, String interests,
	    String position, Date timeOfDisable, String cvAddress,
	    boolean enabled);

    public HashSet<Project> showAllProjects();

    public HashSet<Project> showSearchedProjects(String term);

    public HashSet<EmployeeDto> showAllProjectEmployees(int id);

    public void addNewProject(String projectName, String clientName,
	    Date projectStartDate, Date projectEndDate,
	    String projectDescription);


    public HashSet<Employee_Exit> showAllDisabledEmployees();

    public HashSet<EmployeeDto> showAllEmployees();
    
    public boolean createJobPostTable();
    
    public void addNewJobPost(String jobPosition, String projectName, String jobType, int noOfPositions,
	    Date closingDate, String description);
    
    public HashSet<JobPost> showAllJobPosts();
    
    public void enableEmployee(int employeeId);

    public void disableEmployee(int employeeId);
    
    public boolean createJobCandidatesTable();

}