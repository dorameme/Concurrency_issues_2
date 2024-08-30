package hello.api.service;

import hello.api.domain.Coupon;
import hello.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;

    public ApplyService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }
    public  void  apply(Long userId){
        long count= couponRepository.count();
        if(count > 100){
            return;//발급불가능
        }
        couponRepository.save(new Coupon(userId));
    }
}
