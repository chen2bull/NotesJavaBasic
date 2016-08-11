package corejava.corejava1.ch14;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import javax.swing.*;

/**
 * This program demonstrates a worker thread that runs a potentially time-consuming task.
 * 
 * @version 1.1 2007-05-18
 * @author Cay Horstmann
 */
public class Ch14_13_SwingWorkerTest {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new SwingWorkerFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * This frame has a text area to show the contents of a text file, a menu to open a file and cancel
 * the opening process, and a status line to show the file loading progress.
 */
class SwingWorkerFrame extends JFrame {
    public SwingWorkerFrame() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        textArea = new JTextArea();
        add(new JScrollPane(textArea));
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        statusLine = new JLabel(" ");
        add(statusLine, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // show file chooser dialog
                int result = chooser.showOpenDialog(null);

                // if file selected, set it as icon of the label
                if (result == JFileChooser.APPROVE_OPTION) {
                    textArea.setText("");
                    openItem.setEnabled(false);
                    textReader = new TextReader(chooser.getSelectedFile());
                    textReader.execute();   //开始执行doInBackground
                    cancelItem.setEnabled(true);
                }
            }
        });

        cancelItem = new JMenuItem("Cancel");
        menu.add(cancelItem);
        cancelItem.setEnabled(false);
        cancelItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textReader.cancel(true);
            }
        });
    }

    private class ProgressData {
        public int    number;
        public String line;
    }

    // 以下工作器，将分产生类型为StringBuilder的结果，以及进度数据为ProgressData的结果
    private class TextReader extends SwingWorker<StringBuilder, ProgressData> {
        public TextReader(File file) {
            this.file = file;
        }

        // doInBackground方法在worker线程中执程；这个方法不能碰Swing组件

        @Override
        public StringBuilder doInBackground() throws IOException, InterruptedException {
            int lineNumber = 0;
            Scanner in = new Scanner(new FileInputStream(file));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                lineNumber++;
                text.append(line);
                text.append("\n");
                ProgressData data = new ProgressData();
                data.number = lineNumber;
                data.line = line;
                publish(data);
                Thread.sleep(1); // to test cancellation; no need to do this in your programs
            }
            return text;
        }

        // Worker线程中对publish的调用，将使得process方法在事件分配线程中执行
        // (几次对publish的调用，引起process的一次调用)
        @Override
        public void process(List<ProgressData> data) {
            if (isCancelled())
                return;
            StringBuilder b = new StringBuilder();
            statusLine.setText("" + data.get(data.size() - 1).number);
            for (ProgressData d : data) {
                b.append(d.line);
                b.append("\n");
            }
            textArea.append(b.toString());
        }

        @Override
        public void done() {
            //当Worker的工作完成时，done方法在事件分配线程中被调用以便完成UI的更新
            try {
                // 在done方法中调用get函数是一个十分好的主意，因为这样get方法绝对不会引起阻塞
                StringBuilder result = get();
                textArea.setText(result.toString());
                statusLine.setText("Done");
            } catch (InterruptedException ex) {
            } catch (CancellationException ex) {
                textArea.setText("");
                statusLine.setText("Cancelled");
            } catch (ExecutionException ex) {
                statusLine.setText("" + ex.getCause());
            }

            cancelItem.setEnabled(false);
            openItem.setEnabled(true);
        }

        private File          file;
        private StringBuilder text = new StringBuilder();
    };

    private JFileChooser                             chooser;
    private JTextArea                                textArea;
    private JLabel                                   statusLine;
    private JMenuItem                                openItem;
    private JMenuItem                                cancelItem;
    private SwingWorker<StringBuilder, ProgressData> textReader;

    public static final int                          DEFAULT_WIDTH  = 450;
    public static final int                          DEFAULT_HEIGHT = 350;
}
