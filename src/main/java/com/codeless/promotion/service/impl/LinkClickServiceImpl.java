package com.codeless.promotion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeless.promotion.entity.PromotionLinkClickEvent;
import com.codeless.promotion.mapper.PromotionLinkClickEventMapper;
import com.codeless.promotion.service.InitService;
import com.codeless.promotion.service.LinkClickService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LinkClickServiceImpl extends ServiceImpl<PromotionLinkClickEventMapper, PromotionLinkClickEvent> implements LinkClickService, InitService {



    /**
     * 服务初始化逻辑，尽量避免加载大量原始数据耗时
     * 加载三个月之内的评论数据
     * 1. 加载所有数据到缓存中
     * 2. 记录所有内容主体ID
     * 3. 进行内容主体所有维度排序缓存重新生成
     */
    @Override
    public void init() {
//        log.info("开始加载所有的评论信息>>>");
//        DateTime beginTime = DateUtil.offset(DateUtil.date(), DateField.DAY_OF_MONTH, -3 * 30);
//        LambdaQueryWrapper<NewsComment> wrapper = new LambdaQueryWrapper<>();
//        wrapper.select(
//            NewsComment::getCommentId,
//            NewsComment::getContentId,
//            NewsComment::getParentCommentId,
//            NewsComment::getCommentContentType,
//            NewsComment::getCommentContentSubType
//        );
//        // 过滤掉用户删除及拒审删除的评论
//        wrapper.eq(NewsComment::getStatus, NewsCommentStatus.NORMAL.getValue());
//        // @wdy todo 需要全量数据
////        wrapper.ne(NewsComment::getReviewStatus, NewsCommentReviewStatus.REJECTED_DELETED.getValue());
////        wrapper.ge(NewsComment::getCreateTime, DateUtil.formatDateTime(beginTime));
//        wrapper.orderByDesc(NewsComment::getCommentId);
////        wrapper.last("limit 1000");
//        List<NewsComment> commentList = this.list(wrapper);
//        log.info("共查询评论信息 {} 条", commentList.size());
//        List<String> totalContentUrlList = new ArrayList<>();
//        commentList.parallelStream().forEach(comment -> {
////        commentList.forEach(comment -> {
//            // @wdy todo 加载历史数据，有可能评论的文章在文章列表不存在，NEWS_INFO_CONTENT_ID_CACHE拿不到 因为是转的缓存
//            CommentContent content = NewsServiceImpl.getCommentContentInfoFromCache(comment);
//            if (content != null) {
//                AbstractCommentContentTypeStrategy contentTypeStrategy = CommentContentTypeStrategyContext.getStrategyByCommentContentType(content.getCommentContentType());
//                String contentCacheKey = contentTypeStrategy.getCommentContentCacheKey(content.getContentId(), content.getCommentContentSubType());
//
//                // @wdy todo 先看效果 再调整
//                // redis中有则不主动加载到内存
//                if (NEWS_COMMENT_ORIGIN_INFO_CACHE.existsInRedis(contentCacheKey)) {
//                    ;
//                }
//                // redis中没有则主动获取，即穿透到数据库进行加载
//                else {
//                    NEWS_COMMENT_ORIGIN_INFO_CACHE.get(contentCacheKey);
//                }
//                totalContentUrlList.add(content.getContentUrl());
//            }
//        });
//        log.info("完成加载所有的评论信息<<<===");
//        log.info("共加载评论信息 {} 条", commentList.size());
//
//        log.info("开始加载一级评论的二级评论列表信息>>>");
//        AtomicInteger subCommentCount = new AtomicInteger();
//        // @wdy todo redis并发异常 修改为固定线程池处理或者直接串行 Caused by: java.util.concurrent.RejectedExecutionException: Thread limit exceeded replacing blocked worker
////        commentList.parallelStream().forEach(comment -> {
//        commentList.forEach(comment -> {
//            if (comment.isLevelOneComment()) {
//                subCommentCount.getAndIncrement();
//                // @wdy todo 先看效果 再调整
//                // redis中有则不主动加载到内存
//                if (NEWS_COMMENT_SUB_COMMENT_ID_LIST_CACHE.existsInRedis(comment.getCommentId())) {
//                    ;
//                }
//                // redis中没有则主动获取，即穿透到数据库进行加载
//                else {
//                    NEWS_COMMENT_SUB_COMMENT_ID_LIST_CACHE.get(comment.getCommentId());
//                }
//            }
//
//        });
//        log.info("完成加载一级评论的二级评论列表信息<<<===");
//        log.info("共加载一级评论的二级评论列表 {} 条", subCommentCount.get());
//
//        log.info("开始进行内容主体所有维度排序缓存重新生成>>>");
//        List<String> newtotalContentUrlList = CollUtil.distinct(totalContentUrlList);
//        newtotalContentUrlList.forEach(NewsCommentServiceImpl::rebuildOrderedCommentList);

//        readCommentInfoFromFile();

        //
    }



}
