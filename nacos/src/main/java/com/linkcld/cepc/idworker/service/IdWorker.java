package com.linkcld.cepc.idworker.service;

/**
 * @author jack
 */

import com.linkcld.cepc.idworker.exception.GenIdClockBackwardException;
import lombok.extern.slf4j.Slf4j;

/**
 *   (a) id构成: 44位的时间前缀 + 8位节点标识 + 12位的sequence避免并发的数字(12位不够用时强制得到新的时间前缀)
 *   *注原组成为  42位时间前缀+5位数据中心+5位节点标识+12位sequence.
 *   (b) 对系统时间的依赖性非常强。当检测到ntp时间调整后，将会拒绝分配id
 */
@Slf4j
public class IdWorker {

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间
     */
    private static final long epoch = 1551946799611L;
    /**
     * 机器标识位数
     */
    private static final long workerIdBits = 8L;
    /**
     * 机器ID最大值
     */
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 毫秒内自增位数
     */
    private static final long sequenceBits = 12L;

    private static final long workerIdShift = sequenceBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits;

    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private static volatile IdWorker idWorker;

    private long lastTimestamp = -1L;

    private long sequence = 0L;
    private long workerIdShiftValue;

    private IdWorker(Long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }

        this.workerIdShiftValue = workerId << workerIdShift;
    }

    public static IdWorker getIdWorker(Long workerId) {
        if(idWorker == null) {
            synchronized (IdWorker.class){
                if(idWorker == null){
                    idWorker = new IdWorker(workerId);
                }
            }
        }
        return idWorker;
    }

    public synchronized long nextId() throws GenIdClockBackwardException {
        long timestamp = IdWorker.timeGen();
        // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环); 对新的timestamp，sequence从0开始
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1 & sequenceMask;
            if (this.sequence == 0) {
                // 重新生成timestamp
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }

        if (timestamp < this.lastTimestamp) {
            log.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new GenIdClockBackwardException(this.lastTimestamp - timestamp);
        }

        this.lastTimestamp = timestamp;
        return timestamp - epoch << timestampLeftShift
            | this.workerIdShiftValue
            | this.sequence;
    }
    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = IdWorker.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = IdWorker.timeGen();
        }
        return timestamp;
    }
    /**
     * 获得系统当前毫秒数
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
