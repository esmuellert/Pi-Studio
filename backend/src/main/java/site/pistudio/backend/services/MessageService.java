package site.pistudio.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.firestore.MessageRepository;
import site.pistudio.backend.dao.firestore.OrderRepository;
import site.pistudio.backend.entities.firestore.Message;
import site.pistudio.backend.entities.firestore.Order;
import site.pistudio.backend.exceptions.InvalidTokenException;
import site.pistudio.backend.utils.MessageSender;

import java.time.LocalDateTime;
import java.util.List;

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
        message.setOrderNumber(order.getOrderNumber());
        message.setTime(LocalDateTime.now());
        return messageRepository.save(message);

    }

    public Page<Message> getMessages(long orderNumber, int page, String openId) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (!openId.equals("admin") && !openId.equals(order.getOpenId())) {
            throw new InvalidTokenException();
        }
        Pageable pageable = PageRequest.of(page, 5);
        return messageRepository.findByOrderNumberOrderByTimeAsc(orderNumber, pageable);
    }

    public List<Message> getMessages(long orderNumber, String openId) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (!openId.equals("admin") && !openId.equals(order.getOpenId())) {
            throw new InvalidTokenException();
        }
        return messageRepository.findByOrderNumberOrderByTimeAsc(orderNumber);
    }
}
