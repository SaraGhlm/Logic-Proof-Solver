package logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawTreeFour extends JFrame {

    private final MyPanel panel;

    public DrawTreeFour() throws HeadlessException {
        panel = new MyPanel();
        this.add(panel);
        this.setSize(900, 700);
    }

    private class MyPanel extends JPanel {

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(900, 700);
        }

        @Override
        public void paintComponent(Graphics g) {

            int lastY;
            int lastX;

            Node root = UserInterface.c.getTree().getRoot();
            super.paintComponent(g);
            this.setBackground(Color.WHITE);
            Font font = new Font("Tahoma", Font.PLAIN, 12);

            //set root X and Y
            root.setY(650);
            root.setX(350);
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int width = (int) (font.getStringBounds(root.print(), frc).getWidth());
            root.setRightX(width + root.getX());
            lastY = root.getY();
            lastX = root.getRightX();
            g.drawString(root.print(), root.getX(), root.getY());

            //Enqueue root's children in the queue
            Queue queue = new Queue();
            if (root.getChildCount() == 2) {
                queue.enqueue(root.getLeftChild());
                queue.enqueue(root.getRightChild());
                String text = root.getLeftChild().print() + root.getRightChild().print();
                int textWidth = (int) (font.getStringBounds(text, frc).getWidth()) + 40;
                int halfRoot = ((int) (font.getStringBounds(root.print(), frc).getWidth()) / 2) + root.getX();
                g.drawLine(halfRoot - textWidth, root.getY() - 20, halfRoot + textWidth, root.getY() - 20);
                if (root.getLeftFunction()) {
                    g.drawString("L" + root.getFunction(), halfRoot + textWidth + 15, root.getY() - 20);
                } else {
                    g.drawString("R" + root.getFunction(), halfRoot + textWidth + 15, root.getY() - 20);
                }
            } else if (root.getChildCount() == 1) {
                queue.enqueue(root.getLeftChild());
                String textLeft = root.getLeftChild().print();
                int textwidth = (int) (font.getStringBounds(textLeft, frc).getWidth());
                g.drawLine(root.getX() - 10, root.getY() - 20, root.getX() + textwidth + 10, root.getY() - 20);
                if (root.getLeftFunction()) {
                    g.drawString("L" + root.getFunction(), root.getX() + textwidth + 12, root.getY() - 20);
                } else {
                    g.drawString("R" + root.getFunction(), root.getX() + textwidth + 12, root.getY() - 20);
                }
            }

            //Enqueue all the nodes int queue and draw them
            while (!queue.isEmpty()) {
                Node n = queue.dequeue();

                //set X and Y for each node
                n.setY(n.getParent().getY() - 30);
                if (n.getParent().getChildCount() == 1) {
                    n.setX(n.getParent().getX());
                } else if (n.getParent().getChildCount() == 2) {
                    if (n == n.getParent().getLeftChild()) {
                        int halfRoot = ((int) (font.getStringBounds(n.getParent().print(), frc).getWidth()) / 2) + n.getParent().getX();
                        int textWidth = (int) (font.getStringBounds(n.print(), frc).getWidth());
                        n.setX(halfRoot - textWidth);
                    } else {
                        int halfRoot = ((int) (font.getStringBounds(n.getParent().print(), frc).getWidth()) / 2) + n.getParent().getX();
                        n.setX(halfRoot + 50);
                    }
                }

                if (n.getY() == lastY) {
                    if (n.getX() <= lastX) {
                        n.setX(lastX + 10);
                    }
                }
                g.drawString(n.print(), n.getX(), n.getY());
                width = (int) (font.getStringBounds(n.print(), frc).getWidth());
                lastX = n.getX() + width;
                lastY = n.getY();
                //draw line
                if (n.getChildCount() == 1) {
                    queue.enqueue(n.getLeftChild());
                    int textwidth = (int) (font.getStringBounds(n.getLeftChild().print(), frc).getWidth());
                    g.drawLine(n.getX() - 10, n.getY() - 20, n.getX() + textwidth + 5, n.getY() - 20);
                    if (n.getLeftFunction()) {
                        g.drawString("L" + n.getFunction(), n.getX() + textwidth + 12, n.getY() - 20);
                    } else {
                        g.drawString("R" + n.getFunction(), n.getX() + textwidth + 12, n.getY() - 20);
                    }
                } else if (n.getChildCount() == 2) {
                    queue.enqueue(n.getLeftChild());
                    queue.enqueue(n.getRightChild());
                    String text = n.getLeftChild().print() + n.getRightChild().print();
                    int textWidth = (int) (font.getStringBounds(text, frc).getWidth()) + 40;
                    int halfParent = ((int) (font.getStringBounds(n.print(), frc).getWidth()) / 2) + n.getX();
                    g.drawLine(halfParent - (textWidth / 2), n.getY() - 20, halfParent + (textWidth / 2), n.getY() - 20);
                    if (n.getLeftFunction()) {
                        g.drawString("L" + n.getFunction(), halfParent + (textWidth / 2) + 15, n.getY() - 20);
                    } else {
                        g.drawString("R" + n.getFunction(), halfParent + (textWidth / 2) + 15, n.getY() - 20);
                    }
                }
            }
        }
    }
}
