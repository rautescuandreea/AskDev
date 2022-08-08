package utcn.ps.assignment1demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import utcn.ps.assignment1demo.dto.QuestionDto;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.event.BaseEvent;
import utcn.ps.assignment1demo.service.QuestionService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public List<QuestionDto> findQuestions() {
        return questionService.findAll();
    }

    @GetMapping(value = "byTagId/{id}")
    public List<QuestionDto> findQuestionByTagId(@PathVariable("id") Integer id) {
        return questionService.findByTagId(id);
    }

    @GetMapping(value = "/{id}")
    public QuestionDto findQuestionById(@PathVariable("id") Integer id){
        return questionService.findById(id);
    }

    @PostMapping()
    public QuestionDto insert(@RequestBody QuestionDto questionDto){
        return questionService.insert(questionDto);
    }

    @PutMapping()
    public QuestionDto update(@RequestBody QuestionDto questionDto) {
        return questionService.update(questionDto);
    }

    @DeleteMapping(value="/{id}")
    public void remove(@PathVariable("id") Integer id){
        questionService.remove(id);
    }

    @EventListener(BaseEvent.class)
    public void handleEvent(BaseEvent event) {
        log.info("Got an event: {}.", event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
