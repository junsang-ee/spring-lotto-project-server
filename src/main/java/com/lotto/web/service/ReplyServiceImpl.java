package com.lotto.web.service;

import com.lotto.web.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
}
