package site.pistudio.backend.services;

import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.mysql.ImageRepository;
import site.pistudio.backend.dao.mysql.OrderRepository;
import site.pistudio.backend.entities.mysql.Image;
import site.pistudio.backend.entities.mysql.Order;
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

    public List<String> getImagesByOrderNumber(long orderNumber, String openId) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        OrderService.checkIfValidOrder(orderNumber, openId, order);
        OrderStatus status = order.getOrderStatus();
        if (status.equals(OrderStatus.PROCESSING) || status.equals(OrderStatus.FINISHED)) {
            List<String> list = new LinkedList<>();
            List<Image> images = imageRepository.findImagesByOrderNumber(orderNumber);
            for (Image image : images) {
                list.add(image.getId().toString() + image.getType());
            }
            return list;
        } else {
            throw new UnsupportedOperationException("Getting image is not allowed at this stage!");
        }
    }

    public Image deleteImageById(UUID imageId) {
        Image image = imageRepository.findImageById(imageId);

        if (image == null) {
            throw new NoSuchElementException("No image " + imageId.toString() + "!");
        }
        imageRepository.deleteImageById(imageId);
        return image;
    }

    public Image updateImage(Image image) {
        long orderNumber = imageRepository.findImageById(image.getId()).getOrderNumber();
        if (orderNumber == image.getOrderNumber()) {
            return image;
        }
        image.setOrderNumber(orderNumber);
        return imageRepository.save(image);
    }
}
