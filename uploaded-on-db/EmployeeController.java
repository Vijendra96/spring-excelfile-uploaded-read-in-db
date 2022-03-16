package com.example.jpa3;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PostMapping
	public ResponseEntity<ArrayList<Employee>> save(@RequestParam MultipartFile excelFile) throws Exception
	{
		//file uploading
		FileOutputStream fout = new FileOutputStream("src/main/resources/uploads/" + excelFile.getOriginalFilename());
		byte[] bytes = excelFile.getBytes();
		fout.write(bytes);
		
		//POI API (in order to read excel file)
		ArrayList<Employee> employees = new ArrayList<Employee>();
		XSSFWorkbook workbook = new XSSFWorkbook("src/main/resources/uploads/" + excelFile.getOriginalFilename());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		XSSFRow row;
		Employee employee;
		for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++)
		{
			row = worksheet.getRow(i);
			employee = new Employee();
			employee.setEmpId(row.getCell(0).getStringCellValue());
			employee.setFirstName(row.getCell(1).getStringCellValue());
			employee.setLastName(row.getCell(2).getStringCellValue());
			employee.setAge((int)row.getCell(3).getNumericCellValue());
			employee.setEmail(row.getCell(4).getStringCellValue());
			employee.setSalary((int)row.getCell(5).getNumericCellValue());
			employees.add(employee);
		}
		
		employeeRepository.saveAll(employees);
		return ResponseEntity.ok(employees);
	}
}
