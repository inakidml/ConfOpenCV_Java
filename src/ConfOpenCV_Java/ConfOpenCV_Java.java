/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConfOpenCV_Java;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

/**
 *
 * @author inaki
 */
public class ConfOpenCV_Java {

    private static Graphics g = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        String outputFile = "mivideo.avi";
        VideoCapture vc = new VideoCapture(0);
        System.out.println("fps= " + Videoio.CAP_PROP_FPS);

        vc.set(5, 50);
        System.out.println(vc.set(3, 1280));
        vc.set(4, 720);
        double fps = 7;
        System.out.println(fps);

        Size frameSize = new Size((int) vc.get(Videoio.CV_CAP_PROP_FRAME_WIDTH), (int) vc.get(Videoio.CV_CAP_PROP_FRAME_HEIGHT));
        System.out.println(frameSize);

        VideoWriter vw = new VideoWriter(outputFile, VideoWriter.fourcc('X', '2', '6', '4'), fps, frameSize, true);
        //System.out.println(VideoWriter.fourcc('X', '2', '6', '4'));
        //System.out.println(vw.isOpened());
        Mat frame = new Mat();

        //para cargar fotos
        //Imgcodecs.imread(outputFile)
        //Imgcodecs.imwrite(outputFile, m);
        int numFramesRemaining = 5 * (int) fps;

        NewJFrame ventana = new NewJFrame();
        ventana.setVisible(true);
        g = ventana.getjPanel1().getGraphics();
        ventana.pack();
        ventana.setVisible(true);

        while (vc.read(frame) && numFramesRemaining > 0) {
            vw.write(frame);
            mostrarImagen(frame);
            numFramesRemaining--;
        }
        
        vw.release();
        vc.release();
        ventana.dispose();

    }

    public static void mostrarImagen(Mat img) {
        //Imgproc.resize(img, img, new Size(640, 480));
        MatOfByte matOfByte = new MatOfByte(); // Matriz de Bytes para poder mostrar el frame en panel swing
        Imgcodecs.imencode(".png", img, matOfByte); //codificamos img(720*1280*CV_8UC3) a matofbyte(1160515*1*CV_8UC1)
        //System.out.println(img);
        //System.out.println(matOfByte);
        byte[] byteArray = matOfByte.toArray(); //convertimos a array de bytes
        //System.out.println(byteArray.length);
        BufferedImage bufImage = null;  //declaramos buffer para imagen
        try {
            InputStream in = new ByteArrayInputStream(byteArray);  //stream de entrada con bytearray
            bufImage = ImageIO.read(in);                            //lo convertimos a imagen en buffer
            g.drawImage(bufImage, 0, 0, null);                      //dibujamos la imagen en g(=ventana.getjPanel1().getGraphics())
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
