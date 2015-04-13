package org.svtcc.online.management.dao;

import java.util.List;

import org.svtcc.online.management.domain.HomeworkAnswer;

public interface HomeworkAnswerDAO extends BaseDAO<HomeworkAnswer> {
    public List<HomeworkAnswer> findHomeworkAnswersByCourseId(Integer courseId,
            Integer studentId);
}
