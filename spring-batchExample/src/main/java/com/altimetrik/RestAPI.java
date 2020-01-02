package com.altimetrik;

import java.util.HashMap;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBatchProcessing
public class RestAPI {

	@Autowired(required = true)
	JobLauncher JobLauncher;

	@Autowired
	org.springframework.batch.core.Job Job;

	@GetMapping
	@RequestMapping("/call")
	public BatchStatus callBatch() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		System.out.println("batch processing to be called..");
		HashMap hm = new HashMap<String, JobParameter>();
		hm.put("time", new JobParameter(System.currentTimeMillis()));
		// JobParameters jobParameters;
		JobParameters parameter = new JobParameters(hm);
		JobExecution exec = JobLauncher.run(Job, parameter);
		System.out.println("job exceution : " + exec.getStatus());
		return exec.getStatus();
	}
}
