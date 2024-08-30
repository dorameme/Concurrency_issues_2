package hello.api.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.api.repository.CouponCountRepository;
import hello.api.repository.CouponRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplyServiceTest {
    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void 한번만응모(){
        applyService.apply(1L);
        assertThat(applyService.count()).isEqualTo(1);
    }

    @Test
    public void 여러명응모() throws InterruptedException {
        int theradCount =1000;
        ExecutorService executorService = Executors.newFixedThreadPool(theradCount);//병렬작업을 돕는다.
        CountDownLatch latch = new CountDownLatch(theradCount);

        for (int i = 0; i < theradCount; i++) {
            long userId=i;
            executorService.submit(()->{
                try {
                    applyService.apply(userId);
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        long count= couponRepository.count();
        assertThat(count) .isEqualTo(100);

    }
}