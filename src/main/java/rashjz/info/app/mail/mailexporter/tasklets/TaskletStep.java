package rashjz.info.app.mail.mailexporter.tasklets;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskletStep implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            File file = new File("path\\to\\file");
            if (file.delete()) {
                log.info("### TaskletStep:" + file.getName() + " is deleted!");
            } else {
                log.error("Delete operation is failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}