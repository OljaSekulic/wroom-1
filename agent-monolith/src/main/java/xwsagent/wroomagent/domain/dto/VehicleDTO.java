package xwsagent.wroomagent.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.domain.FuelType;
import xwsagent.wroomagent.domain.GearboxType;
import xwsagent.wroomagent.domain.ModelType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehicleDTO {

	private Long id;
	
	private Double mileage;
	
	@Min(3)
	@Max(5)
	private Integer childSeats;
	
	private Boolean cdw;
	
	@NotNull(message = "Model Type may not be blank.")
	private FeatureDTO modelType;
	
	@NotNull(message = "Brand Type may not be blank.")
	private FeatureDTO brandType;
	
	@NotNull(message = "Body Type may not be blank.")
	private FeatureDTO bodyType;
	
	@NotNull(message = "Fuel Type may not be blank.")
	private FeatureDTO fuelType;
	
	@NotNull(message = "Gearbox Type may not be blank.")
	private FeatureDTO gearboxType;


}
