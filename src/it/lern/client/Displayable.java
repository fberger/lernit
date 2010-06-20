package it.lern.client;

import java.util.Collections;
import java.util.Map;

public interface Displayable {
	
	public static final Map<String, Object> EMPTY = Collections.emptyMap();

	public void setData(Map<String, ?> data);
}
