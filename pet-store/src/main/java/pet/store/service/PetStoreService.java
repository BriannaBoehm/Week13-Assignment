package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;

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

}
