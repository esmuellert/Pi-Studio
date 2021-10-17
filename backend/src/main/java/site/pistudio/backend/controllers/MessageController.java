package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.entities.mysql.Message;
import site.pistudio.backend.services.MessageService;
import site.pistudio.backend.services.VerifyTokenService;

import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

    final MessageService messageService;
    final VerifyTokenService verifyTokenService;

    public MessageController(MessageService messageService,
                             VerifyTokenService verifyTokenService) {
        this.messageService = messageService;
        this.verifyTokenService = verifyTokenService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Message receiveMessage(@RequestBody Map<String, String> body,
                                  @RequestHeader(name = "Authorization") String token) {
        String openId = verifyTokenService.verifyToken(token);
        String message = body.get("message");
        long orderNumber = Long.parseLong(body.get("orderNumber"));
        return messageService.receiveMessage(message, orderNumber, openId);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Message> getMessages(@RequestParam(name = "page", required = false) Integer page,
                                         @PathVariable("id") long orderNumber,
                                     @RequestHeader(name = "Authorization") String token) {
        String openId = verifyTokenService.verifyToken(token);
        if (page == null) {
            return messageService.getMessages(orderNumber, openId);
        }
        return messageService.getMessages(orderNumber, page, openId);
    }
}
