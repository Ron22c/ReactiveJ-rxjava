package com.quicktutorialz.nio.endpoints;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mawashi.nio.annotations.Api;
import com.mawashi.nio.utils.Action;
import com.mawashi.nio.utils.Endpoints;
import com.quicktutorialz.nio.daos.v1.ToDoDao;
import com.quicktutorialz.nio.daos.v1.ToDoDaoImpl;
import com.quicktutorialz.nio.entities.ResponseDto;
import com.quicktutorialz.nio.entities.ToDo;
import com.quicktutorialz.nio.entities.ToDoDto;

public class EndPoints extends Endpoints{
	
	ToDoDao todoDao = ToDoDaoImpl.getInstance();
	
	@Api(path = "/api/v1/todo/create", method = "POST", consumes = "application/JSON", produces = "application/JSON")
	Action createTodo = (HttpServletRequest request, HttpServletResponse response) -> {
		ToDoDto toDoDto = (ToDoDto)getDataFromJsonBodyRequest(request, ToDoDto.class);
		ToDo todo = todoDao.create(toDoDto);
		toJsonResponse(request, response, new ResponseDto(200, todo)); 
		
	};
	
	Action readTodo = (HttpServletRequest request, HttpServletResponse response) -> {
		String id = getPathVariables(request).get("id");
		Optional<ToDo> todo = todoDao.read(id);
		toJsonResponse(request, response, new ResponseDto(200, todo.isPresent() ? todo.get() : "todo not found"));
	};
	
	Action readAllTodo = (HttpServletRequest request, HttpServletResponse response) -> {
		List<ToDo> todos = todoDao.readAll(); 
		toJsonResponse(request, response, todos);
	};
	
	Action updateTodo = (HttpServletRequest request, HttpServletResponse response) -> {
		ToDo todoDto = (ToDo)getDataFromJsonBodyRequest(request, ToDo.class);
		Optional<ToDo> todoOptional = todoDao.update(new ToDo(todoDto.getTitle(), todoDto.getDescription()));
		toJsonResponse(request, response, new ResponseDto(200, todoOptional.isPresent() ? todoOptional.get() : "update failed"));
	};
	
	Action deleteTodo = (HttpServletRequest request, HttpServletResponse response) -> {
		String id = getPathVariables(request).get("id");
		Boolean isDeleted = todoDao.delete(id);
		toJsonResponse(request, response, new ResponseDto(200, isDeleted?"success":"failed"));
	};
	
	public EndPoints() {
		setEndpoint("/api/v1/todo/create", createTodo);
		setEndpoint("/api/v1/todo/get/{id}", readTodo);
		setEndpoint("/api/v1/todo/getAll", readAllTodo);
		setEndpoint("/api/v1/todo/update", updateTodo);
		setEndpoint("/api/v1/todo/delete/{id}", deleteTodo);
	}
}
