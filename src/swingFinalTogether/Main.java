package swingFinalTogether;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3178887496967390131L;
	JLabel panelMain = null;
	private JLabel[] labels;
	private String imagePath;
	private int rows;
	private int cols;
	private int chunks;

	int chunkWidth;
	int chunkHeight;
	private int widthImage = 500;
	private int heightImage = 400;
	private int deviationWidth;
	private int deviationHeight;
	private String nameUser;

	private String exeptionImageMessage;

	List<MissedSpace> missedSpace = new ArrayList<>();
	String resultMessage = new String();
	Integer countSpace;

	JLabel labelOne;
	JLabel labelTwo;

	public Main(String imagePath, String name, int rows, int cols) {
		this.imagePath = imagePath;
		this.rows = rows;
		this.cols = cols;
		this.nameUser = name;
		chunks = rows * cols;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 900);
		setLocationRelativeTo(this);
		JPanel back = new JPanel();
		back.setSize(getSize());
		back.setBackground(Color.WHITE);
		back.setLayout(null);
		back.setLocation(0, 0);

		JButton b = new JButton();

		b.setText("Finish");
		b.setBounds(100, 650, 150, 50);

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				countSpace = 0;
				resultMessage = "";
				exeptionImageMessage = "";

				Component[] panel = panelMain.getComponents();

				deviationWidth = (int) (panel[0].getSize().getWidth() / 3);
				deviationHeight = (int) (panel[0].getSize().getHeight() / 3);

				int counterRowsForCols = 0;
				// лінійки
				for (int i = 0; i < rows; i++) {
					noPairRows(panel, counterRowsForCols);
					counterRowsForCols = counterRowsForCols + cols;
				}

				// стовпці
				for (int i = 0; i < cols; i++) {
					noPairColm(panel, i);
				}

				if (exeptionImageMessage.length() != 0) {
					JOptionPane.showMessageDialog(null, exeptionImageMessage);
				} else {

					for (MissedSpace ms : missedSpace) {
						resultMessage += "Name: " + ms.getName() + "Space: " + ms.getWhereMissedSpace()
								+ "Space Result: " + ms.getMissedSpace() + "\n";
						countSpace = countSpace + ms.getMissedSpace();
					}
					missedSpace.clear();
					if (resultMessage == null || resultMessage.equals("") || resultMessage.length() == 0) {
						JOptionPane.showMessageDialog(null,
								nameUser + " " + "you dont have space, Your are the Best " + "Result spaces: " + 0);
					} else {
						JOptionPane.showMessageDialog(null,
								"                Total spaces by pixel = " + countSpace + "\n" + resultMessage);
					}
				}
			}

		});

		JButton buildImage2 = new JButton();

		buildImage2.setText("Auto Build Image 2");
		buildImage2.setBounds(500, 650, 150, 50);

		buildImage2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Component[] panel = panelMain.getComponents();

				BufferedImage[] imgs = getImages();

				Component[] panelImage = panelMain.getComponents();

				boolean answer = false;
				int testIncr = 0;
				int rowsIncr = 0;
				int columnIncr = 0;

				if (imgs.length == panelImage.length) {

					for (int i = 0; i < imgs.length; i++) {
						for (int j = 0; j < panelImage.length; j++) {
							JLabel lableGetIconFor = (JLabel) panel[j];
							ImageIcon imageIconFor = (ImageIcon) lableGetIconFor.getIcon();
							BufferedImage bufferedImageCompare = convertToBufferedImage(imageIconFor.getImage());

							answer = bufferedImagesEqualAuto(bufferedImageCompare, imgs[i]);
							if (answer == true) {

								if (testIncr > 0 && testIncr > cols) {
									if (testIncr % cols == 0) {
										rowsIncr = 0;
										columnIncr = columnIncr + 1;
									} else {
									}
									;
								} else if (testIncr > 0) {
									if (testIncr % cols == 0) {
										rowsIncr = 0;
										columnIncr = columnIncr + 1;
									} else {
									}
									;
								} else {
								}
								;

								int qwe = lableGetIconFor.getWidth() * rowsIncr;
								int qwe2 = lableGetIconFor.getHeight() * columnIncr;
								lableGetIconFor.setLocation(qwe, qwe2);

								rowsIncr++;
								testIncr++;

							} else {

							}
						}
					}
				}
			}
		});

		JButton buildImage = new JButton();

		buildImage.setText("Auto Build Image");
		buildImage.setBounds(300, 650, 150, 50);

		buildImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Component[] panel = panelMain.getComponents();

				List<Integer> repeatElementList = new ArrayList<Integer>(panel.length);

				int rowsIncr = 0;
				int columnIncr = 0;
				int position = 0;
				for (int i = 0; i < chunks; i++) {

					JLabel lableGetIconFor = (JLabel) panel[i];
					ImageIcon imageIconFor = (ImageIcon) lableGetIconFor.getIcon();
					BufferedImage bufferedImageCompare = convertToBufferedImage(imageIconFor.getImage());

					position = bufferedImagesEqual(bufferedImageCompare, panel, i, repeatElementList);
					if (compareRepeatElement(repeatElementList, position)) {
						if (i > 0 && i > cols) {
							if (i % cols == 0) {
								rowsIncr = 0;
								columnIncr = columnIncr + 1;
							} else {
							}

						} else if (i > 0) {
							if (i % cols == 0) {
								rowsIncr = 0;
								columnIncr = columnIncr + 1;
							} else {
							}

						} else {
						}

						int qwe = lableGetIconFor.getWidth() * rowsIncr;
						int qwe2 = lableGetIconFor.getHeight() * columnIncr;
						repeatElementList.add(position);
						panel[position].setLocation(qwe, qwe2);

						rowsIncr++;

					} else {
					}
				}

			}
		});

		panelMain = new JLabel();
		panelMain.setOpaque(true);

		panelMain.setBackground(Color.RED);
		panelMain.setLocation(50, 50);
		panelMain.setSize(700, 500);

		initComponents();

		back.add(panelMain);
		Movement mv = new Movement(panelMain, panelMain.getComponents());

		add(b);
		add(buildImage);
		add(buildImage2);
		add(back);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Main("C:\\Users\\Admin\\Desktop\\Безымянный.png", "Ihor", 2,2);// Безымянный.png images.png
	}

	private boolean bufferedImagesEqualAuto(BufferedImage img1, BufferedImage img2) {
		if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
			for (int x = 0; x < img1.getWidth(); x++) {
				for (int y = 0; y < img1.getHeight(); y++) {
					if (img1.getRGB(x, y) != img2.getRGB(x, y))

						return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private int bufferedImagesEqual(BufferedImage img1, Component[] panel, int blockElement,
			List<Integer> repeatElementList) {

		int trueRGB = 0, answer = 0, position = 0;

		for (int i = 0; i < chunks; i++) {
			if (compareRepeatElement(repeatElementList, i)) {
				JLabel lableGetIconFor = (JLabel) panel[i];
				ImageIcon imageIconFor = (ImageIcon) lableGetIconFor.getIcon();
				BufferedImage bufferedImageCompare = convertToBufferedImage(imageIconFor.getImage());

				for (int n = 0; n < bufferedImageCompare.getHeight(); n++) {
					if (bufferedImageCompare.getRGB(img1.getWidth() - 1, n) == bufferedImageCompare.getRGB(0, n)) {
						trueRGB++;

					} else {
					}

				}
				if (answer < trueRGB || (answer == 0 && trueRGB == 0)) {
					position = i;
					answer = trueRGB;

				}
				trueRGB = 0;
			} else {
			}
			
		}

		return position;
	}

	public boolean compareRepeatElement(List<Integer> repeatElementList, int value) {
		for (Integer e : repeatElementList) {
			if (e == value) {
				return false;

			}
		}

		return true;
	}

	public static BufferedImage convertToBufferedImage(Image image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}

	public int countAllConcurrence(Integer[] a) {
		int answer = 0;
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] >= a[i + 1]) {
				answer = a[i];
			}
		}
		return answer;
	}

	public void noPairRows(Component[] panel, int colsCounter) {
		for (int i = 0; i < cols - 1; i++) {
			int j = colsCounter + i;

			if (panel[j].getLocation().y + deviationHeight >= panel[j + 1].getLocation().y
					&& panel[j].getLocation().y - deviationHeight <= panel[j + 1].getLocation().y) {

				int second = panel[j + 1].getLocation().x;
				int first = panel[j].getLocation().x + panel[j].getSize().width;

				int result = second - first;
				if (result == 0) {
				} else {
					missedSpace.add(new MissedSpace(nameUser + " ",
							"Space between " + panel[j].getName() + " and " + panel[j + 1].getName(), result));
				}

				if ((panel[j].getLocation().x + panel[j].getSize().width) <= panel[j + 1].getLocation().getX()) {

				} else {
					exeptionImageMessage = exeptionImageMessage.length() == 0
							? "Block " + panel[j].getName() + " is on another block"
							: exeptionImageMessage;
				}

			} else {
				exeptionImageMessage = exeptionImageMessage.length() == 0
						? "Block incorrectly located " + panel[j].getName()
						: exeptionImageMessage;
			}

		}
	}

	int caseJ = 0;

	public void noPairColm(Component[] panel, int rowsCounter) {
		for (int i = 0; i < rows - 1; i++) {
			int j = i == 0 ? rowsCounter : caseJ + cols;
			caseJ = j;

			if (panel[j].getLocation().x + deviationWidth >= panel[j + cols].getLocation().x
					&& panel[j].getLocation().x - deviationWidth <= panel[j + cols].getLocation().x) {

				int second = panel[j + cols].getLocation().y;
				int first = panel[j].getLocation().y + panel[j].getSize().height;

				int result = second - first;
				if (result == 0) {
				} else {
					missedSpace.add(new MissedSpace(nameUser + " ",
							"Space between " + panel[j].getName() + " and " + panel[j + cols].getName(), result));
				}

				if ((panel[j].getLocation().y + panel[j].getSize().height) <= panel[j + cols].getLocation().getY()) {
				} else {

					exeptionImageMessage = exeptionImageMessage.length() == 0
							? "Block " + panel[j].getName() + " is on another block"
							: exeptionImageMessage;

				}

			} else {
				exeptionImageMessage = exeptionImageMessage.length() == 0
						? "Block incorrectly located " + panel[j].getName()
						: exeptionImageMessage;
			}
		}
	}

	private void initComponents() {

		BufferedImage[] imgs = getImages();

		labels = new JLabel[imgs.length];

		int[] a = randomTest(createArray(imgs.length));

		for (int i = 0; i < a.length; i++) {
			labels[i] = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(imgs[i].getSource())));
			JLabel panel2 = new JLabel();
			panel2.setIcon(labels[i].getIcon());
			panel2.setSize(chunkWidth, chunkHeight);
			panel2.setName(String.valueOf(i));
			panelMain.add(panel2);
		}
	}

	private BufferedImage[] getImages() {
		File file = new File(imagePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(fis);
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

		BufferedImage thumbnail = Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, widthImage,
				heightImage);

		chunkWidth = thumbnail.getWidth() / cols;
		chunkHeight = thumbnail.getHeight() / rows;
		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {

				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, thumbnail.getType());

				Graphics2D gr = imgs[count++].createGraphics();

				gr.drawImage(thumbnail, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
						chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);

				gr.dispose();
			}
		}
		return imgs;
	}

	public int[] createArray(int size) {
		int[] b = new int[size];

		for (int i = 0; i < size; i++) {
			b[i] = i;
		}

		return b;
	}

	public static int[] randomTest(int[] a) {

		int pos1, pos2, temp;

		for (int i = 0; i < 20; i++) {
			pos1 = (int) (Math.random() * a.length);
			pos2 = (int) (Math.random() * a.length);
			temp = a[pos1];
			a[pos1] = a[pos2];
			a[pos2] = temp;
		}

		return a;

	}

	public static Component[] randomComponent(Component[] panel) {

		int pos1, pos2;
		Component temp;
		int length = panel.length;

		for (int i = 0; i < 20; i++) {
			pos1 = (int) (Math.random() * length);
			pos2 = (int) (Math.random() * length);
			temp = panel[pos1];
			panel[pos1] = panel[pos2];
			panel[pos2] = temp;
		}

		return panel;

	}
}
