package com.roncoo.education.course.service.admin.biz;

import cn.hutool.core.collection.CollUtil;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.service.BaseBiz;
import com.roncoo.education.course.service.admin.req.AdminRatingDashboardReq;
import com.roncoo.education.course.service.admin.resp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminRatingDashboardBiz extends BaseBiz {

    public Result<AdminRatingDashboardOverviewResp> overview(AdminRatingDashboardReq req) {
        StringBuilder where = new StringBuilder(" where a.content_type='course' ");
        Map<String, Object> params = buildParams(req, where);
        String sql = "select count(1) as course_count, " +
                "ifnull(sum(a.score_count),0) as score_count, " +
                "ifnull(sum(a.score_sum),0) as score_sum, " +
                "ifnull(sum(a.score_1_count),0) as score_1_count, " +
                "ifnull(sum(a.score_2_count),0) as score_2_count, " +
                "ifnull(sum(a.score_3_count),0) as score_3_count, " +
                "ifnull(sum(a.score_4_count),0) as score_4_count, " +
                "ifnull(sum(a.score_5_count),0) as score_5_count " +
                "from content_rating_agg a left join course c on c.id=a.content_id " + where;
        Map<String, Object> row = namedParameterJdbcTemplate.queryForMap(sql, params);
        AdminRatingDashboardOverviewResp resp = new AdminRatingDashboardOverviewResp();
        int scoreCount = number(row.get("score_count"));
        int scoreSum = number(row.get("score_sum"));
        resp.setCourseCount(number(row.get("course_count")));
        resp.setScoreCount(scoreCount);
        resp.setScoreAvg(scoreCount > 0 ? BigDecimal.valueOf(scoreSum).divide(BigDecimal.valueOf(scoreCount), 2, RoundingMode.HALF_UP) : null);
        resp.setScore1Count(number(row.get("score_1_count")));
        resp.setScore2Count(number(row.get("score_2_count")));
        resp.setScore3Count(number(row.get("score_3_count")));
        resp.setScore4Count(number(row.get("score_4_count")));
        resp.setScore5Count(number(row.get("score_5_count")));
        return Result.success(resp);
    }

    public Result<List<AdminRatingTrendResp>> trend(AdminRatingDashboardReq req) {
        StringBuilder where = new StringBuilder(" where cc.score between 1 and 5 ");
        Map<String, Object> params = new HashMap<>();
        if (req.getCourseId() != null) {
            where.append(" and cc.content_id=:courseId ");
            params.put("courseId", req.getCourseId());
        }
        if (req.getLecturerId() != null) {
            where.append(" and c.lecturer_id=:lecturerId ");
            params.put("lecturerId", req.getLecturerId());
        }
        if (StringUtils.hasText(req.getBeginDate())) {
            where.append(" and date(cc.gmt_create)>=:beginDate ");
            params.put("beginDate", req.getBeginDate());
        }
        if (StringUtils.hasText(req.getEndDate())) {
            where.append(" and date(cc.gmt_create)<=:endDate ");
            params.put("endDate", req.getEndDate());
        }
        String sql = "select date(cc.gmt_create) as day, count(1) as score_count, round(avg(cc.score),2) as score_avg " +
                "from chain_comment cc left join course c on c.id=cc.content_id " + where +
                " group by date(cc.gmt_create) order by day asc";
        List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, params);
        List<AdminRatingTrendResp> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            AdminRatingTrendResp r = new AdminRatingTrendResp();
            r.setDay(String.valueOf(row.get("day")));
            r.setScoreCount(number(row.get("score_count")));
            Object avg = row.get("score_avg");
            r.setScoreAvg(avg == null ? null : new BigDecimal(String.valueOf(avg)));
            list.add(r);
        }
        return Result.success(list);
    }

    public Result<List<AdminRatingCourseRankResp>> courseRank(AdminRatingDashboardReq req) {
        int topN = req.getTopN() == null || req.getTopN() <= 0 ? 10 : req.getTopN();
        StringBuilder where = new StringBuilder(" where a.content_type='course' ");
        Map<String, Object> params = buildParams(req, where);
        params.put("topN", topN);
        String sql = "select c.id as course_id, c.course_name, a.score_count, round(a.score_sum/a.score_count,2) as score_avg " +
                "from content_rating_agg a left join course c on c.id=a.content_id " + where +
                " and a.score_count > 0 order by score_avg desc, a.score_count desc limit :topN";
        List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, params);
        List<AdminRatingCourseRankResp> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            AdminRatingCourseRankResp r = new AdminRatingCourseRankResp();
            r.setCourseId(longNumber(row.get("course_id")));
            r.setCourseName(String.valueOf(row.get("course_name")));
            r.setScoreCount(number(row.get("score_count")));
            Object avg = row.get("score_avg");
            r.setScoreAvg(avg == null ? null : new BigDecimal(String.valueOf(avg)));
            list.add(r);
        }
        return Result.success(list);
    }

    public Result<List<AdminKeywordResp>> keywords(AdminRatingDashboardReq req) {
        StringBuilder where = new StringBuilder(" where uc.comment_text is not null and uc.comment_text != '' ");
        Map<String, Object> params = new HashMap<>();
        if (req.getCourseId() != null) {
            where.append(" and uc.course_id=:courseId ");
            params.put("courseId", req.getCourseId());
        }
        if (req.getLecturerId() != null) {
            where.append(" and c.lecturer_id=:lecturerId ");
            params.put("lecturerId", req.getLecturerId());
        }
        if (StringUtils.hasText(req.getBeginDate())) {
            where.append(" and date(uc.gmt_create)>=:beginDate ");
            params.put("beginDate", req.getBeginDate());
        }
        if (StringUtils.hasText(req.getEndDate())) {
            where.append(" and date(uc.gmt_create)<=:endDate ");
            params.put("endDate", req.getEndDate());
        }
        String sql = "select uc.comment_text from user_course_comment uc left join course c on c.id=uc.course_id " +
                where + " order by uc.id desc limit 1000";
        List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, params);
        if (CollUtil.isEmpty(rows)) {
            return Result.success(Collections.emptyList());
        }
        Map<String, Integer> wordMap = new HashMap<>();
        for (Map<String, Object> row : rows) {
            String commentText = String.valueOf(row.get("comment_text"));
            if (!StringUtils.hasText(commentText)) {
                continue;
            }
            String text = commentText.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9\\s]", " ");
            for (String word : text.split("\\s+")) {
                word = word.trim();
                if (word.length() < 2 || STOP_WORDS.contains(word)) {
                    continue;
                }
                wordMap.merge(word, 1, Integer::sum);
            }
        }
        int topN = req.getTopN() == null || req.getTopN() <= 0 ? 10 : req.getTopN();
        List<AdminKeywordResp> list = wordMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(topN)
                .map(e -> {
                    AdminKeywordResp r = new AdminKeywordResp();
                    r.setWord(e.getKey());
                    r.setCount(e.getValue());
                    return r;
                }).collect(Collectors.toList());
        return Result.success(list);
    }

    private Map<String, Object> buildParams(AdminRatingDashboardReq req, StringBuilder where) {
        Map<String, Object> params = new HashMap<>();
        if (req.getCourseId() != null) {
            where.append(" and a.content_id=:courseId ");
            params.put("courseId", req.getCourseId());
        }
        if (req.getLecturerId() != null) {
            where.append(" and c.lecturer_id=:lecturerId ");
            params.put("lecturerId", req.getLecturerId());
        }
        return params;
    }

    private int number(Object v) {
        if (v == null) return 0;
        return ((Number) v).intValue();
    }

    private long longNumber(Object v) {
        if (v == null) return 0L;
        return ((Number) v).longValue();
    }

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "这个", "那个", "老师", "课程", "真的", "就是", "感觉", "我们", "你们", "他们", "非常", "比较", "还是", "可以", "不错", "一般"
    ));
}

