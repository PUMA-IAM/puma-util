package puma.util.attributes;

import java.util.List;

public class AttributeJSON {
	
	private String name;
	private List<String> values;
	private String multiplicity;
	private String dataType;
	
	public AttributeJSON() {
		
	}
	
	public AttributeJSON(String name, List<String> values, String multiplicity, String dataType) {
		this.setName(name);
		this.setValues(values);
		this.setMultiplicity(multiplicity);
		this.setDataType(dataType);
	}
	
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final List<String> getValues() {
		return values;
	}
	public final void setValues(List<String> values) {
		this.values = values;
	}
	public final String getMultiplicity() {
		return multiplicity;
	}
	public final void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}
	public final String getDataType() {
		return dataType;
	}
	public final void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
