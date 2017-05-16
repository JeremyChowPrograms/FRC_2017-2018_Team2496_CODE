package com.arj.vision.display;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class VideoFrame extends JFrame {
	public VideoFrame(String title) {
		setSize(800, 800);
		setTitle(title);
		setVisible(true);
		setLayout(new BorderLayout());

	}

	public void update(Mat frm) {
		byte[] data = new byte[3];
		frm.get(3, 3, data);
		//System.out.println(data[2]);
		
		MatOfByte raw = new MatOfByte();

		Imgcodecs.imencode(".jpg", frm, raw);

		byte[] veryRaw = raw.toArray();
		BufferedImage img = null;

		try {
			InputStream in = new ByteArrayInputStream(veryRaw);
			img = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		getContentPane().removeAll();
		getContentPane().add(new JLabel(new ImageIcon(img)));
		revalidate();
		repaint();
		
	}
}
