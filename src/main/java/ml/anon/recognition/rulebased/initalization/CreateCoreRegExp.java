package ml.anon.recognition.rulebased.initalization;

import lombok.extern.java.Log;
import ml.anon.recognition.rulebased.model.RegExpRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by mirco on 11.06.17.
 */
@Component
@Log
class CreateCoreRegExp implements CommandLineRunner {

    @Resource
    private RegExpRepository repo;

    @Override
    public void run(String... args) throws Exception {
        if (repo.findByCore(true).isEmpty()) {
            log.info("Can not find core RegExp; adding ...");
            new CoreRegExp().getMap().entries().forEach(a -> repo.save(a.getValue()));
        }
    }
}
