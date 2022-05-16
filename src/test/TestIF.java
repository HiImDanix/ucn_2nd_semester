package test;

import model.modelIF;

public interface TestIF <T extends modelIF>{
	
	public boolean compareObjects(T obj, T obj2);
	
}
