package org.svtcc.online.management.service;

import java.util.List;

import org.svtcc.online.management.domain.HomeworkAnswer;

public interface HomeworkAnswerService extends BaseService<HomeworkAnswer> {
    public List<HomeworkAnswer> findHomeworkAnswersByCourseId(Integer courseId);
}
