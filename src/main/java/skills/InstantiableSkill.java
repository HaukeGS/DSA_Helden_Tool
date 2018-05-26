package skills;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface InstantiableSkill {

	enum SkillType {
		PROPERTY, BADPROPERTY, LANGUAGE, PRIMARY_ATTRIBUTE, SECONDARY_ATTRIBUTE
	}

	SkillType value();

}
