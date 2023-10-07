package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
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
}
