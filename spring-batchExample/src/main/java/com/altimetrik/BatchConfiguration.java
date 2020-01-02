package com.altimetrik;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class BatchConfiguration {

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, 
			StepBuilderFactory stepBuilderFactory,
			ItemReader<UserEntity> itemReader,
			ItemProcessor<UserEntity, UserEntity> itemProcessor,
			ItemWriter<UserEntity> itemWriter) {
		// jobbuilder factory provided by spring to startjob using build,
		// get= name of job
		// increment to give number to each step
		// start the step , flow to use multiple steps and then next.
		Step step = stepBuilderFactory.get("ETL-FILE_PROCESSOR_WRITER")
				.<UserEntity, UserEntity>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
		
		return jobBuilderFactory.
				get("ETL-JOB")
				.incrementer(new RunIdIncrementer()).
				start(step).
				build();

	}

	@Bean
	FlatFileItemReader<UserEntity> fileItemReader(@Value("${input}") Resource resource) {
		FlatFileItemReader<UserEntity> flatFileItemReader = new FlatFileItemReader<UserEntity>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;

	}

	@Bean
	public LineMapper<UserEntity> lineMapper() {
		// map line
		DefaultLineMapper<UserEntity> defaultLineMapper = new DefaultLineMapper<UserEntity>();
		// line mapper ..wht type of file... csv
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] { "id", "name", "dept", "salary" });

		BeanWrapperFieldSetMapper<UserEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<UserEntity>();
		fieldSetMapper.setTargetType(UserEntity.class);

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}
}
