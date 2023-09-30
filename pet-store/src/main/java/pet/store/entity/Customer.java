package pet.store.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Customer {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	
	private String customerFirstName; 
	private String customerLastName; 
	
	@Column(unique = true) //does not allow duplicate emails 
	private String customerEmail;
	
	@EqualsAndHashCode.Exclude //prevents recursion when .equals() and .hashCode() methods are called
	@ToString.Exclude //prevents recursion when .toString() is called 
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	Set<PetStore> petStores;
	
}
