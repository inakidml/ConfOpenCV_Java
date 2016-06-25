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
        // TODO code application logic here

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        System.out.println("Welcome to OpenCV " + Core.VERSION);

        String outputFile = "mivideo.avi";
        VideoCapture vc = new VideoCapture(0);
        vc.set(Videoio.CAP_PROP_FPS, 10);
        System.out.println(vc.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 1600));//no lo cambia
        System.out.println(Videoio.CV_CAP_PROP_FRAME_WIDTH);
        vc.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 1200);
        
        double fps = vc.get(Videoio.CAP_PROP_FPS);
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
        int numFramesRemaining = 10 * (int) fps;

        NewJFrame ventana = new NewJFrame();
        ventana.setVisible(true);

        g = ventana.getjPanel1().getGraphics();

        ventana.pack();
        ventana.setVisible(true);

        while (vc.read(frame) && numFramesRemaining > 0) {
            vw.write(frame);
            showResult(frame);
            numFramesRemaining--;
        }
        vw.release();
        vc.release();
        frame.release();

    }

    public static void showResult(Mat img) {
        //Imgproc.resize(img, img, new Size(640, 480));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", img, matOfByte);

        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            g.drawImage(bufImage, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
