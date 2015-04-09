package org.svtcc.online.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.svtcc.online.management.domain.AttendanceSetting;
import org.svtcc.online.management.service.AttendanceSettingService;

@Controller
@RequestMapping(value = "/attend/setting")
public class AttendanceSettingController {
	
	@Autowired
	private AttendanceSettingService attendanceSettingService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveOrUpdate(AttendanceSetting attend) {
		ModelAndView view = new ModelAndView("redirect:/attend/setting");
		attendanceSettingService.saveOrUpdate(attend);
		return view;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showDetail() {
		ModelAndView view = new ModelAndView("attendance/setting");
		view.addObject("studentSetting", attendanceSettingService.findAttendanceSettingByType(3));
		view.addObject("teacherSetting", attendanceSettingService.findAttendanceSettingByType(1));
		view.addObject("classSetting", attendanceSettingService.findAttendanceSettingByType(2));
		return view;
	}
}
