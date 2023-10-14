package pet.store.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;
	
	@Autowired 
	private EmployeeDao employeeDao;
	
	@Autowired 
	private CustomerDao customerDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) { //creates a new PetStoreData object from info put in the client 
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStoreId(petStoreId);
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
		}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {//sets the values of the parameters of the petStore object to the values received from the JSON 
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

	private PetStore findOrCreatePetStoreId(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			PetStore petStore = new PetStore();
			return petStore;
			// return new petStore object if petStoreId is null
		} else {
			return findPetStoreById(petStoreId); //returns the petStore object identified by the petStoreId if not null 
		}
	}

	private PetStore findPetStoreById(Long petStoreId) {
		// returns a PetStore object if the petStoreId exists or else returns a NSEE
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " was not found."));
	}

	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) { //saves the newly added employee to the database 
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(employeeId, petStoreId);
	
		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		
		Set<Employee> employeeSet = petStore.getEmployees();
		employeeSet.add(employee); //adds the employee to the petStore employee set 
		petStore.setEmployees(employeeSet); //sets this new set to the petStore employee set 
		
		Employee dbEmployee = employeeDao.save(employee);
		return new PetStoreEmployee(dbEmployee); 
		
	}

	private Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {//finds an employee or creates one if that employeeIid does not excist 
		if (Objects.isNull(employeeId)) {
			Employee employee = new Employee();
			return employee;
			//returns new Employee if employeeId is null
		} else {
			return findEmployeeById(petStoreId, employeeId);
		}
	}
	
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {//finds an employee by their employeeId
		Employee employee = employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId +" was not found."));
		if (employee.getPetStore().getPetStoreId() == petStoreId) {
			return employee;
		} else {
			throw new IllegalArgumentException("This employee does not belong to the pet store with a pet store ID=" + petStoreId);
		}
	}
	
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {//copies employee fields to a PetStoreEmployee object 
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeTitle(petStoreEmployee.getEmployeeTitle());
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
	}
	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {//saves the newly created customer to the database 
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, petStoreId);
	
		copyCustomerFields(customer, petStoreCustomer);
		
		Set<PetStore> petStoreSet = customer.getPetStores();
		petStoreSet.add(petStore); //adds the pet store to the customer petStore set 
		customer.setPetStores(petStoreSet); //sets this new set to the petStore customer set 
		
		Set<Customer> customerSet = petStore.getCustomers();
		customerSet.add(customer); //adds the customer to the petStore customer set 
		petStore.setCustomers(customerSet); //sets this new set to the petStore customer set 
		
		Customer dbCustomer = customerDao.save(customer);
		return new PetStoreCustomer(dbCustomer); 
		
	}

	private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {//finds a customer by their customerId or creates a new customer if they don't already exist 
		if (Objects.isNull(customerId)) {
			Customer customer = new Customer();
			return customer;
			//returns new Customer if customerId is null
		} else {
			return findCustomerById(petStoreId, customerId);
		}
	}
	
	private Customer findCustomerById(Long petStoreId, Long customerId) {//finds a customer by their customerId 
		Customer customer = customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId +" was not found."));
		int count = 0;
		for(PetStore petStoreIdentified : customer.getPetStores()) {
			if(petStoreIdentified.getPetStoreId() == petStoreId) {
				return customer;
			}else {
				count++;
			}
		}
		if(count > petStoreId.MAX_VALUE) {//tests to see if the count is greater than the maximum value of the petStoreId. This will tell us if the previous enhanced for loop ran all the way through the set without finding a matching pet store. 
			throw new IllegalArgumentException("This customer does not shop at a pet store.");
		}
		return null;
	}
	
	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {//sets the values of the customer to a PetStoreCustomer object 
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}

	@Transactional(readOnly = false)
	public List<PetStoreData> retrieveAllPetStores() { //uses findAll from the PetStoreDao class to retrieve all pet stores without their customer and employee data 
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		for(PetStore petStore : petStores) {
			
			PetStoreData petStoreData = new PetStoreData(petStore); //throwing an error because the customer set in the pet store is null 
			petStoreData.getPetStoreCustomer().clear(); 
			petStoreData.getPetStoreEmployee().clear();
			
			result.add(petStoreData);
		}
		return result;
	}

	@Transactional(readOnly = false)
	public PetStoreData getPetStoreById(Long petStoreId) {//returns the pet store data of one pet store 
		PetStore ps = findPetStoreById(petStoreId);
		PetStoreData psd = new PetStoreData(ps);
		return psd;
	}

	public Map<String, String> deletePetStoreById(Long petStoreId) {//deletes a pet store by their petStoreId and returns a message that it was deleted 
		PetStore ps = findPetStoreById(petStoreId);
		petStoreDao.delete(ps);
		Map<String,String> map = new HashMap<String,String>();
		map.put("message","Pet store with ID=" + petStoreId + " was successfully deleted.");
		return null;
	}
}
