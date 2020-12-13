package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.entities.Image;
import site.pistudio.backend.exceptions.InvalidTokenException;
import site.pistudio.backend.services.ImageService;
import site.pistudio.backend.services.VerifyTokenService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("image")
public class ImageController {
    private final ImageService imageService;
    private final VerifyTokenService verifyTokenService;

    public ImageController(ImageService imageService,
                           VerifyTokenService verifyTokenService) {
        this.imageService = imageService;
        this.verifyTokenService = verifyTokenService;

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
    public List<UUID> getImages(@RequestHeader(name = "Authorization") String token,
                                @PathVariable(name = "id") long id) {
        String openId = verifyTokenService.verifyToken(token);
        return imageService.getImagesByOrderNumber(id, openId);
    }
}
