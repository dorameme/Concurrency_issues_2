package hello.api.service;

import hello.api.domain.Coupon;
import hello.api.repository.CouponCountRepository;
import hello.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;


    public ApplyService(CouponRepository couponRepository,CouponCountRepository couponCountRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository= couponCountRepository;
    }
    public Long count(){
        return couponRepository.count();
    }
    public  void  apply(Long userId){
        Long count = couponCountRepository.increment();
        if(count > 100){
            return;//발급불가능
        }
        couponRepository.save(new Coupon(userId));
    }
}
