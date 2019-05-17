package com.linkcld.cepc.idworker.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @since 2019-03-07
 */
class IdWorkerTest {

    @Test
    void nextId() {
        IdWorker idWorker = IdWorker.getIdWorker(0L);
        Assertions.assertThat(idWorker.nextId()).isLessThan(idWorker.nextId());
    }

    @Test
    void getIdWorker() {
        Assertions.assertThat(IdWorker.getIdWorker(0L)).isEqualTo(IdWorker.getIdWorker(0L));
    }
}