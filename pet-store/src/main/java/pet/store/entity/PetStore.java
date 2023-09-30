package pet.store.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class PetStore {//generates the pet store table with the columns listed 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer petStoreId;
	
	private String petStoreName; 
	private String petStoreAddress; 
	private String petStoreCity; 
	private String petStoreState; 
	private Integer petStoreZip; 
	private Integer petStorePhone; 
	
	@EqualsAndHashCode.Exclude //prevents recursion when .equals() and .hashCode() methods are called
	@ToString.Exclude //prevents recursion when .toString() is called 
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "pet_store_customer", joinColumns = 
	@JoinColumn(name = "pet_store_id"), inverseJoinColumns = 
	@JoinColumn(name = "customer_id"))
	Set<Customer> customers; 
	
	@EqualsAndHashCode.Exclude //prevents recursion when .equals() and .hashCode() methods are called 
	@ToString.Exclude //prevents recursion when .toString() is called 
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Employee> employees;
	
}
