package rashjz.info.app.mail.mailexporter.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rashjz.info.app.mail.mailexporter.domain.ClientDTO;
import rashjz.info.app.mail.mailexporter.tasklets.TaskletStep;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public TaskletStep taskletStep;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job1(@Qualifier("step1") Step step) {
        return jobBuilderFactory.get("job1")
//                .listener(protocolListener())
                .start(step).next(step2())
                .build();
    }

    @Bean
    public Step step1(ItemReader<ClientDTO> itemReader,
                      ItemWriter<ClientDTO> itemWriter,
                      ItemProcessor<ClientDTO, ClientDTO> itemProcessor) {
        return stepBuilderFactory.get("step1")
                .<ClientDTO, ClientDTO>chunk(10) //important to be one in this case to commit after every line read
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
//                .listener(logProcessListener())
                .faultTolerant()
                .skipLimit(0) //default is set to 0
//                .skip(MySQLIntegrityConstraintViolationException.class)
                .build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(taskletStep)
                .build();
    }
}
