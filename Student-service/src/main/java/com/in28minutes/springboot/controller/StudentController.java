package com.in28minutes.springboot.controller;

//import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.serivce.StudentService;
//import com.sun.jndi.toolkit.url.Uri;
//import com.sun.org.apache.xerces.internal.util.URI;

@RestController
public class StudentController {

	@Autowired(required = false)
	private  StudentService studentService;
	
	@GetMapping("/students/{studentID}/course")
	public List<Course> retrieveCoursesForStudent(@PathVariable String studentID){
		return studentService.retrieveCourse(studentID);
	}
	
	@GetMapping("/students/{studentID}/course/{courseID}")
	public Course retrieveDetailForCourse(@PathVariable String studentID, @PathVariable String courseID) {
		return studentService.retrieveCourse(studentID, courseID);
	}
	
	@PostMapping("/students/{studentID}/course")
	public ResponseEntity<Void> registerStudentForCourse(
			@PathVariable String studentID, @RequestBody Course newCourse){
		Course course = studentService.addCourse(studentID, newCourse);
		
		if(course == null)
			return ResponseEntity.noContent().build();
		
		java.net.URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(course.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

}
