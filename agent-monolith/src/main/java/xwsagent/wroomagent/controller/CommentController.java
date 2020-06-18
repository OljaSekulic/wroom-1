package xwsagent.wroomagent.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.CommentConverter;
import xwsagent.wroomagent.domain.dto.CommentDTO;
import xwsagent.wroomagent.service.CommentService;

@RestController
@RequestMapping(value = EndpointConfig.COMMENTS_BASE_URL)
@Log4j2
public class CommentController {

	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	@GetMapping(value = "/{ad}")
	public ResponseEntity<List<CommentDTO>> get(@PathVariable("ad") Long adId) {
		return new ResponseEntity<>(CommentConverter.fromEntityList(commentService.findByAd(adId), CommentConverter::fromEntity),
				HttpStatus.OK);
	}
	
}