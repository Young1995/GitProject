package org.svtcc.online.management.service.impl;

import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.OnlineNoteDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.OnlineNote;
import org.svtcc.online.management.dto.OnlineNoteDTO;
import org.svtcc.online.management.service.OnlineNoteService;

import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created by hanrenwei on 4/1/15.
 */
@Service
public class OnlineNoteServiceImpl implements OnlineNoteService {
    @Autowired
    private OnlineNoteDAO onlineNoteDAO;

    @Override
    public void saveOrUpdate(OnlineNoteDTO onlineNoteDTO) {
        OnlineNote onlineNote = new OnlineNote();
        if (onlineNoteDTO.getId() != null) {
            onlineNote = onlineNoteDAO.find(onlineNoteDTO.getId());
        } else {
            onlineNote.setUserId(onlineNoteDTO.getUserId());
        }
        onlineNote.setContent(onlineNoteDTO.getContent());
        onlineNote.setTitle(onlineNoteDTO.getTitle());

        onlineNoteDAO.saveOrUpdate(onlineNote);
    }

    @Override
    public void deleteById(Integer id) {
        onlineNoteDAO.deleteById(id);
    }

    @Override
    public PageSupport<OnlineNoteDTO> findPagination(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public PageSupport<OnlineNoteDTO> findPagination(int pageNo, int pageSize, Integer userId) {
        List<OnlineNote> onlineNoteList = onlineNoteDAO.findList(pageNo, pageSize, userId);
        return new PageSupport<OnlineNoteDTO>(pageNo, onlineNoteList.size(), pageSize, getTransformData(onlineNoteList));
    }

    @Override
    public void addComment(Integer noteId, String comment) {
        OnlineNote onlineNote = onlineNoteDAO.find(noteId);
        onlineNote.setComment(comment);
        onlineNoteDAO.saveOrUpdate(onlineNote);
    }

    @Override
    public OnlineNoteDTO findById(Integer noteId) {
        OnlineNote note = onlineNoteDAO.find(noteId);
        OnlineNoteDTO onlineNoteDTO = new OnlineNoteDTO();
        onlineNoteDTO.setId(note.getId());
        onlineNoteDTO.setTitle(note.getTitle());
        onlineNoteDTO.setContent(note.getContent());
        onlineNoteDTO.setComment(note.getComment());
        onlineNoteDTO.setUserId(note.getUserId());
        return onlineNoteDTO;
    }

    private List<OnlineNoteDTO> getTransformData(List<OnlineNote> noteList) {
        return transform(noteList, new Function<OnlineNote, OnlineNoteDTO>() {
            public OnlineNoteDTO apply(OnlineNote note) {
                OnlineNoteDTO onlineNoteDTO = new OnlineNoteDTO();
                onlineNoteDTO.setId(note.getId());
                onlineNoteDTO.setTitle(note.getTitle());
                onlineNoteDTO.setContent(note.getContent());
                onlineNoteDTO.setComment(note.getComment());
                onlineNoteDTO.setUserId(note.getUserId());
                return onlineNoteDTO;
            }
        });
    }
}
