package site.pistudio.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.MessageRepository;
import site.pistudio.backend.dao.OrderRepository;
import site.pistudio.backend.entities.Message;
import site.pistudio.backend.entities.Order;
import site.pistudio.backend.exceptions.InvalidTokenException;
import site.pistudio.backend.utils.MessageSender;

import java.time.LocalDateTime;

@Service
public class MessageService {

    final MessageRepository messageRepository;
    final OrderRepository orderRepository;

    public MessageService(MessageRepository messageRepository,
                          OrderRepository orderRepository) {
        this.messageRepository = messageRepository;
        this.orderRepository = orderRepository;
    }

    public Message receiveMessage(String messageText, long orderNumber, String openId) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (!openId.equals("admin") && !openId.equals(order.getOpenId())) {
            throw new InvalidTokenException();
        }
        Message message = new Message();
        if (openId.equals("admin")) {
            message.setMessageSender(MessageSender.ADMIN);
        } else {
            message.setMessageSender(MessageSender.USER);
        }
        message.setMessage(messageText);
        message.setOrder(order);
        message.setTime(LocalDateTime.now());
        return messageRepository.save(message);

    }

    public Page<Message> getMessages(long orderNumber, int page, String openId) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (!openId.equals("admin") && !openId.equals(order.getOpenId())) {
            throw new InvalidTokenException();
        }
        Pageable pageable = PageRequest.of(page, 5);
        return messageRepository.findByOrder_OrderNumberOrderByTime(orderNumber, pageable);
    }
}
