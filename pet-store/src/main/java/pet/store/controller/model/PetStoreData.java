package pet.store.controller.model;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {

	private Long petStoreId;
	private String petStoreName; 
	private String petStoreAddress; 
	private String petStoreCity; 
	private String petStoreState; 
	private String petStoreZip; 
	private String petStorePhone; 
	Set<Customer> petStoreCustomer; 
	Set<Employee> petStoreEmployee;
	
	
	public PetStoreData(PetStore petStore) {//creates a new constructor for PetStoreData that takes in a PetStore object as a parameter 
		//these statements set the values of the petStore data pieces to the values stored in the petStore object 
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip(); 
		petStorePhone = petStore.getPetStorePhone();
		
		for(Customer customer: petStore.getCustomers()) {//for loops are needed to set the sets to contain the information from the petStore object 
			petStoreCustomer.add(customer);
		}
		
		for(Employee employee: petStore.getEmployees()) {
			petStoreEmployee.add(employee);
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class PetStoreCustomer {//creates PetStoreCustomer as an internal class to PetStoreData
		
		private Long customerId;
		private String customerFirstName; 
		private String customerLastName; 
		private String customerEmail;
		
		public PetStoreCustomer (Customer customer) {//constructor for PetStoreCustomer DTO that takes in a customer object 
			customerId = customer.getCustomerId(); 
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class PetStoreEmployee {//creates PetStoreEmployee as an internal class to PetStoreData
		
		private Long employeeId; 
		private String employeeFirstName; 
		private String employeeLastName; 
		private String employeePhone; 
		private String employeeTitle;
		
		public PetStoreEmployee(Employee employee) {//constructor for PetStoreEmployee DTO that takes in an employee object 
			employeeId = employee.getEmployeeId(); 
			employeeFirstName = employee.getEmployeeFirstName(); 
			employeeLastName = employee.getEmployeeLastName(); 
			employeePhone = employee.getEmployeePhone(); 
			employeeTitle = employee.getEmployeeTitle();
		}
		
	}
	
}
