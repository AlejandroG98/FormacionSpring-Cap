package com.example.batch;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;

public class FTPLoadTasklet implements Tasklet, InitializingBean {

	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	private static final Logger log = LoggerFactory.getLogger(FTPLoadTasklet.class);
	private Resource source;

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File dir = source.getFile();
		Assert.state(dir.isDirectory(), "No es un Directory");
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File file, String name) {
				return name.toLowerCase().endsWith(".csv");
			}
		});
		// if(files.length % 2 == 1) throw new UnexpectedJobExecutionException("Error
		// forzado");
		for (int i = 0; i < files.length; i++) {
			Files.copy(files[i].toPath(), Paths.get("src/main/resources/" + files[i].getName()),
					StandardCopyOption.REPLACE_EXISTING);
			log.info("Copy " + files[i].getName());
		}
		return RepeatStatus.FINISHED;
	}

	public void setDirectoryResource(Resource directory) {
		this.source = directory;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(source, "directory must be set");
	}

	@Bean
	public FTPLoadTasklet ftpLoadTasklet(@Value("${input.dir.name:./ftp}") String dir) {
		FTPLoadTasklet tasklet = new FTPLoadTasklet();
		tasklet.setDirectoryResource(new FileSystemResource(dir));
		return tasklet;
	}

	@Bean
	public Step copyFilesInDir(FTPLoadTasklet ftpLoadTasklet) {
		return new StepBuilder("copyFilesInDir", jobRepository).tasklet(ftpLoadTasklet, transactionManager).build();
	}

	@Bean
	public Job personasJob(PersonasJobListener listener, Step copyFilesInDir) {
		return new JobBuilder("personasJob", jobRepository).incrementer(new RunIdIncrementer()).listener(listener)
				.start(copyFilesInDir).build();
	}

}