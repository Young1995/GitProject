package org.svtcc.online.management.service.impl;

import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.OnlineQuestionDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.OnlineQuestion;
import org.svtcc.online.management.dto.OnlineQuestionDTO;
import org.svtcc.online.management.service.OnlineQuestionService;

import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created by hanrenwei on 4/1/15.
 */
@Service
public class OnlineQuestionServiceImpl implements OnlineQuestionService {
    @Autowired
    private OnlineQuestionDAO onlineQuestionDAO;

    @Override
    public void saveOrUpdate(OnlineQuestionDTO onlineQuestionDTO) {
        OnlineQuestion onlineQuesion = new OnlineQuestion();
        if (onlineQuestionDTO.getId() != null) {
            onlineQuesion = onlineQuestionDAO.find(onlineQuestionDTO.getId());
        } else {
            onlineQuesion.setUserId(onlineQuestionDTO.getUserId());
        }
        onlineQuesion.setContent(onlineQuestionDTO.getContent());
        onlineQuesion.setTitle(onlineQuestionDTO.getTitle());

        onlineQuestionDAO.saveOrUpdate(onlineQuesion);
    }

    @Override
    public void deleteById(Integer id) {
        onlineQuestionDAO.deleteById(id);
    }

    @Override
    public PageSupport<OnlineQuestionDTO> findPagination(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public PageSupport<OnlineQuestionDTO> findPagination(int pageNo, int pageSize, Integer userId) {
        List<OnlineQuestion> onlineQuestionList = onlineQuestionDAO.findList(pageNo, pageSize, userId);
        return new PageSupport<OnlineQuestionDTO>(pageNo, onlineQuestionList.size(), pageSize, getTransformData(onlineQuestionList));
    }

    @Override
    public void addComment(Integer noteId, String comment) {
        OnlineQuestion onlineQuestion = onlineQuestionDAO.find(noteId);
        onlineQuestion.setComment(comment);
        onlineQuestionDAO.saveOrUpdate(onlineQuestion);
    }

    @Override
    public OnlineQuestionDTO findById(Integer noteId) {
        OnlineQuestion note = onlineQuestionDAO.find(noteId);
        OnlineQuestionDTO onlineQuestionDTO = new OnlineQuestionDTO();
        onlineQuestionDTO.setId(note.getId());
        onlineQuestionDTO.setTitle(note.getTitle());
        onlineQuestionDTO.setContent(note.getContent());
        onlineQuestionDTO.setComment(note.getComment());
        onlineQuestionDTO.setUserId(note.getUserId());
        return onlineQuestionDTO;
    }

    private List<OnlineQuestionDTO> getTransformData(List<OnlineQuestion> questionList) {
        return transform(questionList, new Function<OnlineQuestion, OnlineQuestionDTO>() {
            public OnlineQuestionDTO apply(OnlineQuestion note) {
                OnlineQuestionDTO onlineQuestionDTO = new OnlineQuestionDTO();
                onlineQuestionDTO.setId(note.getId());
                onlineQuestionDTO.setTitle(note.getTitle());
                onlineQuestionDTO.setContent(note.getContent());
                onlineQuestionDTO.setComment(note.getComment());
                onlineQuestionDTO.setUserId(note.getUserId());
                return onlineQuestionDTO;
            }
        });
    }
}
