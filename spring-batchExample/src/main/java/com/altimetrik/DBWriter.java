package com.altimetrik;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBWriter implements ItemWriter<UserEntity> {
	
	@Autowired
	UserRepo repo;

	@Override
	public void write(List<? extends UserEntity> items) throws Exception {
		System.out.println("added to db...");
		repo.saveAll(items);
		
		
	}

}
