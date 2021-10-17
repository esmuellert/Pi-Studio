package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.entities.mysql.Image;
import site.pistudio.backend.exceptions.InvalidTokenException;
import site.pistudio.backend.services.AWSS3Service;
import site.pistudio.backend.services.ImageService;
import site.pistudio.backend.services.VerifyTokenService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("image")
public class ImageController {
    private final ImageService imageService;
    private final VerifyTokenService verifyTokenService;
    private final AWSS3Service s3Service;
    public ImageController(ImageService imageService,
                           VerifyTokenService verifyTokenService, AWSS3Service s3Service) {
        this.imageService = imageService;
        this.verifyTokenService = verifyTokenService;

        this.s3Service = s3Service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Image uploadImage(@RequestHeader(name = "Authorization") String token,
                             @RequestBody Image image) {
        String openId = verifyTokenService.verifyToken(token);
        if (!openId.equals("admin")) {
            throw new InvalidTokenException();
        }
        return imageService.uploadImage(image);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getImages(@RequestHeader(name = "Authorization") String token,
                                               @PathVariable(name = "id") long id) {
        String openId = verifyTokenService.verifyToken(token);
        return imageService.getImagesByOrderNumber(id, openId);
    }

    @Transactional
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public long deleteImage(@RequestHeader(name = "Authorization") String token,
                              @PathVariable(name = "id") String imageId) {
        String openId = verifyTokenService.verifyToken(token);
        if (!openId.equals("admin")) {
            throw new InvalidTokenException();
        }
        Image image = imageService.deleteImageById(UUID.fromString(imageId));
        s3Service.deleteImage(imageId + image.getType());
        return image.getOrderNumber();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public Image updateImage(@RequestHeader(name = "Authorization") String token,
                             @RequestBody Image image) {
        String openId = verifyTokenService.verifyToken(token);
        if (!openId.equals("admin")) {
            throw new InvalidTokenException();
        }
        return imageService.updateImage(image);
    }
}
