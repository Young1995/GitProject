package org.svtcc.online.management.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.svtcc.online.management.domain.CourseAttendance;
import org.svtcc.online.management.service.AttendanceSettingService;
import org.svtcc.online.management.service.CourseAttendanceService;

@Controller
@RequestMapping(value = "/attend")
public class CourseAttendanceController {

	@Autowired
	private CourseAttendanceService courseAttendanceService;
	
	@Autowired
	private AttendanceSettingService attendanceSettingService;

	@RequestMapping(value = "/teacher", method = RequestMethod.GET)
	public ModelAndView listTeacherAttend(Integer pageNo) {
		ModelAndView view = new ModelAndView("attendance/attend_teacher");
		view.addObject("attends",
				courseAttendanceService.findCourseAttendance(pageNo, 10, 1));
		view.addObject("setting", attendanceSettingService.findAttendanceSettingByType(1));
		return view;
	}
	
	@RequestMapping(value = "/class", method = RequestMethod.GET)
    public ModelAndView listClassAttend(Integer pageNo) {
        ModelAndView view = new ModelAndView("attendance/attend_class");
        view.addObject("attends",
                courseAttendanceService.findCourseAttendance(pageNo, 10, 2));
        view.addObject("setting", attendanceSettingService.findAttendanceSettingByType(2));
        return view;
    }
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)
    public ModelAndView listStudentAttend(Integer pageNo) {
        ModelAndView view = new ModelAndView("attendance/attend_student");
        view.addObject("attends",
                courseAttendanceService.findCourseAttendance(pageNo, 10, 3));
        view.addObject("setting", attendanceSettingService.findAttendanceSettingByType(3));
        return view;
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CourseAttendance queryAttendace(@PathVariable(value = "id") Integer id) {
	    return courseAttendanceService.findCourseAttendanceById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAttendance(CourseAttendance attend) {
	    Map<String, Object> result = new HashMap<String, Object>();
	    boolean checkResult = courseAttendanceService.checkAttendance(attend);
	    
	    result.put("status", checkResult);
	    
	    return result;
	}
	
	@RequestMapping(value = "/statistic", method = RequestMethod.GET)
	public ModelAndView statistic(Integer pageNo) {
		ModelAndView view = new ModelAndView("attendance/attend_statistic");
		view.addObject("attends", courseAttendanceService.findCourseAttendanceStatistic(pageNo, 10));
		return view;
	}
}
