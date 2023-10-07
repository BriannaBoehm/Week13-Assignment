package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
public class Customer {//generates the customer table with the columns listed 

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	private String customerFirstName; 
	private String customerLastName; 
	private String customerEmail;
	
	@EqualsAndHashCode.Exclude //prevents recursion when .equals() and .hashCode() methods are called
	@ToString.Exclude //prevents recursion when .toString() is called 
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)//creates the many to many relationship between customer and pet store 
	private Set<PetStore> petStores = new HashSet<>();
	
}
