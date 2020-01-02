package com.altimetrik;

import java.util.HashMap;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomProcessor implements ItemProcessor<UserEntity, UserEntity> {

	private static final HashMap<String, String> hashMap = new HashMap<String, String>();

	CustomProcessor() {
		hashMap.put("CSE", "900");
		hashMap.put("IT", "9001");
		hashMap.put("ITI", "9002");
	}

	@Override
	public UserEntity process(UserEntity item) throws Exception {
		System.out.println("item processor..");
		String depString = item.getDept();// CSE
		String code = hashMap.get(depString);// 900
		item.setDept(code);

		return item;
	}
}
