package com.jredfox.splitimage;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SplitImage {
	
	public static void main(String[] args)
	{
		if(args.length >= 3)
		{
			long start = System.currentTimeMillis();
			File dirImage = new File(args[0].replace("\"", ""));
			int colums = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			File out = args.length > 3 ? new File(args[3].replace("\"", "")) : new File(dirImage.getParentFile(), getFileBaseName(dirImage) + "-Output");
			splitImage(dirImage, colums, rows, out);
			System.out.println("Done In:" + (System.currentTimeMillis() - start) + "MS");
		}
		else
		{
			System.out.println("java -jar SplitImage.jar \"Image.png\" colums rows \"(OPTIONAL) Output Directory\"");
		}
	}
	
	public static void splitImage(File dirImage, int colums, int rows, File output) 
	{
		try
		{
			BufferedImage image = ImageIO.read(dirImage);
			int imgW = image.getWidth()  / colums;
			int imgH = image.getHeight() / rows;
			String bname = getFileBaseName(dirImage);
			
			//Create Output Directory
			if(!output.exists())
				output.mkdirs();
			
			int x = 0;
			int y = 0;

			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < colums; j++)
				{
					BufferedImage img = image.getSubimage(x, y, imgW, imgH);
					ImageIO.write(img, "png", new File(output, bname + "-" + i + "-" + j + ".png"));
					x += imgW;
				}
				y += imgH;
				x = 0;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static String getFileBaseName(File f)
	{
		String name = f.getName();
		int index = name.indexOf('.');
		if(index != -1)
			name = name.substring(0, index);
		return name;
	}

}
