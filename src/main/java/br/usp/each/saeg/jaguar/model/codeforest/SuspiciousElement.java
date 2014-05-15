package br.usp.each.saeg.jaguar.model.codeforest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class SuspiciousElement {

	protected String name;
	protected Integer number;
	protected Integer location;
	protected Double suspiciousValue = 0.0;

	/**
	 * Return the name.
	 * Package name, simple class name, method signature or line number. 
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name.
	 * Package name, simple class name or method signature. 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the total amount of requirements within this element which has the
	 * maximum suspicious value
	 */
	@XmlAttribute
	public Integer getNumber() {
		return number;
	}

	/**
	 * Set the total amount of requirements within this element which has the
	 * maximum suspicious value
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * Return the line number where the element begins
	 */
	@XmlAttribute
	public Integer getLocation() {
		return location;
	}

	/**
	 * Set the line number where the element begins
	 */
	public void setLocation(Integer location) {
		this.location = location;
	}

	/**
	 * Return the suspicious value of the element. For package, class or
	 * method represent its children's maximum suspicious value.
	 */
	@XmlAttribute(name = "suspicious-value")
	public Double getSuspiciousValue() {
		return suspiciousValue;
	}

	/**
	 * Set the suspicious value of the element. For package, class or
	 * method represent its children's maximum suspicious value.
	 */
	public void setSuspiciousValue(Double suspiciousValue) {
		this.suspiciousValue = suspiciousValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((suspiciousValue == null) ? 0 : suspiciousValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuspiciousElement other = (SuspiciousElement) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (suspiciousValue == null) {
			if (other.suspiciousValue != null)
				return false;
		} else if (!suspiciousValue.equals(other.suspiciousValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SuspiciousElement [name=" + name + ", number=" + number + ", location=" + location
				+ ", suspiciousValue=" + suspiciousValue + "]";
	}

}
