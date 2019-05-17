package com.linkcld.cepc.idworker.api;

import com.linkcld.cepc.idworker.service.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 2019-03-07
 */
@RestController
public class IdWorkerRestController {

    @Autowired
    private IdWorker idWorker;

    @GetMapping("id/next")
    public Long genId(){
        return idWorker.nextId();
    }

}
