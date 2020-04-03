package at.coon_and_friends.foundation;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class BaseObjectClass {

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
