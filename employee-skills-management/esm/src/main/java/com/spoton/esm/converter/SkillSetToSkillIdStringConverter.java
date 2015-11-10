package com.spoton.esm.converter;

import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.spoton.esm.model.Skill;

@Component
public class SkillSetToSkillIdStringConverter implements Converter<Set<Skill>, String> {

	@Override
	public String convert(Set<Skill> element) {
		// Cannot explain why this class and this nearly empty implementation is
		// required. But, without this we get error when clicking on Edit Employee link

		// Error: Failed to convert from type @javax.persistence.ManyToMany to type
		// java.lang.String for value '[[id=19, name=Business Analysis], [id=18, name=Java Programming]]'; nested exception
		// is org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type
		// @org.hibernate.validator.constraints.NotEmpty @javax.persistence.ManyToMany @javax.persistence.JoinTable
		// java.util.Set to type java.lang.String
		return "";
	}
}
