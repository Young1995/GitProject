package org.svtcc.online.management.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.svtcc.online.management.dao.HomeworkAnswerDAO;
import org.svtcc.online.management.domain.HomeworkAnswer;

@Repository
public class HomeworkAnswerDAOImpl extends BaseDaoImpl<HomeworkAnswer>
        implements HomeworkAnswerDAO {
    @Override
    public List<HomeworkAnswer> findHomeworkAnswersByCourseId(Integer courseId,
            Integer studentId) {
        return find(
                "from HomeworkAnswer obj where obj.question. = ? and obj.studentId = ?",
                courseId, studentId);
    }
}
