package utcn.ps.assignment1demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import utcn.ps.assignment1demo.dto.VoteAnswerDto;
import utcn.ps.assignment1demo.event.BaseEvent;
import utcn.ps.assignment1demo.service.VoteAnswerService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/voteAnswers")
@RequiredArgsConstructor
public class VoteAnswerController {
    private final VoteAnswerService voteAnswerService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public List<VoteAnswerDto> findVoteAnswers() {
        return voteAnswerService.findAll();
    }

    @GetMapping(value = "/{id}")
    public VoteAnswerDto findVoteAnswerById(@PathVariable("id") Integer id){
        return voteAnswerService.findById(id);
    }

    @PostMapping()
    public VoteAnswerDto insert(@RequestBody VoteAnswerDto voteAnswerDto){
        return voteAnswerService.insert(voteAnswerDto);
    }

    @PutMapping()
    public VoteAnswerDto update(@RequestBody VoteAnswerDto voteAnswerDto) {
        return voteAnswerService.update(voteAnswerDto);
    }

    @DeleteMapping(value="/{id}")
    public void remove(@PathVariable("id") Integer id){
        voteAnswerService.remove(id);
    }

    @EventListener(BaseEvent.class)
    public void handleEvent(BaseEvent event) {
        log.info("Got an event: {}.", event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
