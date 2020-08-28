package com.quicktutorialz.nio.endpoints;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mawashi.nio.utils.Action;
import com.mawashi.nio.utils.Endpoints;
import com.quicktutorialz.nio.daos.v2.ToDoDao;
import com.quicktutorialz.nio.daos.v2.ToDoDaoImpl;
import com.quicktutorialz.nio.entities.ResponseDto;
import com.quicktutorialz.nio.entities.ToDo;
import com.quicktutorialz.nio.entities.ToDoDto;

import io.reactivex.Observable;

public class EndpointsV2 extends Endpoints {

	private ToDoDao todoDao = ToDoDaoImpl.getInstance();

	Action create = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request)
				.map(req -> (ToDoDto) getDataFromJsonBodyRequest(request, ToDoDto.class))
				.flatMap(input -> todoDao.create(input))
				.subscribe(resp -> toJsonResponse(request, response, new ResponseDto(200, resp)));
	};

	Action getAll = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request)
				.flatMap(obs -> todoDao.readAll())
				.subscribe(resp -> toJsonResponse(request, response, new ResponseDto(200, resp)));
	};

	Action getTodo = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request)
				.map(req -> getPathVariables(request).get("id"))
				.flatMap(id -> todoDao.read(id))
				.subscribe(resp -> toJsonResponse(request, response,
						new ResponseDto(200, resp.isPresent() ? resp.get() : "not found")));
	};

	Action update = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request)
				.map(req -> (ToDo)getDataFromJsonBodyRequest(req, ToDo.class))
				.flatMap(body -> todoDao.update(body))
				.subscribe(resp -> toJsonResponse(request, response, 
						new ResponseDto(200, resp.isPresent() ? resp.get() : "failed to update")));
				  
	};
	
	Action delete = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request)
			.map(req -> getPathVariables(req).get("id"))
			.flatMap(id -> todoDao.delete(id))
			.subscribe(resp -> toJsonResponse(request, response, new ResponseDto(200, resp)));
	};

	public EndpointsV2() {
		setEndpoint("api/v2/todo/create", create);
		setEndpoint("api/v2/todo/get", getAll);
		setEndpoint("api/v2/todo/get/{id}", getTodo);
		setEndpoint("api/v2/todo/update", update);
		setEndpoint("api/v2/todo/delete/{id}", delete);
	}
}
