package product.spring.demo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class category implements Serializable {

	@Id
	@NotBlank(message = "Id đang trống bạn ơi")
	@Column(name = "idcategory")
	
	private String idcategory;
	
	@NotBlank(message = "Tên đang trống bạn ơi ")
	private String name;
	@OneToMany(mappedBy = "idcategory", cascade = CascadeType.ALL)
	private List<product> productList = new ArrayList<>();

	public String getIdcategory() {
		return idcategory;
	}

	public void setIdcategory(String idcategory) {
		this.idcategory = idcategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<product> getProductList() {
		return productList;
	}

	public void setProductList(List<product> productList) {
		this.productList = productList;
	}

}
