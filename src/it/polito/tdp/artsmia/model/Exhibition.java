package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Exhibition {
	
	private int id;
	private String department;
	private String title;
	private int begin;
	private int end;
	private List<Integer> artObjectId;
	
	public Exhibition(int id, String department, String title, int begin, int end) {
		super();
		this.id = id;
		this.department = department;
		this.title = title;
		this.begin = begin;
		this.end = end;
		this.artObjectId = new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public String getDepartment() {
		return department;
	}

	public String getTitle() {
		return title;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public List<Integer> getArtObjectId() {
		return artObjectId;
	}

	public void setArtObjectId(List<Integer> artObjectId) {
		this.artObjectId = artObjectId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Exhibition other = (Exhibition) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Exhibition [id=%s, department=%s, title=%s, begin=%s, end=%s]", id, department, title,
				begin, end);
	}
	
	
}
