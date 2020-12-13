package site.pistudio.backend.services;

import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.ImageRepository;
import site.pistudio.backend.dao.OrderRepository;
import site.pistudio.backend.entities.Image;
import site.pistudio.backend.entities.Order;
import site.pistudio.backend.utils.OrderStatus;

import java.util.*;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final OrderRepository orderRepository;

    public ImageService(ImageRepository imageRepository, OrderRepository orderRepository) {
        this.imageRepository = imageRepository;
        this.orderRepository = orderRepository;
    }

    public Image uploadImage(Image image) {
        Order order = orderRepository.findByOrderNumber(image.getOrderNumber());
        if (order == null) {
            throw new NoSuchElementException("Order: " + image.getOrderNumber() + " not found!");
        }
        if (!order.getOrderStatus().equals(OrderStatus.PROCESSING)) {
            throw new UnsupportedOperationException("Uploading image is not allowed at this stage!");
        }
        return imageRepository.save(image);
    }

    public List<UUID> getImagesByOrderNumber(long orderNumber, String openId) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        OrderService.checkIfValidOrder(orderNumber, openId, order);
        OrderStatus status = order.getOrderStatus();
        if (status.equals(OrderStatus.PROCESSING) || status.equals(OrderStatus.FINISHED)) {
            List<UUID> uuids = new LinkedList<>();
            List<Image> images = imageRepository.findImagesByOrderNumber(orderNumber);
            for (Image image:images) {
                uuids.add(image.getId());
            }
            return uuids;
        } else {
            throw new UnsupportedOperationException("Getting image is not allowed at this stage!");
        }

    }
}
