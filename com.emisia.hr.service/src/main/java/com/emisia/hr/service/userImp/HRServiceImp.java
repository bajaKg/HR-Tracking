package com.emisia.hr.service.userImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.emisia.hr.dao.userIMP.HRDaoImp;
import com.emisia.hr.model.Employee;
import com.emisia.hr.model.EmployeeDto;
import com.emisia.hr.model.Employee_Exit;
import com.emisia.hr.model.JobCandidate;
import com.emisia.hr.model.JobPost;
import com.emisia.hr.model.Project;
import com.emisia.hr.service.user.HRService;

public class HRServiceImp implements HRService {
    private HRDaoImp hrDao;

    public HRServiceImp() {
	hrDao = new HRDaoImp();
    }

    public HashSet<Project> showAllProjects() {
	return hrDao.showAllProjects();
    }

    public HashSet<Project> showSearchedProjects(String term) {
	return hrDao.showSearchedProjects(term);
    }

    public void addNewProjects(String projectName, String clientName,
	    Date projectStartDate, Date projectEndDate,
	    String projectDescription) {
	hrDao.addNewProject(projectName, clientName, projectStartDate,
		projectEndDate, projectDescription);
    }

    public HashSet<EmployeeDto> showAllEmployees() {
	return hrDao.showAllEmployees();
    }

    public HashSet<EmployeeDto> showSearchedEmployees(String term) {
	return hrDao.showSearchedEmployees(term);
    }

    public HashSet<Employee_Exit> showAllDisabledEmployees() {
	return hrDao.showAllDisabledEmployees();
    }

    public boolean createEmployeeTable() {
	return hrDao.createEmployeeTable();
    }

    public HashSet<EmployeeDto> getAllEmployees() {
	return hrDao.getAllEmployees();
    }

    public void enableEmployee(int employeeId) {
	hrDao.enableEmployee(employeeId);
    }

    public void disableEmployee(int employeeId) {
	hrDao.disableEmployee(employeeId);
    }

    public boolean addNewEmployee(String firstName, String lastName,
	    Date startDate, String emailAddress, String phone,
	    String education, String workExpiriance, String interests,
	    String position, Date timeOfDisable, String cvAddress,
	    boolean enabled) {
	return hrDao.addNewEmployee(firstName, lastName, startDate,
		emailAddress, phone, education, workExpiriance, interests,
		position, timeOfDisable, cvAddress, enabled);
    }

    public HashSet<EmployeeDto> showAllProjectEmployees(int id) {
	return hrDao.showAllProjectEmployees(id);
    }

    public HashSet<EmployeeDto> showPossibleEmployees() {
	return hrDao.showPossibleEmployees();
    }

    public void editPossibleEmployees(ArrayList<Integer> selectedEmployees,
	    String projectName) {
	hrDao.editPossibleEmployees(selectedEmployees, projectName);
    }

    public void addToTeam(String projectName, int employeeID) {
	hrDao.addToTeam(projectName, employeeID);
    }

    public HashSet<JobPost> showAllJobPosts() {
	return hrDao.showAllJobPosts();
    }

    public HashSet<JobPost> showSearchedJobPosts(String term) {
	return hrDao.showSearchedJobPosts(term);
    }

    public boolean createJobCandidatesTable() {
	return hrDao.createJobCandidatesTable();
    }

    public HashSet<JobCandidate> showSearchedJobCandidate(String term) {
	return hrDao.showSearchedJobCandidate(term);
    }

    public void addNewJobPost(String jobPosition, String projectName,
	    String jobType, int noOfPositions, Date closingDate,
	    String description) {
	hrDao.addNewJobPost(jobPosition, projectName, jobType, noOfPositions,
		closingDate, description);
    }

    public void addNewJobCandidate(String firstName, String lastName,
	    String gender, String address, String email, String phone,
	    String mobile, String education, String workExpiriance,
	    String hobbies, String position, String cvAddress) {

	hrDao.addNewJobCandidate(firstName, lastName, gender, address, email,
		phone, mobile, education, workExpiriance, hobbies, position,
		cvAddress);
    }
}