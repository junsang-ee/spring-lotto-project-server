package com.lotto.web.service.countable;

import com.lotto.web.constants.countable.CountableType;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.count.PostCountEntity;
import com.lotto.web.model.entity.count.ReplyCountEntity;
import com.lotto.web.service.BoardService;
import com.lotto.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CountableServiceImpl implements CountableService{
    private final PostService postService;

    private final BoardService boardService;

    @Override
    public ReplyCountEntity getReplyCount(String postId) {
        PostEntity post = postService.get(postId);
        return post.getReplyCount();
    }

    @Override
    @Transactional
    public void updateReplyCount(String postId, CountableType type) {
        PostEntity post = postService.get(postId);
        ReplyCountEntity replyCount = post.getReplyCount();
        int disabledCount = replyCount.getDisabledCount();
        int enabledCount = replyCount.getEnabledCount();
        switch (type) {
            case CANCEL:
                disabledCount--;
                enabledCount++;
                break;
            case CREATE:
                enabledCount++;
                break;
            case REMOVE:
            case DISABLE:
                if (enabledCount != 0)
                    enabledCount--;
                disabledCount++;
                break;
            default: break;
        }
        replyCount.setEnabledCount(enabledCount);
        replyCount.setDisabledCount(disabledCount);
        postService.updateReplyCount(post, replyCount);
    }

    @Override
    @Transactional
    public void updatePostCount(String boardId, CountableType type) {
        BoardEntity board = boardService.get(boardId);
        PostCountEntity postCount = board.getPostCount();
        int disabledCount = postCount.getDisabledCount();
        int enabledCount = postCount.getEnabledCount();
        switch (type) {
            case CANCEL:
                disabledCount--;
                enabledCount++;
                break;
            case CREATE:
                enabledCount++;
                break;
            case REMOVE:
            case DISABLE:
                if (enabledCount != 0)
                    enabledCount--;
                disabledCount++;
                break;
            default: break;
        }
        postCount.setEnabledCount(enabledCount);
        postCount.setDisabledCount(disabledCount);
        boardService.updatePostCount(board, postCount);
    }
}
