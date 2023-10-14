package pet.store.controller.model;

import java.util.HashSet;
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
	Set<PetStoreCustomer> petStoreCustomer = new HashSet<>(); 
	Set<PetStoreEmployee> petStoreEmployee = new HashSet<>();
	
	
	public PetStoreData(PetStore petStore) {//creates a new constructor for PetStoreData that takes in a PetStore object as a parameter 
		//these statements set the values of the petStore data pieces to the values stored in the petStore object 
		this.petStoreId = petStore.getPetStoreId();
		this.petStoreName = petStore.getPetStoreName();
		this.petStoreAddress = petStore.getPetStoreAddress();
		this.petStoreCity = petStore.getPetStoreCity();
		this.petStoreState = petStore.getPetStoreState();
		this.petStoreZip = petStore.getPetStoreZip(); 
		this.petStorePhone = petStore.getPetStorePhone();
		
		for(Customer customer: petStore.getCustomers()) {//for loops are needed to set the sets to contain the information from the petStore object 
			this.petStoreCustomer.add(new PetStoreCustomer(customer));
		}
		
		for(Employee employee: petStore.getEmployees()) {
			this.petStoreEmployee.add(new PetStoreEmployee(employee));
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
			this.customerId = customer.getCustomerId(); 
			this.customerFirstName = customer.getCustomerFirstName();
			this.customerLastName = customer.getCustomerLastName();
			this.customerEmail = customer.getCustomerEmail();
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
			this.employeeId = employee.getEmployeeId(); 
			this.employeeFirstName = employee.getEmployeeFirstName(); 
			this.employeeLastName = employee.getEmployeeLastName(); 
			this.employeePhone = employee.getEmployeePhone(); 
			this.employeeTitle = employee.getEmployeeTitle();
		}
		
	}
	
}
