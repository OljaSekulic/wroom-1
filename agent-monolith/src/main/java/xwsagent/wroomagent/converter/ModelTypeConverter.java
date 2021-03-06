package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.service.BrandTypeService;

public class ModelTypeConverter extends AbstractConverter {
	
	public static FeatureDTO fromEntity(ModelType entity) {
		return new FeatureDTO(
				entity.getId(),
				entity.getName(),
				(entity.getBrandType() != null) ? entity.getBrandType().getId() : null);
	}
	
	public static ModelType toEntity(FeatureDTO dto) {
		ModelType modelType = new ModelType();
		modelType.setName(dto.getName());
		modelType.setDeleted(false);
		
		return modelType;
	}
}
