package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

	
	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping("/pet_store") //maps post verbs with url values that end in /pet_store to this method 
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore (@RequestBody PetStoreData petStoreData) {
		log.info("Creating pet store!");
		return petStoreService.savePetStore(petStoreData); //calls on savePetStore method from PetStoreService to save the post info to the database 
	}
	
	@PutMapping("/pet_store/{petStoreId}") //maps put verbs with url values that end in /pet_store/{petStoreId} to this method 
	public PetStoreData modifyPetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Modifying pet store.");
		return petStoreService.savePetStore(petStoreData); //calls on savePetStore method from PetStoreService to save the post info to the database
	}
	
	@PostMapping("/{petStoreId}/employee") //maps the post verbs with url values that end in /pet_store/{petStoreId}/employee to this method 
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertEmployee (@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Adding new employee!");
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	@PostMapping("/{petStoreId}/customer") //maps the post verbs with url values that end in /pet_store/{petStoreId}/employee to this method 
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertCustomer (@PathVariable Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Adding new customer!");
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	@GetMapping("/pet_store") //maps get verbs with /pet_store/pet_store to this method 
	public List<PetStoreData> listPetStores(){
		return petStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/{petStoreId}") //maps get verbs with /pet_store/{petStoreId} to this method 
	public PetStoreData getPetStoreById(@PathVariable Long petStoreId) {
		return petStoreService.getPetStoreById(petStoreId);
	}
	
	@DeleteMapping("/{petStoreId}") //maps delete verbs with /pet_store/{petStoreId} to this method 
	public Map<String,String> deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("Deleted pet store with ID=" + petStoreId); 
		return petStoreService.deletePetStoreById(petStoreId);
	}
}
