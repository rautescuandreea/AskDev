package utcn.ps.assignment1demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import utcn.ps.assignment1demo.dto.AnswerDto;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.event.BaseEvent;
import utcn.ps.assignment1demo.service.AnswerService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/answers")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public List<AnswerDto> findAnswers() {
        return answerService.findAll();
    }

    @GetMapping(value = "/{id}")
    public AnswerDto findAnswerById(@PathVariable("id") Integer id){
        return answerService.findById(id);
    }

    @GetMapping(value = "byQuestionId/{id}")
    public List<AnswerDto> findAnswerByQuestionId(@PathVariable("id") Integer id) {
        return answerService.findByQuestionId(id);
    }

    @GetMapping(value = "byUserId/{id}")
    public List<AnswerDto> findAnswerByUserId(@PathVariable("id") Integer id) {
        return answerService.findByUserId(id);
    }

    @PostMapping()
    public AnswerDto insert(@RequestBody AnswerDto answerDto){
        return answerService.insert(answerDto);
    }

    @PutMapping()
    public AnswerDto update(@RequestBody AnswerDto answerDto) {
        return answerService.update(answerDto);
    }

    @DeleteMapping(value="/{id}")
    public void remove(@PathVariable("id") Integer id){
        answerService.remove(id);
    }

    @EventListener(BaseEvent.class)
    public void handleEvent(BaseEvent event) {
        log.info("Got an event: {}.", event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
