package internal.webserver.rest;

import io.swagger.annotations.Api;
import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController("/v1/api/image")
@Api(value = "Images Resource REST Endpoint")
public class ImageController {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void postImages(@RequestParam("imageOn") MultipartFile imageOn, @RequestParam("imageOff") MultipartFile imageOff, @RequestParam("nodeId") Integer nodeId, @RequestParam("unitId") Integer unitId) throws IOException {

        long start = System.currentTimeMillis();
        ImageIO.read(imageOn.getInputStream());
        long finish = System.currentTimeMillis();

        BufferedImage bImageOn = Scalr.resize(ImageIO.read(imageOn.getInputStream()), Scalr.Mode.FIT_TO_HEIGHT,200);
        BufferedImage bImageOff = Scalr.resize(ImageIO.read(imageOff.getInputStream()), Scalr.Mode.FIT_TO_HEIGHT,200);

        /*
        File outputfile = new File("/home/pablo/saved.png");
        ImageIO.write(bImageOn, "png", outputfile);

        outputfile = new File("/home/pablo/saved2.png");
        ImageIO.write(bImageOff, "png", outputfile);
         */



        System.out.println(finish - start);

    }
}
