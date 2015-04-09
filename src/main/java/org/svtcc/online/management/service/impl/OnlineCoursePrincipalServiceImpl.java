package org.svtcc.online.management.service.impl;

import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svtcc.online.management.dao.OnlineCoursePrincipalDAO;
import org.svtcc.online.management.dao.util.PageSupport;
import org.svtcc.online.management.domain.Department;
import org.svtcc.online.management.domain.OnlineCourse;
import org.svtcc.online.management.domain.OnlineCoursePrincipal;
import org.svtcc.online.management.dto.OnlineCoursePrincipalDTO;
import org.svtcc.online.management.service.OnlineCoursePrincipalService;

import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created by hanrenwei on 3/3/15.
 */
@Service
public class OnlineCoursePrincipalServiceImpl implements OnlineCoursePrincipalService {
    @Autowired
    private OnlineCoursePrincipalDAO onlineCoursePrincipalDAO;

    @Override
    public void saveOrUpdate(OnlineCoursePrincipalDTO onlineCoursePrincipalDTO) {
        OnlineCoursePrincipal onlineCoursePrincipal = new OnlineCoursePrincipal();
        if (null != onlineCoursePrincipalDTO.getId()) {
            onlineCoursePrincipal = onlineCoursePrincipalDAO.find(onlineCoursePrincipalDTO.getId());
        } else {
            OnlineCourse onlineCourse = new OnlineCourse();
            onlineCourse.setId(onlineCoursePrincipalDTO.getCourseId());
            onlineCoursePrincipal.setOnlineCourse(onlineCourse);
        }

        Department department = new Department();
        department.setId(onlineCoursePrincipalDTO.getDepartmentId());
        onlineCoursePrincipal.setDepartment(department);

        onlineCoursePrincipal.setName(onlineCoursePrincipalDTO.getName());
        onlineCoursePrincipal.setUserName(onlineCoursePrincipalDTO.getUserName());
        onlineCoursePrincipal.setEmployeeId(onlineCoursePrincipalDTO.getEmployeeId());
        onlineCoursePrincipal.setDescription(onlineCoursePrincipalDTO.getDescription());
        onlineCoursePrincipal.setGender(onlineCoursePrincipalDTO.getGender());
        onlineCoursePrincipal.setEducation(onlineCoursePrincipalDTO.getEducation());
        onlineCoursePrincipal.setEmail(onlineCoursePrincipalDTO.getEmail());
        onlineCoursePrincipal.setTelNo(onlineCoursePrincipalDTO.getTelNo());
        onlineCoursePrincipal.setTitle(onlineCoursePrincipalDTO.getTitle());

        onlineCoursePrincipalDAO.saveOrUpdate(onlineCoursePrincipal);
    }

    @Override
    public void deleteById(Integer id) {
        onlineCoursePrincipalDAO.deleteById(id);
    }

    @Override
    public PageSupport<OnlineCoursePrincipalDTO> findPagination(int pageNo, int pageSize) {
        List<OnlineCoursePrincipal> principalList = onlineCoursePrincipalDAO.findPagination(pageNo, pageSize).getResultData();
        return new PageSupport<OnlineCoursePrincipalDTO>(pageNo, principalList.size(), pageSize, getTransformData(principalList));
    }

    @Override
    public PageSupport<OnlineCoursePrincipalDTO> findPagination(int pageNo, int pageSize, int courseId) {
        String hql = "from OnlineCoursePrincipal p where p.onlineCourse.id= ? ";
        List<OnlineCoursePrincipal> principalList = onlineCoursePrincipalDAO.findPagination(hql, pageNo, pageSize, courseId).getResultData();

        return new PageSupport<OnlineCoursePrincipalDTO>(pageNo, principalList.size(), pageSize, getTransformData(principalList));
    }

    @Override
    public OnlineCoursePrincipalDTO findById(Integer principalId) {
        OnlineCoursePrincipal onlineCoursePrincipal = onlineCoursePrincipalDAO.find(principalId);
        OnlineCoursePrincipalDTO principalDTO = new OnlineCoursePrincipalDTO();
        principalDTO.setId(onlineCoursePrincipal.getId());
        principalDTO.setDepartmentId(onlineCoursePrincipal.getDepartment().getId());
        principalDTO.setName(onlineCoursePrincipal.getName());
        principalDTO.setUserName(onlineCoursePrincipal.getUserName());
        principalDTO.setEmployeeId(onlineCoursePrincipal.getEmployeeId());
        principalDTO.setGender(onlineCoursePrincipal.getGender());
        principalDTO.setEducation(onlineCoursePrincipal.getEducation());
        principalDTO.setTitle(onlineCoursePrincipal.getTitle());
        principalDTO.setTelNo(onlineCoursePrincipal.getTelNo());
        principalDTO.setEmail(onlineCoursePrincipal.getEmail());
        principalDTO.setDescription(onlineCoursePrincipal.getDescription());
        principalDTO.setDepartmentName(onlineCoursePrincipal.getDepartment() == null ? "" : onlineCoursePrincipal.getDepartment().getName());
        return principalDTO;
    }

    private List<OnlineCoursePrincipalDTO> getTransformData(List<OnlineCoursePrincipal> courseList) {
        return transform(courseList, new Function<OnlineCoursePrincipal, OnlineCoursePrincipalDTO>() {
            public OnlineCoursePrincipalDTO apply(OnlineCoursePrincipal principal) {
                OnlineCoursePrincipalDTO principalDTO = new OnlineCoursePrincipalDTO();
                principalDTO.setId(principal.getId());
                principalDTO.setName(principal.getName());
                principalDTO.setUserName(principal.getUserName());
                principalDTO.setEmployeeId(principal.getEmployeeId());
                principalDTO.setTitle(principal.getTitle());
                principalDTO.setDepartmentName(principal.getDepartment() == null ? "" : principal.getDepartment().getName());
                return principalDTO;
            }
        });
    }
}
