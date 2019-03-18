package com.emisia.hr.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import com.emisia.hr.model.EmployeeDto;
import com.emisia.hr.service.userImp.HRServiceImp;

public class HRServiceTest {
    @Test
    public void testGetAllEmployees() {
	HRServiceImp hr = new HRServiceImp();
	HashSet<EmployeeDto> employees = hr.getAllEmployees();
	assertFalse(employees.isEmpty());
    }
    
    @Test
    public void testAddNewEmployee(){
	HRServiceImp hr = new HRServiceImp();
	boolean added = hr.addNewEmployee("milos", "milos", new Date(),
		"milos","milos","milos","milos","milos","milos",new Date(), "milos", true);
	assertTrue(added);
    }
}