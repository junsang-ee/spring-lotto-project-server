package com.lotto.web.service.aspect;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidBasicFormatException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.model.dto.response.RandomLottoListResponse;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.transaction.Transactional;
import java.util.List;

import static com.lotto.web.util.LottoUtil.getLottoCount;
import static com.lotto.web.util.LottoUtil.getIsCorrectPriceUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LottoAspect {

    private final UserService userService;

    @Transactional
    @Around(value = "execution(* com..LottoService.getRandomList(..)) && args(userId, price, exceptList, needsList)",
            argNames = "point, userId, price, exceptList, needsList")
    public Object getRandomLottoList(ProceedingJoinPoint point, String userId, int price, List<Integer> exceptList, List<Integer> needsList) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (!getIsCorrectPriceUnit(price))
            throw new InvalidBasicFormatException(ErrorMessage.LOTTO_PRICE_UNIT);

        int count = getLottoCount(price);
        UserEntity user = userService.getUser(userId);

        if (user.getDailyAvailableCount() < count)
            throw new InvalidStateException(ErrorMessage.LOTTO_EXCEED_ISSUE);

        RandomLottoListResponse result = (RandomLottoListResponse) point.proceed();
        stopWatch.stop();
        log.info("getRandomLottoList :: " + stopWatch.prettyPrint());
        userService.updateAvailableCount(userId, count);
        userService.saveExtractionLottos(userId, result);
        return result;
    }
}
