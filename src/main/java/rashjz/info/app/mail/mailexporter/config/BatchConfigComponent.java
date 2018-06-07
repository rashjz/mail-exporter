package rashjz.info.app.mail.mailexporter.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import rashjz.info.app.mail.mailexporter.domain.ClientDTO;
import rashjz.info.app.mail.mailexporter.processor.StudentProcessor;

@Configuration
public class BatchConfigComponent {
    private static final String[] FIELDS = new String[]{"name", "emailAddress", "purchasedPackage"};

    @Bean
    ItemReader<ClientDTO> itemReader() {
        FlatFileItemReader<ClientDTO> csvFileReader = new FlatFileItemReader<>();
        csvFileReader.setResource(new ClassPathResource("data/students.csv"));
        csvFileReader.setLinesToSkip(1);

        DefaultLineMapper<ClientDTO> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setNames(FIELDS);
        defaultLineMapper.setLineTokenizer(lineTokenizer);


        BeanWrapperFieldSetMapper<ClientDTO> wrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        wrapperFieldSetMapper.setTargetType(ClientDTO.class);
        defaultLineMapper.setFieldSetMapper(wrapperFieldSetMapper);


        csvFileReader.setLineMapper(defaultLineMapper);

        return csvFileReader;
    }


    @Bean
    ItemWriter<ClientDTO> itemWriter() {
        FlatFileItemWriter<ClientDTO> csvFileWriter = new FlatFileItemWriter<>();

//        String exportFileHeader = "NAME;EMAIL_ADDRESS;PACKAGE";
//        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
//        csvFileWriter.setHeaderCallback(headerWriter);

        String exportFilePath = "tmp/students.csv";
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));

        DelimitedLineAggregator<ClientDTO> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        BeanWrapperFieldExtractor<ClientDTO> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(FIELDS);
        lineAggregator.setFieldExtractor(extractor);


        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    @Bean
    ItemProcessor<ClientDTO, ClientDTO> itemProcessor(JavaMailSender javaMailSender) {
        return new StudentProcessor(javaMailSender);
    }

}	